package ru.sfedu.nanicky.shop.db.xml;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.csv.SodaCsvDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Soda;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SodaXmlDataProviderTest {
    private static final File TEST_FILES_FOLDER = new File("tmpTests");
    private static final File DB_FILE = new File(TEST_FILES_FOLDER, "soda.xml");
    private SodaXmlDataProvider dataProvider;

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

        dataProvider = new SodaXmlDataProvider(DB_FILE);

    }

    @Test
    public void getAll() throws IOException {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);
        Soda second = new Soda(1, "Kind Juice", 1, 70, Constants.CATEGORY_SODA, "apple");

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Soda> all = dataProvider.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(first, all.get(0));
    }

    @Test
    public void getAllBad() throws IOException {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);
        Soda second = new Soda(1, "Kind Juice", 1, 70, Constants.CATEGORY_SODA, "apple");

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Soda> all = dataProvider.getAll();

        Assert.assertNotEquals(1, all.size());
        Assert.assertNotEquals(second, all.get(0));
    }

    @Test
    public void getById() {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);

        dataProvider.insert(first);

        Soda fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertEquals(first, fromDb);
    }

    @Test
    public void getByIdBad() {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);

        dataProvider.insert(null);

        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }

    @Test
    public void delete() {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);

        dataProvider.insert(first);

        List<Soda> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(first);

        List<Soda> fromDbEmpty = dataProvider.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void deleteBad() {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);

        dataProvider.insert(first);

        List<Soda> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(null);

        List<Soda> fromDbEmpty = dataProvider.getAll();
        Assert.assertFalse(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);

        dataProvider.insert(first);

        List<Soda> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Soda newMike = new Soda(0, "Kind Juice", 1, 70, Constants.CATEGORY_SODA, "apple");

        dataProvider.update(newMike);

        List<Soda> fromDbNew = dataProvider.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(first, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }


    @Test
    public void updateBad() {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);

        dataProvider.insert(first);

        List<Soda> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Soda update = new Soda(0, "Kind Juice", 1, 70, Constants.CATEGORY_SODA, "apple");

        dataProvider.update(null);

        List<Soda> fromDbNew = dataProvider.getAll();

        Assert.assertNotEquals(2, fromDbNew.size());
        Assert.assertEquals(first, fromDbNew.get(0));
        Assert.assertNotEquals(update, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);

        dataProvider.insert(first);
        Soda fromDb = dataProvider.getById(first.getId()).get();
        Assert.assertEquals(first, fromDb);

        Assert.assertFalse(dataProvider.insert(first));
        Assert.assertEquals(1, dataProvider.getAll().size());
    }

    @Test
    public void insertBad() throws IOException {
        Soda first = new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_SODA, "orange", true);

        dataProvider.insert(null);
        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }
}