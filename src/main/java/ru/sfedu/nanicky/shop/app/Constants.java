package ru.sfedu.nanicky.shop.app;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final File CATEGORY_XML_FILE = new File("./category.xml");
    public static File CATEGORY_CSV_FILE = new File("./category.csv");

    public static final File SODA_XML_FILE = new File("./soda.xml");
    public static File SODA_CSV_FILE = new File("./soda.csv");

    public static final File COMPUTER_XML_FILE = new File("./computer.xml");
    public static File COMPUTER_CSV_FILE = new File("./computer.csv");

    public static final File FRIDGE_XML_FILE = new File("./fridge.xml");
    public static File FRIDGE_CSV_FILE = new File("./fridge.csv");

    public static final File BUCKET_XML_FILE = new File("./bucket.xml");
    public static File BUCKET_CSV_FILE = new File("./bucket.csv");

    public static final File SESSION_XML_FILE = new File("./session.xml");
    public static File SESSION_CSV_FILE = new File("./session.csv");

    public static final File CHECK_XML_FILE = new File("./check.xml");
    public static File CHECK_CSV_FILE = new File("./check.csv");



    public static final String INIT = "init";
    public static final String INIT_ALL = "init_all";
    public static final String CATEGORY = "category";
    public static final String FRIDGE = "fridge";
    public static final String SODA = "soda";
    public static final String COMPUTER = "computer";
    public static final String CHECK = "check";
    public static final String SESSION = "session";
    public static final String BUCKET = "add";


    public static final String XML = "xml";
    public static final String CSV = "csv";
    public static final String JDBC = "jdbc";
    public static final String HIBERNATE = "hibernate";

    public static final List<String> ALL_ACTIONS = Arrays.asList(Constants.INSERT, Constants.GET_ALL, Constants.GET, Constants.UPDATE, Constants.DELETE);
    public static final List<String> GET_DELETE_ACTIONS = Arrays.asList(Constants.GET_ALL, Constants.GET, Constants.DELETE);

    public static final String GET = "get";
    public static final String GET_ALL = "get_all";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";

    public static final String START = "start";
    public static final String FINISH = "finish";
    public static final String ADD_PRODUCT_TO_BUCKET = "add_product";

    public static final String CATEGORY_FRIDGE = "fridge";
    public static final String CATEGORY_SODA = "soda";
    public static final String CATEGORY_COMPUTER = "computer";

    public static final String PRODUCT_CATEGORY_SEPARATOR = ":";
    public static final String PRODUCTS_SEPATOR = "-";

}
