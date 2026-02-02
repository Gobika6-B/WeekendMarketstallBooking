package com.market.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "MARKET_USER";
    private static final String PASSWORD = "market_pwd";

    public static Connection getDBConnection() {

        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } 
        catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found");
            e.printStackTrace();
        } 
        catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }

        return connection;
    }
}
