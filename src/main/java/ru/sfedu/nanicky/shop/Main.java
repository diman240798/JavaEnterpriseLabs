package ru.sfedu.nanicky.shop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.api.cli.CliManager;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Reposotiries;

public class Main {

    private static Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("Start Program");
        LOG.debug("Start Program with args: {}", String.join(",", args));

        Constants.init();

        Reposotiries reposotiries = new Reposotiries();
        CliManager cliManager = new CliManager(reposotiries);
        cliManager.workoutRequest(args);

    }
}
