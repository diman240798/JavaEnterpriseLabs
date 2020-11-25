package ru.sfedu.nanicky.shop.db.jdbc.model;

import ru.sfedu.nanicky.shop.db.jdbc.JdbcDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.sql.ResultSet;

public class ReceiptJdbcDao extends JdbcDao<Receipt> {

    public ReceiptJdbcDao() {
        super("receipt");
    }

    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL, " +
                "productsAndPrices VARCHAR(255)," +
                "totalPrice DOUBLE";
    }

    @Override
    public Receipt getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String productsAndPrices = resultSet.getString("productsAndPrices");
        double totalPrice = resultSet.getDouble("totalPrice");
        return new Receipt(id, productsAndPrices, totalPrice);
    }

    @Override
    protected String getUpdateValues(Receipt receipt) {
        return String.format("productsAndPrices=%s', totalPrice=%s'", receipt.getProductsAndPrices(), receipt.getTotalPrice());
    }

    @Override
    public String getValues(Receipt receipt) {
        return String.format("%d, '%s', '%s'", receipt.getId(), receipt.getProductsAndPrices(), receipt.getTotalPrice());
    }
}
