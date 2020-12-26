package ru.sfedu.nanicky.shop.api.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.app.RepositoriesUtil;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
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
     *
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
     *
     * @param dataProviderStr - датапровайдер в виде строки
     * @return void
     */
    private void startSession(String dataProviderStr) {
        LOG.info("Start session");
        LOG.debug("Start session for dataProvider {}", dataProviderStr);
        BaseDataProvider<Session> sessionDataProvider = RepositoriesUtil.getSessionDataProvider(dataProviderStr, repositories);
        long id = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        Session session = new Session(id);
        if (sessionDataProvider.insert(session)) {
            LOG.info("Your session key:  {}", session.getSession());
        } else {
            LOG.error("Error creating session!");
        }
    }

    /**
     * Завершение покупок в магазине
     *
     * @param dataProviderStr - датапровайдер в виде строки
     * @param userSession     - сессия юзера
     * @return void
     */
    private void finishSession(String dataProviderStr, String userSession) {
        LOG.info("Finish session");
        LOG.debug("Finish session for dataProvider {} and userSession {}", dataProviderStr, userSession);
        LOG.info("Getting session entity");
        BaseDataProvider<Session> sessionDataProvider = RepositoriesUtil.getSessionDataProvider(dataProviderStr, repositories);
        Optional<Session> sessionOption = sessionDataProvider.getAll().stream()
                .filter(it -> it.getSession().equals(userSession))
                .findFirst();
        if (sessionOption.isPresent()) {
            LOG.info("Found session");
            BaseDataProvider<Bucket> bucketDataProvider = RepositoriesUtil.getBucketDataProvider(dataProviderStr, repositories);
            LOG.info("Getting bucket entity");
            Optional<Bucket> bucketOption = bucketDataProvider.getAll().stream()
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
                    BaseDataProvider<Product> prodDataProvider = RepositoriesUtil.getProductDataProvider(category, dataProviderStr, repositories);
                    Product product = prodDataProvider.getById(productId).get();

                    receiptTextSb.append(product.toString()).append("\n");
                    totalPrice.updateAndGet(v -> v + product.getPrice());
                });

                LOG.info("Receipt data ready. Printing.");
                long receiptId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
                BaseDataProvider<Receipt> receiptsDataProvider = RepositoriesUtil.getReceiptsDataProvider(dataProviderStr, repositories);
                Receipt receipt = new Receipt(receiptId, receiptTextSb.toString(), totalPrice.get());
                receiptsDataProvider.insert(receipt);
                LOG.info("Your receipt is: \n{}", receipt);

                bucketDataProvider.delete(bucket);
                sessionDataProvider.delete(sessionOption.get());

            } else {
                LOG.info("You have not added single product to bucket. No check will be printed.");
                sessionDataProvider.delete(sessionOption.get());
                LOG.info("Session closed");
            }
        } else {
            LOG.info("Could not find session with key: {}", userSession);
        }

    }

    /**
     * Добавление продукта в корзину
     *
     * @param args - входные аргументы программы
     * @return void
     */
    private void addProduct(String[] args) {
        String dataProviderStr = args[0];
        String userSession = args[2];
        BaseDataProvider<Session> sessionDataProvider = RepositoriesUtil.getSessionDataProvider(dataProviderStr, repositories);
        Optional<Session> sessionOption = sessionDataProvider.getAll().stream()
                .filter(it -> it.getSession().equals(userSession))
                .findFirst();
        if (sessionOption.isPresent()) {
            BaseDataProvider<Bucket> bucketDataProvider = RepositoriesUtil.getBucketDataProvider(dataProviderStr, repositories);
            LOG.info("Found session");
            long productId = Long.parseLong(args[3]);
            String userCategory = args[4];

            BaseDataProvider<Category> categoryBaseDataProvider = RepositoriesUtil.getCategoryDataProvider(dataProviderStr, repositories);
            Optional<Category> categoryOption = categoryBaseDataProvider.getAll().stream().filter(it -> it.getName().equals(userCategory)).findFirst();
            if (!categoryOption.isPresent()) {
                LOG.info("No such category: {}", userCategory);
                return;
            }
            Category category = categoryOption.get();

            BaseDataProvider productDataProvider = RepositoriesUtil.getProductDataProvider(userCategory, dataProviderStr, repositories);

            if (productDataProvider == null) {
                LOG.info("Cant get product data manager!");
                return;
            }

            Optional productOptional = productDataProvider.getById(productId);

            if (productOptional.isPresent()) {
                Object product = productOptional.get();
                LOG.info("Found product: {}", product);
                List<Bucket> buckets = bucketDataProvider.getAll();
                Optional<Bucket> bucketOption = buckets.stream()
                        .filter(it -> it.getSession().equals(userSession))
                        .findFirst();
                Bucket bucket = bucketOption.orElseGet(() -> {
                    long bucketId = buckets.stream().sorted(Comparator.comparingLong(Bucket::getId)).map(Bucket::getId).findFirst().orElse(-1L) + 1L;
                    return new Bucket(bucketId, userSession);
                });

                bucket.addProduct(productId, category);
                bucketDataProvider.delete(bucket);
                bucketDataProvider.insert(bucket);
                LOG.info("Product added succesfully");

            } else {
                LOG.info("Product with such id was not found: {}", userSession);
            }

        } else {
            LOG.info("Could not find session with key: {}", userSession);
        }
    }
}
