package ru.sfedu.nanicky.getpetshome.db.protocol.dao;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;

import java.util.List;

public interface BaseDao<V extends IdEntity> {
    List<V> getAll();
    V getById(long id);
    boolean delete(V v);
    boolean update(V v);
    boolean insert(V v);
    default boolean upsert(V v) {
        V item = getById(v.getId());
        if (item != null) {
            return update(v);
        } else {
            return insert(v);
        }
    }
}
