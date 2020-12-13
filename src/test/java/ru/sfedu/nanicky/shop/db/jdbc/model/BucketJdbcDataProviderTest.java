package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Bucket;

import java.util.Optional;

public class BucketJdbcDataProviderTest {

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
        BucketJdbcDataProvider dao = new BucketJdbcDataProvider();
        Bucket model = new Bucket(1, "session", "fridge:1");

        Assert.assertTrue(dao.insert(model));
        Assert.assertFalse(dao.insert(model));

        Bucket modelFromDb = dao.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        BucketJdbcDataProvider dao = new BucketJdbcDataProvider();
        Bucket model = new Bucket(1, "session", "fridge:1");

        Assert.assertTrue(dao.insert(model));

        Optional<Bucket> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        BucketJdbcDataProvider dao = new BucketJdbcDataProvider();
        Bucket model = new Bucket(1, "session", "fridge:1");
        Bucket modelUpdate = new Bucket(1, "session", "fridge:1");
        Assert.assertTrue(dao.insert(model));
        Assert.assertTrue(dao.update(modelUpdate));

        Optional<Bucket> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());
    }
}
