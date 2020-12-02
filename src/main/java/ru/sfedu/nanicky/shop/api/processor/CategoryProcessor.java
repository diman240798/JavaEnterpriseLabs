package ru.sfedu.nanicky.shop.api.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

public class CategoryProcessor extends Processor<Category> {

    private static Logger LOG = LogManager.getLogger(Main.class);

    private final Reposotiries reposotiries;

    public CategoryProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Category> getDaoForDataProvider(String dataProvider) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProvider);
        BaseDao<Category> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = reposotiries.categoryXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = reposotiries.categoryCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = reposotiries.categoryJdbcDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            baseDao = reposotiries.categoryHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    @Override
    protected Category getModel(String modelStr) {
        LOG.info("Parsing string to model");
        LOG.debug("Parsing string to model {}", modelStr);
        String[] split = modelStr.split("\\s");
        long id = Long.parseLong(split[0]);
        String name = split[1];
        return new Category(id, name);
    }
}
