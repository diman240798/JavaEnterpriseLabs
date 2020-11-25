package ru.sfedu.nanicky.shop.db.jdbc.model;


import ru.sfedu.nanicky.shop.db.jdbc.JdbcDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Bucket;

import java.sql.ResultSet;

public class BucketJdbcDao extends JdbcDao<Bucket> {

    public BucketJdbcDao() {
        super("bucket");
    }

    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL, " +
                "session VARCHAR(255)," +
                "products VARCHAR(255)";
    }

    @Override
    public Bucket getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String session = resultSet.getString("session");
        String products = resultSet.getString("products");
        return new Bucket(id, session, products);
    }

    @Override
    public String getValues(Bucket bucket) {
        return String.format("%d, %s, %s", bucket.getId(), bucket.getSession(), bucket.getProducts());
    }
}
