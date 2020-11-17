package ru.sfedu.nanicky.shop.api.processor;

import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;

public class FridgeProcessor extends Processor<Fridge> {
    private Reposotiries reposotiries;

    public FridgeProcessor(Reposotiries reposotiries) {
        this.reposotiries = reposotiries;
    }

    @Override
    public BaseDao<Fridge> processDataProvider(String dataProvider) {
        BaseDao<Fridge> baseDao;
        if (dataProvider.equals(Constants.XML)) {
            baseDao = reposotiries.fridgeXmlDao;
        } else if (dataProvider.equals(Constants.CSV)) {
            baseDao = reposotiries.fridgeCsvDao;
        } else if (dataProvider.equals(Constants.JDBC)) {
            baseDao = reposotiries.fridgeHibernateDao;
        } else if (dataProvider.equals(Constants.HIBERNATE)) {
            baseDao = reposotiries.fridgeHibernateDao;
        } else {
            throw new RuntimeException("Cant parse data provider: " + dataProvider);
        }
        return baseDao;
    }

    @Override
    protected Fridge getModel(String modelStr) {
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
            return new Fridge(id, name, weight, price, volume, color, power, noFrost);
        }

        return new Fridge(id, name, weight, price, volume, color, power);
    }
}
