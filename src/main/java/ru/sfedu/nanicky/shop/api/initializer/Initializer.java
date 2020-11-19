package ru.sfedu.nanicky.shop.api.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sfedu.nanicky.shop.api.processor.CategoryProcessor;
import ru.sfedu.nanicky.shop.api.processor.ComputerProcessor;
import ru.sfedu.nanicky.shop.api.processor.FridgeProcessor;
import ru.sfedu.nanicky.shop.api.processor.SodaProcessor;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.Category;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;
import ru.sfedu.nanicky.shop.db.protocol.model.Fridge;
import ru.sfedu.nanicky.shop.db.protocol.model.Soda;

import java.util.Arrays;
import java.util.List;

public class Initializer {

    private static final Logger LOG = LoggerFactory.getLogger(Initializer.class);


    private final static List<Category> CATEGORIES = Arrays.asList(
            new Category(0, Constants.CATEGORY_FOOD),
            new Category(1, Constants.CATEGORY_COMPUTER),
            new Category(2, Constants.CATEGORY_APPLIANCES)
    );

    private final static List<Fridge> FRIDGES = Arrays.asList(
            new Fridge(0, "Indesit c30", 30, 31000, Constants.CATEGORY_APPLIANCES, 50, "white", 300),
            new Fridge(1, "Toshiba a52", 50, 60650, Constants.CATEGORY_APPLIANCES, 100, "gray", 600, true),
            new Fridge(2, "Samsung wqe123", 30, 45300, Constants.CATEGORY_APPLIANCES, 60, "pink", 500, true),
            new Fridge(3, "Beko tr44", 30, 42300, Constants.CATEGORY_APPLIANCES, 60, "black", 450),
            new Fridge(4, "Atlant xm435", 30, 50300, Constants.CATEGORY_APPLIANCES, 70, "yellow", 600)
    );

    private final static List<Computer> COMPUTERS = Arrays.asList(
            new Computer(0, "Dell compact RT 3", 2.2, 52500, Constants.CATEGORY_COMPUTER, "intel i3", 500, "integrated", 100),
            new Computer(1, "Dell gaming MT 5", 2.9, 73400, Constants.CATEGORY_COMPUTER, "amd ryzen x9", 1000, "geforge gtx 2050ti", 100, true, true),
            new Computer(2, "HP spectre x360", 1.3, 85700, Constants.CATEGORY_COMPUTER, "intel i5", 700, "geforge gtx 1060", 100),
            new Computer(3, "HP pavillion x3", 2.7, 92000, Constants.CATEGORY_COMPUTER, "intel i7", 900, "geforge gtx 1050", 100, true, true),
            new Computer(4, "Dexp laptop sereies", 2.5, 31000, Constants.CATEGORY_COMPUTER, "amd ryzen x1", 300, "integrated", 100)
    );

    private final static List<Soda> SODA = Arrays.asList(
            new Soda(0, "Fanta", 2, 200, Constants.CATEGORY_FOOD, "orange", true),
            new Soda(1, "Kind Juice", 1, 70, Constants.CATEGORY_FOOD, "apple"),
            new Soda(2, "Rich Juice", 1, 110, Constants.CATEGORY_FOOD, "strawberry"),
            new Soda(3, "Limonella", 2.2, 52500, Constants.CATEGORY_FOOD, "lemon", true),
            new Soda(4, "Mirinda Blue", 2.2, 52500, Constants.CATEGORY_FOOD, "blueberry-lemon", true)
    );

    public static void initFor(String dataProvider, Reposotiries reposotiries) {
        LOG.info("Init for {}", dataProvider);
        BaseDao<Category> categoryDao = new CategoryProcessor(reposotiries).processDataProvider(dataProvider);
        BaseDao<Fridge> fridgeDao = new FridgeProcessor(reposotiries).processDataProvider(dataProvider);
        BaseDao<Computer> computerDao = new ComputerProcessor(reposotiries).processDataProvider(dataProvider);
        BaseDao<Soda> sodaDao = new SodaProcessor(reposotiries).processDataProvider(dataProvider);

        categoryDao.insertAll(CATEGORIES);
        fridgeDao.insertAll(FRIDGES);
        computerDao.insertAll(COMPUTERS);
        sodaDao.insertAll(SODA);


    }

    public static void initAll(Reposotiries reposotiries) {
        LOG.info("Init all");
        initFor(Constants.XML, reposotiries);
        initFor(Constants.CSV, reposotiries);
//        initFor(Constants.JDBC, reposotiries);
        initFor(Constants.HIBERNATE, reposotiries);

    }
}
