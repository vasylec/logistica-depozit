package com.example.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ErrorController implements Initializable {
    @FXML
    Label label;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        label.setText("Error: " + App.errorMessage);
    }

}
