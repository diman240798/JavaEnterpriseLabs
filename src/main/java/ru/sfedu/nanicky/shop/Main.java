package ru.sfedu.nanicky.shop;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.sfedu.nanicky.shop.api.cli.CliManager;
import ru.sfedu.nanicky.shop.app.ConfigurationUtil;
import ru.sfedu.nanicky.shop.app.Reposotiries;

public class Main {

    private static Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        LOG.debug("Init");

        ConfigurationUtil configurationUtil = new ConfigurationUtil("/en/strings.properties");
        String cat = configurationUtil.readConfig("cat");
        LOG.info(cat);

        Reposotiries reposotiries = new Reposotiries();
        CliManager cliManager = new CliManager(reposotiries);
        cliManager.workoutRequest(args);

    }
}
