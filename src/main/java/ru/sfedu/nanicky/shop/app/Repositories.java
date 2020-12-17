package ru.sfedu.nanicky.shop.app;

import ru.sfedu.nanicky.shop.db.csv.*;
import ru.sfedu.nanicky.shop.db.jdbc.model.*;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.*;
import ru.sfedu.nanicky.shop.db.xml.*;

public class Repositories {

    public final CategoryCsvDataProvider categoryCsvDataProvider = new CategoryCsvDataProvider(Constants.CATEGORY_CSV_FILE);
    public final CategoryXmlDataProvider categoryXmlDataProvider = new CategoryXmlDataProvider(Constants.CATEGORY_XML_FILE);
    public CategoryJdbcDataProvider categoryJdbcDataProvider = new CategoryJdbcDataProvider();

    public final SodaCsvDataProvider sodaCsvDataProvider = new SodaCsvDataProvider(Constants.SODA_CSV_FILE);
    public final SodaXmlDataProvider sodaXmlDataProvider = new SodaXmlDataProvider(Constants.SODA_XML_FILE);
    public SodaJdbcDataProvider sodaJdbcDataProvider = new SodaJdbcDataProvider();

    public final BaseDataProvider<Computer> computerCsvDataProvider = new ComputerCsvDataProvider(Constants.COMPUTER_CSV_FILE);
    public final ComputerXmlDataProvider computerXmlDataProvider = new ComputerXmlDataProvider(Constants.COMPUTER_XML_FILE);
    public final ComputerJdbcDataProvider computerJdbcDataProvider = new ComputerJdbcDataProvider();

    public final FridgeCsvDataProvider fridgeCsvDataProvider = new FridgeCsvDataProvider(Constants.FRIDGE_CSV_FILE);
    public final FridgeXmlDataProvider fridgeXmlDataProvider = new FridgeXmlDataProvider(Constants.FRIDGE_XML_FILE);
    public FridgeJdbcDataProvider fridgeJdbcDataProvider = new FridgeJdbcDataProvider();

    public final BaseDataProvider<Bucket> bucketCsvDataProvider = new BucketCsvDataProvider(Constants.BUCKET_CSV_FILE);
    public final BaseDataProvider<Bucket> bucketXmlDataProvider = new BucketXmlDataProvider(Constants.BUCKET_XML_FILE);
    public BucketJdbcDataProvider bucketJdbcDataProvider = new BucketJdbcDataProvider();

    public final BaseDataProvider<Session> sessionCsvDataProvider = new SessionCsvDataProvider(Constants.SESSION_CSV_FILE);
    public final BaseDataProvider<Session> sessionXmlDataProvider = new SessionXmlDataProvider(Constants.SESSION_XML_FILE);
    public SessionJdbcDataProvider sessionJdbcDataProvider = new SessionJdbcDataProvider();

    public final BaseDataProvider<Receipt> receiptCsvDataProvider = new ReceiptCsvDataProvider(Constants.RECEIPT_CSV_FILE);
    public final BaseDataProvider<Receipt> receiptXmlDataProvider = new ReceiptXmlDataProvider(Constants.RECEIPT_XML_FILE);
    public ReceiptJdbcDataProvider receiptJdbcDataProvider = new ReceiptJdbcDataProvider();

}
