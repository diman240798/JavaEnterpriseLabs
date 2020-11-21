package ru.sfedu.nanicky.shop.db.hibernate.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

import java.util.List;

public class HibernateDaoTest {

    @Before
    public void clearDataFromDatabase() {
        HibernateDao<Category> dao = new HibernateDao<>(Category.class.getName());
        dao.getAll().forEach(dao::delete);
    }

    @Test
    public void upsert() {
        HibernateDao<Category> dao = new HibernateDao<>(Category.class.getName());

        Category mike = new Category(0, Constants.CATEGORY_SODA);
        Category pyke = new Category(0, Constants.CATEGORY_COMPUTER);

        dao.upsert(mike);

        Category fromDb = dao.getById(mike.getId()).get();
        Assert.assertEquals(mike, fromDb);

        dao.upsert(pyke);

        Category fromDbPyke = dao.getById(pyke.getId()).get();
        Assert.assertEquals(pyke, fromDbPyke);
        Assert.assertNotEquals(mike, fromDbPyke);
    }

    @Test
    public void getAll() {
        HibernateDao<Category> dao = new HibernateDao<>(Category.class.getName());

        Category mike = new Category(0, Constants.CATEGORY_SODA);
        Category mike1 = new Category(0, Constants.CATEGORY_COMPUTER);

        dao.insert(mike);
        dao.insert(mike1);

        List<Category> fromDb = dao.getAll();
        Assert.assertEquals(mike, fromDb.get(0));
    }

    @Test
    public void delete() {
        HibernateDao<Category> dao = new HibernateDao<>(Category.class.getName());

        Category mike = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(mike);

        List<Category> fromDb = dao.getAll();
        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(mike, fromDb.get(0));

        dao.delete(mike);

        List<Category> fromDbEmpty = dao.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());

        Category pyke = new Category(0, Constants.CATEGORY_COMPUTER);

        dao.insert(pyke);

        Category fromDbPyke = dao.getById(pyke.getId()).get();
        Assert.assertEquals(pyke, fromDbPyke);
        Assert.assertNotEquals(mike, fromDbPyke);
    }

    @Test
    public void update() {
        HibernateDao<Category> dao = new HibernateDao<>(Category.class.getName());

        Category mike = new Category(0, Constants.CATEGORY_SODA);
        Category pyke = new Category(0, Constants.CATEGORY_COMPUTER);

        dao.insert(mike);

        Category fromDb = dao.getById(mike.getId()).get();
        Assert.assertEquals(mike, fromDb);

        dao.update(pyke);

        Category fromDbPyke = dao.getById(pyke.getId()).get();
        Assert.assertEquals(pyke, fromDbPyke);
        Assert.assertNotEquals(mike, fromDbPyke);
    }

    @Test
    public void insert() {
        HibernateDao<Category> dao = new HibernateDao<>(Category.class.getName());

        Category mike = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(mike);

        Category fromDb = dao.getById(mike.getId()).get();

        Assert.assertEquals(mike, fromDb);

    }
}