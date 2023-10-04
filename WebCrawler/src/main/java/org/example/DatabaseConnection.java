package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        String user = "root";
        String pwd = "8553509288";
        String database = "searchengineapp";
        return getConnection(user, pwd, database);
    }

    private static Connection getConnection(String user, String pwd, String database) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + database + "?user =" + user + "&password = " + pwd);
            return connection;

        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }
}
