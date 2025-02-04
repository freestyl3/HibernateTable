package ru.tyaguschev.db;

import org.hibernate.Session;
import ru.tyaguschev.models.Employee;
import ru.tyaguschev.models.Position;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {};

    /**
     * Функция для возвращения фабрики запросов
     * @return
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Position.class);
                configuration.addAnnotatedClass(Employee.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    /**
     * Функция для возвращения названий всех таблиц
     * (не используется)
     * @return
     */
    public static List<String> fetchTableNames() {
        try (Session session = sessionFactory.openSession()) {
            return session.createNativeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching table names: " + e.getMessage());
            return List.of();
        }
    }
}