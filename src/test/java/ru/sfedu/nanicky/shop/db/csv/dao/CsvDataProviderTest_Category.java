package ru.sfedu.nanicky.shop.db.csv.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CsvDataProviderTest_Category {

    private static final File TEST_FILES_FOLDER = new File("tmpTests");
    private static final File DB_FILE = new File(TEST_FILES_FOLDER, "test.csv");
    private CsvDataProvider<Category> dao;

    @Before
    public void beforeEach() throws IOException {

        if (!TEST_FILES_FOLDER.exists()) {
            if (!TEST_FILES_FOLDER.mkdirs()) {
                throw new RuntimeException("Cant create test dir: " + TEST_FILES_FOLDER.getAbsolutePath());
            }
        }

        if (DB_FILE.exists()) {
            if (!DB_FILE.delete()) {
                throw new RuntimeException("Cant delete file: " + DB_FILE.getAbsolutePath());
            }
        }
        DB_FILE.createNewFile();

        dao = new CsvDataProvider<>(Category.class, DB_FILE);

    }

    @Test
    public void getAll() throws IOException {
        Category first = new Category(0, Constants.CATEGORY_SODA);
        Category second = new Category(1, Constants.CATEGORY_SODA);

        dao.insert(first);
        dao.insert(second);
        List<Category> all = dao.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(first, all.get(0));
    }

    @Test
    public void getAllBad() throws IOException {
        Category first = new Category(0, Constants.CATEGORY_SODA);
        Category second = new Category(1, Constants.CATEGORY_SODA);

        dao.insert(first);
        dao.insert(second);
        List<Category> all = dao.getAll();

        Assert.assertNotEquals(1, all.size());
        Assert.assertNotEquals(second, all.get(0));
    }

    @Test
    public void getById() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(first);

        Category fromDb = dao.getById(first.getId()).get();

        Assert.assertEquals(first, fromDb);
    }

    @Test
    public void getByIdBad() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(first);

        Category fromDb = dao.getById(first.getId()).get();

        Assert.assertNotEquals(first.getName() + "not equals", fromDb.getName());
    }

    @Test
    public void delete() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(first);

        List<Category> fromDb = dao.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dao.delete(first);

        List<Category> fromDbEmpty = dao.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void deleteBad() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(first);

        List<Category> fromDb = dao.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        dao.delete(null);

        List<Category> fromDbEmpty = dao.getAll();
        Assert.assertFalse(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(first);

        List<Category> fromDb = dao.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Category newMike = new Category(0, Constants.CATEGORY_COMPUTER);

        dao.update(newMike);

        List<Category> fromDbNew = dao.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(first, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }


    @Test
    public void updateBad() {
        Category first = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(first);

        List<Category> fromDb = dao.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(first, fromDb.get(0));

        Category update = new Category(0, Constants.CATEGORY_COMPUTER);

        dao.update(null);

        List<Category> fromDbNew = dao.getAll();

        Assert.assertNotEquals(2, fromDbNew.size());
        Assert.assertEquals(first, fromDbNew.get(0));
        Assert.assertNotEquals(update, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Category mike = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(mike);
        Category fromDb = dao.getById(mike.getId()).get();
        Assert.assertEquals(mike, fromDb);

        Assert.assertFalse(dao.insert(mike));
        Assert.assertEquals(1, dao.getAll().size());
    }

    @Test
    public void insertBad() throws IOException {
        Category mike = new Category(0, Constants.CATEGORY_SODA);

        dao.insert(null);
        Assert.assertFalse(dao.getById(mike.getId()).isPresent());
    }
}