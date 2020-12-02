package ru.sfedu.nanicky.shop.api.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;

public class ComputerProcessor extends Processor<Computer> {

    private static Logger LOG = LogManager.getLogger(Main.class);

    private Reposotiries reposotiries;

    public ComputerProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Computer> getDaoForDataProvider(String dataProvider) {
        LOG.info("Getting dao for data provider");
        LOG.debug("Getting dao for data provider {}", dataProvider);
        BaseDao<Computer> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = reposotiries.computerXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = reposotiries.computerCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = reposotiries.computerJdbcDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            baseDao = reposotiries.computerHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

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
