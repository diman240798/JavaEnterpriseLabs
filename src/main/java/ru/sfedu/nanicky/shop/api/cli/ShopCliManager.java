package ru.sfedu.nanicky.shop.api.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.app.RepositoriesUtil;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class ShopCliManager {
    private static final Logger LOG = LogManager.getLogger(ShopCliManager.class);

    private Repositories repositories;

    public ShopCliManager(Repositories repositories) {
        this.repositories = repositories;
    }


    /**
     * Обработка параметров на осуществление покупок в магазине
     * @param args - входные аргументы программы
     * @return void
     */
    public void processRequest(String[] args) {
        String dataProviderStr = args[0];
        String method = args[1];
        if (method.equals(Constants.START)) {
            startSession(dataProviderStr);
        } else if (method.equals(Constants.FINISH)) {
            finishSession(dataProviderStr, args[2]);
        } else if (method.equals(Constants.ADD_PRODUCT_TO_BUCKET)) {
            addProduct(args);
        } else {
            throw new RuntimeException("Incorrect method: " + method);
        }
    }

    /**
     * Создание сессии покупок в магазине
     * @param dataProviderStr - датапровайдер в виде строки
     * @return void
     */
    private void startSession(String dataProviderStr) {
        BaseDao<Session> sessionDao = RepositoriesUtil.getSessionDataProvider(dataProviderStr, repositories);
        long id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        Session session = new Session(id);
        if (sessionDao.insert(session)) {
            LOG.info("Your session key:  {}", session.getSession());
        } else {
            LOG.info("Error creating session!");
        }
    }

    /**
     * Завершение покупок в магазине
     * @param dataProviderStr - датапровайдер в виде строки
     * @param userSession - сессия юзера
     * @return void
     */
    private void finishSession(String dataProviderStr, String userSession) {
        LOG.info("Getting session entity");
        BaseDao<Session> sessionDao = RepositoriesUtil.getSessionDataProvider(dataProviderStr, repositories);
        Optional<Session> sessionOption = sessionDao.getAll().stream()
                .filter(it -> it.getSession().equals(userSession))
                .findFirst();
        if (sessionOption.isPresent()) {
            LOG.info("Found session");
            BaseDao<Bucket> bucketDao = RepositoriesUtil.getBucketDataProvider(dataProviderStr, repositories);
            LOG.info("Getting bucket entity");
            Optional<Bucket> bucketOption = bucketDao.getAll().stream()
                    .filter(it -> it.getSession().equals(userSession))
                    .findFirst();
            if (bucketOption.isPresent()) {
                LOG.info("Found bucket");
                Bucket bucket = bucketOption.get();
                List<String> products = bucket.getProductsList();

                LOG.info("Got bucket products. Counting receipt.");

                StringBuilder receiptTextSb = new StringBuilder();
                AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);

                products.forEach(productStr -> {
                    String[] split = productStr.split(Constants.PRODUCT_CATEGORY_SEPARATOR);
                    String category = split[0];
                    long productId = Long.parseLong(split[1]);
                    BaseDao<Product> prodDao =  RepositoriesUtil.getProductDataProvider(category, dataProviderStr, repositories);
                    Product product = prodDao.getById(productId).get();

                    receiptTextSb.append(product.toString()).append("\n");
                    totalPrice.updateAndGet(v -> v + product.getPrice());
                });

                LOG.info("Receipt data ready. Printing.");
                long receiptId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
                BaseDao<Receipt> receiptDao = RepositoriesUtil.getReceiptsDataProvider(dataProviderStr, repositories);
                Receipt receipt = new Receipt(receiptId, receiptTextSb.toString(), totalPrice.get());
                receiptDao.insert(receipt);
                LOG.info("Your receipt is: \n{}", receipt);

                bucketDao.delete(bucket);
                sessionDao.delete(sessionOption.get());

            }
            else {
                LOG.info("You have not added single product to bucket. No check will be printed.");
                sessionDao.delete(sessionOption.get());
                LOG.info("Session closed");
            }
        } else {
            LOG.info("Could not find session with key: {}", userSession);
        }

    }

    /**
     * Добавление продукта в корзину
     * @param args - входные аргументы программы
     * @return void
     */
    private void addProduct(String[] args) {
        String dataProviderStr = args[0];
        BaseDao<Bucket> bucketDao = RepositoriesUtil.getBucketDataProvider(dataProviderStr, repositories);
        String userSession = args[2];
        BaseDao<Session> sessionDao = RepositoriesUtil.getSessionDataProvider(dataProviderStr, repositories);
        Optional<Session> sessionOption = sessionDao.getAll().stream()
                .filter(it -> it.getSession().equals(userSession))
                .findFirst();
        if (sessionOption.isPresent()) {
            LOG.info("Found session");
            long productId = Long.parseLong(args[3]);
            String userCategory = args[4];

            BaseDao<Category> categoryBaseDao = RepositoriesUtil.getCategoryDataProvider(dataProviderStr, repositories);
            Optional<Category> categoryOption = categoryBaseDao.getAll().stream().filter(it -> it.getName().equals(userCategory)).findFirst();
            if (!categoryOption.isPresent()) {
                LOG.info("No such category: {}", userCategory);
                return;
            }
            Category category = categoryOption.get();

            BaseDao productDao = RepositoriesUtil.getProductDataProvider(userCategory, dataProviderStr, repositories);

            if (productDao == null) {
                LOG.info("Cant get product data manager!");
                return;
            }

            Optional productOptional = productDao.getById(productId);

            if (productOptional.isPresent()) {
                Object product = productOptional.get();
                LOG.info("Found product: {}", product);
                List<Bucket> buckets = bucketDao.getAll();
                Optional<Bucket> bucketOption = buckets.stream()
                        .filter(it -> it.getSession().equals(userSession))
                        .findFirst();
                Bucket bucket = bucketOption.orElseGet(() -> {
                    long bucketId = buckets.stream().sorted(Comparator.comparingLong(IdEntity::getId)).map(IdEntity::getId).findFirst().orElse(-1L) + 1L;
                    return new Bucket(bucketId, userSession);
                });

                bucket.addProduct(productId, category);
                bucketDao.delete(bucket);
                bucketDao.insert(bucket);
                LOG.info("Product added succesfully");

            } else {
                LOG.info("Product with such id was not found: {}", userSession);
            }

        } else {
            LOG.info("Could not find session with key: {}", userSession);
        }
    }
}
