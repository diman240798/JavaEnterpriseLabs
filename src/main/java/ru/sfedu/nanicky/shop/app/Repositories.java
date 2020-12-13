package ru.sfedu.nanicky.shop.app;

import ru.sfedu.nanicky.shop.db.csv.dao.CsvDao;
import ru.sfedu.nanicky.shop.db.jdbc.model.*;
import ru.sfedu.nanicky.shop.db.protocol.model.*;
import ru.sfedu.nanicky.shop.db.xml.dao.XmlDao;

public class Repositories {
    public final CsvDao<Category> categoryCsvDao = new CsvDao<>(Category.class, Constants.CATEGORY_CSV_FILE);
    public final XmlDao<Category> categoryXmlDao = new XmlDao<>(Constants.CATEGORY_XML_FILE);
    public CategoryJdbcDataProvider categoryJdbcDataProvider = new CategoryJdbcDataProvider();

    public final CsvDao<Soda> sodaCsvDao = new CsvDao<>(Soda.class, Constants.SODA_CSV_FILE);
    public final XmlDao<Soda> sodaXmlDao = new XmlDao<>(Constants.SODA_XML_FILE);
    public SodaJdbcDataProvider sodaJdbcDataProvider = new SodaJdbcDataProvider();

    public final CsvDao<Computer> computerCsvDao = new CsvDao<>(Computer.class, Constants.COMPUTER_CSV_FILE);
    public final XmlDao<Computer> computerXmlDao = new XmlDao<>(Constants.COMPUTER_XML_FILE);
    public final ComputerJdbcDataProvider computerJdbcDataProvider = new ComputerJdbcDataProvider();

    public final CsvDao<Fridge> fridgeCsvDao = new CsvDao<>(Fridge.class, Constants.FRIDGE_CSV_FILE);
    public final XmlDao<Fridge> fridgeXmlDao = new XmlDao<>(Constants.FRIDGE_XML_FILE);
    public FridgeJdbcDataProvider fridgeJdbcDataProvider = new FridgeJdbcDataProvider();

    public final CsvDao<Bucket> bucketCsvDao = new CsvDao<>(Bucket.class, Constants.BUCKET_CSV_FILE);
    public final XmlDao<Bucket> bucketXmlDao = new XmlDao<>(Constants.BUCKET_XML_FILE);
    public BucketJdbcDataProvider bucketJdbcDataProvider = new BucketJdbcDataProvider();

    public final CsvDao<Session> sessionCsvDao = new CsvDao<>(Session.class, Constants.SESSION_CSV_FILE);
    public final XmlDao<Session> sessionXmlDao = new XmlDao<>(Constants.SESSION_XML_FILE);
    public SessionJdbcDataProvider sessionJdbcDataProvider = new SessionJdbcDataProvider();

    public final CsvDao<Receipt> receiptCsvDao = new CsvDao<>(Receipt.class, Constants.RECEIPT_CSV_FILE);
    public final XmlDao<Receipt> receiptXmlDao = new XmlDao<>(Constants.RECEIPT_XML_FILE);
    public ReceiptJdbcDataProvider receiptJdbcDataProvider = new ReceiptJdbcDataProvider();

}
