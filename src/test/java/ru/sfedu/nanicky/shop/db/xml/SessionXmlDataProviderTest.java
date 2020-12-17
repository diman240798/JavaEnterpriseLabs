package ru.sfedu.nanicky.shop.db.xml;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.db.csv.SessionCsvDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Session;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class SessionXmlDataProviderTest {
    private static final File TEST_FILES_FOLDER = new File("tmpTests");
    private static final File DB_FILE = new File(TEST_FILES_FOLDER, "session.xml");
    private SessionXmlDataProvider dataProvider;

    @Before
    public void beforeEach() throws IOException {

        if (!TEST_FILES_FOLDER.exists()) {
            if (!TEST_FILES_FOLDER.mkdirs()) {
                throw new RuntimeException("Cant create test dir: " + TEST_FILES_FOLDER.getAbsolutePath());
            }
        }

        if (DB_FILE.exists()) {
            if (!DB_FILE.delete()) {
                throw new RuntimeException("Cant delete file: " + DB_FILE.getAbsolutePath());
            }
        }
        DB_FILE.createNewFile();

        dataProvider = new SessionXmlDataProvider(DB_FILE);

    }

    @Test
    public void getAll() throws IOException {
        Session first = new Session(0, UUID.randomUUID().toString());
        Session second = new Session(1, UUID.randomUUID().toString());

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Session> all = dataProvider.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(first, all.get(0));
    }

    @Test
    public void getAllBad() throws IOException {
        Session first = new Session(0, UUID.randomUUID().toString());
        Session second = new Session(1, UUID.randomUUID().toString());

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Session> all = dataProvider.getAll();

        Assert.assertNotEquals(1, all.size());
        Assert.assertNotEquals(second, all.get(0));
    }

    @Test
    public void getById() {
        Session first = new Session(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        Session fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertEquals(first, fromDb);
    }

    @Test
    public void getByIdBad() {
        Session first = new Session(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        Session fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertNotEquals(first.getId() + "not equals", fromDb.getId());
    }

    @Test
    public void delete() {
        Session first = new Session(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        List<Session> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(first);

        List<Session> fromDbEmpty = dataProvider.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void deleteBad() {
        Session first = new Session(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        List<Session> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(null);

        List<Session> fromDbEmpty = dataProvider.getAll();
        Assert.assertFalse(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Session first = new Session(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        List<Session> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Session newMike = new Session(0, UUID.randomUUID().toString());

        dataProvider.update(newMike);

        List<Session> fromDbNew = dataProvider.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(first, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }


    @Test
    public void updateBad() {
        Session first = new Session(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        List<Session> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Session update = new Session(0, UUID.randomUUID().toString());

        dataProvider.update(null);

        List<Session> fromDbNew = dataProvider.getAll();

        Assert.assertNotEquals(2, fromDbNew.size());
        Assert.assertEquals(first, fromDbNew.get(0));
        Assert.assertNotEquals(update, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Session first = new Session(0, UUID.randomUUID().toString());

        dataProvider.insert(first);
        Session fromDb = dataProvider.getById(first.getId()).get();
        Assert.assertEquals(first, fromDb);

        Assert.assertFalse(dataProvider.insert(first));
        Assert.assertEquals(1, dataProvider.getAll().size());
    }

    @Test
    public void insertBad() throws IOException {
        Session first = new Session(0, UUID.randomUUID().toString());

        dataProvider.insert(null);
        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }
}