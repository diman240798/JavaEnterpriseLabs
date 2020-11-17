package ru.sfedu.nanicky.shop.db.protocol.dao;

import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class TextDao<V extends IdEntity> implements BaseDao<V>{
    public Optional<V> getById(long id) {
        return getAll().stream()
                .filter(it -> it.getId() == id)
                .findFirst();
    }


    public boolean delete(V v) {
        List<V> all = getAll();
        List<V> items = all.stream()
                .filter(it -> it.getId() != v.getId())
                .collect(Collectors.toList());
        if (all.size() == items.size()) {
            return false;
        }
        return insertAll(items);
    }
    public boolean delete(long id) {
        List<V> all = getAll();
        List<V> items = all.stream()
                .filter(it -> it.getId() != id)
                .collect(Collectors.toList());
        if (all.size() == items.size()) {
            return false;
        }
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
        Optional<V> itemSameId = items.stream().filter(it -> it.getId() == v.getId()).findFirst();
        if (itemSameId.isPresent()) {
            return false;
        }
        items.add(v);
        return insertAll(items);
    }

}
