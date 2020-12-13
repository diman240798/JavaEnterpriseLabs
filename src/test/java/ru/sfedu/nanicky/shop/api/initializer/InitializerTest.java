package ru.sfedu.nanicky.shop.api.initializer;

import org.junit.*;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public class InitializerTest {

    @BeforeClass
    public static void init() {
        Constants.init();
    }

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
        Repositories repositories = new Repositories();
        Initializer.initFor(Constants.CSV, repositories);

        assertCsv(repositories);
    }


    private void assertCsv(Repositories repositories) {
        Assert.assertTrue(Objects.deepEquals(repositories.categoryCsvDataProvider.getAll(), Initializer.CATEGORIES));
        Assert.assertTrue(Objects.deepEquals(repositories.computerCsvDataProvider.getAll(), Initializer.COMPUTERS));
        Assert.assertTrue(Objects.deepEquals(repositories.sodaCsvDataProvider.getAll(), Initializer.SODA));
        Assert.assertTrue(Objects.deepEquals(repositories.fridgeCsvDataProvider.getAll(), Initializer.FRIDGES));
    }


    @Test
    public void initForCSVBad() {
        Repositories repositories = new Repositories();
        Initializer.initFor(Constants.CSV, repositories);

        assertCsvBad(repositories);
    }

    private void assertCsvBad(Repositories repositories) {
        Assert.assertFalse(Objects.deepEquals(repositories.categoryCsvDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.categoryCsvDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.computerCsvDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.computerCsvDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.sodaCsvDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.sodaCsvDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.fridgeCsvDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.fridgeCsvDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
    }

    @Test
    public void initForXML() {
        Repositories repositories = new Repositories();
        Initializer.initFor(Constants.XML, repositories);

        assertXml(repositories);
    }

    private void assertXml(Repositories repositories) {
        Assert.assertTrue(Objects.deepEquals(repositories.categoryXmlDataProvider.getAll(), Initializer.CATEGORIES));
        Assert.assertTrue(Objects.deepEquals(repositories.computerXmlDataProvider.getAll(), Initializer.COMPUTERS));
        Assert.assertTrue(Objects.deepEquals(repositories.sodaXmlDataProvider.getAll(), Initializer.SODA));
        Assert.assertTrue(Objects.deepEquals(repositories.fridgeXmlDataProvider.getAll(), Initializer.FRIDGES));
    }

    @Test
    public void initForXMLBad() {
        Repositories repositories = new Repositories();
        Initializer.initFor(Constants.XML, repositories);

        assertXmlBad(repositories);
    }

    private void assertXmlBad(Repositories repositories) {
        Assert.assertFalse(Objects.deepEquals(repositories.categoryXmlDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.categoryXmlDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.computerXmlDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.computerXmlDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.sodaXmlDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.sodaXmlDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.fridgeXmlDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.fridgeXmlDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
    }

    @Test
    public void initForJDBC() {
        Repositories repositories = new Repositories();
        Initializer.initFor(Constants.JDBC, repositories);

        assertJdbc(repositories);
    }

    private void assertJdbc(Repositories repositories) {
        Assert.assertTrue(Objects.deepEquals(repositories.categoryJdbcDataProvider.getAll(), Initializer.CATEGORIES));
        Assert.assertTrue(Objects.deepEquals(repositories.computerJdbcDataProvider.getAll(), Initializer.COMPUTERS));
        Assert.assertTrue(Objects.deepEquals(repositories.sodaJdbcDataProvider.getAll(), Initializer.SODA));
        Assert.assertTrue(Objects.deepEquals(repositories.fridgeJdbcDataProvider.getAll(), Initializer.FRIDGES));
    }


    @Test
    public void initForJDBCBad() {
        Repositories repositories = new Repositories();
        Initializer.initFor(Constants.JDBC, repositories);

        assertJdbcBad(repositories);
    }

    private void assertJdbcBad(Repositories repositories) {
        Assert.assertFalse(Objects.deepEquals(repositories.categoryJdbcDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.categoryJdbcDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.computerJdbcDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.computerJdbcDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.sodaJdbcDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.sodaJdbcDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(repositories.fridgeJdbcDataProvider.getAll(), Arrays.asList()));
        Assert.assertFalse(Objects.deepEquals(repositories.fridgeJdbcDataProvider.getAll(), Arrays.asList(null, null, null, null, null)));
    }

    @Test
    public void initAll() {
        Repositories repositories = new Repositories();
        Initializer.initAll(repositories);

        assertCsv(repositories);
        assertXml(repositories);
        assertJdbc(repositories);
    }

    @Test
    public void initAllBad() {
        Repositories repositories = new Repositories();
        Initializer.initAll(repositories);

        assertCsvBad(repositories);
        assertXmlBad(repositories);
        assertJdbcBad(repositories);
    }
}