package ru.sfedu.nanicky.getpetshome.db.protocol.dao;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;

import java.util.List;

public interface BaseDao<T extends IdEntity> {
    List<T> getAll();
    T getById(long id);
    boolean delete(T t);
    boolean update(T t);
    boolean insert(T t);
    boolean upsert(T t);
}
