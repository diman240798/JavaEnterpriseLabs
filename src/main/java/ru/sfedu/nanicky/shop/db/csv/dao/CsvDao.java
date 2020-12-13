package ru.sfedu.nanicky.shop.db.csv.dao;

import com.opencsv.bean.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dao.TextDao;
import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvDao<V extends IdEntity> extends TextDao<V> {


    private static Logger LOG = LogManager.getLogger(CsvDao.class);

    private final Class clazz;
    private final File dbFile;

    public CsvDao(Class clazz, File dbFile) {
        this.clazz = clazz;
        dbFile.getParentFile().mkdirs();
        this.dbFile = dbFile;
    }

    /**
     * Получение списка для csv
     * @return возвращает список моделей
     */
    public List<V> getAll() {
        if (!dbFile.exists()) return new ArrayList<>();
        try (Reader reader = new FileReader(dbFile)) {
            HeaderColumnNameMappingStrategy<V> ms = new HeaderColumnNameMappingStrategy<V>();
            ms.setType(clazz);

            CsvToBean<V> cb = new CsvToBeanBuilder<V>(reader)
                    .withMappingStrategy(ms)
                    .build();

            List<V> result = cb.parse();
            return result;
        } catch (Exception e) {
            LOG.error("CSV getALL error", e);
            return new ArrayList<>();
        }
    }

    /**
     * Вставка списка для csv
     * @return Результат вставки
     */
    public boolean insertAll(List<V> items) {
        if (items.isEmpty()) return dbFile.delete();
        try (Writer writer = new FileWriter(dbFile)) {
            StatefulBeanToCsv<V> sbc = new StatefulBeanToCsvBuilder<V>(writer).build();
            sbc.write(items);
            return true;
        } catch (Exception e) {
            LOG.error("CSV insert error", e);
            return false;
        }
    }

}
