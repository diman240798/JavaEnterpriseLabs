package ru.sfedu.nanicky.getpetshome.db.protocol.dao;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TextDao<V extends IdEntity> implements BaseDao<V>{
    public V getById(long id) {
        return getAll().stream()
                .filter(it -> it.getId() == id)
                .findFirst()
                .get();
    }


    public boolean delete(V v) {
        List<V> items = getAll().stream()
                .filter(it -> it.getId() != v.getId())
                .collect(Collectors.toList());
        return insertAll(items);
    }

    protected abstract boolean insertAll(List<V> items);

    public boolean update(V v) {
        List<V> items = getAll().stream()
                .filter(it -> it.getId() != v.getId())
                .collect(Collectors.toList());
        items.add(v);
        return insertAll(items);
    }

    @Override
    public boolean insert(V v) {
        List<V> items = getAll();
        items.add(v);
        return insertAll(items);
    }

}
