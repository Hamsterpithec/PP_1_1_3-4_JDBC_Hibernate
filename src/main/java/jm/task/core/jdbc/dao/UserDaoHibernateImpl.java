package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {


    SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS User"
                            + "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                            + "name VARCHAR(100) NOT NULL, lastName VARCHAR(100) NOT NULL, "
                            + "age LONG NOT NULL)").addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS User").addEntity(User.class).executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("INSERT INTO User(name,lastName,age)" + "SELECT name,lastName,age FROM User").executeUpdate();
            transaction.commit();
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
            session.createQuery("DELETE from User WHERE id = :id")
                    .setParameter("id", user.getId())
                    .executeUpdate();
            transaction.commit();
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
            return session.createQuery("SELECT a FROM User a", User.class).getResultList();
        }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User u").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
