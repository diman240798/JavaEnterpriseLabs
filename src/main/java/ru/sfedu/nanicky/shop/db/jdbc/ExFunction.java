package ru.sfedu.nanicky.shop.db.jdbc;

public interface ExFunction<T, R> {
    R apply(T t) throws Exception;
}
