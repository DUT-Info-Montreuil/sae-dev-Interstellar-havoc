package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlleurOption implements Initializable {
    @FXML
    private Label label;

    @FXML
    void actionRetour(ActionEvent event) {
        Parent root;
        try {
            Stage stage1 = (Stage) label.getScene().getWindow();
            stage1.close();
            root = FXMLLoader.load(Main.class.getResource("fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tower Defence");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
