package ru.sfedu.nanicky.getpetshome.db.sql.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.getpetshome.db.protocol.model.Animal;

import java.util.Arrays;
import java.util.List;

public class JdbcDaoTest {

    @Before
    public void clearDataFromDatabase() {
        JdbcDao<Animal> dao = new JdbcDao<>(Animal.class.getName());
        dao.getAll().forEach(dao::delete);
    }

    @Test
    public void upsert() {
        JdbcDao<Animal> dao = new JdbcDao<>(Animal.class.getName());

        Animal mike = new Animal(0, "Mike", Arrays.asList(1L, 2L));
        Animal pyke = new Animal(0, "Pyke", Arrays.asList(3L));

        dao.upsert(mike);

        Animal fromDb = dao.getById(mike.getId());
        Assert.assertEquals(mike, fromDb);

        dao.upsert(pyke);

        Animal fromDbPyke = dao.getById(pyke.getId());
        Assert.assertEquals(pyke, fromDbPyke);
        Assert.assertNotEquals(mike, fromDbPyke);
    }

    @Test
    public void getAll() {
        JdbcDao<Animal> dao = new JdbcDao<>(Animal.class.getName());

        Animal mike = new Animal(0, "Mike", Arrays.asList(1L, 2L));
        Animal mike1 = new Animal(1, "Mike", Arrays.asList(1L, 2L));

        dao.insert(mike);
        dao.insert(mike1);

        List<Animal> fromDb = dao.getAll();
        Assert.assertEquals(mike, fromDb.get(0));
    }

    @Test
    public void delete() {
        JdbcDao<Animal> dao = new JdbcDao<>(Animal.class.getName());

        Animal mike = new Animal(0, "Mike", Arrays.asList(1L, 2L));

        dao.insert(mike);

        List<Animal> fromDb = dao.getAll();
        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(mike, fromDb.get(0));

        dao.delete(mike);

        List<Animal> fromDbEmpty = dao.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());

        Animal pyke = new Animal(0, "Pyke", Arrays.asList(3L));

        dao.insert(pyke);

        Animal fromDbPyke = dao.getById(pyke.getId());
        Assert.assertEquals(pyke, fromDbPyke);
        Assert.assertNotEquals(mike, fromDbPyke);
    }

    @Test
    public void update() {
        JdbcDao<Animal> dao = new JdbcDao<>(Animal.class.getName());

        Animal mike = new Animal(0, "Mike", Arrays.asList(1L, 2L));
        Animal pyke = new Animal(0, "Pyke", Arrays.asList(3L));

        dao.insert(mike);

        Animal fromDb = dao.getById(mike.getId());
        Assert.assertEquals(mike, fromDb);

        dao.update(pyke);

        Animal fromDbPyke = dao.getById(pyke.getId());
        Assert.assertEquals(pyke, fromDbPyke);
        Assert.assertNotEquals(mike, fromDbPyke);
    }

    @Test
    public void insert() {
        JdbcDao<Animal> dao = new JdbcDao<>(Animal.class.getName());

        Animal mike = new Animal(0, "Mike", Arrays.asList(1L, 2L));

        dao.insert(mike);

        Animal fromDb = dao.getById(mike.getId());

        Assert.assertEquals(mike, fromDb);

    }
}