package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;

import java.util.Optional;

public class ComputerJdbcDaoTest {

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
        ComputerJdbcDao computerJdbcDao = new ComputerJdbcDao();
        Computer computer = new Computer(15, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        Assert.assertTrue(computerJdbcDao.insert(computer));

        Computer computerFromDb = computerJdbcDao.getAll().get(0);

        Assert.assertEquals(computer, computerFromDb);
    }

    @Test
    public void testGetById() {
        ComputerJdbcDao computerJdbcDao = new ComputerJdbcDao();
        Computer computer = new Computer(15, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        Assert.assertTrue(computerJdbcDao.insert(computer));

        Optional<Computer> computerFromDb = computerJdbcDao.getById(computer.getId());

        Assert.assertTrue(computerFromDb.isPresent());
        Assert.assertEquals(computer, computerFromDb.get());
    }
}