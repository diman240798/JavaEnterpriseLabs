package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

import java.util.Optional;

public class CategoryJdbcDataProviderTest {

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
        CategoryJdbcDataProvider dataProvider = new CategoryJdbcDataProvider();
        Category model = new Category(1, "category");

        Assert.assertTrue(dataProvider.insert(model));
        Assert.assertFalse(dataProvider.insert(model));

        Category modelFromDb = dataProvider.getAll().get(0);

        Assert.assertEquals(model, modelFromDb);
    }

    @Test
    public void testGetById() {
        CategoryJdbcDataProvider dataProvider = new CategoryJdbcDataProvider();
        Category model = new Category(1, "category");

        Assert.assertTrue(dataProvider.insert(model));

        Optional<Category> modelFromDb = dataProvider.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(model, modelFromDb.get());
    }

    @Test
    public void testUpdate() {
        CategoryJdbcDataProvider jdbcDataProvider = new CategoryJdbcDataProvider();
        Category model = new Category(1, "category");
        Category modelUpdate = new Category(1, "category_new");

        Assert.assertTrue(jdbcDataProvider.insert(model));
        Assert.assertTrue(jdbcDataProvider.update(modelUpdate));

        Optional<Category> modelFromDb = jdbcDataProvider.getById(model.getId());

        Assert.assertTrue(modelFromDb.isPresent());
        Assert.assertEquals(modelUpdate, modelFromDb.get());


    }
}
