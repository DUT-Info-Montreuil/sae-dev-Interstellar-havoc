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
        // Texte du labelCredit
        labelCredit.setText(
                "Merci d'avoir joué et défendu la galaxie !\n" +
                "Nous tenons à exprimer notre profonde gratitude envers les professeurs de l'IUT \n" +
                "pour leurs conseils et leur supervision tout au long de la création de ce jeu.\n" +
                "Ensemble, nous avons accompli quelque chose d'extraordinaire \n" +
                "\n"+
                "Merci encore pour votre participation et votre dévouement. \n"
        );
    }
    @FXML
    void buttonRetour(ActionEvent event) {
        Parent root;
        try {
            Stage stage1 = (Stage) labelCredit.getScene().getWindow(); // recuperation de la fenêtre parente du labelCredit
            stage1.close(); // fermeture de la fenêtre parente du labelCredit
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Menu de jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();  // Affichage du Menu de jeu
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
