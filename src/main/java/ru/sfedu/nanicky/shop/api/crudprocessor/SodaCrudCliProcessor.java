package ru.sfedu.nanicky.shop.api.crudprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.app.RepositoriesUtil;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Soda;

public class SodaCrudCliProcessor extends CrudCliProcessor<Soda> {

    private static Logger LOG = LogManager.getLogger(Main.class);

    public SodaCrudCliProcessor(Repositories repositories) {
        super(repositories);
    }

    /**
     * Реализация выдачи дата провадера для соответствующей модели
     * @param dataProvider - тип датапровайдера в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return BaseDataProvider
     */
    @Override
    public BaseDataProvider<Soda> getDataProvider(String dataProvider, Repositories repositories) {
        return RepositoriesUtil.getSodaDataProvider(dataProvider, repositories);
    }

    /**
     * Реализация выдачи распарсенной модели
     * @param modelStr - данные модели в виде строки
     * @return Soda
     */
    @Override
    protected Soda getModel(String modelStr) {
        LOG.info("Parsing string to model");
        LOG.debug("Parsing string to model {}", modelStr);
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
