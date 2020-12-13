package ru.sfedu.nanicky.shop.app;

import ru.sfedu.nanicky.shop.db.csv.dao.CsvDataProvider;
import ru.sfedu.nanicky.shop.db.jdbc.model.*;
import ru.sfedu.nanicky.shop.db.protocol.model.*;
import ru.sfedu.nanicky.shop.db.xml.dao.XmlDataProvider;

public class Repositories {

    public final CsvDataProvider<Category> categoryCsvDataProvider = new CsvDataProvider<>(Category.class, Constants.CATEGORY_CSV_FILE);
    public final XmlDataProvider<Category> categoryXmlDataProvider = new XmlDataProvider<>(Constants.CATEGORY_XML_FILE);
    public CategoryJdbcDataProvider categoryJdbcDataProvider = new CategoryJdbcDataProvider();

    public final CsvDataProvider<Soda> sodaCsvDataProvider = new CsvDataProvider<>(Soda.class, Constants.SODA_CSV_FILE);
    public final XmlDataProvider<Soda> sodaXmlDataProvider = new XmlDataProvider<>(Constants.SODA_XML_FILE);
    public SodaJdbcDataProvider sodaJdbcDataProvider = new SodaJdbcDataProvider();

    public final CsvDataProvider<Computer> computerCsvDataProvider = new CsvDataProvider<>(Computer.class, Constants.COMPUTER_CSV_FILE);
    public final XmlDataProvider<Computer> computerXmlDataProvider = new XmlDataProvider<>(Constants.COMPUTER_XML_FILE);
    public final ComputerJdbcDataProvider computerJdbcDataProvider = new ComputerJdbcDataProvider();

    public final CsvDataProvider<Fridge> fridgeCsvDataProvider = new CsvDataProvider<>(Fridge.class, Constants.FRIDGE_CSV_FILE);
    public final XmlDataProvider<Fridge> fridgeXmlDataProvider = new XmlDataProvider<>(Constants.FRIDGE_XML_FILE);
    public FridgeJdbcDataProvider fridgeJdbcDataProvider = new FridgeJdbcDataProvider();

    public final CsvDataProvider<Bucket> bucketCsvDataProvider = new CsvDataProvider<>(Bucket.class, Constants.BUCKET_CSV_FILE);
    public final XmlDataProvider<Bucket> bucketXmlDataProvider = new XmlDataProvider<>(Constants.BUCKET_XML_FILE);
    public BucketJdbcDataProvider bucketJdbcDataProvider = new BucketJdbcDataProvider();

    public final CsvDataProvider<Session> sessionCsvDataProvider = new CsvDataProvider<>(Session.class, Constants.SESSION_CSV_FILE);
    public final XmlDataProvider<Session> sessionXmlDataProvider = new XmlDataProvider<>(Constants.SESSION_XML_FILE);
    public SessionJdbcDataProvider sessionJdbcDataProvider = new SessionJdbcDataProvider();

    public final CsvDataProvider<Receipt> receiptCsvDataProvider = new CsvDataProvider<>(Receipt.class, Constants.RECEIPT_CSV_FILE);
    public final XmlDataProvider<Receipt> receiptXmlDataProvider = new XmlDataProvider<>(Constants.RECEIPT_XML_FILE);
    public ReceiptJdbcDataProvider receiptJdbcDataProvider = new ReceiptJdbcDataProvider();

}
