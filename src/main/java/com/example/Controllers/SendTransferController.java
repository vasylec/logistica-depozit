package com.example.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.example.App;
import com.example.Database.DatabaseConnection;
import com.example.Model.Product;
import com.example.Model.Warehouse;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import net.sf.jasperreports.crosstabs.interactive.DataColumnInfo;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class SendTransferController implements Initializable {

    @FXML
    ChoiceBox<Warehouse> warehouseChoiceBox;
    @FXML
    ChoiceBox<Product> productChoiceBox;
    @FXML
    Spinner<Integer> quantitySpinner;
    @FXML
    TextField referenceTextField;
    @FXML
    Label label;

    List<Product> list;

    @FXML
    public void addProduct() {

        if (productChoiceBox.getValue() == null) {
            label.setText("Selectează un produs !");
            return;

        } else {
            Product product = productChoiceBox.getValue().clone();
            product.setQuantity(quantitySpinner.getValue());

            DatabaseConnection.inventoryProducts.stream()
                    .filter(p -> p.getId() == product.getId())
                    .forEach(p -> {
                        p.setQuantity(p.getQuantity() - product.getQuantity());
                    });

            Optional<Product> existing = list.stream()
                    .filter(p -> p.getId() == product.getId())
                    .findFirst();

            if (existing.isPresent()) {
                Product p = existing.get();
                p.setQuantity(p.getQuantity() + product.getQuantity());
            } else {
                list.add(product);
            }

            productCBox_setitems();
        }
    }

    @FXML
    public void showProducts() {
        if (list.isEmpty()) {
            label.setText("Lista e goală !");
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Mesaj");
            alert.setHeaderText("Lista de produse");

            String items = "";
            for (Product p : list) {
                items += p.toString() + "\n";
            }

            alert.setContentText("Ai in lista ta urmatoarele produse: \n\n" + items);
            alert.showAndWait();
        }
    }

    @FXML
    public void sendTransfer() {

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        list = new ArrayList<>();
        warehouseChoiceBox.setItems(DatabaseConnection.warehouseTable.stream()
                .filter(p -> p.getId() != App.warehouseID)
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));
        productCBox_setitems();
    }

    private void productCBox_setitems() {
        FilteredList<Product> filteredProducts = new FilteredList<>(
                DatabaseConnection.inventoryProducts,
                p -> p.getQuantity() > 0);

        productChoiceBox.setItems(filteredProducts);

        productChoiceBox.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                return product == null ? "" : product.getName() + " (cantitate: " + product.getQuantity() + ")";
            }

            @Override
            public Product fromString(String string) {
                return null; // nu e nevoie dacă ChoiceBox nu e editabil
            }
        });

        quantitySpinner.setEditable(true);
        quantitySpinner.getEditor().focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (!isFocused) {
                try {
                    int entered = Integer.parseInt(quantitySpinner.getEditor().getText());
                    int min = ((SpinnerValueFactory.IntegerSpinnerValueFactory) quantitySpinner.getValueFactory())
                            .getMin();
                    int max = ((SpinnerValueFactory.IntegerSpinnerValueFactory) quantitySpinner.getValueFactory())
                            .getMax();

                    if (entered < min) {
                        quantitySpinner.getValueFactory().setValue(min);
                    } else if (entered > max) {
                        quantitySpinner.getValueFactory().setValue(max);
                    } else {
                        quantitySpinner.getValueFactory().setValue(entered);
                    }
                } catch (NumberFormatException e) {
                    quantitySpinner.getValueFactory().setValue(1);
                }
            }
        });
        productChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                quantitySpinner.setValueFactory(
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, newVal.getQuantity(), 1));
            }
        });

        // productChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs,
        // oldVal, newVal) -> {
        // if (newVal != null) {
        // quantitySpinner.setValueFactory(
        // new SpinnerValueFactory.IntegerSpinnerValueFactory(1, newVal.getQuantity(),
        // 1));
        // quantitySpinner.setEditable(true);

        // quantitySpinner.getEditor().focusedProperty().addListener((obs_, wasFocused,
        // isFocused) -> {
        // if (!isFocused) { // când utilizatorul a ieșit din editor
        // try {
        // int entered = Integer.parseInt(quantitySpinner.getEditor().getText());
        // if (entered < 1) {
        // quantitySpinner.getValueFactory().setValue(1);
        // } else if (entered > newVal.getQuantity()) {
        // quantitySpinner.getValueFactory().setValue(newVal.getQuantity());
        // } else {
        // quantitySpinner.getValueFactory().setValue(entered);
        // }
        // } catch (NumberFormatException e) {
        // quantitySpinner.getValueFactory().setValue(1);
        // }
        // }
        // });

        // }
        // });

    }

}
