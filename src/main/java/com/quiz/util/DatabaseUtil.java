package com.quiz.util;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseUtil {
    private static HikariDataSource dataSource;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("DB_URL");
            config.setUsername("DB_USER");
            config.setPassword("DB_PASSWORD");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(1);
            config.setIdleTimeout(600000); 
            config.setMaxLifetime(1800000); 

            dataSource = new HikariDataSource(config);
            System.out.println("DataSource has been initialized");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load MySQL driver: " + e.getMessage());
            throw new RuntimeException("Failed to load MySQL driver", e);
        } catch (Exception ex) {
            System.out.println("Failed to initialize DataSource: " + ex.getMessage());
            throw new RuntimeException("Failed to initialize DataSource", ex);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            System.out.println("Failed to obtain connection: " + ex.getMessage());
            throw ex;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Connection successfully closed");
            } catch (SQLException e) {
                System.out.println("Failed to close connection: " + e.getMessage());
            }
        }
    }
}
