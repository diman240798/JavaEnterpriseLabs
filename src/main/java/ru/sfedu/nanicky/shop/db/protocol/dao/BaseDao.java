package ru.sfedu.nanicky.shop.db.protocol.dao;

import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<V extends IdEntity> {
    List<V> getAll();
    Optional<V> getById(long id);
    boolean delete(V v);
    boolean delete(long id);
    boolean update(V v);
    boolean insert(V v);
    default boolean upsert(V v) {
        Optional<V> itemOption = getById(v.getId());
        if (itemOption.isPresent()) {
            return update(v);
        } else {
            return insert(v);
        }
    }
}
