package ru.sfedu.nanicky.shop.api.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.api.crudprocessor.*;
import ru.sfedu.nanicky.shop.api.initializer.Initializer;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;

import java.util.Arrays;

public class CliManager {
    private static final Logger LOG = LogManager.getLogger(CliManager.class);

    private final Repositories repositories;

    public CliManager(Repositories repositories) {
        this.repositories = repositories;
    }

    /**
     * Проверка параметров на методы INIT, и передача далее на обработку
     *
     * @param args - входные аргументы программы
     * @return void
     */
    public void workoutRequest(String[] args) {
        LOG.info("Start parsing arguments");
        LOG.debug("Start parsing arguments: " + String.join(",", args));
        String dataProvider = args[0];

        if (args[0].equals(Constants.INIT_ALL)) {
            Initializer.initAll(repositories);
        } else if (args[1].equals(Constants.INIT)) {
            Initializer.initFor(dataProvider, repositories);
        } else {
            workoutModel(dataProvider, args);
        }

    }

    /**
     * Проверка параметров на CRUD операции по моделям, и передача далее на обработку покупок
     *
     * @param args - входные аргументы программы
     * @return void
     */
    private void workoutModel(String dataProviderStr, String[] args) {
        LOG.info("Start parsing model");
        LOG.debug("Start parsing model modelStr for data provider: " + dataProviderStr);
        String modelStr = args[1];

        if (modelStr.equals(Constants.CATEGORY)) {
            CategoryCrudCliProcessor crudCliProcessor = new CategoryCrudCliProcessor(repositories);
                crudCliProcessor.processCategoryCrudApi(args, Arrays.asList(Constants.GET_ALL));
        } else if (modelStr.equals(Constants.FRIDGE)) {
            FridgeCrudCliProcessor crudCliProcessor = new FridgeCrudCliProcessor(repositories);
            crudCliProcessor.processFridgeCrudApi(args, Constants.ALL_ACTIONS);
        } else if (modelStr.equals(Constants.COMPUTER)) {
            ComputerCrudCliProcessor crudCliProcessor = new ComputerCrudCliProcessor(repositories);
            crudCliProcessor.processComputerCrudApi(args, Constants.ALL_ACTIONS);
        } else if (modelStr.equals(Constants.SODA)) {
            SodaCrudCliProcessor crudCliProcessor = new SodaCrudCliProcessor(repositories);
            crudCliProcessor.processSodaCrudApi(args, Constants.ALL_ACTIONS);
        } else if (modelStr.equals(Constants.RECEIPT)) {
            ReceiptCrudCliProcessor crudCliProcessor = new ReceiptCrudCliProcessor(repositories);
            crudCliProcessor.processReceiptCrudApi(args, Constants.GET_DELETE_ACTIONS);
        } else {
            ShopCliManager shopCliManager = new ShopCliManager(repositories);
            shopCliManager.processRequest(args);
        }
    }

}
