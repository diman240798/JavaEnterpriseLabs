package ru.sfedu.nanicky.shop.api.crudprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.app.RepositoriesUtil;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;

public class ComputerCrudCliProcessor extends CrudCliProcessor<Computer> {

    private static Logger LOG = LogManager.getLogger(Main.class);

    public ComputerCrudCliProcessor(Repositories repositories) {
        super(repositories);
    }

    /**
     * Реализация выдачи дата провадера для соответствующей модели
     * @param dataProvider - тип датапровайдера в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return BaseDao
     */
    @Override
    public BaseDao<Computer> getDaoForDataProvider(String dataProvider, Repositories repositories) {
        return RepositoriesUtil.getComputerDataProvider(dataProvider, repositories);
    }

    /**
     * Реализация выдачи распарсенной модели
     * @param modelStr - данные модели в виде строки
     * @return Computer
     */
    @Override
    protected Computer getModel(String modelStr) {
        LOG.info("Parsing string to model");
        LOG.debug("Parsing string to model {}", modelStr);
        String[] split = modelStr.split("\\s");
        long id = Long.parseLong(split[0]);
        String name = split[1];
        double weight = Double.parseDouble(split[2]);
        double price = Double.parseDouble(split[3]);
        String processorName = split[4];
        int processorPower = Integer.parseInt(split[5]);
        String graphicsName = split[6];
        int graphicsVolume = Integer.parseInt(split[7]);

        if (split.length > 8) {
            boolean wifiIntegrated = Boolean.parseBoolean(split[8]);
            boolean bluetoothIntegrated = Boolean.parseBoolean(split[9]);
            return new Computer(id, name, weight, price, Constants.CATEGORY_COMPUTER, processorName, processorPower, graphicsName, graphicsVolume, wifiIntegrated, bluetoothIntegrated);
        }

        return new Computer(id, name, weight, price, Constants.CATEGORY_COMPUTER, processorName, processorPower, graphicsName, graphicsVolume);
    }
}
