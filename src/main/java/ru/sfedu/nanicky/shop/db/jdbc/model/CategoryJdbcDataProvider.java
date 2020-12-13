package ru.sfedu.nanicky.shop.db.jdbc.model;

import ru.sfedu.nanicky.shop.db.jdbc.JdbcDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

import java.sql.ResultSet;

public class CategoryJdbcDataProvider extends JdbcDao<Category> {

    public CategoryJdbcDataProvider() {
        super("category");
    }

    /**
     * Полученить скрипт создания таблицы
     */
    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL PRIMARY KEY, " +
                "name VARCHAR(255)";
    }

    /**
     * Получение модель из result set
     */
    @Override
    public Category getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Category(id, name);
    }

    /**
     * Полученить скрипт создания таблицы
     */
    @Override
    protected String getUpdateValues(Category category) {
        return String.format("name='%s'", category.getName());
    }

    @Override
    public String getValues(Category category) {
        return String.format("%d, '%s'", category.getId(), category.getName());
    }
}
