package ru.sfedu.nanicky.shop.api.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sfedu.nanicky.shop.api.initializer.Initializer;
import ru.sfedu.nanicky.shop.api.processor.*;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class CliManager {


    private static final Logger LOG = LoggerFactory.getLogger(CliManager.class);


    private final Reposotiries reposotiries;

    public CliManager(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }


    public void workoutRequest(String[] args) {
        String dataProvider = args[0];

        if (args[0].equals(Constants.INIT_ALL)) {
            Initializer.initAll(reposotiries);
        } else if (args[1].equals(Constants.INIT)) {
            Initializer.initFor(dataProvider, reposotiries);
        } else {
            workoutModel(dataProvider, args);
        }

    }

    private void workoutModel(String dataProvider, String[] args) {
        String model = args[1];

        if (model.equals(Constants.CATEGORY)) {
            Processor processor = new CategoryProcessor(reposotiries);
            processor.processBaseApi(args, Constants.ALL_ACTIONS);
        } else if (model.equals(Constants.FRIDGE)) {
            Processor processor = new FridgeProcessor(reposotiries);
            processor.processBaseApi(args, Constants.ALL_ACTIONS);
        } else if (model.equals(Constants.COMPUTER)) {
            Processor processor = new ComputerProcessor(reposotiries);
            processor.processBaseApi(args, Constants.ALL_ACTIONS);
        } else if (model.equals(Constants.SODA)) {
            Processor processor = new SodaProcessor(reposotiries);
            processor.processBaseApi(args, Constants.ALL_ACTIONS);
        } else if (model.equals(Constants.RECEIPT)) {
            Processor processor = new ReceiptProcessor(reposotiries);
            processor.processBaseApi(args, Constants.GET_DELETE_ACTIONS);
        } else if (model.equals(Constants.START)) {
            startSession(dataProvider);
        } else if (model.equals(Constants.FINISH)) {
            finishSession(dataProvider, args[2]);
        } else if (model.equals(Constants.ADD_PRODUCT_TO_BUCKET)) {
            addProduct(dataProvider, args);

        } else {
            throw new RuntimeException("Incorrect model: " + model);
        }
    }

    private void addProduct(String dataProvider, String[] args) {
        BaseDao<Bucket> bucketDao = getBucketDao(dataProvider);
        String userSession = args[2];
        BaseDao<Session> sessionDao = getSessionDao(dataProvider);
        Optional<Session> sessionOption = sessionDao.getAll().stream()
                .filter(it -> it.getSession().equals(userSession))
                .findFirst();
        if (sessionOption.isPresent()) {
            LOG.info("Found session");
            long productId = Long.parseLong(args[3]);
            String userCategory = args[4];

            BaseDao<Category> categoryBaseDao = new CategoryProcessor(reposotiries).getDaoForDataProvider(dataProvider);
            Optional<Category> categoryOption = categoryBaseDao.getAll().stream().filter(it -> it.getName().equals(userCategory)).findFirst();
            if (!categoryOption.isPresent()) {
                LOG.info("No such category: {}", userCategory);
                return;
            }
            Category category = categoryOption.get();

            BaseDao productDao = getProductDao(dataProvider, userCategory);

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
                bucketDao.upsert(bucket);
                LOG.info("Product added succesfully");

            } else {
                LOG.info("Product with such id was not found: {}", userSession);
            }

        } else {
            LOG.info("Could not find session with key: {}", userSession);
        }
    }

    private BaseDao getProductDao(String dataProvider, String userCategory) {
        BaseDao productDao = null;
        if (userCategory.equals(Constants.CATEGORY_SODA)) {
            productDao = new SodaProcessor(reposotiries).getDaoForDataProvider(dataProvider);
        } else if (userCategory.equals(Constants.CATEGORY_FRIDGE)) {
            productDao = new FridgeProcessor(reposotiries).getDaoForDataProvider(dataProvider);
        } else if (userCategory.equals(Constants.COMPUTER)) {
            productDao = new ComputerProcessor(reposotiries).getDaoForDataProvider(dataProvider);
        } else {
            LOG.info("Unknown category: {}", userCategory);
        }
        return productDao;
    }

    private void finishSession(String dataProvider, String userSession) {
        LOG.info("Getting session entity");
        BaseDao<Session> sessionDao = getSessionDao(dataProvider);
        Optional<Session> sessionOption = sessionDao.getAll().stream()
                .filter(it -> it.getSession().equals(userSession))
                .findFirst();
        if (sessionOption.isPresent()) {
            LOG.info("Found session");
            BaseDao<Bucket> bucketDao = getBucketDao(dataProvider);
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
                    BaseDao<Product> prodDao =  getProductDaoByCategory(category, dataProvider, reposotiries);
                    Product product = prodDao.getById(productId).get();

                    receiptTextSb.append(product.toString()).append("\n");
                    totalPrice.updateAndGet(v -> v + product.getPrice());
                });

                LOG.info("Receipt data ready. Printing.");
                long receiptId = new Random().nextLong();
                BaseDao<Receipt> receiptDao = getReceiptDao(dataProvider, reposotiries);
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

    private BaseDao<Receipt> getReceiptDao(String dataProvider, Reposotiries reposotiries) {
        BaseDao<Receipt> receiptBaseDao = new ReceiptProcessor(reposotiries).getDaoForDataProvider(dataProvider);
        return receiptBaseDao;
    }

    private BaseDao<Product> getProductDaoByCategory(String category, String dataProvider, Reposotiries reposotiries) {
        if (category.equals(Constants.FRIDGE)) {
            BaseDao dao = new FridgeProcessor(reposotiries).getDaoForDataProvider(dataProvider);
            return dao;
        } else if (category.equals(Constants.SODA)) {
            BaseDao dao = new SodaProcessor(reposotiries).getDaoForDataProvider(dataProvider);
            return dao;
        } else if (category.equals(Constants.COMPUTER)) {
            BaseDao dao = new ComputerProcessor(reposotiries).getDaoForDataProvider(dataProvider);
            return dao;
        } else {
            throw new RuntimeException("Unknown category: " + category);
        }
    }

    private BaseDao<Bucket> getBucketDao(String dataProvider) {
        BaseDao<Bucket> bucketDao;
        if (dataProvider.equals(Constants.XML)) {
            bucketDao = reposotiries.bucketXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            bucketDao = reposotiries.bucketCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            bucketDao = reposotiries.bucketHibernateDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            bucketDao = reposotiries.bucketHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return bucketDao;
    }

    private void startSession(String dataProvider) {
        BaseDao<Session> sessionDao = getSessionDao(dataProvider);
        long id = new Random().nextLong();
        Session session = new Session(id);
        if (sessionDao.insert(session)) {
            LOG.info("Your session key:  {}", session.getSession());
        } else {
            LOG.info("Error creating session!");
        }
    }

    private BaseDao<Session> getSessionDao(String dataProvider) {
        BaseDao<Session> sessionDao;
        if (dataProvider.equals(Constants.XML)) {
            sessionDao = reposotiries.sessionXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            sessionDao = reposotiries.sessionCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            sessionDao = reposotiries.sessionHibernateDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            sessionDao = reposotiries.sessionHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return sessionDao;
    }

}
