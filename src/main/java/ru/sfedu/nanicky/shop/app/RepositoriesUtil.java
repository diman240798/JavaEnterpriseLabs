package ru.sfedu.nanicky.shop.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.*;

public class RepositoriesUtil {

    private static Logger LOG = LogManager.getLogger(RepositoriesUtil.class);

    public static BaseDao<Receipt> getReceiptsDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDao<Receipt> baseDao;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDao = repositories.receiptXmlDao;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.receiptCsvDao;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDao = repositories.receiptJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDao;
    }

    public static BaseDao<Category> getCategoryDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDao<Category> baseDao;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDao = repositories.categoryXmlDao;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.categoryCsvDao;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDao = repositories.categoryJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDao;
    }

    public static BaseDao<Computer> getComputerDataProvider(String dataProvider, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProvider);
        BaseDao<Computer> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = repositories.computerXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = repositories.computerCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = repositories.computerJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    public static BaseDao<Fridge> getFridgeDataProvider(String dataProvider, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProvider);
        BaseDao<Fridge> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = repositories.fridgeXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = repositories.fridgeCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = repositories.fridgeJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    public static BaseDao<Soda> getSodaDataProvider(String dataProvider, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProvider);
        BaseDao<Soda> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = repositories.sodaXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = repositories.sodaCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = repositories.sodaJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    public static BaseDao<Session> getSessionDataProvider(String dataProvider, Repositories repositories) {
        BaseDao<Session> sessionDao;
        if (dataProvider.equals(Constants.XML)) {
            sessionDao = repositories.sessionXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            sessionDao = repositories.sessionCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            sessionDao = repositories.sessionJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return sessionDao;
    }

    /**
     * Получение датапровайдера для Корзины
     * @param dataProvider - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     */
    public static BaseDao<Bucket> getBucketDataProvider(String dataProvider, Repositories repositories) {
        BaseDao<Bucket> bucketDao;
        if (dataProvider.equals(Constants.XML)) {
            bucketDao = repositories.bucketXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            bucketDao = repositories.bucketCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            bucketDao = repositories.bucketJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return bucketDao;
    }

    public static BaseDao<Product> getProductDataProvider(String dataProvider, String userCategory, Repositories repositories) {
        BaseDao productDao = null;
        if (userCategory.equals(Constants.CATEGORY_SODA)) {
            productDao = RepositoriesUtil.getSodaDataProvider(dataProvider, repositories);
        } else if (userCategory.equals(Constants.CATEGORY_FRIDGE)) {
            productDao = RepositoriesUtil.getFridgeDataProvider(dataProvider, repositories);
        } else if (userCategory.equals(Constants.COMPUTER)) {
            productDao = RepositoriesUtil.getComputerDataProvider(dataProvider, repositories);
        } else {
            LOG.info("Unknown category: {}", userCategory);
        }
        return productDao;
    }
}
