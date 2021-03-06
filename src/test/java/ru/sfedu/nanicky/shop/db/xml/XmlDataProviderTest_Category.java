package ru.sfedu.nanicky.shop.db.xml;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlDataProviderTest_Category {

    private static final File TEST_FILES_FOLDER = new File("tmpTests");
    private static final File DB_FILE = new File(TEST_FILES_FOLDER, "test.xml");
    private XmlDataProvider<Category> dataProvider;

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

        dataProvider = new XmlDataProvider<Category>(DB_FILE);

    }

    @Test
    public void getAll() throws IOException {
        Category mike = new Category(0, Constants.CATEGORY_SODA);
        Category pyke = new Category(1, Constants.CATEGORY_SODA);

        Assert.assertTrue(dataProvider.insert(mike));
        Assert.assertTrue(dataProvider.insert(pyke));
        List<Category> all = dataProvider.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(mike, all.get(0));
    }

    @Test
    public void getById() {
        Category mike = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(mike);

        Category fromDb = dataProvider.getById(mike.getId()).get();

        Assert.assertEquals(mike, fromDb);
    }

    @Test
    public void delete() {
        Category mike = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(mike);

        List<Category> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(mike, fromDb.get(0));

        dataProvider.delete(mike);

        List<Category> fromDbEmpty = dataProvider.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        Category mike = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(mike);

        List<Category> fromDb = dataProvider.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(mike, fromDb.get(0));

        Category newMike = new Category(0, Constants.CATEGORY_COMPUTER);

        dataProvider.update(newMike);

        List<Category> fromDbNew = dataProvider.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(mike, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        Category mike = new Category(0, Constants.CATEGORY_SODA);

        dataProvider.insert(mike);
        Category fromDb = dataProvider.getById(mike.getId()).get();
        Assert.assertEquals(mike, fromDb);

        Assert.assertFalse(dataProvider.insert(mike));
        Assert.assertEquals(1, dataProvider.getAll().size());
    }
}