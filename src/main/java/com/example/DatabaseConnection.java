package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages remote PostgreSQL database connections for JasperReports export.
 * Compatible with JasperReports Server 7.0.3
 */
public class DatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    // ─── Configure your remote PostgreSQL connection here ───────────────────────
    private static final String DB_HOST     = "dpg-d6evbhcr85hc73fr2o10-a.frankfurt-postgres.render.com";
    private static final int    DB_PORT     = 5432;
    private static final String DB_NAME     = "logistica_depozit";
    private static final String DB_USER     = "user1";
    private static final String DB_PASSWORD = "wwycyaY47aGk1sKkEnJVUJpy5Irq9Ujw";
    // ────────────────────────────────────────────────────────────────────────────

    private static final String JDBC_URL =
            String.format("jdbc:postgresql://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "PostgreSQL JDBC driver not found. "
                    + "Add postgresql-<version>.jar to your classpath.", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Opens and returns a new connection to the remote PostgreSQL database.
     *
     * @return an open {@link Connection}
     * @throws SQLException if the connection cannot be established
     */
    public static Connection getConnection() throws SQLException {
        LOGGER.info("Connecting to PostgreSQL at " + JDBC_URL);
        Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        conn.setAutoCommit(false); // use explicit transactions where needed
        return conn;
    }

    /**
     * Quietly closes a connection, suppressing any exception.
     *
     * @param conn the connection to close (may be {@code null})
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Failed to close database connection.", e);
            }
        }
    }
}