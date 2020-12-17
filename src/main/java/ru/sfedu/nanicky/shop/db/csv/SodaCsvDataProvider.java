package ru.sfedu.nanicky.shop.db.csv;

import com.opencsv.bean.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Soda;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SodaCsvDataProvider implements BaseDataProvider<Soda> {


    private static Logger LOG = LogManager.getLogger(SodaCsvDataProvider.class);

    private final Class clazz = Soda.class;
    private final File dbFile;

    public SodaCsvDataProvider(File dbFile) {
        dbFile.getParentFile().mkdirs();
        this.dbFile = dbFile;
    }

    /**
     * Получение списка для csv
     *
     * @return возвращает список моделей
     */
    public List<Soda> getAll() {
        if (!dbFile.exists()) return new ArrayList<>();
        try (Reader reader = new FileReader(dbFile)) {
            HeaderColumnNameMappingStrategy<Soda> ms = new HeaderColumnNameMappingStrategy<Soda>();
            ms.setType(clazz);

            CsvToBean<Soda> cb = new CsvToBeanBuilder<Soda>(reader)
                    .withMappingStrategy(ms)
                    .build();

            List<Soda> result = cb.parse();
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
    public boolean insertAll(List<Soda> items) {
        if (items == null || items.contains(null)) {
            LOG.info("Attempt to insert list with null");
            LOG.debug("Attempt to insert list with null");
            return false;
        }
        if (items.isEmpty()) return dbFile.delete();
        try (Writer writer = new FileWriter(dbFile)) {
            StatefulBeanToCsv<Soda> sbc = new StatefulBeanToCsvBuilder<Soda>(writer).build();
            sbc.write(items);
            return true;
        } catch (Exception e) {
            LOG.error("CSV insert error", e);
            return false;
        }
    }


    public Optional<Soda> getById(long id) {
        return getAll().stream()
                .filter(it -> it.getId() == id)
                .findFirst();
    }

    /**
     * Метод удаления записи
     */
    public boolean delete(Soda v) {
        if (v == null) {
            LOG.info("Attempt to delete null");
            LOG.debug("Attempt to delete null");
            return false;
        }
        List<Soda> all = getAll();
        List<Soda> items = all.stream()
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
        List<Soda> all = getAll();
        List<Soda> items = all.stream()
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
    public boolean update(Soda v) {
        if (v == null) {
            LOG.info("Attempt to update null");
            LOG.debug("Attempt to update null");
            return false;
        }
        if (v == null) {
            LOG.info("Attempt to update null");
            LOG.debug("Attempt to update null");
            return false;
        }
        List<Soda> items = getAll().stream()
                .filter(it -> it.getId() != v.getId())
                .collect(Collectors.toList());
        items.add(v);
        return insertAll(items);
    }


    /**
     * Метод вставки записи
     */
    @Override
    public boolean insert(Soda v) {
        if (v == null) {
            LOG.info("Attempt to insert null");
            LOG.debug("Attempt to insert null");
            return false;
        }
        List<Soda> items = getAll();
        Optional<Soda> itemSameId = items.stream().filter(it -> it.getId() == v.getId()).findFirst();
        if (itemSameId.isPresent()) {
            return false;
        }
        items.add(v);
        return insertAll(items);
    }
}
