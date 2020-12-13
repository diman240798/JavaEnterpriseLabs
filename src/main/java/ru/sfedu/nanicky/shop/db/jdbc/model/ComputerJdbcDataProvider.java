package ru.sfedu.nanicky.shop.db.jdbc.model;

import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.jdbc.JdbcDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;

import java.sql.ResultSet;

public class ComputerJdbcDataProvider extends JdbcDao<Computer> {

    public ComputerJdbcDataProvider() {
        super("computer");
    }

    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "weight DOUBLE, " +
                "price DOUBLE, " +
                "category VARCHAR(255), " +
                "processorName VARCHAR(255), " +
                "processorPower INTEGER, " +
                "graphicsName VARCHAR(255), " +
                "graphicsVolume INTEGER, " +
                "integratedWifi BIT, " +
                "integratedBluetooth BIT";
    }

    @Override
    public Computer getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double weight = resultSet.getDouble("weight");
        double price = resultSet.getDouble("price");
        String category = resultSet.getString("category");
        String processorName = resultSet.getString("processorName");
        int processorPower = resultSet.getInt("processorPower");
        String graphicsName = resultSet.getString("graphicsName");
        int graphicsVolume = resultSet.getInt("graphicsVolume");
        boolean integratedWifi = resultSet.getBoolean("integratedWifi");
        boolean integratedBluetooth = resultSet.getBoolean("integratedBluetooth");
        Computer computer = new Computer(
                id,
                name,
                weight,
                price,
                category,
                processorName,
                processorPower,
                graphicsName,
                graphicsVolume,
                integratedWifi,
                integratedBluetooth
        );
        return computer;
    }

    @Override
    protected String getUpdateValues(Computer computer) {
        String baseString =
                "NAME='%s', WEIGHT='%s', " +
                        "PRICE='%s', ProcessorName='%s', " +
                        "ProcessorPower=%d, GraphicsName='%s', GraphicsVolume=%d, " +
                        "IntegratedWifi=%b, IntegratedBluetooth=%b";
        String result = String.format(
                baseString,
                computer.getName(),
                computer.getWeight(),
                computer.getPrice(),
                computer.getProcessorName(),
                computer.getProcessorPower(),
                computer.getGraphicsName(),
                computer.getGraphicsVolume(),
                computer.isIntegratedWifi(),
                computer.isIntegratedBluetooth()
        );
        return result;
    }

    @Override
    public String getValues(Computer computer) {
        String baseString =
                        "%d, '%s', '%s', " +
                        "'%s', '%s', '%s', " +
                        "%d, '%s', %d, " +
                        "%b, %b";
        String result = String.format(
                baseString,
                computer.getId(),
                computer.getName(),
                computer.getWeight(),
                computer.getPrice(),
                Constants.COMPUTER,
                computer.getProcessorName(),
                computer.getProcessorPower(),
                computer.getGraphicsName(),
                computer.getGraphicsVolume(),
                computer.isIntegratedWifi(),
                computer.isIntegratedBluetooth()
        );
        return result;
    }
}
