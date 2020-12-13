package ru.sfedu.nanicky.shop.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
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
            baseDao = repositories.computerXmlDao;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.computerCsvDao;
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
            baseDao = repositories.fridgeXmlDao;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.fridgeCsvDao;
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
            baseDao = repositories.sodaXmlDao;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDao = repositories.sodaCsvDao;
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
            sessionDao = repositories.sessionXmlDao;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            sessionDao = repositories.sessionCsvDao;
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
            bucketDao = repositories.bucketXmlDao;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            bucketDao = repositories.bucketCsvDao;
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
