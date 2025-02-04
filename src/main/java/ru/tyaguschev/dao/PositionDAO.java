package ru.tyaguschev.dao;

import ru.tyaguschev.models.Employee;
import ru.tyaguschev.models.Model;
import ru.tyaguschev.models.Position;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.tyaguschev.db.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class PositionDAO implements DataAccesObject{

    /**
     * Функция для поиска должности по id
     * @param id
     * @return
     */
    public Position findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Position position = session.get(Position.class, id);
        session.close();
        return position;
    }

    /**
     * Функция для создания должности
     * @param position
     */
    @Override
    public void insert(Model position) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(position);
        tx1.commit();
        session.close();
    }

    /**
     * Функция для редактирования должности
     * @param position
     */
    @Override
    public void update(Model position) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(position);
        tx1.commit();
        session.close();
    }

    /**
     * Функция для удаления должности
     * @param position
     */
    @Override
    public void delete(Model position) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(position);
        tx1.commit();
        session.close();
    }

    /**
     * Функция для поиска всех должностей
     * @return
     */
    public List<Integer> findAllIds() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Position position : findAll()) {
            ids.add(position.getId());
        }
        return ids;
    }

    /**
     * Функция для поиска сотрудника по id
     * @param id
     * @return
     */
    public Employee findEmployeeById(int id) {
        return HibernateUtil.getSessionFactory().openSession().get(Employee.class, id);
    }

    /**
     * Функция для поиска должности по ее названию
     * @param name
     * @return
     */
    public Position findByPositionName(String name) {
        return (Position) HibernateUtil.getSessionFactory().openSession().
                createQuery("from Position where position_name = :name").
                setParameter("name", name).uniqueResult();
    }

    /**
     * Функция для поиска всех должностей
     * @return
     */
    @Override
    public List<Position> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        var result = session.createQuery("from Position", Position.class).getResultList();
        session.close();
        return result;
    }

    /**
     * Дополнительная функция для возвращения названий колонок
     * (не используется)
     * @return
     */
    @Override
    public List<String> getColumns() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        var result = session.createNativeQuery("SELECT column_name FROM information_schema.columns " +
                        "WHERE table_name = 'positions' order by ordinal_position;").getResultList();
        session.close();
        return (List<String>) result;
    }
}
