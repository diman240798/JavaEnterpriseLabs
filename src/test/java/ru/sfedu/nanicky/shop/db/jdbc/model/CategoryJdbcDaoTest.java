package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

import java.util.Optional;

public class CategoryJdbcDaoTest {

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
        CategoryJdbcDao dao = new CategoryJdbcDao();
        Category model = new Category(1, "category");

        Assert.assertTrue(dao.insert(model));

        Category modelFromDb = dao.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        CategoryJdbcDao dao = new CategoryJdbcDao();
        Category model = new Category(1, "category");

        Assert.assertTrue(dao.insert(model));

        Optional<Category> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        CategoryJdbcDao dao = new CategoryJdbcDao();
        Category model = new Category(1, "category");
        Category modelUpdate = new Category(1, "category_new");

        Assert.assertTrue(dao.insert(model));
        Assert.assertTrue(dao.update(modelUpdate));

        Optional<Category> modelFromDb = dao.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
