package com.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.App;
import com.example.Model.Inventory;
import com.example.Model.Product;
import com.example.Model.Receipt;
import com.example.Model.ReceiptProduct;
import com.example.Model.Supplier;
import com.example.Model.Warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

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

    public static ObservableList<ReceiptProduct> receiptProducts;

    // ────────────────────────────────────────────────────────────────────────────

    public static List<MenuItem> receptionMenuList;
    public static List<MenuItem> transferSentMenuList;
    public static List<MenuItem> transferGetMenuList;
    public static List<MenuItem> transferGetHistoryMenuList;

    // ────────────────────────────────────────────────────────────────────────────

    public static ObservableList<Product> inventoryProducts;

    public static void initConnect() throws SQLException {
        getConnection();
    }

    public static void init() throws SQLException {
        warehouseTable = selectWarehouses();
        inventoryTable = selectInventories();
        supplierTable = selectSuppliers();
        receiptTable = selectReceipts();

        receptionMenuList = selectReception();
        transferSentMenuList = selectSentTransfer();
        transferGetMenuList = selectGetTransfer();
        transferGetHistoryMenuList = selectGetTransferHistory();

        inventoryProducts = loadProductsInventory(App.warehouseID);
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

    public static ObservableList<Warehouse> selectWarehouses() throws SQLException {
        String sql = "SELECT * FROM warehouse";
        ObservableList<Warehouse> list = FXCollections.observableArrayList();
        Connection conn2 = getConnection();
        PreparedStatement stmt = conn2.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(new Warehouse(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getInt("capacity")));
        }
        return list;
    }

    public static ObservableList<Inventory> selectInventories() throws SQLException {
        String sql = "SELECT * FROM inventory";
        ObservableList<Inventory> list = FXCollections.observableArrayList();
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(new Inventory(rs.getInt("id"),
                    rs.getInt("warehouseId"),
                    rs.getInt("productId"),
                    rs.getInt("quantity"),
                    rs.getTimestamp("lastupdated")));
        }
        return list;

        // WHERE warehouseId = " + App.warehouseID
    }

    public static ObservableList<Receipt> selectReceipts() throws SQLException {
        String sql = "SELECT * FROM receipt";
        ObservableList<Receipt> list = FXCollections.observableArrayList();
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
        return list;
    }

    public static ObservableList<Supplier> selectSuppliers() throws SQLException {
        String sql = "SELECT * FROM supplier";
        ObservableList<Supplier> list = FXCollections.observableArrayList();
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
        return list;
    }

    public static List<MenuItem> selectReception() throws SQLException {
        String sql = "SELECT id, documentnumber FROM receipt WHERE warehouseid = " + App.warehouseID;
        List<MenuItem> menu = new ArrayList<>();
        Connection conn;
        conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            MenuItem menuItem = new MenuItem(rs.getString("documentnumber"));
            menuItem.setId(Integer.toString(rs.getInt("id")));
            System.out.println(menuItem.getText());
            menu.add(menuItem);
        }

        return menu;
    }

    public static List<MenuItem> selectSentTransfer() throws SQLException {

        String sql = "SELECT id, reference FROM stockmovement WHERE fromwarehouseid = " + App.warehouseID;

        List<MenuItem> menu = new ArrayList<>();
        Connection conn;
        conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            MenuItem menuItem = new MenuItem(rs.getString("reference"));
            menuItem.setId(Integer.toString(rs.getInt("id")));
            menu.add(menuItem);
        }

        return menu;
    }

    public static List<MenuItem> selectGetTransfer() throws SQLException {

        String sql = "SELECT id, reference FROM stockmovement WHERE towarehouseid = " + App.warehouseID
                + " AND isprocessed = FALSE";

        List<MenuItem> menu = new ArrayList<>();
        Connection conn;
        conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            MenuItem menuItem = new MenuItem(rs.getString("reference"));
            menuItem.setId(Integer.toString(rs.getInt("id")));
            menu.add(menuItem);
        }

        return menu;
    }

    public static List<MenuItem> selectGetTransferHistory() throws SQLException {

        String sql = "SELECT id, reference FROM stockmovement WHERE towarehouseid = ? OR fromwarehouseid = ?";

        List<MenuItem> menu = new ArrayList<>();
        Connection conn;
        conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setLong(1, App.warehouseID);
        stmt.setLong(2, App.warehouseID);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            MenuItem menuItem = new MenuItem(rs.getString("reference"));
            menuItem.setId(Integer.toString(rs.getInt("id")));
            menu.add(menuItem);
        }

        return menu;
    }

    public static int getWarehouseCapacity(Long warehouseID) throws SQLException {
        String sql = "SELECT capacity FROM warehouse WHERE id = ?";

        Connection conn = getConnection();
        PreparedStatement psmt = conn.prepareStatement(sql);

        psmt.setLong(1, warehouseID);

        ResultSet rs = psmt.executeQuery();

        int capacity = 0;

        if (rs.next()) {
            capacity = rs.getInt("capacity");
        }

        return capacity;
    }

    public static int selectReceiptQuantityByReceiptId(Long receiptIdParam) throws SQLException {
        String sql = "SELECT SUM(quantity) AS total " +
                "FROM receiptitem " +
                "INNER JOIN receipt ON receiptitem.receiptid = receipt.id " +
                "INNER JOIN product ON receiptitem.productid = product.id " +
                "WHERE warehouseid = ? AND receiptid = ?";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setLong(1, App.warehouseID);
        stmt.setLong(2, receiptIdParam);

        ResultSet rs = stmt.executeQuery();

        int sum = 0;

        if (rs.next()) {
            sum = rs.getInt("total");
        }

        return sum;
    }

    // public static int selectTransferQuantityByReceiptId(Long receiptIdParam)
    // throws SQLException {

    // String sql = "SELECT SUM(quantity) AS total\r\n" +
    // "FROM stockmovementitem smi\r\n" +
    // "INNER JOIN product ON productid = product.id\r\n" +
    // "INNER JOIN stockmovement ON stockmovementid = stockmovement.id\r\n" +
    // "WHERE stockmovement.id = ?";

    // Connection conn = getConnection();

    // PreparedStatement stmt = conn.prepareStatement(sql);
    // stmt.setLong(1, App.warehouseID);

    // ResultSet rs = stmt.executeQuery();

    // Long quantity = (long) 0;
    // if (rs.next()) {
    // quantity = rs.getLong(1);
    // }

    // int i = (int) quantity;

    // return i;
    // }

    public static int selectTransferQuantityByReceiptId(Long receiptIdParam) throws SQLException {

        String sql = "SELECT SUM(quantity) AS total " +
                "FROM stockmovementitem smi " +
                "INNER JOIN product p ON smi.productid = p.id " +
                "INNER JOIN stockmovement sm ON smi.stockmovementid = sm.id " +
                "WHERE sm.id = ?";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, receiptIdParam);

            try (ResultSet rs = stmt.executeQuery()) {
                long quantity = 0;
                if (rs.next()) {
                    quantity = rs.getLong("total"); // citim alias-ul
                    if (rs.wasNull()) { // dacă e NULL
                        quantity = 0;
                    }
                }

                // Conversie sigură la int
                if (quantity > Integer.MAX_VALUE) {
                    throw new IllegalArgumentException("Quantity prea mare pentru int");
                }

                return (int) quantity;
            }
        }
    }

    public static ObservableList<ReceiptProduct> selectReceiptById(Long receiptIdParam, boolean forHistory)
            throws SQLException {

        String sql = "";
        if (forHistory == false)
            sql = "SELECT\r\n" + //
                    "    receipt.id,\r\n" + //
                    "    product.id AS product_id,\r\n" + //
                    "    product.name,\r\n" + //
                    "    quantity,\r\n" + //
                    "    receiptdate,\r\n" + //
                    "    notes,\r\n" + //
                    "    receiptitem.isprocessed AS isprocessed\r\n" + //
                    "FROM\r\n" + //
                    "    receiptitem\r\n" + //
                    "    INNER JOIN receipt ON receiptitem.receiptid = receipt.id\r\n" + //
                    "    INNER JOIN product ON receiptitem.productid = product.id\r\n" + //
                    "WHERE\r\n" + //
                    "    warehouseid = \r\n" + App.warehouseID + //
                    "    AND\r\n" + //
                    "    receiptid = " + receiptIdParam + " AND receipt.isprocessed = FALSE";
        else
            sql = "SELECT\r\n" + //
                    "    receipt.id,\r\n" + //
                    "    product.id AS product_id,\r\n" + //
                    "    product.name,\r\n" + //
                    "    quantity,\r\n" + //
                    "    receiptdate,\r\n" + //
                    "    notes,\r\n" + //
                    "    receiptitem.isprocessed AS isprocessed\r\n" + //
                    "FROM\r\n" + //
                    "    receiptitem\r\n" + //
                    "    INNER JOIN receipt ON receiptitem.receiptid = receipt.id\r\n" + //
                    "    INNER JOIN product ON receiptitem.productid = product.id\r\n" + //
                    "WHERE\r\n" + //
                    "    warehouseid = \r\n" + App.warehouseID + //
                    "    AND\r\n" + //
                    "    receiptid = " + receiptIdParam;

        ObservableList<ReceiptProduct> list = FXCollections.observableArrayList();
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            list.add(new ReceiptProduct(
                    rs.getInt("id"),
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getTimestamp("receiptdate"),
                    rs.getString("notes"),
                    rs.getBoolean("isprocessed")));
        }
        return list;
    }

    public static ObservableList<ReceiptProduct> selectTransferById(Long transferID) throws SQLException {
        String sql = "SELECT " +
                "stockmovement.id, " +
                "productid, " +
                "product.name, " +
                "quantity, " +
                "movementdate, " +
                "reference, " +
                "smi.isprocessed AS isprocessed " +
                "FROM stockmovementitem smi " +
                "INNER JOIN product ON productid = product.id " +
                "INNER JOIN stockmovement ON stockmovementid = stockmovement.id " +
                "WHERE towarehouseid = ? " +
                "AND stockmovement.id = ?";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setLong(1, App.warehouseID);
        stmt.setLong(2, transferID);
        ResultSet rs = stmt.executeQuery();

        ObservableList<ReceiptProduct> list = FXCollections.observableArrayList();
        while (rs.next()) {
            list.add(new ReceiptProduct(rs.getInt("id"),
                    rs.getInt("productid"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getTimestamp("movementdate"),
                    rs.getString("reference"),
                    rs.getBoolean("isprocessed")));
        }

        return list;
    }

    public static ObservableList<ReceiptProduct> selectTransferHistory(Long transferId) throws SQLException {
        String sql = "SELECT " +
                "stockmovement.id, " +
                "productid, " +
                "product.name, " +
                "quantity, " +
                "movementdate, " +
                "reference, " +
                "smi.isprocessed AS isprocessed " +
                "FROM stockmovementitem smi " +
                "INNER JOIN product ON productid = product.id " +
                "INNER JOIN stockmovement ON stockmovementid = stockmovement.id " +
                "WHERE (towarehouseid = ? OR fromwarehouseid = ?) AND stockmovement.id = ?";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setLong(1, App.warehouseID);
        stmt.setLong(2, App.warehouseID);
        stmt.setLong(3, transferId);
        ResultSet rs = stmt.executeQuery();

        ObservableList<ReceiptProduct> list = FXCollections.observableArrayList();
        while (rs.next()) {
            list.add(new ReceiptProduct(rs.getInt("id"),
                    rs.getInt("productid"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getTimestamp("movementdate"),
                    rs.getString("reference"),
                    rs.getBoolean("isprocessed")));
        }

        return list;
    }

    public static boolean getLogin(Long id) throws SQLException {
        String sql = "SELECT isWarehouseIdValid(?)";

        Connection conn = getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        boolean isValid = false;

        if (rs.next()) {
            isValid = rs.getBoolean(1);
        }

        return isValid;
    }

    public static boolean getIsReceiptProcessed(Long receiptId) throws SQLException {
        String sql = "SELECT isprocessed FROM receipt WHERE id = ?";

        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, receiptId);

        ResultSet rs = stmt.executeQuery();

        boolean b = false;
        if (rs.next())
            b = rs.getBoolean(1);

        return b;
    }

    public static void updateReceipt(Long receiptId, List<ReceiptProduct> list) throws SQLException {

        String sql = "UPDATE receiptitem SET isprocessed = ? WHERE id = ? AND receiptid = ?";
        String sql2 = "UPDATE receipt SET isprocessed = ? WHERE id = ?";

        boolean allProcessed = true;

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

            for (ReceiptProduct prod : list) {

                boolean processed = prod.isHighlighted();
                prod.setIsprocessed(processed);

                if (!processed)
                    allProcessed = false;

                stmt.setBoolean(1, processed);
                stmt.setLong(2, prod.getItemId());
                stmt.setLong(3, receiptId);

                stmt.addBatch();
            }

            stmt.executeBatch();

            stmt2.setBoolean(1, allProcessed);
            stmt2.setLong(2, receiptId);
            stmt2.executeUpdate();
        }
    }

    public static void updateStockMovement(Long movementId, List<ReceiptProduct> list) throws SQLException {

        String sql = "UPDATE stockmovementitem SET isprocessed = ? WHERE id = ? AND stockmovementid = ?";
        String sql2 = "UPDATE stockmovement SET isprocessed = ? WHERE id = ?";

        boolean allProcessed = true;

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

            for (ReceiptProduct prod : list) {

                boolean processed = prod.isHighlighted();
                prod.setIsprocessed(processed);

                if (!processed)
                    allProcessed = false;

                stmt.setBoolean(1, processed);
                stmt.setLong(2, prod.getItemId());
                stmt.setLong(3, movementId);

                stmt.addBatch();
            }

            stmt.executeBatch();

            stmt2.setBoolean(1, allProcessed);
            stmt2.setLong(2, movementId);
            stmt2.executeUpdate();
        }
    }

    public static ObservableList<Product> loadProductsInventory(Long warehouseId) throws SQLException {
        String sql = "SELECT productid, name, description, unitprice, volume, quantity FROM inventory " +
                "INNER JOIN product ON productid = product.id WHERE warehouseid = ?";

        Connection conn = getConnection();
        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setLong(1, warehouseId);
        ResultSet rs = pstm.executeQuery();

        ObservableList<Product> list = FXCollections.observableArrayList();

        while (rs.next()) {
            list.add(new Product(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDouble(4),
                    rs.getDouble(5),
                    rs.getInt(6)));
        }

        return list;
    }

}