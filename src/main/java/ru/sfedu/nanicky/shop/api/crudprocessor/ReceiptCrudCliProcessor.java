package ru.sfedu.nanicky.shop.api.crudprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.app.RepositoriesUtil;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

public class ReceiptCrudCliProcessor extends CrudCliProcessor<Receipt> {

    private static Logger LOG = LogManager.getLogger(Main.class);

    public ReceiptCrudCliProcessor(Repositories repositories) {
        super(repositories);
    }

    /**
     * Реализация выдачи дата провадера для соответствующей модели
     * @param dataProvider - тип датапровайдера в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return BaseDao
     */
    @Override
    public BaseDataProvider<Receipt> getDaoForDataProvider(String dataProvider, Repositories repositories) {
        return RepositoriesUtil.getReceiptsDataProvider(dataProvider, repositories);
    }

    /**
     * Реализация выдачи ошибки о невозможности добавления через консоль
     * @param modelStr - данные модели в виде строки
     * @return Receipt
     */
    @Override
    protected Receipt getModel(String modelStr) {
        LOG.info("Error calling string to model");
        LOG.debug("Error calling to model {}", modelStr);
        throw new RuntimeException("Receipts should not be inserted by user!");
    }
}
