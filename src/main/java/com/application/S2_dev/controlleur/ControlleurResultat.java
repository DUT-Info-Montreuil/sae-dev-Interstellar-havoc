package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.connexion.Connexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlleurResultat  implements Initializable {
    @FXML
    private Tab tabEnnemis;

    @FXML
    private Tab tabEnnemisTues;

    @FXML
    private Tab tabToursPlacees;

    @FXML
    private TabPane tab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connexion connexion = new Connexion();

        String sql1 = "select * from ennemis_tues;";
        connexion.initConnexion();
        connexion.executeQuery(sql1, tabEnnemisTues);

        String sql2 = "select * from partie_jouee;";
        Connexion connexion2 = new Connexion();
        connexion2.initConnexion();
        connexion2.executeQuery(sql2, tabEnnemis);

        String sql3 ="SELECT * FROM Partie;";
        Connexion connexion3 = new Connexion();
        connexion3.initConnexion();
        connexion3.executeQuery(sql3, tabToursPlacees);

    }

    @FXML
    void quitter(ActionEvent event) {
        exit();
    }
    public void exit() {
        Parent root;
        try {
            Stage stage1 = (Stage) tab.getScene().getWindow();
            stage1.close(); // Fermeture de la page de jeu
            root = FXMLLoader.load(Main.class.getResource("fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Menu de jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show(); // Affichage du menu de jeu
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
