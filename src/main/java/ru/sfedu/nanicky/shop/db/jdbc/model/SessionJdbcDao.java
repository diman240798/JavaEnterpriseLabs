package ru.sfedu.nanicky.shop.db.jdbc.model;


import ru.sfedu.nanicky.shop.db.jdbc.JdbcDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Session;

import java.sql.ResultSet;

public class SessionJdbcDao extends JdbcDao<Session> {

    public SessionJdbcDao() {
        super("session");
    }

    @Override
    protected String getTableCreateQuery() {
        return "id BIGINT not NULL, " +
                "session VARCHAR(255)," +
                "date LONG";
    }

    @Override
    public Session getModel(ResultSet resultSet) throws Exception {
        long id = resultSet.getLong("id");
        String session = resultSet.getString("session");
        long date = resultSet.getLong("date");
        return new Session(id, session, date);
    }

    @Override
    protected String getUpdateValues(Session session) {
        return String.format("session=%s', date=%d", session.getSession(), session.getDate());
    }

    @Override
    public String getValues(Session session) {
        return String.format("%d, '%s', %d", session.getId(), session.getSession(), session.getDate());
    }
}
