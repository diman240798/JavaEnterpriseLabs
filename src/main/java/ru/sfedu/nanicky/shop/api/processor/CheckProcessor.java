package ru.sfedu.nanicky.shop.api.processor;

import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

public class CheckProcessor extends Processor<Receipt> {

    private Reposotiries reposotiries;

    public CheckProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Receipt> getDaoForDataProvider(String dataProvider) {
        BaseDao<Receipt> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = reposotiries.checkXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = reposotiries.checkCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = reposotiries.checkHibernateDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            baseDao = reposotiries.checkHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    @Override
    protected Receipt getModel(String modelStr) {
        throw new RuntimeException("Checks should not be insrted by user!");
    }
}
