package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FridgeJdbcDataProviderTest {

    FridgeJdbcDataProvider dataProvider;

    @Before
    public void beforeEach() {
        clear();
        dataProvider = new FridgeJdbcDataProvider();
    }

    @AfterClass
    public static void afterAll() {
        clear();
    }

    private static void clear() {
        Constants.JDBC_MV.delete();
        Constants.JDBC_TRACE.delete();
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


        dataProvider.insert(null);

        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
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
