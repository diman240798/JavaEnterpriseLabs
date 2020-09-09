package ru.sfedu.nanicky.getpetshome.db.csv.service;

import ru.sfedu.nanicky.getpetshome.db.csv.dao.CsvDao;
import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;
import ru.sfedu.nanicky.getpetshome.db.protocol.service.BaseService;

public class CsvService<V extends IdEntity> extends BaseService<V, CsvDao<V>> {
    public CsvService(CsvDao csvDao) {
        super(csvDao);
    }
}
