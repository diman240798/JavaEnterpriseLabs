package ru.sfedu.nanicky.shop.db.xml;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.db.csv.ReceiptCsvDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ReceiptXmlDataProviderTest {
    private static final File TEST_FILES_FOLDER = new File("tmpTests");
    private static final File DB_FILE = new File(TEST_FILES_FOLDER, "receipt.xml");
    private ReceiptXmlDataProvider dataProvider;

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

        dataProvider = new ReceiptXmlDataProvider(DB_FILE);

    }

    @Test
    public void getAll() throws IOException {
        Receipt first = new Receipt(0, "1 fridge", 5400);
        Receipt second = new Receipt(1, "3 soda", 600);

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Receipt> all = dataProvider.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(first, all.get(0));
    }

    @Test
    public void getAllBad() throws IOException {
        Receipt first = new Receipt(0, "1 fridge", 5400);
        Receipt second = new Receipt(1, "3 soda", 600);

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Receipt> all = dataProvider.getAll();

        Assert.assertNotEquals(1, all.size());
        Assert.assertNotEquals(second, all.get(0));
    }

    @Test
    public void getById() {
        Receipt first = new Receipt(0, "1 fridge", 5400);

        dataProvider.insert(first);

        Receipt fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertEquals(first, fromDb);
    }

    @Test
    public void getByIdBad() {
        Receipt first = new Receipt(0, "1 fridge", 5400);

        dataProvider.insert(first);

        Receipt fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertNotEquals(first.getId() + "not equals", fromDb.getId());
    }

    @Test
    public void delete() {
        Receipt first = new Receipt(0, "1 fridge", 5400);

        dataProvider.insert(first);

        List<Receipt> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(first);

        List<Receipt> fromDbEmpty = dataProvider.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void deleteBad() {
        Receipt first = new Receipt(0, "1 fridge", 5400);

        dataProvider.insert(first);

        List<Receipt> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(null);

        List<Receipt> fromDbEmpty = dataProvider.getAll();
        Assert.assertFalse(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Receipt first = new Receipt(0, "1 fridge", 5400);

        dataProvider.insert(first);

        List<Receipt> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Receipt newMike = new Receipt(0, "3 soda", 600);

        dataProvider.update(newMike);

        List<Receipt> fromDbNew = dataProvider.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(first, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }


    @Test
    public void updateBad() {
        Receipt first = new Receipt(0, "1 fridge", 5400);

        dataProvider.insert(first);

        List<Receipt> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Receipt update = new Receipt(0, "3 soda", 600);

        dataProvider.update(null);

        List<Receipt> fromDbNew = dataProvider.getAll();

        Assert.assertNotEquals(2, fromDbNew.size());
        Assert.assertEquals(first, fromDbNew.get(0));
        Assert.assertNotEquals(update, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Receipt first = new Receipt(0, "1 fridge", 5400);

        dataProvider.insert(first);
        Receipt fromDb = dataProvider.getById(first.getId()).get();
        Assert.assertEquals(first, fromDb);

        Assert.assertFalse(dataProvider.insert(first));
        Assert.assertEquals(1, dataProvider.getAll().size());
    }

    @Test
    public void insertBad() throws IOException {
        Receipt first = new Receipt(0, "1 fridge", 5400);

        dataProvider.insert(null);
        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }
}