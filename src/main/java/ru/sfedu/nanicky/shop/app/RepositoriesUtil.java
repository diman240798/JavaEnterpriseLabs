package ru.sfedu.nanicky.shop.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
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
    public static BaseDataProvider<Receipt> getReceiptsDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDataProvider<Receipt> baseDataProvider;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDataProvider = repositories.receiptXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDataProvider = repositories.receiptCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDataProvider = repositories.receiptJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDataProvider;
    }

    /**
     * Получение датапровайдера для Чека
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Receipt
     */
    public static BaseDataProvider<Category> getCategoryDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDataProvider<Category> baseDataProvider;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDataProvider = repositories.categoryXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDataProvider = repositories.categoryCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDataProvider = repositories.categoryJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDataProvider;
    }

    /**
     * Получение датапровайдера для Компьютера
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Computer
     */
    public static BaseDataProvider<Computer> getComputerDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDataProvider<Computer> baseDataProvider;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDataProvider = repositories.computerXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDataProvider = repositories.computerCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDataProvider = repositories.computerJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDataProvider;
    }

    /**
     * Получение датапровайдера для Холодильника
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Fridge
     */
    public static BaseDataProvider<Fridge> getFridgeDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDataProvider<Fridge> baseDataProvider;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDataProvider = repositories.fridgeXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDataProvider = repositories.fridgeCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDataProvider = repositories.fridgeJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDataProvider;
    }

    /**
     * Получение датапровайдера для Напитков
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Soda
     */
    public static BaseDataProvider<Soda> getSodaDataProvider(String dataProviderStr, Repositories repositories) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProviderStr);
        BaseDataProvider<Soda> baseDataProvider;
        if (dataProviderStr.equals(Constants.XML)) {
            baseDataProvider = repositories.sodaXmlDataProvider;
        } else if (dataProviderStr.equals(Constants.CSV)) {
            baseDataProvider = repositories.sodaCsvDataProvider;
        } else if (dataProviderStr.equals(Constants.JDBC)) {
            baseDataProvider = repositories.sodaJdbcDataProvider;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProviderStr);
        }
        return baseDataProvider;
    }

    /**
     * Получение датапровайдера для Сессии
     * @param dataProviderStr - датапровайдер в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return возвращает неализацию датапровайдера в соответствии с входным параметром
     * @see Repositories
     * @see Session
     */
    public static BaseDataProvider<Session> getSessionDataProvider(String dataProviderStr, Repositories repositories) {
        BaseDataProvider<Session> sessionDao;
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
    public static BaseDataProvider<Bucket> getBucketDataProvider(String dataProviderStr, Repositories repositories) {
        BaseDataProvider<Bucket> bucketDao;
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
    public static BaseDataProvider<Product> getProductDataProvider(String userCategory, String dataProviderStr, Repositories repositories) {
        BaseDataProvider productDao;
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
