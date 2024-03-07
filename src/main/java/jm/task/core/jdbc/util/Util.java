package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USERNAME = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/pp_1_1_1_4db";
    private static final String PASSWORD = "Admin_1337";
    public static Connection getConnection() {

        Connection connection = null;
            try {

                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            } catch (SQLException e) {
                System.out.println("Connection failed...");

            }
        return connection;
    }
}
