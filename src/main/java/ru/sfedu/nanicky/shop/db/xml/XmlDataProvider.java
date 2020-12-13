package ru.sfedu.nanicky.shop.db.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.TextDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlDataProvider<V extends IdEntity> extends TextDataProvider<V> {


    private static Logger LOG = LogManager.getLogger(XmlDataProvider.class);

    @Root
    public static class EntityList<V> {

        public EntityList() {}

        @ElementList(entry = "data", inline = true)
        private List<V> data;

        public EntityList(List<V> data) {
            this.data = data;
        }

        public List<V> getData() {
            return this.data;
        }

        public void setData(List<V> data) {
            this.data = data;
        }

    }

    private final File dbFile;

    public XmlDataProvider(File dbFile) {
        dbFile.getParentFile().mkdirs();
        this.dbFile = dbFile;
    }

    /**
     * Метод получния списка xml
     */
    @Override
    public List<V> getAll() {
        Serializer serializer = new Persister();
        if (dbFile.length() == 0) return new ArrayList<>();
        try {
            EntityList entityList = serializer.read(EntityList.class, dbFile);
            return entityList.getData();
        } catch (Exception e) {
            LOG.error("CSV getAll error", e);
            return new ArrayList<>();
        }
    }
    /**
     * Метод вставки списка xml
     */
    public boolean insertAll(List<V> items) {
        if (items.isEmpty()) return dbFile.delete();
        Serializer serializer = new Persister();
        EntityList entityList = new EntityList(items);
        try {
            serializer.write(entityList, dbFile);
            return true;
        } catch (Exception e) {
            LOG.error("CSV insert error", e);
            return false;
        }
    }
}
