package ru.sfedu.nanicky.shop.app;

import ru.sfedu.nanicky.shop.db.csv.dao.CsvDao;
import ru.sfedu.nanicky.shop.db.hibernate.dao.HibernateDao;
import ru.sfedu.nanicky.shop.db.protocol.model.*;
import ru.sfedu.nanicky.shop.db.xml.dao.XmlDao;

public class Reposotiries {
    public final CsvDao<Category> categoryCsvDao = new CsvDao<>(Category.class, Constants.CATEGORY_CSV_FILE);
    public final XmlDao<Category> categoryXmlDao = new XmlDao<>(Constants.CATEGORY_XML_FILE);
    public final HibernateDao<Category> categoryHibernateDao = new HibernateDao<>(Category.class.getName());

    public final CsvDao<Soda> sodaCsvDao = new CsvDao<>(Soda.class, Constants.SODA_CSV_FILE);
    public final XmlDao<Soda> sodaXmlDao = new XmlDao<>(Constants.SODA_XML_FILE);
    public final HibernateDao<Soda> sodaHibernateDao = new HibernateDao<>(Soda.class.getName());

    public final CsvDao<Computer> computerCsvDao = new CsvDao<>(Computer.class, Constants.COMPUTER_CSV_FILE);
    public final XmlDao<Computer> computerXmlDao = new XmlDao<>(Constants.COMPUTER_XML_FILE);
    public final HibernateDao<Computer> computerHibernateDao = new HibernateDao<>(Computer.class.getName());

    public final CsvDao<Fridge> fridgeCsvDao = new CsvDao<>(Fridge.class, Constants.FRIDGE_CSV_FILE);
    public final XmlDao<Fridge> fridgeXmlDao = new XmlDao<>(Constants.FRIDGE_XML_FILE);
    public final HibernateDao<Fridge> fridgeHibernateDao = new HibernateDao<>(Fridge.class.getName());

    public final CsvDao<Bucket> bucketCsvDao = new CsvDao<>(Bucket.class, Constants.BUCKET_CSV_FILE);
    public final XmlDao<Bucket> bucketXmlDao = new XmlDao<>(Constants.BUCKET_XML_FILE);
    public final HibernateDao<Bucket> bucketHibernateDao = new HibernateDao<>(Bucket.class.getName());

    public final CsvDao<Session> sessionCsvDao = new CsvDao<>(Session.class, Constants.SESSION_CSV_FILE);
    public final XmlDao<Session> sessionXmlDao = new XmlDao<>(Constants.SESSION_XML_FILE);
    public final HibernateDao<Session> sessionHibernateDao = new HibernateDao<>(Session.class.getName());

    public final CsvDao<Receipt> checkCsvDao = new CsvDao<>(Receipt.class, Constants.CHECK_CSV_FILE);
    public final XmlDao<Receipt> checkXmlDao = new XmlDao<>(Constants.CHECK_XML_FILE);
    public final HibernateDao<Receipt> checkHibernateDao = new HibernateDao<>(Receipt.class.getName());

}
