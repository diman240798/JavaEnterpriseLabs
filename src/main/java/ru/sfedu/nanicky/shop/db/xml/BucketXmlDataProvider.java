package ru.sfedu.nanicky.shop.db.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Bucket;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BucketXmlDataProvider implements BaseDataProvider<Bucket> {


    private static Logger LOG = LogManager.getLogger(BucketXmlDataProvider.class);

    @Root
    public static class EntityList<Bucket> {

        public EntityList() {
        }

        @ElementList(entry = "data", inline = true)
        private List<Bucket> data;

        public EntityList(List<Bucket> data) {
            this.data = data;
        }

        public List<Bucket> getData() {
            return this.data;
        }

        public void setData(List<Bucket> data) {
            this.data = data;
        }

    }

    private final File dbFile;

    public BucketXmlDataProvider(File dbFile) {
        dbFile.getParentFile().mkdirs();
        this.dbFile = dbFile;
    }

    /**
     * Метод получния списка xml
     */
    @Override
    public List<Bucket> getAll() {
        Serializer serializer = new Persister();
        if (dbFile.length() == 0) return new ArrayList<>();
        try {
            EntityList entityList = serializer.read(EntityList.class, dbFile);
            return entityList.getData();
        } catch (Exception e) {
            LOG.error("CSBucket getAll error", e);
            return new ArrayList<>();
        }
    }

    /**
     * Метод вставки списка xml
     */
    public boolean insertAll(List<Bucket> items) {
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
            LOG.error("CSBucket insert error", e);
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
