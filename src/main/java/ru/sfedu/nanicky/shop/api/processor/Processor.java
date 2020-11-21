package ru.sfedu.nanicky.shop.api.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sfedu.nanicky.shop.app.Constants;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;

import java.util.List;

public abstract class Processor<M extends IdEntity> {

    private static final Logger LOG = LoggerFactory.getLogger(Processor.class);

    public void processBaseApi(String[] args, List<String> actions) {
        String dataProvider = args[0];
        BaseDao<M> categoryDao = getDaoForDataProvider(dataProvider);
        String method = args[2];


        if (method.equals(Constants.GET)) {
            if (checkActionSupported(Constants.GET, actions)) {
                long id = Long.parseLong(args[3]);
                M item = categoryDao.getById(id).get();
                LOG.info(item.toString());
            }
        } else if (method.equals(Constants.GET_ALL)) {
            if (checkActionSupported(Constants.GET_ALL, actions)) {
                List<M> all = categoryDao.getAll();
                for (M item : all) {
                    LOG.info(item.toString());
                }
            }
        } else if (method.equals(Constants.INSERT)) {
            if (checkActionSupported(Constants.INSERT, actions)) {
                M category = getModel(args[3]);
                categoryDao.insert(category);
            }
        } else if (method.equals(Constants.UPDATE)) {
            if (checkActionSupported(Constants.UPDATE, actions)) {
                M category = getModel(args[3]);
                categoryDao.update(category);
            }
        } else if (method.equals(Constants.DELETE)) {
            if (checkActionSupported(Constants.DELETE, actions)) {
                long id = Long.parseLong(args[3]);
                logDelete(categoryDao.delete(id));
            }
        }
    }

    private boolean checkActionSupported(String action, List<String> actions) {
        if (actions.contains(action)) {
            return true;
        }
        LOG.info("Action {} not supported", action);
        return false;
    }

    private void logDelete(boolean delete) {
        if (delete) {
            LOG.info("Deleted successfully!");
        } else {
            LOG.info("Deletion error");
        }
    }


    public abstract BaseDao<M> getDaoForDataProvider(String dataProvider);

    protected abstract M getModel(String modelStr);
}
