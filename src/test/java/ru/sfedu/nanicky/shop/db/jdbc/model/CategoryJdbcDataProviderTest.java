package ru.sfedu.nanicky.shop.db.jdbc.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

import java.io.IOException;
import java.util.List;

public class CategoryJdbcDataProviderTest {



    CategoryJdbcDataProvider dataProvider;
    @Before
    public void beforeEach() {
        clear();
        dataProvider = new CategoryJdbcDataProvider();
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
    public void getAll() throws IOException {
        Category first = new Category(0, Constants.CATEGORY_SODA);
        Category second = new Category(1, Constants.CATEGORY_SODA);

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Category> all = dataProvider.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(first, all.get(0));
    }

    @Test
    public void getAllBad() throws IOException {
        Category first = new Category(0, Constants.CATEGORY_SODA);
        Category second = new Category(1, Constants.CATEGORY_SODA);

        dataProvider.insert(first);
        dataProvider.insert(second);
        List<Category> all = dataProvider.getAll();

        Assert.assertNotEquals(1, all.size());
        Assert.assertNotEquals(second, all.get(0));
    }

    @Test
    public void getById() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(first);

        Category fromDb = dataProvider.getById(first.getId()).get();

        Assert.assertEquals(first, fromDb);
    }

    @Test
    public void getByIdBad() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(null);

        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }

    @Test
    public void delete() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(first);

        List<Category> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(first);

        List<Category> fromDbEmpty = dataProvider.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void deleteBad() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(first);

        List<Category> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dataProvider.delete(null);

        List<Category> fromDbEmpty = dataProvider.getAll();
        Assert.assertFalse(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(first);

        List<Category> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Category newMike = new Category(0, Constants.CATEGORY_COMPUTER);

        dataProvider.update(newMike);

        List<Category> fromDbNew = dataProvider.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(first, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }


    @Test
    public void updateBad() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(first);

        List<Category> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Category update = new Category(1, Constants.CATEGORY_COMPUTER);

        dataProvider.update(null);

        List<Category> fromDbNew = dataProvider.getAll();

        Assert.assertNotEquals(2, fromDbNew.size());
        Assert.assertEquals(first, fromDbNew.get(0));
        Assert.assertNotEquals(update, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(first);
        Category fromDb = dataProvider.getById(first.getId()).get();
        Assert.assertEquals(first, fromDb);

        Assert.assertFalse(dataProvider.insert(first));
        Assert.assertEquals(1, dataProvider.getAll().size());
    }

    @Test
    public void insertBad() throws IOException {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(null);
        Assert.assertFalse(dataProvider.getById(first.getId()).isPresent());
    }
}
