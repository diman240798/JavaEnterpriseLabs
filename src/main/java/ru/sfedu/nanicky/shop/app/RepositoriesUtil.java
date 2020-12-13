package ru.sfedu.nanicky.shop.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.*;

public class RepositoriesUtil {

    private static Logger LOG = LogManager.getLogger(RepositoriesUtil.class);


    /**
     * Получение датапровайдера для Чека
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Receipt
     */
    public static BaseDao<Receipt> getReceiptsDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDao<Receipt> baseDao;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDao = repositories.receiptXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.receiptCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDao = repositories.receiptJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDao;
    }

    /**
     * Получение датапровайдера для Чека
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Receipt
     */
    public static BaseDao<Category> getCategoryDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDao<Category> baseDao;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDao = repositories.categoryXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.categoryCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDao = repositories.categoryJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDao;
    }

    /**
     * Получение датапровайдера для Компьютера
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Computer
     */
    public static BaseDao<Computer> getComputerDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDao<Computer> baseDao;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDao = repositories.computerXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.computerCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDao = repositories.computerJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDao;
    }

    /**
     * Получение датапровайдера для Холодильника
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Fridge
     */
    public static BaseDao<Fridge> getFridgeDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDao<Fridge> baseDao;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDao = repositories.fridgeXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.fridgeCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDao = repositories.fridgeJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDao;
    }

    /**
     * Получение датапровайдера для Напитков
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Soda
     */
    public static BaseDao<Soda> getSodaDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDao<Soda> baseDao;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDao = repositories.sodaXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.sodaCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDao = repositories.sodaJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDao;
    }

    /**
     * Получение датапровайдера для Сессии
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Session
     */
    public static BaseDao<Session> getSessionDataProvider(String dataProviderStr, Repositories repositories) {
        BaseDao<Session> sessionDao;
        if (dataProviderStr.equals(Constants.XML)) {
            sessionDao = repositories.sessionXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            sessionDao = repositories.sessionCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            sessionDao = repositories.sessionJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return sessionDao;
    }

    /**
     * Получение датапровайдера для Корзины
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Bucket
     */
    public static BaseDao<Bucket> getBucketDataProvider(String dataProviderStr, Repositories repositories) {
        BaseDao<Bucket> bucketDao;
        if (dataProviderStr.equals(Constants.XML)) {
            bucketDao = repositories.bucketXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            bucketDao = repositories.bucketCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            bucketDao = repositories.bucketJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return bucketDao;
    }

    /**
     * Получение датапровайдера для категории продуктов
     * @param userCategory - категория продуктов в виде строки
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Soda
     */
    public static BaseDao<Product> getProductDataProvider(String userCategory, String dataProviderStr, Repositories repositories) {
        BaseDao productDao;
        if (userCategory.equals(Constants.CATEGORY_SODA)) {
            productDao = RepositoriesUtil.getSodaDataProvider(dataProviderStr, repositories);
        } else if (userCategory.equals(Constants.CATEGORY_FRIDGE)) {
            productDao = RepositoriesUtil.getFridgeDataProvider(dataProviderStr, repositories);
        } else if (userCategory.equals(Constants.COMPUTER)) {
            productDao = RepositoriesUtil.getComputerDataProvider(dataProviderStr, repositories);
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return productDao;
    }
}
