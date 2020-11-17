package ru.sfedu.nanicky.shop.db.csv.dao;

import com.opencsv.bean.*;
import ru.sfedu.nanicky.shop.db.protocol.dao.TextDao;
import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvDao<V extends IdEntity> extends TextDao<V> {

    private final Class clazz;
    private final File dbFile;

    public CsvDao(Class clazz, File dbFile) {
        this.clazz = clazz;
        this.dbFile = dbFile;
    }

    public List<V> getAll() {
        try (Reader reader = new FileReader(dbFile)) {
            HeaderColumnNameMappingStrategy<V> ms = new HeaderColumnNameMappingStrategy<V>();
            ms.setType(clazz);

            CsvToBean<V> cb = new CsvToBeanBuilder<V>(reader)
                    .withMappingStrategy(ms)
                    .build();

            List<V> result = cb.parse();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean insertAll(List<V> items) {
        if (items.isEmpty()) return dbFile.delete();
        try (Writer writer = new FileWriter(dbFile)) {
            StatefulBeanToCsv<V> sbc = new StatefulBeanToCsvBuilder<V>(writer).build();
            sbc.write(items);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
