package ru.sfedu.nanicky.shop.api.initializer;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;

import java.io.File;
import java.util.Objects;

public class InitializerTest {

    @Before
    public void beforeEach() {
        clear();
    }

    @AfterClass
    public static void afterAll() {
        clear();
    }

    private static void clear() {
        Constants.DB_FILES.forEach(File::delete);
    }

    @Test
    public void initForCSV() {
        Reposotiries repositories = new Reposotiries();
        Initializer.initFor(Constants.CSV, repositories);

        assertCsv(repositories);
    }

    private void assertCsv(Reposotiries repositories) {
        Assert.assertTrue(Objects.deepEquals(repositories.categoryCsvDao.getAll(), Initializer.CATEGORIES));
        Assert.assertTrue(Objects.deepEquals(repositories.computerCsvDao.getAll(), Initializer.COMPUTERS));
        Assert.assertTrue(Objects.deepEquals(repositories.sodaCsvDao.getAll(), Initializer.SODA));
        Assert.assertTrue(Objects.deepEquals(repositories.fridgeCsvDao.getAll(), Initializer.FRIDGES));
    }

    @Test
    public void initForXML() {
        Reposotiries repositories = new Reposotiries();
        Initializer.initFor(Constants.XML, repositories);

        assertXml(repositories);
    }

    private void assertXml(Reposotiries repositories) {
        Assert.assertTrue(Objects.deepEquals(repositories.categoryXmlDao.getAll(), Initializer.CATEGORIES));
        Assert.assertTrue(Objects.deepEquals(repositories.computerXmlDao.getAll(), Initializer.COMPUTERS));
        Assert.assertTrue(Objects.deepEquals(repositories.sodaXmlDao.getAll(), Initializer.SODA));
        Assert.assertTrue(Objects.deepEquals(repositories.fridgeXmlDao.getAll(), Initializer.FRIDGES));
    }

    @Test
    public void initForJDBC() {
        Reposotiries repositories = new Reposotiries();
        Initializer.initFor(Constants.JDBC, repositories);

        assertJdbc(repositories);
    }

    private void assertJdbc(Reposotiries repositories) {
        Assert.assertTrue(Objects.deepEquals(repositories.categoryJdbcDao.getAll(), Initializer.CATEGORIES));
        Assert.assertTrue(Objects.deepEquals(repositories.computerJdbcDao.getAll(), Initializer.COMPUTERS));
        Assert.assertTrue(Objects.deepEquals(repositories.sodaJdbcDao.getAll(), Initializer.SODA));
        Assert.assertTrue(Objects.deepEquals(repositories.fridgeJdbcDao.getAll(), Initializer.FRIDGES));
    }

    @Test
    public void initForHIBERNATE() {

    }

    @Test
    public void initAll() {
        Reposotiries repositories = new Reposotiries();
        Initializer.initAll(repositories);

        assertCsv(repositories);
        assertXml(repositories);
        assertJdbc(repositories);
    }
}