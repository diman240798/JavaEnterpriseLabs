package ru.sfedu.nanicky.shop.db.jdbc.model;


import ru.sfedu.nanicky.shop.db.jdbc.JdbcDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Session;

import java.sql.ResultSet;

public class SessionJdbcDataProvider extends JdbcDataProvider<Session> {

    public SessionJdbcDataProvider() {
        super("session");
    }

    /**
     * Полученить скрипт создания таблицы
     */
    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL PRIMARY KEY, " +
                "session VARCHAR(255)," +
                "date LONG";
    }


    /**
     * Получение модель из result set
     */
    @Override
    public Session getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String session = resultSet.getString("session");
        long date = resultSet.getLong("date");
        return new Session(id, session, date);
    }

    /**
     * Полученить скрипт обновления записи
     */
    @Override
    protected String getUpdateValues(Session session) {
        return String.format("session='%s', date=%d", session.getSession(), session.getDate());
    }

    /**
     * Полученить скрипт вставки записи
     */
    @Override
    public String getValues(Session session) {
        return String.format("%d, '%s', %d", session.getId(), session.getSession(), session.getDate());
    }
}
