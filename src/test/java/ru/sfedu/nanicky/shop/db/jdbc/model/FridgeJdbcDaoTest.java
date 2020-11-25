package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;

import java.util.Optional;

public class FridgeJdbcDaoTest {

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
        FridgeJdbcDao dao = new FridgeJdbcDao();
        Fridge model = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        Assert.assertTrue(dao.insert(model));

        Fridge modelFromDb = dao.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        FridgeJdbcDao dao = new FridgeJdbcDao();
        Fridge model = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        Assert.assertTrue(dao.insert(model));

        Optional<Fridge> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        FridgeJdbcDao dao = new FridgeJdbcDao();
        Fridge model = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);
        Fridge modelUpdate = new Fridge(0, "Toshiba a52", 50, 60650, Constants.CATEGORY_FRIDGE, 100, "gray", 600, true);

        Assert.assertTrue(dao.insert(model));
        Assert.assertTrue(dao.update(modelUpdate));

        Optional<Fridge> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
