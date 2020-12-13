package ru.sfedu.nanicky.shop.db.jdbc.model;

import ru.sfedu.nanicky.shop.db.jdbc.JdbcDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.sql.ResultSet;

public class ReceiptJdbcDataProvider extends JdbcDataProvider<Receipt> {

    public ReceiptJdbcDataProvider() {
        super("receipt");
    }

    /**
     * Полученить скрипт создания таблицы
     */
    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL PRIMARY KEY, " +
                "productsAndPrices VARCHAR(255)," +
                "totalPrice DOUBLE";
    }


    /**
     * Получение модель из result set
     */
    @Override
    public Receipt getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String productsAndPrices = resultSet.getString("productsAndPrices");
        double totalPrice = resultSet.getDouble("totalPrice");
        return new Receipt(id, productsAndPrices, totalPrice);
    }


    /**
     * Полученить скрипт обновления записи
     */
    @Override
    protected String getUpdateValues(Receipt receipt) {
        return String.format("productsAndPrices='%s', totalPrice='%s'", receipt.getProductsAndPrices(), receipt.getTotalPrice());
    }

    /**
     * Полученить скрипт вставки записи
     */
    @Override
    public String getValues(Receipt receipt) {
        return String.format("%d, '%s', '%s'", receipt.getId(), receipt.getProductsAndPrices(), receipt.getTotalPrice());
    }
}
