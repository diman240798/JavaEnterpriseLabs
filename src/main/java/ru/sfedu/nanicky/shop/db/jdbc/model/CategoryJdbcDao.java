package ru.sfedu.nanicky.shop.db.jdbc.model;

import ru.sfedu.nanicky.shop.db.jdbc.JdbcDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

import java.sql.ResultSet;

public class CategoryJdbcDao extends JdbcDao<Category> {

    public CategoryJdbcDao() {
        super("category");
    }

    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL, " +
                "name VARCHAR(255)";
    }

    @Override
    public Category getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Category(id, name);
    }

    @Override
    public String getValues(Category category) {
        return category.getId() + ", '" + category.getName() + "'";
    }
}
