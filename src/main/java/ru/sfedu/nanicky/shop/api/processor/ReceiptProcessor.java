package ru.sfedu.nanicky.shop.api.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

public class ReceiptProcessor extends Processor<Receipt> {

    private static Logger LOG = LogManager.getLogger(Main.class);

    private Reposotiries reposotiries;

    public ReceiptProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Receipt> getDaoForDataProvider(String dataProvider) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProvider);
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
        LOG.info("Error calling string to model");
        LOG.debug("Error calling to model {}", modelStr);
        throw new RuntimeException("Receipts should not be inserted by user!");
    }
}
