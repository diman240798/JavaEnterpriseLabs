package ru.sfedu.nanicky.shop.db.csv;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class FridgeCsvDataProviderTest {
    private static final File TEST_FILES_FOLDER = new File("tmpTests");
    private static final File DB_FILE = new File(TEST_FILES_FOLDER, "fridge.csv");
    private FridgeCsvDataProvider dataProvider;

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

        dataProvider = new FridgeCsvDataProvider(DB_FILE);

    }

    @Test
    public void getAll() throws IOException {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);
        Fridge second = new Fridge(1, "Toshiba a52", 50, 60650, Constants.CATEGORY_FRIDGE, 100, "gray", 600, true);

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Fridge> all = dataProvider.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(first, all.get(0));
    }

    @Test
    public void getAllBad() throws IOException {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);
        Fridge second = new Fridge(1, "Toshiba a52", 50, 60650, Constants.CATEGORY_FRIDGE, 100, "gray", 600, true);

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Fridge> all = dataProvider.getAll();

        Assert.assertNotEquals(1, all.size());
        Assert.assertNotEquals(second, all.get(0));
    }

    @Test
    public void getById() {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        dataProvider.insert(first);

        Fridge fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertEquals(first, fromDb);
    }

    @Test
    public void getByIdBad() {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        dataProvider.insert(first);

        Fridge fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertNotEquals(first.getId() + "not equals", fromDb.getId());
    }

    @Test
    public void delete() {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        dataProvider.insert(first);

        List<Fridge> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(first);

        List<Fridge> fromDbEmpty = dataProvider.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void deleteBad() {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        dataProvider.insert(first);

        List<Fridge> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(null);

        List<Fridge> fromDbEmpty = dataProvider.getAll();
        Assert.assertFalse(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        dataProvider.insert(first);

        List<Fridge> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Fridge newMike = new Fridge(0, "Toshiba a52", 50, 60650, Constants.CATEGORY_FRIDGE, 100, "gray", 600, true);

        dataProvider.update(newMike);

        List<Fridge> fromDbNew = dataProvider.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(first, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }


    @Test
    public void updateBad() {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        dataProvider.insert(first);

        List<Fridge> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Fridge update = new Fridge(1, "Toshiba a52", 50, 60650, Constants.CATEGORY_FRIDGE, 100, "gray", 600, true);

        dataProvider.update(null);

        List<Fridge> fromDbNew = dataProvider.getAll();

        Assert.assertNotEquals(2, fromDbNew.size());
        Assert.assertEquals(first, fromDbNew.get(0));
        Assert.assertNotEquals(update, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        dataProvider.insert(first);
        Fridge fromDb = dataProvider.getById(first.getId()).get();
        Assert.assertEquals(first, fromDb);

        Assert.assertFalse(dataProvider.insert(first));
        Assert.assertEquals(1, dataProvider.getAll().size());
    }

    @Test
    public void insertBad() throws IOException {
        Fridge first = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        dataProvider.insert(null);
        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }
}