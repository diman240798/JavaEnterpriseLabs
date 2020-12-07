package ru.sfedu.nanicky.shop.app;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Constants {


    public static File JDBC_MV = new File("./jdbc.db.mv.db");
    public static File JDBC_TRACE = new File("./jdbc.db.trace.db");


    public static void init() {
        String path;
        if (System.getProperty("environment") != null) {
            path = System.getProperty("environment");
        } else {
            path = "/strings.properties";
        }
        ConfigurationUtil configurationUtil = new ConfigurationUtil(path);

        CATEGORY_XML_FILE = new File(configurationUtil.readConfig("category_xml"));
        CATEGORY_CSV_FILE = new File(configurationUtil.readConfig("category_csv"));

        SODA_XML_FILE = new File(configurationUtil.readConfig("soda_xml"));
        SODA_CSV_FILE = new File(configurationUtil.readConfig("soda_csv"));

        COMPUTER_XML_FILE = new File(configurationUtil.readConfig("computer_xml"));
        COMPUTER_CSV_FILE = new File(configurationUtil.readConfig("computer_csv"));

        FRIDGE_XML_FILE = new File(configurationUtil.readConfig("fridge_xml"));
        FRIDGE_CSV_FILE = new File(configurationUtil.readConfig("fridge_csv"));

        BUCKET_XML_FILE = new File(configurationUtil.readConfig("bucket_xml"));
        BUCKET_CSV_FILE = new File(configurationUtil.readConfig("bucket_csv"));

        SESSION_XML_FILE = new File(configurationUtil.readConfig("session_xml"));
        SESSION_CSV_FILE = new File(configurationUtil.readConfig("session_csv"));

        RECEIPT_XML_FILE = new File(configurationUtil.readConfig("receipt_xml"));
        RECEIPT_CSV_FILE = new File(configurationUtil.readConfig("receipt_csv"));

        DB_FILES = Arrays.asList(
                CATEGORY_CSV_FILE, CATEGORY_XML_FILE,
                SODA_CSV_FILE, SODA_XML_FILE,
                COMPUTER_CSV_FILE, COMPUTER_XML_FILE,
                FRIDGE_CSV_FILE, FRIDGE_XML_FILE,
                BUCKET_CSV_FILE, BUCKET_XML_FILE,
                SESSION_CSV_FILE, SESSION_XML_FILE,
                RECEIPT_CSV_FILE, RECEIPT_XML_FILE,
                JDBC_MV, JDBC_TRACE
        );
    }

    public static File CATEGORY_XML_FILE;
    public static File CATEGORY_CSV_FILE;

    public static File SODA_XML_FILE;
    public static File SODA_CSV_FILE;

    public static File COMPUTER_XML_FILE;
    public static File COMPUTER_CSV_FILE;

    public static File FRIDGE_XML_FILE = new File("./xml/fridge.xml");
    public static File FRIDGE_CSV_FILE = new File("./fridge.csv");

    public static File BUCKET_XML_FILE = new File("./xml/bucket.xml");
    public static File BUCKET_CSV_FILE = new File("./bucket.csv");

    public static File SESSION_XML_FILE = new File("./xml/session.xml");
    public static File SESSION_CSV_FILE = new File("./session.csv");

    public static File RECEIPT_XML_FILE = new File("./xml/receipt.xml");
    public static File RECEIPT_CSV_FILE = new File("./receipt.csv");


    public static List<File> DB_FILES;



    public static final String INIT = "init";
    public static final String INIT_ALL = "init_all";
    public static final String CATEGORY = "category";
    public static final String FRIDGE = "fridge";
    public static final String SODA = "soda";
    public static final String COMPUTER = "computer";
    public static final String RECEIPT = "receipt";
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
