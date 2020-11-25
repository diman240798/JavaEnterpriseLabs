package ru.sfedu.nanicky.shop.api.processor;

import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Soda;

public class SodaProcessor extends Processor<Soda> {

    private Reposotiries reposotiries;

    public SodaProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Soda> getDaoForDataProvider(String dataProvider) {
        BaseDao<Soda> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = reposotiries.sodaXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = reposotiries.sodaCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = reposotiries.sodaJdbcDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            baseDao = reposotiries.sodaHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    @Override
    protected Soda getModel(String modelStr) {
        String[] split = modelStr.split("\\s");
        long id = Long.parseLong(split[0]);
        String name = split[1];
        double weight = Double.parseDouble(split[2]);
        double price = Double.parseDouble(split[3]);
        String flavour = split[4];
        if (split.length > 5) {
            boolean sparkled = Boolean.parseBoolean(split[5]);
            return new Soda(id, name, weight, price, Constants.CATEGORY_SODA, flavour, sparkled);
        } else {
            return new Soda(id, name, weight, price, Constants.CATEGORY_SODA, flavour);
        }
    }
}
