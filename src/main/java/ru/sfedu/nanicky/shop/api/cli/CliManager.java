package ru.sfedu.nanicky.shop.api.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sfedu.nanicky.shop.api.initializer.Initializer;
import ru.sfedu.nanicky.shop.api.processor.*;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.*;

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
        }


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
        } else if (model.equals(Constants.CHECK)) {
            Processor processor = new CheckProcessor(reposotiries);
            processor.processBaseApi(args, Constants.GET_DELETE_ACTIONS);
        } else if (model.equals(Constants.START)) {
            startSession(dataProvider);
        } else if (model.equals(Constants.FINISH)) {
            finishSession(dataProvider, args[3]);
        } else if (model.equals(Constants.BUCKET)) {

        } else {
            throw new RuntimeException("Incorrect model: " + model);
        }
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
                List<String> products = bucket.getProducts();

                LOG.info("Got bucket products. Counting check.");

                StringBuilder checkTextSb = new StringBuilder();
                AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);

                products.forEach(productStr -> {
                    String[] split = productStr.split(Bucket.PRODUCTS_SEPARATOR);
                    String category = split[0];
                    long productId = Long.parseLong(split[1]);
                    BaseDao<Product> prodDao =  getProductDaoByCategory(category, dataProvider, reposotiries);
                    Product product = prodDao.getById(productId).get();

                    checkTextSb.append(product.toString()).append("\n");
                    totalPrice.updateAndGet(v -> v + product.getPrice());
                });

                LOG.info("Check data ready. Printing.");
                long checkId = new Random().nextLong();
                BaseDao<Receipt> receiptDao = getReceiptDao(dataProvider, reposotiries);
                Receipt receipt = new Receipt(checkId, checkTextSb.toString(), totalPrice.get());
                receiptDao.insert(receipt);
                LOG.info("Your receipt is: \n{}", receipt);
            }
        } else {
            LOG.info("Could not find session with key: {}", userSession);
        }

    }

    private BaseDao<Receipt> getReceiptDao(String dataProvider, Reposotiries reposotiries) {
        BaseDao<Receipt> receiptBaseDao = new CheckProcessor(reposotiries).processDataProvider(dataProvider);
        return receiptBaseDao;
    }

    private BaseDao<Product> getProductDaoByCategory(String category, String dataProvider, Reposotiries reposotiries) {
        if (category.equals(Constants.FRIDGE)) {
            BaseDao dao = new FridgeProcessor(reposotiries).processDataProvider(dataProvider);
            return dao;
        } else if (category.equals(Constants.SODA)) {
            BaseDao dao = new SodaProcessor(reposotiries).processDataProvider(dataProvider);
            return dao;
        } else if (category.equals(Constants.COMPUTER)) {
            BaseDao dao = new ComputerProcessor(reposotiries).processDataProvider(dataProvider);
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
        sessionDao.insert(session);
        LOG.info("Your session key:  {}", session.getSession());
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
