package ru.sfedu.nanicky.shop.db.jdbc.model;


import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.jdbc.JdbcDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;

import java.sql.ResultSet;

public class FridgeJdbcDataProvider extends JdbcDao<Fridge> {

    public FridgeJdbcDataProvider() {
        super("fridge");
    }

    /**
     * Полученить скрипт создания таблицы
     */
    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "weight DOUBLE, " +
                "price DOUBLE, " +
                "category VARCHAR(255), " +
                "volume INT, " +
                "color VARCHAR(255), " +
                "power INT, " +
                "noFrost BIT";
    }


    /**
     * Получение модель из result set
     */
    @Override
    public Fridge getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double weight = resultSet.getDouble("weight");
        double price = resultSet.getDouble("price");
        String category = resultSet.getString("category");
        int volume = resultSet.getInt("volume");
        String color = resultSet.getString("color");
        int power = resultSet.getInt("power");
        boolean noFrost = resultSet.getBoolean("noFrost");
        return new Fridge(id, name, weight, price, category, volume, color, power, noFrost);
    }

    /**
     * Полученить скрипт обновления записи
     */
    @Override
    protected String getUpdateValues(Fridge fridge) {
        String baseString = "Name='%s', Weight='%s', Price='%s', Volume='%s', Color='%s', Power=%d, NoFrost=%b";

        String result = String.format(
                baseString,
                fridge.getName(),
                fridge.getWeight(),
                fridge.getPrice(),
                fridge.getVolume(),
                fridge.getColor(),
                fridge.getPower(),
                fridge.isNoFrost()
        );
        return result;
    }

    /**
     * Полученить скрипт вставки записи
     */
    @Override
    public String getValues(Fridge fridge) {
        String baseString =
                "%d, '%s', '%s', '%s', '%s'," +
                        "%d, '%s', %d, %b";

        String result = String.format(
                baseString,
                fridge.getId(),
                fridge.getName(),
                fridge.getWeight(),
                fridge.getPrice(),
                Constants.FRIDGE,
                fridge.getVolume(),
                fridge.getColor(),
                fridge.getPower(),
                fridge.isNoFrost()
        );
        return result;
    }
}

