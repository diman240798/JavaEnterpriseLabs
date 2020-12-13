package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;

import java.util.Optional;

public class ComputerJdbcDataProviderTest {

    @Before
    public void beforeEach() {
        clear();
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
    public void testGetAll() {
        ComputerJdbcDataProvider dao = new ComputerJdbcDataProvider();
        Computer model = new Computer(15, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        Assert.assertTrue(dao.insert(model));
        Assert.assertFalse(dao.insert(model));

        Computer computerFromDb = dao.getAll().get(0);

        Assert.assertEquals(model, computerFromDb);
    }

    @Test
    public void testGetById() {
        ComputerJdbcDataProvider computerJdbcDataProvider = new ComputerJdbcDataProvider();
        Computer computer = new Computer(15, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        Assert.assertTrue(computerJdbcDataProvider.insert(computer));

        Optional<Computer> computerFromDb = computerJdbcDataProvider.getById(computer.getId());

        Assert.assertTrue(computerFromDb.isPresent());
        Assert.assertEquals(computer, computerFromDb.get());
    }

    @Test
    public void testUpdate() {
        ComputerJdbcDataProvider computerJdbcDataProvider = new ComputerJdbcDataProvider();
        Computer computer = new Computer(15, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);
        Computer computerUpdate = new Computer(15, "Hp pavillion MH 5", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        Assert.assertTrue(computerJdbcDataProvider.insert(computer));
        Assert.assertTrue(computerJdbcDataProvider.update(computerUpdate));

        Optional<Computer> computerFromDb = computerJdbcDataProvider.getById(computer.getId());

        Assert.assertTrue(computerFromDb.isPresent());
        Assert.assertEquals(computerUpdate, computerFromDb.get());


    }
}