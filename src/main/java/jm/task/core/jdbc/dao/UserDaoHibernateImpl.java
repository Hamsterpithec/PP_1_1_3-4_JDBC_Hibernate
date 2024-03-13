package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {


    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User "
                            + "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "name VARCHAR(100) NOT NULL, lastName VARCHAR(100) NOT NULL, "
                            + "age LONG NOT NULL)")
                    .executeUpdate();
            transaction.commit();
            sessionFactory.close();
        } catch (HibernateException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();

            transaction.commit();
            sessionFactory.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("INSERT INTO User (name,lastname,age)").executeUpdate();
            transaction.commit();
            sessionFactory.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("User с именем — " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User();
            session.createSQLQuery("DELETE User WHERE id = :id")
                    .setParameter("id", user.getId())
                    .executeUpdate();
            transaction.commit();
            sessionFactory.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User.class").executeUpdate();
            transaction.commit();
            sessionFactory.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
