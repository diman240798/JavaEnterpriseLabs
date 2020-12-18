package ru.sfedu.nanicky.shop.db.csv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.db.protocol.model.Bucket;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


public class BucketCsvDataProviderTest {
    private static final File TEST_FILES_FOLDER = new File("tmpTests");
    private static final File DB_FILE = new File(TEST_FILES_FOLDER, "bucket.csv");
    private BucketCsvDataProvider dataProvider;

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

        dataProvider = new BucketCsvDataProvider(DB_FILE);

    }

    @Test
    public void getAll() throws IOException {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());
        Bucket second = new Bucket(1, UUID.randomUUID().toString());

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Bucket> all = dataProvider.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(first, all.get(0));
    }

    @Test
    public void getAllBad() throws IOException {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());
        Bucket second = new Bucket(1, UUID.randomUUID().toString());

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Bucket> all = dataProvider.getAll();

        Assert.assertNotEquals(1, all.size());
        Assert.assertNotEquals(second, all.get(0));
    }

    @Test
    public void getById() {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        Bucket fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertEquals(first, fromDb);
    }

    @Test
    public void getByIdBad() {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.insert(null);

        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }

    @Test
    public void delete() {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        List<Bucket> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(first);

        List<Bucket> fromDbEmpty = dataProvider.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void deleteBad() {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        List<Bucket> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(null);

        List<Bucket> fromDbEmpty = dataProvider.getAll();
        Assert.assertFalse(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        List<Bucket> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Bucket newMike = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.update(newMike);

        List<Bucket> fromDbNew = dataProvider.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(first, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }


    @Test
    public void updateBad() {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.insert(first);

        List<Bucket> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Bucket update = new Bucket(1, UUID.randomUUID().toString());

        dataProvider.update(null);

        List<Bucket> fromDbNew = dataProvider.getAll();

        Assert.assertNotEquals(2, fromDbNew.size());
        Assert.assertEquals(first, fromDbNew.get(0));
        Assert.assertNotEquals(update, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.insert(first);
        Bucket fromDb = dataProvider.getById(first.getId()).get();
        Assert.assertEquals(first, fromDb);

        Assert.assertFalse(dataProvider.insert(first));
        Assert.assertEquals(1, dataProvider.getAll().size());
    }

    @Test
    public void insertBad() throws IOException {
        Bucket first = new Bucket(0, UUID.randomUUID().toString());

        dataProvider.insert(null);
        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }

}