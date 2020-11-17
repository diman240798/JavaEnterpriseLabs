package ru.sfedu.nanicky.shop.db.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.sfedu.nanicky.shop.db.protocol.dao.BaseDao;
import ru.sfedu.nanicky.shop.db.protocol.model.IdEntity;
import ru.sfedu.nanicky.shop.db.hibernate.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HibernateDao<V extends IdEntity> implements BaseDao<V> {

    private final String clazzName;

    public HibernateDao(String clazzName) {
        this.clazzName = clazzName;
    }

    @Override
    public List<V> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<V> query = session.createQuery("from " + clazzName);
            List<V> items = query.list();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<V> getById(long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<V> query = session.createQuery("from " + clazzName + " where id=" + id);
            V item = query.uniqueResult();
            return Optional.of(item);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(V v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(v);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(id);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(V v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(v);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean insert(V v) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(v);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
          e.printStackTrace();
          session.getTransaction().rollback();
          return false;
        } finally {
            session.close();
        }
    }
}
