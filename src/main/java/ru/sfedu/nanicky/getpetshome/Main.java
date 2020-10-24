package ru.sfedu.nanicky.getpetshome;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Main {

    private static Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        LOG.debug("Init");
        ConfigurationUtil configurationUtil = new ConfigurationUtil("/en/strings.properties");
        String cat = configurationUtil.readConfig("cat");
        LOG.info(cat);
    }
}
