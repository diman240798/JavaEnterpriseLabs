package ru.sfedu.nanicky.shop.api.crudprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.Main;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.app.RepositoriesUtil;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.Computer;

import java.util.List;
import java.util.Optional;

public class ComputerCrudCliProcessor {

    private static Logger LOG = LogManager.getLogger(Main.class);


    private final Repositories repositories;

    public ComputerCrudCliProcessor(Repositories repositories) {
        this.repositories = repositories;
    }


    /**
     * Реализация выдачи распарсенной модели
     * @param modelStr - данные модели в виде строки
     * @return Computer
     */
    protected Computer getModel(String modelStr) {
        LOG.info("Parsing string to model");
        LOG.debug("Parsing string to model {}", modelStr);
        String[] split = modelStr.split("\\s");
        long id = Long.parseLong(split[0]);
        String name = split[1];
        double weight = Double.parseDouble(split[2]);
        double price = Double.parseDouble(split[3]);
        String processorName = split[4];
        int processorPower = Integer.parseInt(split[5]);
        String graphicsName = split[6];
        int graphicsVolume = Integer.parseInt(split[7]);

        if (split.length > 8) {
            boolean wifiIntegrated = Boolean.parseBoolean(split[8]);
            boolean bluetoothIntegrated = Boolean.parseBoolean(split[9]);
            return new Computer(id, name, weight, price, Constants.CATEGORY_COMPUTER, processorName, processorPower, graphicsName, graphicsVolume, wifiIntegrated, bluetoothIntegrated);
        }

        return new Computer(id, name, weight, price, Constants.CATEGORY_COMPUTER, processorName, processorPower, graphicsName, graphicsVolume);
    }



    /**
     * Обработка crud операций для модели
     * @param args - входные параметры
     * @param crudActionsSupportedList - поддерживаемы для данной модели crud операции
     * @return boolean
     */
    public void processCrudApi(String[] args, List<String> crudActionsSupportedList) {
        String dataProviderStr = args[0];
        String method = args[2];

        Optional<String> modelData = Optional.empty();
        if (args.length > 3) {
            modelData = Optional.of(args[3]);
        }

        LOG.info("Start processing model operation");
        LOG.debug("Start processing model data provider {} operation {}", dataProviderStr, method);

        BaseDataProvider<Computer> dataProviderImpl = RepositoriesUtil.getComputerDataProvider(dataProviderStr, repositories);

        if (method.equals(Constants.GET)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                long id = Long.parseLong(modelData.get());
                Computer item = dataProviderImpl.getById(id).get();
                LOG.info(item.toString());
            }
        } else if (method.equals(Constants.GET_ALL)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                List<Computer> all = dataProviderImpl.getAll();
                for (Computer item : all) {
                    LOG.info(item.toString());
                }
            }
        } else if (method.equals(Constants.INSERT)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                Computer item = getModel(modelData.get());
                logAction(method, dataProviderImpl.insert(item));
            }
        } else if (method.equals(Constants.UPDATE)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                Computer item = getModel(modelData.get());
                logAction(method, dataProviderImpl.update(item));
            }
        } else if (method.equals(Constants.DELETE)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                long id = Long.parseLong(modelData.get());
                logAction(method, dataProviderImpl.delete(id));
            }
        }
    }

    /**
     * Вспомогательный метод логирования результатов crud операций
     * @param action - вид crud операции
     * @param actions - список поддерживаемых операций
     * @return boolean
     */
    private boolean checkActionSupported(String action, List<String> actions) {
        if (actions.contains(action)) {
            return true;
        }
        LOG.info("Action {} not supported", action);
        LOG.debug("Action {} not supported. Use another model.", action);
        return false;
    }

    /**
     * Вспомогательный метод логирования результатов crud операций
     * @param action - вид crud операции
     * @param actionResult - результат операции
     * @return void
     */
    private void logAction(String action, boolean actionResult) {
        if (actionResult) {
            LOG.info("Cli action: {} competed successfully!", action);
        } else {
            LOG.info("Cli action: {} error", action);
        }
    }
}
