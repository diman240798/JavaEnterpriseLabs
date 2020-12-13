package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Soda;

import java.util.Optional;

public class SodaJdbcDataProviderTest {

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
        SodaJdbcDataProvider dao = new SodaJdbcDataProvider();
        Soda model = new Soda(0, "Indesit c30", 30, 1, "soda", "apple", true);

        Assert.assertTrue(dao.insert(model));
        Assert.assertFalse(dao.insert(model));

        Soda modelFromDb = dao.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        SodaJdbcDataProvider dao = new SodaJdbcDataProvider();
        Soda model = new Soda(0, "Indesit c30", 30, 1, "soda", "apple", true);

        Assert.assertTrue(dao.insert(model));

        Optional<Soda> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        SodaJdbcDataProvider dao = new SodaJdbcDataProvider();
        Soda model = new Soda(0, "Indesit c30", 30, 1, "soda", "apple", true);
        Soda modelUpdate = new Soda(0, "fanta", 20, 10, "soda", "lemon");

        Assert.assertTrue(dao.insert(model));
        Assert.assertTrue(dao.update(modelUpdate));

        Optional<Soda> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
