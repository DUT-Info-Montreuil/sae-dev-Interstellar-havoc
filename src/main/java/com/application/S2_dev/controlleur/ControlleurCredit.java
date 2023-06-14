package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlleurCredit implements Initializable {
    @FXML
    private Label labelCredit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelCredit.setText("Au nom de l'équipe d'Interstellar Havoc, merci d'avoir joué et avoir défendu la galaxie\n" +
                "\n" +
                "Rabab, Elsa, Mehdi, Franço\n" +
                "\n" +
                "Merci aux professeurs de l'IUT pour leurs conseils et supervision, ce jeu est possible grâce à vous.\n" +
                "Merci MILLE FOIS !");
    }


    @FXML
    void buttonRetour(ActionEvent event) {
        Parent root;
        try {
            Stage stage1 = (Stage) labelCredit.getScene().getWindow();
            stage1.close();
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Menu de jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
