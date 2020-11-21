package ru.sfedu.nanicky.shop.api.processor;

import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

public class CategoryProcessor extends Processor<Category> {

    private final Reposotiries reposotiries;

    public CategoryProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Category> getDaoForDataProvider(String dataProvider) {
        BaseDao<Category> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = reposotiries.categoryXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = reposotiries.categoryCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = reposotiries.categoryHibernateDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            baseDao = reposotiries.categoryHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    @Override
    protected Category getModel(String modelStr) {
        String[] split = modelStr.split("\\s");
        long id = Long.parseLong(split[0]);
        String name = split[1];
        return new Category(id, name);
    }
}
