package ru.sfedu.nanicky.getpetshome.db.xml.service;

import ru.sfedu.nanicky.getpetshome.db.protocol.model.IdEntity;
import ru.sfedu.nanicky.getpetshome.db.protocol.service.BaseService;
import ru.sfedu.nanicky.getpetshome.db.xml.dao.XmlDao;

public class XmlService<V extends IdEntity> extends BaseService<V, XmlDao<V>> {
    public XmlService(XmlDao<V> xmlDao) {
        super(xmlDao);
    }
}
