package ru.sfedu.nanicky.getpetshome.db.xml.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.getpetshome.db.protocol.modeltext.AnimalText;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlDaoTest_AnimalText {

    private static final File TEST_FILES_FOLDER = new File("tmpTests");
    private static final File DB_FILE = new File(TEST_FILES_FOLDER, "test.xml");
    private XmlDao<AnimalText> dao;

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

        dao = new XmlDao<AnimalText>(DB_FILE);

    }

    @Test
    public void getAll() throws IOException {
        AnimalText mike = new AnimalText(0, "Mike", "[1L, 2L, 3L]");

        Assert.assertTrue(dao.insert(mike));
        Assert.assertTrue(dao.insert(mike));
        List<AnimalText> all = dao.getAll();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(mike, all.get(0));
    }

    @Test
    public void getById() {
        AnimalText mike = new AnimalText(0, "Mike", "[1, 2, 3]");

        dao.insert(mike);

        AnimalText fromDb = dao.getById(mike.getId());

        Assert.assertEquals(mike, fromDb);
    }

    @Test
    public void delete() {
        AnimalText mike = new AnimalText(0, "Mike", "[1, 2, 3]");

        dao.insert(mike);

        List<AnimalText> fromDb = dao.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(mike, fromDb.get(0));

        dao.delete(mike);

        List<AnimalText> fromDbEmpty = dao.getAll();
        Assert.assertTrue(fromDbEmpty.isEmpty());
    }

    @Test
    public void update() {
        AnimalText mike = new AnimalText(0, "Mike", "[1, 2, 3]");

        dao.insert(mike);

        List<AnimalText> fromDb = dao.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(mike, fromDb.get(0));

        AnimalText newMike = new AnimalText(0, "Nick", "[6]");

        dao.update(newMike);

        List<AnimalText> fromDbNew = dao.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(mike, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }

    @Test
    public void insert() throws IOException {
        AnimalText mike = new AnimalText(0, "Mike", "[1L, 2L, 3L]");

        dao.insert(mike);
        AnimalText fromDb = dao.getById(mike.getId());
        Assert.assertEquals(mike, fromDb);
    }

    @Test
    public void upsert() {
        AnimalText mike = new AnimalText(0, "Mike", "[1, 2, 3]");

        dao.upsert(mike);

        List<AnimalText> fromDb = dao.getAll();

        Assert.assertEquals(1, fromDb.size());
        Assert.assertEquals(mike, fromDb.get(0));

        AnimalText newMike = new AnimalText(0, "Nick", "[6]");

        dao.upsert(newMike);

        List<AnimalText> fromDbNew = dao.getAll();

        Assert.assertEquals(1, fromDbNew.size());
        Assert.assertNotEquals(mike, fromDbNew.get(0));
        Assert.assertEquals(newMike, fromDbNew.get(0));
    }
}