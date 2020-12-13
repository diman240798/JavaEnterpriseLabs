package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;

import java.util.Optional;

public class FridgeJdbcDataProviderTest {

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
        FridgeJdbcDataProvider dataProvider = new FridgeJdbcDataProvider();
        Fridge model = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        Assert.assertTrue(dataProvider.insert(model));
        Assert.assertFalse(dataProvider.insert(model));

        Fridge modelFromDb = dataProvider.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        FridgeJdbcDataProvider dataProvider = new FridgeJdbcDataProvider();
        Fridge model = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);

        Assert.assertTrue(dataProvider.insert(model));

        Optional<Fridge> modelFromDb = dataProvider.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        FridgeJdbcDataProvider dataProvider = new FridgeJdbcDataProvider();
        Fridge model = new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_FRIDGE, 50, "white", 300);
        Fridge modelUpdate = new Fridge(0, "Toshiba a52", 50, 60650, Constants.CATEGORY_FRIDGE, 100, "gray", 600, true);

        Assert.assertTrue(dataProvider.insert(model));
        Assert.assertTrue(dataProvider.update(modelUpdate));

        Optional<Fridge> modelFromDb = dataProvider.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
