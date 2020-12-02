package ru.sfedu.nanicky.shop.db.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JdbcDao<V extends IdEntity> implements BaseDao<V> {

    private static final Logger LOG = LogManager.getLogger(JdbcDao.class);

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


    private final String entity;

    private String QUERY_CREATE;
    private String QUERY_GET_ALL;
    private String QUERY_GET_BY_ID;
    private String QUERY_DELETE_BY_ID;
    private String QUERY_UPDATE;
    private String QUERY_INSERT;

    public JdbcDao(String entity) {
        this.entity = entity;
        QUERY_CREATE = "CREATE TABLE IF NOT EXISTS " + this.entity + " (%s)";
        QUERY_GET_ALL = "SELECT * FROM " + this.entity;
        QUERY_GET_BY_ID = "SELECT * FROM " + this.entity + " WHERE id = %d";
        QUERY_DELETE_BY_ID = "DELETE FROM " + this.entity + " WHERE id = %d";
        QUERY_UPDATE = "UPDATE " + this.entity + " SET %s where id = %d";
        QUERY_INSERT = "INSERT INTO " + this.entity + " VALUES (%s)";
        createTable();
    }



    private void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = conn.createStatement();
            String queryCreate = String.format(QUERY_CREATE, getTableCreateQuery());
            statement.executeUpdate(queryCreate);
        } catch (Exception ex) {
            LOG.info("Error creating table: {}", entity, ex);
        }
    }

    protected abstract String getTableCreateQuery();


    public <M> M runOn(ExFunction<Statement, M> consumer) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement statement = conn.createStatement();
            return consumer.apply(statement);
        } catch (Exception ex) {
            LOG.debug("Error creating jdbc statement for entity: {}", entity, ex);
            return null;
        }
    }

    @Override
    public List<V> getAll() {
        List<V> result = new ArrayList<>();
        try {
            runOn(statemnt -> {
                ResultSet resultSet = statemnt.executeQuery(QUERY_GET_ALL);
                while (resultSet.next()) {
                    V model = getModel(resultSet);
                    result.add(model);
                }
                return true;
            });

        } catch (Exception ex) {
            LOG.info("Error getting entity {} from jdbc", entity, ex);
        }
        return result;
    }

    public abstract V getModel(ResultSet resultSet) throws Exception;

    @Override
    public Optional<V> getById(long id) {
        try {
            Optional<V> runOn = runOn(statemnt -> {
                ResultSet resultSet = statemnt.executeQuery(String.format(QUERY_GET_BY_ID, id));
                resultSet.next();
                V model = getModel(resultSet);
                return Optional.ofNullable(model);
            });
            return runOn == null ? Optional.empty() : runOn;
        } catch (Exception ex) {
            LOG.info("Error getting entity {} from jdbc", entity, ex);
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(V v) {
        return delete(v.getId());
    }

    @Override
    public boolean delete(long id) {
        try {
            return runOn(statemnt -> statemnt.execute(String.format(QUERY_DELETE_BY_ID, id)));
        } catch (Exception ex) {
            LOG.info("Error deleting entity {} with id: {} from jdbc ", entity, id, ex);
            return false;
        }
    }

    @Override
    public boolean update(V v) {
        try {
            String values = getUpdateValues(v);
            String query = String.format(QUERY_UPDATE, values, v.getId());
            return runOn(statement -> statement.executeUpdate(query) != 0);
        } catch (Exception ex) {
            LOG.info("Error updating entity {} from jdbc", entity, ex);
            return false;
        }
    }

    protected abstract String getUpdateValues(V v);

    @Override
    public boolean insert(V v) {
        try {
            String values = getValues(v);
            String query = String.format(QUERY_INSERT, values);
            return runOn(statement -> statement.executeUpdate(query) != 0);
        } catch (Exception ex) {
            LOG.info("Error inserting entity {} from jdbc", entity, ex);
            return false;
        }
    }

    public abstract String getValues(V v);

    @Override
    public boolean insertAll(List<V> items) {
        items.forEach(this::insert);
        return true;
    }
}
