package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ReceiptJdbcDataProviderTest {

    ReceiptJdbcDataProvider dataProvider;

    @Before
    public void beforeEach() {
        clear();
        dataProvider = new ReceiptJdbcDataProvider();
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


        dataProvider.insert(null);

        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
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
