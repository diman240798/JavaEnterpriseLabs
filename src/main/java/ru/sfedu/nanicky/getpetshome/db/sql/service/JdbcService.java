package ru.sfedu.nanicky.getpetshome.db.sql.service;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;
import ru.sfedu.nanicky.getpetshome.db.protocol.service.BaseService;
import ru.sfedu.nanicky.getpetshome.db.sql.dao.JdbcDao;

public class JdbcService<V extends IdEntity> extends BaseService<V, JdbcDao<V>> {
    public JdbcService(JdbcDao<V> dao) {
        super(dao);
    }
}
