package ru.sfedu.nanicky.shop.db.jdbc.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.jdbc.ExFunction;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Bucket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BucketJdbcDataProvider implements BaseDataProvider<Bucket> {


    private static Logger LOG = LogManager.getLogger(BucketJdbcDataProvider.class);

    /**
     * Полученить скрипт создания таблицы
     */
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL PRIMARY KEY, " +
                "session VARCHAR(255)," +
                "products VARCHAR(255)";
    }

    /**
     * Получение модель из result set
     */
    public Bucket getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String session = resultSet.getString("session");
        String products = resultSet.getString("products");
        return new Bucket(id, session, products);
    }

    /**
     * Полученить скрипт обновления записи
     */
    protected String getUpdateValues(Bucket bucket) {
        return String.format("session='%s', products='%s'", bucket.getSession(), bucket.getProducts());
    }

    public String getValues(Bucket bucket) {
        return String.format("%d, '%s', '%s'", bucket.getId(), bucket.getSession(), bucket.getProducts());
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


    private final String entity = "bucket";

    private String QUERY_CREATE;
    private String QUERY_GET_ALL;
    private String QUERY_GET_BY_ID;
    private String QUERY_DELETE_BY_ID;
    private String QUERY_UPDATE;
    private String QUERY_INSERT;

    public BucketJdbcDataProvider() {
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
    public List<Bucket> getAll() {
        List<Bucket> result = new ArrayList<>();
        try {
            runOn(statemnt -> {
                ResultSet resultSet = statemnt.executeQuery(QUERY_GET_ALL);
                while (resultSet.next()) {
                    Bucket model = getModel(resultSet);
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
    public Optional<Bucket> getById(long id) {
        try {
            Optional<Bucket> runOn = runOn(statemnt -> {
                ResultSet resultSet = statemnt.executeQuery(String.format(QUERY_GET_BY_ID, id));
                resultSet.next();
                Bucket model = getModel(resultSet);
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
    public boolean delete(Bucket v) {
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
    public boolean update(Bucket v) {
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
    public boolean insert(Bucket v) {
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
    public boolean insertAll(List<Bucket> items) {
        if (items == null || items.contains(null)) {
            LOG.info("Attempt to insert list with null");
            LOG.debug("Attempt to insert list with null");
            return false;
        }
        items.forEach(this::insert);
        return true;
    }
}
