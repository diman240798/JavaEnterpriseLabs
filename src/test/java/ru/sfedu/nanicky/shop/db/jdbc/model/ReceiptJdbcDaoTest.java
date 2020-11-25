package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Receipt;

import java.util.Optional;

public class ReceiptJdbcDaoTest {

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
        ReceiptJdbcDao dao = new ReceiptJdbcDao();
        Receipt model = new Receipt(0, "Indesit c30", 30);

        Assert.assertTrue(dao.insert(model));

        Receipt modelFromDb = dao.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        ReceiptJdbcDao dao = new ReceiptJdbcDao();
        Receipt model = new Receipt(0, "Indesit c30", 30);

        Assert.assertTrue(dao.insert(model));

        Optional<Receipt> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        ReceiptJdbcDao dao = new ReceiptJdbcDao();
        Receipt model = new Receipt(0, "Indesit c30", 30);
        Receipt modelUpdate = new Receipt(0, "Toshiba m20", 50);

        Assert.assertTrue(dao.insert(model));
        Assert.assertTrue(dao.update(modelUpdate));

        Optional<Receipt> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
