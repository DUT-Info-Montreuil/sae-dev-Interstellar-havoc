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

public class ControlleurMenu implements Initializable  {

    @FXML
    private Label lab;
    @FXML
    void ButtonConsigne(ActionEvent event) {
        Parent root;
        try {
            // Fermer la fenêtre actuelle
            Stage stage1 = (Stage) lab.getScene().getWindow();
            stage1.close();

            // Charger le fichier FXML de Consigne
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Consigne/Consigne.fxml"));

            // Créer une nouvelle fenêtre pour afficher la Consigne
            Stage stage = new Stage();
            stage.setTitle("Consigne du jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void ButtonCredit(ActionEvent event) {
        Parent root;
        try {
            // Fermer la fenêtre actuelle
            Stage stage1 = (Stage) lab.getScene().getWindow();
            stage1.close();

            // Charger le fichier FXML de Credit
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Credit/Credit.fxml"));

            // Créer une nouvelle fenêtre pour afficher le Credit
            Stage stage = new Stage();
            stage.setTitle("Credit");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ButtonNiveau(ActionEvent event) {
        Parent root;
        try {
            // Fermer la fenêtre actuelle
            Stage stage1 = (Stage) lab.getScene().getWindow();
            stage1.close();

            // Charger le fichier FXML de Option
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Niveau/Option.fxml"));

            // Créer une nouvelle fenêtre pour afficher le Niveau de jeu
            Stage stage = new Stage();
            stage.setTitle("Niveau de jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actionQuit(ActionEvent event) {
        // Fermer la fenêtre
        Stage stage = (Stage) lab.getScene().getWindow();
        stage.close();
    }
    @FXML
    void ButtonPlay(ActionEvent event) {
            Parent root;
            try {
                // Fermer la fenêtre actuelle
                Stage stage1 = (Stage) lab.getScene().getWindow();
                stage1.close();

                // Charger le fichier FXML de TerrainJeu
                root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/TerrainJeu/TerrainJeu.fxml"));

                // Créer une nouvelle fenêtre pour afficher le Terrain de jeu
                Stage stage = new Stage();
                stage.setTitle("Interstellar Havoc: Infinite Frontiers");
                stage.setScene(new Scene(root, 1250, 800));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
