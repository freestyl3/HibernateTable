package ru.tyaguschev.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.tyaguschev.db.HibernateUtil;
import ru.tyaguschev.models.Employee;
import ru.tyaguschev.models.Model;
import ru.tyaguschev.models.Position;

import java.util.List;

public class EmployeeDAO implements DataAccesObject {

    public Employee findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = session.get(Employee.class, id);
        session.close();
        return employee;
    }

    @Override
    public void insert(Model employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(employee);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(Model employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(employee);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Model employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(employee);
        tx1.commit();
        session.close();
    }

    @Override
    public List<Employee> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        var result = session.createQuery("from Employee", Employee.class).getResultList();
        session.close();
        return result;
    }

    public List<List> findAll2() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        var result = session.createQuery("select e.id, e.firstname, e.lastname, e.birthday, p.position_name " +
                "FROM Employee e JOIN e.position p", List.class).list();
        session.close();
        return result;
    }

    @Override
    public List<String> getColumns() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        var result = (List<String>) session
                .createNativeQuery("SELECT column_name FROM information_schema.columns " +
                        "WHERE table_name = 'employees' order by ordinal_position;")
                .getResultList();
        session.close();
        return result;
    }
}
