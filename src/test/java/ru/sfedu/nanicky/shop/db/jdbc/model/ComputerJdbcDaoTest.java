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
        ComputerJdbcDao dao = new ComputerJdbcDao();
        Computer model = new Computer(15, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        Assert.assertTrue(dao.insert(model));
        Assert.assertFalse(dao.insert(model));

        Computer computerFromDb = dao.getAll().get(0);

        Assert.assertEquals(model, computerFromDb);
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

    @Test
    public void testUpdate() {
        ComputerJdbcDao computerJdbcDao = new ComputerJdbcDao();
        Computer computer = new Computer(15, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);
        Computer computerUpdate = new Computer(15, "Hp pavillion MH 5", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100);

        Assert.assertTrue(computerJdbcDao.insert(computer));
        Assert.assertTrue(computerJdbcDao.update(computerUpdate));

        Optional<Computer> computerFromDb = computerJdbcDao.getById(computer.getId());

        Assert.assertTrue(computerFromDb.isPresent());
        Assert.assertEquals(computerUpdate, computerFromDb.get());


    }
}