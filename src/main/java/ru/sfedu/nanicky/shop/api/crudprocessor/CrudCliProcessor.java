package ru.sfedu.nanicky.shop.api.crudprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.db.protocol.dataprovider.BaseDataProvider;
import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.util.List;
import java.util.Optional;

public abstract class CrudCliProcessor<M extends IdEntity> {

    private static final Logger LOG = LogManager.getLogger(CrudCliProcessor.class);

    private final Repositories repositories;

    public CrudCliProcessor(Repositories repositories) {
        this.repositories = repositories;
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

        BaseDataProvider<M> dataProviderImpl = getDataProvider(dataProviderStr, repositories);

        if (method.equals(Constants.GET)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                long id = Long.parseLong(modelData.get());
                M item = dataProviderImpl.getById(id).get();
                LOG.info(item.toString());
            }
        } else if (method.equals(Constants.GET_ALL)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                List<M> all = dataProviderImpl.getAll();
                for (M item : all) {
                    LOG.info(item.toString());
                }
            }
        } else if (method.equals(Constants.INSERT)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                M category = getModel(modelData.get());
                logAction(method, dataProviderImpl.insert(category));
            }
        } else if (method.equals(Constants.UPDATE)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                M category = getModel(modelData.get());
                logAction(method, dataProviderImpl.update(category));
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

    /**
     * Выдача дата провадера для соответствующей модели
     * @param dataProvider - тип датапровайдера в виде строки
     * @param repositories - класс содержащий все репозитории используемые в проекте
     * @return BaseDataProvider
     */
    public abstract BaseDataProvider<M> getDataProvider(String dataProvider, Repositories repositories);

    /**
     * Выдача распарсенной модели
     * @param modelStr - данные модели в виде строки
     * @return M
     */
    protected abstract M getModel(String modelStr);
}
