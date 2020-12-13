package ru.sfedu.nanicky.shop.api.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.api.crudprocessor.*;
import ru.sfedu.nanicky.shop.api.initializer.Initializer;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;

import java.util.Arrays;
import java.util.Optional;

public class CliManager {
    private static final Logger LOG = LogManager.getLogger(CliManager.class);


    private final Repositories repositories;

    public CliManager(Repositories repositories) {
        this.repositories = repositories;
    }


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

    private void workoutModel(String dataProviderStr, String[] args) {
        LOG.info("Start parsing model");
        LOG.debug("Start parsing model method for data provider: " + dataProviderStr);
        String method = args[1];

        Optional<String> modelData = Optional.empty();
        if (args.length > 2) {
            modelData = Optional.of(args[2]);
        }

        if (method.equals(Constants.CATEGORY)) {
            CrudCliProcessor crudCliProcessor = new CategoryCrudCliProcessor(repositories);
            crudCliProcessor.processCrudApi(dataProviderStr, method, modelData, Arrays.asList(Constants.GET_ALL));
        } else if (method.equals(Constants.FRIDGE)) {
            CrudCliProcessor crudCliProcessor = new FridgeCrudCliProcessor(repositories);
            crudCliProcessor.processCrudApi(dataProviderStr, method, modelData, Constants.ALL_ACTIONS);
        } else if (method.equals(Constants.COMPUTER)) {
            CrudCliProcessor crudCliProcessor = new ComputerCrudCliProcessor(repositories);
            crudCliProcessor.processCrudApi(dataProviderStr, method, modelData, Constants.ALL_ACTIONS);
        } else if (method.equals(Constants.SODA)) {
            CrudCliProcessor crudCliProcessor = new SodaCrudCliProcessor(repositories);
            crudCliProcessor.processCrudApi(dataProviderStr, method, modelData, Constants.ALL_ACTIONS);
        } else if (method.equals(Constants.RECEIPT)) {
            CrudCliProcessor crudCliProcessor = new ReceiptCrudCliProcessor(repositories);
            crudCliProcessor.processCrudApi(dataProviderStr, method, modelData, Constants.GET_DELETE_ACTIONS);
        } else {
            ShopCliManager shopCliManager = new ShopCliManager(repositories);
            shopCliManager.processRequest(method, dataProviderStr, args);
        }
    }

}
