package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS usertable (" +
                "  id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT ," +
                "  name VARCHAR(100) NOT NULL," +
                "  lastname VARCHAR(110) NOT Null," +
                "  age TINYINT NOT NULL)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS usertable")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {


        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO usertable (name, lastname, age)" + "VALUES (?,?,?)")) {
            connection.setAutoCommit(false);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }




    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM usertable WHERE id = ?")){
            connection.setAutoCommit(false);
                preparedStatement.setLong(1, id);
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }


    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usertable")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));

                usersList.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return usersList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS usertable")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
