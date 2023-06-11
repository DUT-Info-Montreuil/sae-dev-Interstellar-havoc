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
        labelCredit.setText("Bienvenue dans notre Tower Defense à thème spatial ! Votre mission consiste à défendre la base contre les vagues d'ennemis qui approchent. Voici votre consigne :\n" +
                "\n" +
                "Vous devez protégez la base en empêchant les ennemis d'atteindre son emplacement. Assurez-vous que la base ne subisse aucun dommage.\n" +
                "Le terrain de jeu représente l'univers. Utilisez cet environnement à votre avantage pour positionner vos tours de défense de manière stratégique.\n" +
                "Soyez prêt à affronter trois types d'ennemis redoutables : les Ballistes, les Scavengers et les Behemoths.\n" +
                "Chacun d'eux possède ses propres capacités et points faibles, alors étudiez-les attentivement pour adapter votre stratégie de défense.\n" +
                "\n" +
                "Vous disposez de trois types de tours de défense, correspondant aux caractéristiques des ennemis. Consultez votre inventaire pour en savoir plus sur les spécificités de chaque tour.\n" +
                "Choisissez judicieusement les tours à utiliser en fonction des vagues d'ennemis à venir.\n" +
                "Soyez conscient que le temps joue un rôle crucial dans votre défense. Les vagues d'ennemis arrivent à intervalles réguliers, alors assurez-vous de prévoir et de préparer vos tours à temps.\n" +
                "Ne laissez pas les ennemis approcher la base sans défense !\n" +
                "\n" +
                "En plus des tours de défense, vous disposez également d'autres objets spéciaux qui peuvent vous aider dans la bataille. Utilisez-les avec parcimonie et stratégie pour maximiser leur effet sur les ennemis.\n" +
                "Au fur et à mesure que vous progressez, vous aurez peut-être la possibilité d'améliorer vos tours de défense.\n" +
                "Gérez efficacement vos ressources pour renforcer votre défense et faire face à des vagues d'ennemis de plus en plus coriaces.\n" +
                "Anticipez les mouvements des ennemis et ajustez votre stratégie en conséquence. Expérimentez différentes configurations de tours et d'objets spéciaux pour trouver celle qui vous convient le mieux.\n" +
                "Restez vigilant et réactif ! Vous devrez peut-être ajuster votre défense en temps réel pour faire face à des situations inattendues ou à des ennemis particulièrement résistants.\n" +
                "\n" +
                "Le jeu enregistre votre performance en fonction de votre capacité à défendre la base. Visez un score élevé et essayez de vous hisser en tête du classement.\n" +
                "Préparez-vous à une bataille spatiale épique dans notre Tower Defense à thème de l'univers. Faites preuve de stratégie, de précision et de rapidité pour protéger la base et sauver l'univers !\n" +
                "\n" +
                "Bonne chance !");
    }

    @FXML
    void buttonRetour(ActionEvent event) {
        Parent root;
        try {
            Stage stage1 = (Stage) labelCredit.getScene().getWindow();
            stage1.close();
            root = FXMLLoader.load(Main.class.getResource("/fxml/Menu/Menu.fxml"));
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
