package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.jdbc.ExFunction;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceiptJdbcDataProvider implements BaseDataProvider<Receipt> {


    private static Logger LOG = LogManager.getLogger(ReceiptJdbcDataProvider.class);


    /**
     * Полученить скрипт создания таблицы
     */
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL PRIMARY KEY, " +
                "productsAndPrices VARCHAR(255)," +
                "totalPrice DOUBLE";
    }


    /**
     * Получение модель из result set
     */
    public Receipt getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String productsAndPrices = resultSet.getString("productsAndPrices");
        double totalPrice = resultSet.getDouble("totalPrice");
        return new Receipt(id, productsAndPrices, totalPrice);
    }


    /**
     * Полученить скрипт обновления записи
     */
    protected String getUpdateValues(Receipt receipt) {
        return String.format("productsAndPrices='%s', totalPrice='%s'", receipt.getProductsAndPrices(), receipt.getTotalPrice());
    }

    /**
     * Полученить скрипт вставки записи
     */
    public String getValues(Receipt receipt) {
        return String.format("%d, '%s', '%s'", receipt.getId(), receipt.getProductsAndPrices(), receipt.getTotalPrice());
    }

    private static final String JDBC_DRIVER = "org.h2.Driver";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            LOG.info("Error registering jdbc driver!", e);
        }
    }

    private static final String DB_URL = "jdbc:h2:./jdbc.db";

    private static final String USER = "user";
    private static final String PASS = "user";


    private final String entity = "receipt";

    private String QUERY_CREATE;
    private String QUERY_GET_ALL;
    private String QUERY_GET_BY_ID;
    private String QUERY_DELETE_BY_ID;
    private String QUERY_UPDATE;
    private String QUERY_INSERT;

    public ReceiptJdbcDataProvider() {
        QUERY_CREATE = "CREATE TABLE IF NOT EXISTS " + this.entity + " (%s)";
        QUERY_GET_ALL = "SELECT * FROM " + this.entity;
        QUERY_GET_BY_ID = "SELECT * FROM " + this.entity + " WHERE id = %d";
        QUERY_DELETE_BY_ID = "DELETE FROM " + this.entity + " WHERE id = %d";
        QUERY_UPDATE = "UPDATE " + this.entity + " SET %s where id = %d";
        QUERY_INSERT = "INSERT INTO " + this.entity + " VALUES (%s)";
        createTable();
    }



    /**
     * Метод создания таблицы
     */
    private void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = conn.createStatement();
            String queryCreate = String.format(QUERY_CREATE, getTableCreateQuery());
            statement.executeUpdate(queryCreate);
        } catch (Exception ex) {
            LOG.error("Error creating table: {}", entity, ex);
        }
    }


    /**
     * Метод выполнения выражений sql на jdbc
     */
    public <M> M runOn(ExFunction<Statement, M> consumer) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = conn.createStatement();
            return consumer.apply(statement);
        } catch (Exception ex) {
            LOG.error("Error creating jdbc statement for entity: {}", entity, ex);
            return null;
        }
    }

    /**
     * Метод получения списка из jdbc
     */
    @Override
    public List<Receipt> getAll() {
        List<Receipt> result = new ArrayList<>();
        try {
            runOn(statemnt -> {
                ResultSet resultSet = statemnt.executeQuery(QUERY_GET_ALL);
                while (resultSet.next()) {
                    Receipt model = getModel(resultSet);
                    result.add(model);
                }
                return true;
            });

        } catch (Exception ex) {
            LOG.error("Error getting entity {} from jdbc", entity, ex);
        }
        return result;
    }

    @Override
    public Optional<Receipt> getById(long id) {
        try {
            Optional<Receipt> runOn = runOn(statemnt -> {
                ResultSet resultSet = statemnt.executeQuery(String.format(QUERY_GET_BY_ID, id));
                resultSet.next();
                Receipt model = getModel(resultSet);
                return Optional.ofNullable(model);
            });
            return runOn == null ? Optional.empty() : runOn;
        } catch (Exception ex) {
            LOG.error("Error getting entity {} from jdbc", entity, ex);
            return Optional.empty();
        }
    }

    /**
     * Метод удаления записи
     */
    @Override
    public boolean delete(Receipt v) {
        if (v == null) {
            LOG.info("Attempt to delete null");
            LOG.debug("Attempt to delete null");
            return false;
        }
        return delete(v.getId());
    }

    /**
     * Метод создания таблицы по id
     */
    @Override
    public boolean delete(long id) {
        try {
            return runOn(statemnt -> statemnt.execute(String.format(QUERY_DELETE_BY_ID, id)));
        } catch (Exception ex) {
            LOG.error("Error deleting entity {} with id: {} from jdbc ", entity, id, ex);
            return false;
        }
    }

    /**
     * Метод обносления записи
     */
    @Override
    public boolean update(Receipt v) {
        if (v == null) {
            LOG.info("Attempt to update null");
            LOG.debug("Attempt to update null");
            return false;
        }
        try {
            String values = getUpdateValues(v);
            String query = String.format(QUERY_UPDATE, values, v.getId());
            return runOn(statement -> statement.executeUpdate(query) != 0);
        } catch (Exception ex) {
            LOG.error("Error updating entity {} from jdbc", entity, ex);
            return false;
        }
    }

    /**
     * Метод вставки записи
     */
    @Override
    public boolean insert(Receipt v) {
        if (v == null) {
            LOG.info("Attempt to insert null");
            LOG.debug("Attempt to insert null");
            return false;
        }
        try {
            String values = getValues(v);
            String query = String.format(QUERY_INSERT, values);
            return runOn(statement -> statement.executeUpdate(query) != 0);
        } catch (Exception ex) {
            LOG.error("Error inserting entity {} from jdbc", entity, ex);
            return false;
        }
    }


    /**
     * Метод вставки списка записей
     */
    @Override
    public boolean insertAll(List<Receipt> items) {
        if (items == null || items.contains(null)) {
            LOG.info("Attempt to insert list with null");
            LOG.debug("Attempt to insert list with null");
            return false;
        }
        items.forEach(this::insert);
        return true;
    }
}
