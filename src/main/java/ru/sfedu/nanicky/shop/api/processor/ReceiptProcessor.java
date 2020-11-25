package ru.sfedu.nanicky.shop.api.processor;

import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

public class ReceiptProcessor extends Processor<Receipt> {

    private Reposotiries reposotiries;

    public ReceiptProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Receipt> getDaoForDataProvider(String dataProvider) {
        BaseDao<Receipt> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = reposotiries.receiptXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = reposotiries.receiptCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = reposotiries.receiptJdbcDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            baseDao = reposotiries.receiptHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    @Override
    protected Receipt getModel(String modelStr) {
        throw new RuntimeException("Receipts should not be inserted by user!");
    }
}
