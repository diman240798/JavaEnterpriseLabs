package ru.sfedu.nanicky.shop.api.processor;

import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;

public class ComputerProcessor extends Processor<Computer> {

    private Reposotiries reposotiries;

    public ComputerProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Computer> getDaoForDataProvider(String dataProvider) {
        BaseDao<Computer> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = reposotiries.computerXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = reposotiries.computerCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = reposotiries.computerHibernateDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            baseDao = reposotiries.computerHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    @Override
    protected Computer getModel(String modelStr) {
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
            boolean wifiEnabled = Boolean.parseBoolean(split[8]);
            boolean bluetoothEnabled = Boolean.parseBoolean(split[9]);
            return new Computer(id, name, weight, price, Constants.CATEGORY_COMPUTER, processorName, processorPower, graphicsName, graphicsVolume, wifiEnabled, bluetoothEnabled);
        }

        return new Computer(id, name, weight, price, Constants.CATEGORY_COMPUTER, processorName, processorPower, graphicsName, graphicsVolume);
    }
}
