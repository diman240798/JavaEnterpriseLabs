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
        SodaJdbcDataProvider dataProvider = new SodaJdbcDataProvider();
        Soda model = new Soda(0, "Indesit c30", 30, 1, "soda", "apple", true);

        Assert.assertTrue(dataProvider.insert(model));
        Assert.assertFalse(dataProvider.insert(model));

        Soda modelFromDb = dataProvider.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        SodaJdbcDataProvider dataProvider = new SodaJdbcDataProvider();
        Soda model = new Soda(0, "Indesit c30", 30, 1, "soda", "apple", true);

        Assert.assertTrue(dataProvider.insert(model));

        Optional<Soda> modelFromDb = dataProvider.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        SodaJdbcDataProvider dataProvider = new SodaJdbcDataProvider();
        Soda model = new Soda(0, "Indesit c30", 30, 1, "soda", "apple", true);
        Soda modelUpdate = new Soda(0, "fanta", 20, 10, "soda", "lemon");

        Assert.assertTrue(dataProvider.insert(model));
        Assert.assertTrue(dataProvider.update(modelUpdate));

        Optional<Soda> modelFromDb = dataProvider.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
