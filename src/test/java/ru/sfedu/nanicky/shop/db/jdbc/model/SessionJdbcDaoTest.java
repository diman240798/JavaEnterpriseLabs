package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Session;

import java.util.Optional;

public class SessionJdbcDaoTest {

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
        SessionJdbcDao dao = new SessionJdbcDao();
        Session model = new Session(0, "Indesit c30", 30);

        Assert.assertTrue(dao.insert(model));
        Assert.assertFalse(dao.insert(model));

        Session modelFromDb = dao.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        SessionJdbcDao dao = new SessionJdbcDao();
        Session model = new Session(0, "Indesit c30", 30);

        Assert.assertTrue(dao.insert(model));

        Optional<Session> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        SessionJdbcDao dao = new SessionJdbcDao();
        Session model = new Session(0, "Indesit c30", 30);
        Session modelUpdate = new Session(0, "Toshiba m20", 50);

        Assert.assertTrue(dao.insert(model));
        Assert.assertTrue(dao.update(modelUpdate));

        Optional<Session> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
