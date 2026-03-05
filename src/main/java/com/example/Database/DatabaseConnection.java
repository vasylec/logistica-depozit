package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    // ─── Remote PostgreSQL configuration ────────────────────────────────────────
    private static final String DB_HOST = "dpg-d6evbhcr85hc73fr2o10-a.frankfurt-postgres.render.com";
    private static final int DB_PORT = 5432;
    private static final String DB_NAME = "logistica_depozit";
    private static final String DB_USER = "user1";
    private static final String DB_PASSWORD = "wwycyaY47aGk1sKkEnJVUJpy5Irq9Ujw";
    // ────────────────────────────────────────────────────────────────────────────

    private static final String JDBC_URL = String.format("jdbc:postgresql://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);
    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (!conn.isClosed() && conn != null) {
            return conn;
        }
        LOGGER.info("Connecting to PostgreSQL at " + JDBC_URL);
        conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        conn.setAutoCommit(false); // use explicit transactions where needed
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Failed to close database connection.", e);
            }
        }
    }

    public static boolean insertProduct(String name, String description, double unitPrice, double volume) {
        String sql = "INSERT INTO product (name, description, unit_price, volume) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, unitPrice);
            stmt.setDouble(4, volume);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertSupplier(String name, String description, String contact, String address) {
        String sql = "INSERT INTO supplier (name, description, contact, address) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, contact);
            stmt.setString(4, address);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertInventory(int warehouseId, int productId, int quantity) {
        String sql = "INSERT INTO inventory (warehouse_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, warehouseId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertReceipt(int supplierId, int warehouseId, String documentNumber, String notes) {
        String sql = "INSERT INTO receipt (supplier_id, warehouse_id, document_number, notes) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplierId);
            stmt.setInt(2, warehouseId);
            stmt.setString(3, documentNumber);
            stmt.setString(4, notes);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertReceiptItem(int receiptId, int productId, int quantity, double unitPrice) {
        String sql = "INSERT INTO receipt_item (receipt_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, receiptId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, unitPrice);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertStockMovement(int fromWarehouseId, int toWarehouseId, String reference) {
        String sql = "INSERT INTO stock_movement (from_warehouse_id, to_warehouse_id, reference) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fromWarehouseId);
            stmt.setInt(2, toWarehouseId);
            stmt.setString(3, reference);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean insertStockMovementItem(int stockMovementId, int productId, int quantity) {
        String sql = "INSERT INTO stock_movement_item (stock_movement_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stockMovementId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}