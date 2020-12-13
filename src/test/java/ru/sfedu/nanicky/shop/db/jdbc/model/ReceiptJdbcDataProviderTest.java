package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.util.Optional;

public class ReceiptJdbcDataProviderTest {

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
        ReceiptJdbcDataProvider dataProvider = new ReceiptJdbcDataProvider();
        Receipt model = new Receipt(0, "Indesit c30", 30);

        Assert.assertTrue(dataProvider.insert(model));
        Assert.assertFalse(dataProvider.insert(model));

        Receipt modelFromDb = dataProvider.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        ReceiptJdbcDataProvider dataProvider = new ReceiptJdbcDataProvider();
        Receipt model = new Receipt(0, "Indesit c30", 30);

        Assert.assertTrue(dataProvider.insert(model));

        Optional<Receipt> modelFromDb = dataProvider.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        ReceiptJdbcDataProvider dataProvider = new ReceiptJdbcDataProvider();
        Receipt model = new Receipt(0, "Indesit c30", 30);
        Receipt modelUpdate = new Receipt(0, "Toshiba m20", 50);

        Assert.assertTrue(dataProvider.insert(model));
        Assert.assertTrue(dataProvider.update(modelUpdate));

        Optional<Receipt> modelFromDb = dataProvider.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
