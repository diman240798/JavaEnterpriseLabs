package ru.sfedu.nanicky.getpetshome.db.sql;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sfedu.nanicky.getpetshome.db.protocol.model.Animal;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .configure()
                    .addAnnotatedClass(Animal.class);
            return configuration.buildSessionFactory();
        }
        catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        sessionFactory.close();
    }

}