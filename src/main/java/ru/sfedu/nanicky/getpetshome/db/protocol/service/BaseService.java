package ru.sfedu.nanicky.getpetshome.db.protocol.service;

import ru.sfedu.nanicky.getpetshome.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;

import java.util.List;

public class BaseService<V extends IdEntity, DAO extends BaseDao<V>> implements BaseDao<V> {
    private final DAO dao;

    public BaseService(DAO dao) {
        this.dao = dao;
    }

    public List<V> getAll() {
        return dao.getAll();
    }

    public V getById(long id) {
        return dao.getById(id);
    }

    public boolean delete(V v) {
        return dao.delete(v);
    }

    public boolean update(V v) {
        return dao.update(v);
    }

    public boolean insert(V v) {
        return dao.insert(v);
    }

    public boolean upsert(V v) {
        return dao.upsert(v);
    }
}
