package com.example.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.App;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

    @FXML
    MenuItem depozitMenuItem;

    @FXML
    BorderPane root;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setMethodsToAllObjects();

    }

    private void setMethodsToAllObjects() {
        depozitMenuItem.setOnAction(eventHander_depozitMenuItem());
    }

    private EventHandler<ActionEvent> eventHander_depozitMenuItem() {
        return event -> {
            try {

                FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/" + "database" + ".fxml"));
                Node view = loader.load();
                root.setCenter(view);

            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
