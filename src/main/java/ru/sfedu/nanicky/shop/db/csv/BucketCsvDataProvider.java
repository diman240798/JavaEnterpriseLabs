package ru.sfedu.nanicky.shop.db.csv;

import com.opencsv.bean.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Bucket;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BucketCsvDataProvider implements BaseDataProvider<Bucket> {


    private static Logger LOG = LogManager.getLogger(BucketCsvDataProvider.class);

    private final Class clazz = Bucket.class;
    private final File dbFile;

    public BucketCsvDataProvider(File dbFile) {
        dbFile.getParentFile().mkdirs();
        this.dbFile = dbFile;
    }

    /**
     * Получение списка для csv
     *
     * @return возвращает список моделей
     */
    public List<Bucket> getAll() {
        if (!dbFile.exists()) return new ArrayList<>();
        try (Reader reader = new FileReader(dbFile)) {
            HeaderColumnNameMappingStrategy<Bucket> ms = new HeaderColumnNameMappingStrategy<Bucket>();
            ms.setType(clazz);

            CsvToBean<Bucket> cb = new CsvToBeanBuilder<Bucket>(reader)
                    .withMappingStrategy(ms)
                    .build();

            List<Bucket> result = cb.parse();
            return result;
        } catch (Exception e) {
            LOG.error("CSV getALL error", e);
            return new ArrayList<>();
        }
    }

    /**
     * Вставка списка для csv
     *
     * @return Результат вставки
     */
    public boolean insertAll(List<Bucket> items) {
        if (items.isEmpty()) return dbFile.delete();
        try (Writer writer = new FileWriter(dbFile)) {
            StatefulBeanToCsv<Bucket> sbc = new StatefulBeanToCsvBuilder<Bucket>(writer).build();
            sbc.write(items);
            return true;
        } catch (Exception e) {
            LOG.error("CSV insert error", e);
            return false;
        }
    }


    public Optional<Bucket> getById(long id) {
        return getAll().stream()
                .filter(it -> it.getId() == id)
                .findFirst();
    }

    /**
     * Метод удаления записи
     */
    public boolean delete(Bucket v) {
        if (v == null) {
            LOG.info("Attempt to delete null");
            LOG.debug("Attempt to delete null");
            return false;
        }
        List<Bucket> all = getAll();
        List<Bucket> items = all.stream()
                .filter(it -> it.getId() != v.getId())
                .collect(Collectors.toList());
        if (all.size() == items.size()) {
            return false;
        }
        return insertAll(items);
    }


    /**
     * Метод удаления записи по id
     */
    public boolean delete(long id) {
        List<Bucket> all = getAll();
        List<Bucket> items = all.stream()
                .filter(it -> it.getId() != id)
                .collect(Collectors.toList());
        if (all.size() == items.size()) {
            return false;
        }
        return insertAll(items);
    }


    /**
     * Метод обновления записи
     */
    public boolean update(Bucket v) {
        if (v == null) {
            LOG.info("Attempt to update null");
            LOG.debug("Attempt to update null");
            return false;
        }
        List<Bucket> items = getAll().stream()
                .filter(it -> it.getId() != v.getId())
                .collect(Collectors.toList());
        items.add(v);
        return insertAll(items);
    }


    /**
     * Метод вставки записи
     */
    @Override
    public boolean insert(Bucket v) {
        if (v == null) {
            LOG.info("Attempt to insert null");
            LOG.debug("Attempt to insert null");
            return false;
        }
        List<Bucket> items = getAll();
        Optional<Bucket> itemSameId = items.stream().filter(it -> it.getId() == v.getId()).findFirst();
        if (itemSameId.isPresent()) {
            return false;
        }
        items.add(v);
        return insertAll(items);
    }
}
