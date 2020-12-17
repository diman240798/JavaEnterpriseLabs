package ru.sfedu.nanicky.shop.db.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReceiptXmlDataProvider implements BaseDataProvider<Receipt> {


    private static Logger LOG = LogManager.getLogger(ReceiptXmlDataProvider.class);

    @Root
    public static class EntityList<Receipt> {

        public EntityList() {
        }

        @ElementList(entry = "data", inline = true)
        private List<Receipt> data;

        public EntityList(List<Receipt> data) {
            this.data = data;
        }

        public List<Receipt> getData() {
            return this.data;
        }

        public void setData(List<Receipt> data) {
            this.data = data;
        }

    }

    private final File dbFile;

    public ReceiptXmlDataProvider(File dbFile) {
        dbFile.getParentFile().mkdirs();
        this.dbFile = dbFile;
    }

    /**
     * Метод получния списка xml
     */
    @Override
    public List<Receipt> getAll() {
        Serializer serializer = new Persister();
        if (dbFile.length() == 0) return new ArrayList<>();
        try {
            EntityList entityList = serializer.read(EntityList.class, dbFile);
            return entityList.getData();
        } catch (Exception e) {
            LOG.error("CSReceipt getAll error", e);
            return new ArrayList<>();
        }
    }

    /**
     * Метод вставки списка xml
     */
    public boolean insertAll(List<Receipt> items) {
        if (items == null || items.contains(null)) {
            LOG.info("Attempt to insert list with null");
            LOG.debug("Attempt to insert list with null");
            return false;
        }
        if (items.isEmpty()) return dbFile.delete();
        Serializer serializer = new Persister();
        EntityList entityList = new EntityList(items);
        try {
            serializer.write(entityList, dbFile);
            return true;
        } catch (Exception e) {
            LOG.error("CSReceipt insert error", e);
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
