package ru.sfedu.nanicky.shop.db.csv;

import com.opencsv.bean.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReceiptCsvDataProvider implements BaseDataProvider<Receipt> {


    private static Logger LOG = LogManager.getLogger(ReceiptCsvDataProvider.class);

    private final Class clazz = Receipt.class;
    private final File dbFile;

    public ReceiptCsvDataProvider(File dbFile) {
        dbFile.getParentFile().mkdirs();
        this.dbFile = dbFile;
    }

    /**
     * Получение списка для csv
     *
     * @return возвращает список моделей
     */
    public List<Receipt> getAll() {
        if (!dbFile.exists()) return new ArrayList<>();
        try (Reader reader = new FileReader(dbFile)) {
            HeaderColumnNameMappingStrategy<Receipt> ms = new HeaderColumnNameMappingStrategy<Receipt>();
            ms.setType(clazz);

            CsvToBean<Receipt> cb = new CsvToBeanBuilder<Receipt>(reader)
                    .withMappingStrategy(ms)
                    .build();

            List<Receipt> result = cb.parse();
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
    public boolean insertAll(List<Receipt> items) {
        if (items.isEmpty()) return dbFile.delete();
        try (Writer writer = new FileWriter(dbFile)) {
            StatefulBeanToCsv<Receipt> sbc = new StatefulBeanToCsvBuilder<Receipt>(writer).build();
            sbc.write(items);
            return true;
        } catch (Exception e) {
            LOG.error("CSV insert error", e);
            return false;
        }
    }


    public Optional<Receipt> getById(long id) {
        return getAll().stream()
                .filter(it -> it.getId() == id)
                .findFirst();
    }

    /**
     * Метод удаления записи
     */
    public boolean delete(Receipt v) {
        if (v == null) {
            LOG.info("Attempt to delete null");
            LOG.debug("Attempt to delete null");
            return false;
        }
        List<Receipt> all = getAll();
        List<Receipt> items = all.stream()
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
        List<Receipt> all = getAll();
        List<Receipt> items = all.stream()
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
    public boolean update(Receipt v) {
        if (v == null) {
            LOG.info("Attempt to update null");
            LOG.debug("Attempt to update null");
            return false;
        }
        List<Receipt> items = getAll().stream()
                .filter(it -> it.getId() != v.getId())
                .collect(Collectors.toList());
        items.add(v);
        return insertAll(items);
    }


    /**
     * Метод вставки записи
     */
    @Override
    public boolean insert(Receipt v) {
        if (v == null) {
            LOG.info("Attempt to insert null");
            LOG.debug("Attempt to insert null");
            return false;
        }
        List<Receipt> items = getAll();
        Optional<Receipt> itemSameId = items.stream().filter(it -> it.getId() == v.getId()).findFirst();
        if (itemSameId.isPresent()) {
            return false;
        }
        items.add(v);
        return insertAll(items);
    }
}
