package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ComputerJdbcDataProviderTest {


    ComputerJdbcDataProvider dataProvider;
    @Before
    public void beforeEach() {
        clear();
        dataProvider = new ComputerJdbcDataProvider();
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
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);
        Computer second = new Computer(1, "Dell gaming MT 5", 2.9, 73400, Constants.CATEGORY_COMPUTER, "amd ryzen x9", 1000, "geforge gtx 2050ti", 100, true, true);

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Computer> all = dataProvider.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(first, all.get(0));
    }

    @Test
    public void getAllBad() throws IOException {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);
        Computer second = new Computer(1, "Dell gaming MT 5", 2.9, 73400, Constants.CATEGORY_COMPUTER, "amd ryzen x9", 1000, "geforge gtx 2050ti", 100, true, true);

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Computer> all = dataProvider.getAll();

        Assert.assertNotEquals(1, all.size());
        Assert.assertNotEquals(second, all.get(0));
    }

    @Test
    public void getById() {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        dataProvider.insert(first);

        Computer fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertEquals(first, fromDb);
    }

    @Test
    public void getByIdBad() {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);


        dataProvider.insert(null);

        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }

    @Test
    public void delete() {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        dataProvider.insert(first);

        List<Computer> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(first);

        List<Computer> fromDbEmpty = dataProvider.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void deleteBad() {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        dataProvider.insert(first);

        List<Computer> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(null);

        List<Computer> fromDbEmpty = dataProvider.getAll();
        Assert.assertFalse(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        dataProvider.insert(first);

        List<Computer> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Computer newMike = new Computer(0, "Dell gaming MT 5", 2.9, 73400, Constants.CATEGORY_COMPUTER, "amd ryzen x9", 1000, "geforge gtx 2050ti", 100, true, true);

        dataProvider.update(newMike);

        List<Computer> fromDbNew = dataProvider.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(first, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }


    @Test
    public void updateBad() {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        dataProvider.insert(first);

        List<Computer> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Computer update = new Computer(1, "Dell gaming MT 5", 2.9, 73400, Constants.CATEGORY_COMPUTER, "amd ryzen x9", 1000, "geforge gtx 2050ti", 100, true, true);

        dataProvider.update(null);

        List<Computer> fromDbNew = dataProvider.getAll();

        Assert.assertNotEquals(2, fromDbNew.size());
        Assert.assertEquals(first, fromDbNew.get(0));
        Assert.assertNotEquals(update, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        dataProvider.insert(first);
        Computer fromDb = dataProvider.getById(first.getId()).get();
        Assert.assertEquals(first, fromDb);

        Assert.assertFalse(dataProvider.insert(first));
        Assert.assertEquals(1, dataProvider.getAll().size());
    }

    @Test
    public void insertBad() throws IOException {
        Computer first = new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        dataProvider.insert(null);
        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }
}