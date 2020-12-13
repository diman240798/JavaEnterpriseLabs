package ru.sfedu.nanicky.shop.api.crudprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.app.RepositoriesUtil;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

public class CategoryCrudCliProcessor extends CrudCliProcessor<Category> {

    private static Logger LOG = LogManager.getLogger(Main.class);


    public CategoryCrudCliProcessor(Repositories repositories) {
        super(repositories);
    }

    /**
     * Реализация выдачи дата провадера для соответствующей модели
     * @param dataProvider - тип датапровайдера в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return BaseDao
     */
    @Override
    public BaseDao<Category> getDaoForDataProvider(String dataProvider, Repositories repositories) {
        return RepositoriesUtil.getCategoryDataProvider(dataProvider, repositories);
    }

    /**
     * Реализация выдачи распарсенной модели
     * @param modelStr - данные модели в виде строки
     * @return Category
     */
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
