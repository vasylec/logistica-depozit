package com.example.Controllers;

import java.io.IOException;
import java.net.URL;
import java.security.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.App;
import com.example.Database.DatabaseConnection;
import com.example.Model.ReceiptProduct;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class RegisterReceptionController implements Initializable {
    @FXML
    Label label;

    @FXML
    MenuButton menuButton;

    @FXML
    TableView<ReceiptProduct> tableView;

    @FXML
    TableColumn<Integer, ReceiptProduct> idReceipt;
    @FXML
    TableColumn<String, ReceiptProduct> product;
    @FXML
    TableColumn<Integer, ReceiptProduct> quantity;
    @FXML
    TableColumn<Timestamp, ReceiptProduct> receiptDate;
    @FXML
    TableColumn<String, ReceiptProduct> receiptNotes;
    @FXML
    TableColumn<Boolean, ReceiptProduct> isprocessedTableColumn;

    @FXML
    Button buttonConfirmAll;
    @FXML
    Button buttonNotFoundAll;
    @FXML
    Button buttonAddItem;
    @FXML
    Button buttonRemoveItem;
    @FXML
    Button buttonConfirm;

    private int totalQuantity;
    private int warehouseQuantity;
    private int varTotalQuantity = 0;
    private int items = 0;

    private Long receiptId;

    @FXML
    void confirmProcess() throws SQLException {
        if (App.reception_tranfer.equals("reception")) {
            List<ReceiptProduct> list = new ArrayList<>();

            tableView.getItems().forEach(item -> {
                if (item.isHighlighted()) {
                    list.add(item);
                }
            });

            DatabaseConnection.updateReceipt(receiptId, list);
        } else {
            List<ReceiptProduct> list = new ArrayList<>();

            tableView.getItems().forEach(item -> {
                if (item.isHighlighted()) {
                    list.add(item);
                }
            });

            DatabaseConnection.updateStockMovement(receiptId, list);
        }

    }

    @FXML
    void confirmAllButton() {

        tableView.getItems().forEach(item -> item.setHighlighted(true));
        tableView.refresh();

        varTotalQuantity = totalQuantity;
        label.setText(
                "Cantitatea receptiei: " + varTotalQuantity + " | Cantitatea inventarului : "
                        + warehouseQuantity);
    }

    @FXML
    void notFoundAll() {
        tableView.getItems().forEach(item -> item.setHighlighted(false));
        tableView.refresh();
        varTotalQuantity = 0;
        label.setText(
                "Cantitatea receptiei: " + varTotalQuantity + " | Cantitatea inventarului : "
                        + warehouseQuantity);
    }

    @FXML
    void addItem() {
        // întâi resetăm highlight-ul tuturor rândurilor

        // tableView.getItems().forEach(item -> item.setHighlighted(false));

        // apoi setăm highlight doar pentru rândul selectat
        ReceiptProduct selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {

            if (selectedItem.isHighlighted() == false) {
                selectedItem.setHighlighted(true);
                items++;
                varTotalQuantity += selectedItem.getQuantity();
            }

        }

        if (items > 0)
            label.setText(
                    "Cantitatea receptiei: " + varTotalQuantity + " | Cantitatea inventarului : "
                            + warehouseQuantity);
        else if (items == 0)
            label.setText(
                    "Cantitatea receptiei: " + totalQuantity + " | Cantitatea inventarului : "
                            + warehouseQuantity);

        // forțăm TableView să redea rândurile
        tableView.refresh();
    }

    @FXML
    void removeItem() {
        ReceiptProduct selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.isHighlighted() == true) {
                selectedItem.setHighlighted(false);
                items--;
                varTotalQuantity -= selectedItem.getQuantity();
            }
        }

        if (items > 0)
            label.setText(
                    "Cantitatea receptiei: " + varTotalQuantity + " | Cantitatea inventarului : "
                            + warehouseQuantity);
        else if (items == 0)
            label.setText(
                    "Cantitatea receptiei: " + totalQuantity + " | Cantitatea inventarului : "
                            + warehouseQuantity);

        tableView.refresh();
    }

    private void setButtons(boolean value) {
        buttonAddItem.setVisible(value);
        buttonConfirm.setVisible(value);
        buttonConfirmAll.setVisible(value);
        buttonNotFoundAll.setVisible(value);
        buttonRemoveItem.setVisible(value);

        buttonAddItem.setDisable(!value);
        buttonConfirm.setDisable(!value);
        buttonConfirmAll.setDisable(!value);
        buttonNotFoundAll.setDisable(!value);
        buttonRemoveItem.setDisable(!value);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        idReceipt.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        product.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        receiptDate.setCellValueFactory(new PropertyValueFactory<>("receiptDate"));
        receiptNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
        isprocessedTableColumn.setCellValueFactory(new PropertyValueFactory<>("isprocessed"));

        menuButton.getItems().clear();
        label.setTextFill(Color.BLACK);

        tableView.setRowFactory(null);
        setButtons(false);

        tableView.setRowFactory(tv -> new TableRow<ReceiptProduct>() {
            @Override
            protected void updateItem(ReceiptProduct item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle(""); // rând gol sau null
                } else if (isSelected() && !item.isHighlighted()) {
                    // culoarea rândului selectat (overridează highlight)
                    setStyle("-fx-background-color: blue;");
                } else if (item.isHighlighted() && !isSelected()) {
                    // rând highlight-uit dar nu selectat
                    setStyle("-fx-background-color: lightgreen;");
                } else if (isSelected() && item.isHighlighted()) {
                    setStyle("-fx-background-color: lightblue;");
                } else {
                    // rând normal
                    setStyle("");
                }
            }
        });

        if (App.reception_tranfer.equals("reception")) {
            setButtons(true);
            menuButton.setText("Selectează recepție");

            for (MenuItem menuItem : DatabaseConnection.receptionMenuList) {

                menuItem.setOnAction(e -> {

                    Parent savedRoot = App.getRoot();
                    try {
                        Task<Void> dbTask = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                receiptId = Long.parseLong(menuItem.getId());
                                tableView.setItems(
                                        DatabaseConnection.selectReceiptById(Long.parseLong(menuItem.getId()), false));

                                int receptionQunatity = DatabaseConnection
                                        .selectReceiptQuantityByReceiptId(Long.parseLong(menuItem.getId()));

                                totalQuantity = receptionQunatity;

                                warehouseQuantity = DatabaseConnection.getWarehouseCapacity(App.warehouseID);

                                if (warehouseQuantity >= receptionQunatity)
                                    label.setTextFill(Color.GREEN);
                                else
                                    label.setTextFill(Color.RED);

                                label.setText(
                                        "Cantitatea receptiei: " + receptionQunatity + " | Cantitatea inventarului : "
                                                + warehouseQuantity);
                                return null;
                            }
                        };

                        dbTask.setOnRunning(event -> {
                            try {
                                App.setRoot("loadingScreen");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        dbTask.setOnSucceeded(event -> {
                            try {
                                App.setRoot(savedRoot);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        dbTask.setOnFailed(event -> {
                            Throwable ex = dbTask.getException(); // captura excepția din task
                            ex.printStackTrace(); // vezi ce a cauzat fail
                            App.errorMessage = "Database connection failure: " + ex.getMessage();
                            try {
                                // App.errorMessage = "ERROR";
                                App.setRoot("errorPage");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        new Thread(dbTask).start();

                    } catch (NumberFormatException e1) {
                        e1.printStackTrace();
                    }
                });

                menuButton.getItems().add(menuItem);
            }

        } else if (App.reception_tranfer.equals("get transfer")) {
            setButtons(true);
            menuButton.setText("Selectează transfer");

            for (MenuItem menuItem : DatabaseConnection.transferGetMenuList) {
                menuItem.setOnAction(e -> {
                    System.out.println(menuItem.getId());

                    Parent savedRoot = App.getRoot();

                    try {
                        Task<Void> dbTask = new Task<>() {

                            @Override
                            protected Void call() throws Exception {
                                tableView.setItems(
                                        DatabaseConnection.selectTransferById(Long.parseLong(menuItem.getId())));

                                totalQuantity = DatabaseConnection
                                        .selectTransferQuantityByReceiptId(Long.parseLong(menuItem.getId()));
                                warehouseQuantity = DatabaseConnection.getWarehouseCapacity(App.warehouseID);

                                if (warehouseQuantity >= totalQuantity)
                                    label.setTextFill(Color.GREEN);
                                else
                                    label.setTextFill(Color.RED);

                                System.out.println("TRANSFER Q: " + totalQuantity);

                                label.setText(
                                        "Cantitatea transferului: " + totalQuantity + " | Cantitatea inventarului : "
                                                + warehouseQuantity);
                                return null;
                            }
                        };

                        dbTask.setOnRunning(event -> {
                            try {
                                App.setRoot("loadingScreen");
                            } catch (

                            IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        dbTask.setOnSucceeded(event -> {
                            try {
                                App.setRoot(savedRoot);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        dbTask.setOnFailed(event -> {
                            try {
                                App.errorMessage = "ERROR";
                                App.setRoot("errorPage");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        new Thread(dbTask).start();

                    } catch (NumberFormatException e1) {
                        e1.printStackTrace();
                    }
                });
                menuButton.getItems().add(menuItem);
            }
        } else if (App.reception_tranfer.equals("send transfer")) {

        } else if (App.reception_tranfer.equals("get transfer history")) {
            tableView.setRowFactory(tv -> new TableRow<ReceiptProduct>() {
                @Override
                protected void updateItem(ReceiptProduct item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setStyle("");
                    } else if (isSelected()) {
                        setStyle("");
                    } else if (item.getIsprocessed() == false) {
                        setStyle("-fx-background-color: #ff9999;");
                    } else if (item.getIsprocessed() == true) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        // rând normal
                        setStyle("");
                    }
                }
            });

            menuButton.setText("Selectează transfer");

            for (MenuItem menuItem : DatabaseConnection.transferGetHistoryMenuList) {
                menuItem.setOnAction(e -> {
                    Parent savedRoot = App.getRoot();
                    try {
                        Task<Void> dbTask = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                tableView.setItems(
                                        DatabaseConnection.selectTransferHistory(Long.parseLong(menuItem.getId())));

                                int transferQunatity = DatabaseConnection
                                        .selectTransferQuantityByReceiptId(Long.parseLong(menuItem.getId()));
                                warehouseQuantity = DatabaseConnection.getWarehouseCapacity(App.warehouseID);

                                label.setText(
                                        "Cantitatea transferului: " + transferQunatity + " | Cantitatea inventarului : "
                                                + warehouseQuantity);
                                return null;
                            }
                        };

                        dbTask.setOnRunning(event -> {

                            try {
                                App.setRoot("loadingScreen");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        dbTask.setOnSucceeded(event -> {
                            try {
                                App.setRoot(savedRoot);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        dbTask.setOnFailed(event -> {
                            Throwable ex = dbTask.getException(); // captura excepția din task
                            ex.printStackTrace(); // vezi ce a cauzat fail
                            App.errorMessage = "Database connection failure: " + ex.getMessage();
                            try {
                                // App.errorMessage = "ERROR";
                                App.setRoot("errorPage");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        new Thread(dbTask).start();

                    } catch (NumberFormatException e1) {
                        e1.printStackTrace();
                    }

                });
                menuButton.getItems().add(menuItem);
            }
        } else if (App.reception_tranfer.equals("get receipt history")) {
            menuButton.setText("Selectează recepție");

            tableView.setRowFactory(tv -> new TableRow<ReceiptProduct>() {
                @Override
                protected void updateItem(ReceiptProduct item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setStyle("");
                    } else if (isSelected()) {
                        setStyle("");
                    } else if (item.getIsprocessed() == false) {
                        setStyle("-fx-background-color: #ff9999;");
                    } else if (item.getIsprocessed() == true) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else {
                        // rând normal
                        setStyle("");
                    }
                }
            });

            for (MenuItem menuItem : DatabaseConnection.receptionMenuList) {

                menuItem.setOnAction(e -> {

                    Parent savedRoot = App.getRoot();
                    try {
                        Task<Void> dbTask = new Task<>() {
                            @Override
                            protected Void call() throws Exception {
                                tableView.setItems(
                                        DatabaseConnection.selectReceiptById(Long.parseLong(menuItem.getId()), false));

                                int receptionQunatity = DatabaseConnection
                                        .selectReceiptQuantityByReceiptId(Long.parseLong(menuItem.getId()));

                                totalQuantity = receptionQunatity;
                                warehouseQuantity = DatabaseConnection.getWarehouseCapacity(App.warehouseID);

                                boolean isProcessed = DatabaseConnection
                                        .getIsReceiptProcessed(Long.parseLong(menuItem.getId()));

                                label.setText(
                                        "Cantitatea receptiei: " + receptionQunatity + " | Cantitatea inventarului : "
                                                + warehouseQuantity + " | Receptie procesata : " + isProcessed);
                                return null;
                            }
                        };

                        dbTask.setOnRunning(event -> {
                            try {
                                App.setRoot("loadingScreen");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        dbTask.setOnSucceeded(event -> {
                            try {
                                App.setRoot(savedRoot);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        dbTask.setOnFailed(event -> {
                            Throwable ex = dbTask.getException(); // captura excepția din task
                            ex.printStackTrace(); // vezi ce a cauzat fail
                            App.errorMessage = "Database connection failure: " + ex.getMessage();
                            try {
                                // App.errorMessage = "ERROR";
                                App.setRoot("errorPage");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        });

                        new Thread(dbTask).start();

                    } catch (NumberFormatException e1) {
                        e1.printStackTrace();
                    }
                });

                menuButton.getItems().add(menuItem);
            }
        }

    }

}
