package ru.sfedu.nanicky.shop.db.jdbc.model;


import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.jdbc.JdbcDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Soda;

import java.sql.ResultSet;

public class SodaJdbcDao extends JdbcDao<Soda> {

    public SodaJdbcDao() {
        super("soda");
    }

    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL, " +
                "name VARCHAR(255), " +
                "weight DOUBLE, " +
                "price DOUBLE, " +
                "category VARCHAR(255), " +
                "flavour VARCHAR(255)," +
                "sparkled BIT";
    }

    @Override
    public Soda getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double weight = resultSet.getDouble("weight");
        double price = resultSet.getDouble("price");
        String category = resultSet.getString("category");
        String flavour = resultSet.getString("flavour");
        boolean sparkled = resultSet.getBoolean("sparkled");
        return new Soda(id, name, weight, price, category, flavour, sparkled);
    }

    @Override
    protected String getUpdateValues(Soda soda) {
        String baseString = "Name=%s', Weight=%s', Price=%s', Flavour=%s', Sparkled=%b";

        String result = String.format(
                baseString,
                soda.getName(),
                soda.getWeight(),
                soda.getPrice(),
                soda.getFlavour(),
                soda.isSparkled()
        );
        return result;
    }

    @Override
    public String getValues(Soda soda) {
        String baseString = "%d, '%s', '%s', '%s', '%s'," +
                        "'%s', %b";

        String result = String.format(
                baseString,
                soda.getId(),
                soda.getName(),
                soda.getWeight(),
                soda.getPrice(),
                Constants.COMPUTER,
                soda.getFlavour(),
                soda.isSparkled()
        );
        return result;
    }
}
