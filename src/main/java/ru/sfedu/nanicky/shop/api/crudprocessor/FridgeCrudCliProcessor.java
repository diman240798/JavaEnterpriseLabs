package ru.sfedu.nanicky.shop.api.crudprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.app.RepositoriesUtil;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;

public class FridgeCrudCliProcessor extends CrudCliProcessor<Fridge> {

    private static Logger LOG = LogManager.getLogger(Main.class);

    public FridgeCrudCliProcessor(Repositories repositories) {
        super(repositories);
    }

    /**
     * Реализация выдачи дата провадера для соответствующей модели
     * @param dataProvider - тип датапровайдера в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return BaseDao
     */
    @Override
    public BaseDao<Fridge> getDaoForDataProvider(String dataProvider, Repositories repositories) {
        return RepositoriesUtil.getFridgeDataProvider(dataProvider, repositories);
    }


    /**
     * Реализация выдачи распарсенной модели
     * @param modelStr - данные модели в виде строки
     * @return Fridge
     */
    @Override
    protected Fridge getModel(String modelStr) {
        LOG.info("Parsing string to model");
        LOG.debug("Parsing string to model {}", modelStr);
        String[] split = modelStr.split("\\s");
        long id = Long.parseLong(split[0]);
        String name = split[1];
        double weight = Double.parseDouble(split[2]);
        double price = Double.parseDouble(split[3]);
        int volume = Integer.parseInt(split[4]);
        String color = split[5];
        int power = Integer.parseInt(split[6]);

        if (split.length > 7) {
            boolean noFrost = Boolean.parseBoolean(split[7]);
            return new Fridge(id, name, weight, price, Constants.CATEGORY_FRIDGE, volume, color, power, noFrost);
        }

        return new Fridge(id, name, weight, price, Constants.CATEGORY_FRIDGE, volume, color, power);
    }
}
