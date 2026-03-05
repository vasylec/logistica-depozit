package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.Model.Inventory;
import com.example.Model.Receipt;
import com.example.Model.Supplier;
import com.example.Model.Warehouse;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    // ────────────────────────────────────────────────────────────────────────────

    public static ObservableList<Warehouse> warehouseTable;
    public static ObservableList<Inventory> inventoryTable;
    public static ObservableList<Supplier> supplierTable;
    public static ObservableList<Receipt> receiptTable;

    // ────────────────────────────────────────────────────────────────────────────

    public static void init(){
        warehouseTable = selectWarehouses();
        inventoryTable = selectInventories();
        supplierTable = selectSuppliers();
        receiptTable = selectReceipts();
    }

    public static Connection getConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
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

    public static ObservableList<Warehouse> selectWarehouses() {
        String sql = "SELECT * FROM warehouse";
        ObservableList<Warehouse> list = FXCollections.observableArrayList();
        try {
            Connection conn2 = getConnection();
            PreparedStatement stmt = conn2.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Warehouse(rs.getInt("id"),
                                       rs.getString("name"),
                                       rs.getString("location"),
                                       rs.getInt("capacity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static ObservableList<Inventory> selectInventories() {
        String sql = "SELECT * FROM inventory";
        ObservableList<Inventory> list = FXCollections.observableArrayList();
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Inventory(rs.getInt("id"),
                        rs.getInt("warehouseId"),
                        rs.getInt("productId"),
                        rs.getInt("quantity"),
                        rs.getTimestamp("quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static ObservableList<Receipt> selectReceipts() {
        String sql = "SELECT * FROM receipt";
        ObservableList<Receipt> list = FXCollections.observableArrayList();
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Receipt(rs.getInt("id"),
                        rs.getInt("supplierId"),
                        rs.getInt("warehouseId"),
                        rs.getTimestamp("receiptDate"),
                        rs.getString("documentNumber"),
                        rs.getString("notes")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static ObservableList<Supplier> selectSuppliers() {
        String sql = "SELECT * FROM supplier";
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Supplier(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("contact"),
                        rs.getString("address")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }
}