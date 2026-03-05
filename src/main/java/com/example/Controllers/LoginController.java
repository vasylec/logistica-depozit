package com.example.Controllers;

import java.io.IOException;

import com.example.App;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    TextField textField;

    @FXML
    public void login() throws IOException {
        if (textField.getText().equals("1")) {
            App.setRoot("main");
        }
    }
}
