package ru.sfedu.nanicky.shop.db.protocol.dataprovider;


import java.util.List;
import java.util.Optional;

public interface BaseDataProvider<V> {
    List<V> getAll();
    Optional<V> getById(long id);
    boolean delete(V v);
    boolean delete(long id);
    boolean update(V v);
    boolean insert(V v);
    boolean insertAll(List<V> items);
}
