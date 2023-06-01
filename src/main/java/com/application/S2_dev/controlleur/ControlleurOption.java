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

public class ControlleurOption implements Initializable {

    @FXML
    private Label label;

    @FXML
    void actionRetour(ActionEvent event) {
        // Action exécutée lors du clic sur le bouton "Retour"
        Parent root;
        try {
            // Ferme la fenêtre actuelle
            Stage stage1 = (Stage) label.getScene().getWindow();
            stage1.close();

            // Charge le fichier FXML correspondant à la fenêtre "Menu"
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tower Defence");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Méthode d'initialisation de l'interface Initializable
        // Peut être utilisée pour effectuer des actions lors du chargement de la fenêtre
    }
}
