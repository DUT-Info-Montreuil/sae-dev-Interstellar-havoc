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

    // Méthode appelée lorsqu'on clique sur le bouton de retour
    @FXML
    void actionRetour(ActionEvent event) {
        Parent root;
        try {
            // Ferme la fenêtre actuelle
            Stage stage1 = (Stage) label.getScene().getWindow();
            stage1.close();

            // Charge le fichier FXML du menu principal
            root = FXMLLoader.load(Main.class.getResource("fxml/Menu/Menu.fxml"));

            // Crée une nouvelle fenêtre pour le menu principal
            Stage stage = new Stage();
            stage.setTitle("Tower Defence");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cette méthode est appelée lors de l'initialisation du contrôleur

    }
}
