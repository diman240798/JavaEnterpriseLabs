package ru.sfedu.nanicky.shop.api.crudprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.app.Repositories;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.util.List;
import java.util.Optional;

public abstract class CrudCliProcessor<M extends IdEntity> {

    private static final Logger LOG = LogManager.getLogger(CrudCliProcessor.class);

    private final Repositories repositories;

    public CrudCliProcessor(Repositories repositories) {
        this.repositories = repositories;
    }

    public void processCrudApi(String dataProviderStr, String method, Optional<String> model, List<String> crudActionsSupportedList) {
        LOG.info("Start processing model operation");
        LOG.debug("Start processing model data provider {} operation {}", dataProviderStr, method);

        BaseDao<M> dataProviderImpl = getDaoForDataProvider(dataProviderStr, repositories);


        if (method.equals(Constants.GET)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                long id = Long.parseLong(model.get());
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
                M category = getModel(model.get());
                logAction(method, dataProviderImpl.insert(category));
            }
        } else if (method.equals(Constants.UPDATE)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                M category = getModel(model.get());
                logAction(method, dataProviderImpl.update(category));
            }
        } else if (method.equals(Constants.DELETE)) {
            if (checkActionSupported(method, crudActionsSupportedList)) {
                long id = Long.parseLong(model.get());
                logAction(method, dataProviderImpl.delete(id));
            }
        }
    }

    private boolean checkActionSupported(String action, List<String> actions) {
        if (actions.contains(action)) {
            return true;
        }
        LOG.info("Action {} not supported", action);
        LOG.debug("Action {} not supported. Use another model.", action);
        return false;
    }

    private void logAction(String action, boolean actionResult) {
        if (actionResult) {
            LOG.info("Cli action: {} competed successfully!", action);
        } else {
            LOG.info("Cli action: {} error", action);
        }
    }


    public abstract BaseDao<M> getDaoForDataProvider(String dataProvider, Repositories repositories);

    protected abstract M getModel(String modelStr);
}
