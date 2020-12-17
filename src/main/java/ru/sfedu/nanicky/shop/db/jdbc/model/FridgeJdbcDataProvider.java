package ru.sfedu.nanicky.shop.db.jdbc.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.jdbc.ExFunction;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FridgeJdbcDataProvider implements BaseDataProvider<Fridge> {

    private static Logger LOG = LogManager.getLogger(FridgeJdbcDataProvider.class);

    /**
     * Полученить скрипт создания таблицы
     */
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


    private final String entity = "fridge";

    private String QUERY_CREATE;
    private String QUERY_GET_ALL;
    private String QUERY_GET_BY_ID;
    private String QUERY_DELETE_BY_ID;
    private String QUERY_UPDATE;
    private String QUERY_INSERT;

    public FridgeJdbcDataProvider() {
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
    public List<Fridge> getAll() {
        List<Fridge> result = new ArrayList<>();
        try {
            runOn(statemnt -> {
                ResultSet resultSet = statemnt.executeQuery(QUERY_GET_ALL);
                while (resultSet.next()) {
                    Fridge model = getModel(resultSet);
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
    public Optional<Fridge> getById(long id) {
        try {
            Optional<Fridge> runOn = runOn(statemnt -> {
                ResultSet resultSet = statemnt.executeQuery(String.format(QUERY_GET_BY_ID, id));
                resultSet.next();
                Fridge model = getModel(resultSet);
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
    public boolean delete(Fridge v) {
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
    public boolean update(Fridge v) {
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
    public boolean insert(Fridge v) {
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
    public boolean insertAll(List<Fridge> items) {
        if (items == null || items.contains(null)) {
            LOG.info("Attempt to insert list with null");
            LOG.debug("Attempt to insert list with null");
            return false;
        }
        items.forEach(this::insert);
        return true;
    }
}

