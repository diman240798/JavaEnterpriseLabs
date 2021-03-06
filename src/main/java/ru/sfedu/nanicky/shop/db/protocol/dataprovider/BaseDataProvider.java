package ru.sfedu.nanicky.shop.db.protocol.dataprovider;

import ru.sfedu.nanicky.shop.db.protocol.model.Id;

import java.util.List;
import java.util.Optional;

public interface BaseDataProvider<V extends Id> {
    List<V> getAll();
    Optional<V> getById(long id);
    boolean delete(V v);
    boolean delete(long id);
    boolean update(V v);
    boolean insert(V v);
    boolean insertAll(List<V> items);
}
