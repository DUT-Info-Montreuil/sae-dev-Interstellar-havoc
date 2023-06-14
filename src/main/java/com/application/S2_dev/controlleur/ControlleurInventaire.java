package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlleurInventaire implements Initializable {

    @FXML
    private Label labelBombe;

    @FXML
    private Label labelDescription;

    @FXML
    private Label labelGrandeImage;

    @FXML
    private Label labelMaintenace;

    @FXML
    private Label labelMur;

    @FXML
    private Label labelTourNikola;

    @FXML
    private Label labelTourEdison;

    @FXML
    private Label labelTourOppenheimer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        labelMaintenace.setOnMouseClicked(
                e -> {
                    URL urlImageMaintenance = Main.class.getResource("image/Inventaire/maintenance.png");
                    Image imageMaintenance = new Image(String.valueOf(urlImageMaintenance));
                    ImageView imageViewMaintenance = new ImageView(imageMaintenance);
                    imageViewMaintenance.setFitWidth(200);
                    imageViewMaintenance.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewMaintenance);
                    labelDescription.setText("Les maintenances permettent de réparer les tours sur la Map " +
                            "Il est appliqué à toutes les Tours.\n.");
                }
        );

        labelMur.setOnMouseClicked(
                e -> {
                    URL urlImageMur = Main.class.getResource("image/Inventaire/mur.png");
                    Image imageMur = new Image(String.valueOf(urlImageMur));
                    ImageView imageViewMur = new ImageView(imageMur);
                    imageViewMur.setFitWidth(200);
                    imageViewMur.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewMur);labelDescription.setText("Les Murs sont des dispositifs spéciaux que les joueurs peuvent " +
                            "placer sur le terrain de jeu.\nElles permettent de bloquer la progression des ennemies " +
                            "\n Lorsque les ennemis s'approchent du Mur, ils le détruiront.");
                }
        );

        labelBombe.setOnMouseClicked(
                e -> {
                    URL urlImageBombe = Main.class.getResource("image/Inventaire/bombe.png");
                    Image imageBombe = new Image(String.valueOf(urlImageBombe));
                    ImageView imageViewBombe = new ImageView(imageBombe);
                    imageViewBombe.setFitWidth(200);
                    imageViewBombe.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewBombe);
                    labelDescription.setText("Les bombes sont des dispositifs spéciaux que les joueurs peuvent " +
                            "placer sur le terrain de jeu.\nElles sont généralement équipées de minuteries précises et " +
                            "d'un pouvoir dévastateur.\nLorsque les ennemis s'approchent de la bombe, elle explose violemment," +
                            " infligeant des dégâts considérables à tout ce qui se trouve à proximité.\n");
                }
        );

        labelTourNikola.setOnMouseClicked(
                e -> {
                    URL urlImageTour1 = Main.class.getResource("image/Inventaire/Nikola.png");
                    Image imageTour1 = new Image(String.valueOf(urlImageTour1));
                    ImageView imageViewTour1 = new ImageView(imageTour1);
                    imageViewTour1.setFitWidth(200);
                    imageViewTour1.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewTour1);
                    labelDescription.setText("La bobine Edison est la première génération de bobine développés par IHI" +
                            "placer sur le terrain de jeu.\nElles ont généralement une faible portée ainsi que des dégats modérés" +
                            "Elle est cependant très peu couteuse.\n");
                }
        );

        labelTourEdison.setOnMouseClicked(
                e -> {
                    URL urlImageTour2 = Main.class.getResource("image/Inventaire/Edison.png");
                    Image imageTour2 = new Image(String.valueOf(urlImageTour2));
                    ImageView imageViewTour2 = new ImageView(imageTour2);
                    imageViewTour2.setFitWidth(200);
                    imageViewTour2.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewTour2);
                    labelDescription.setText("La bobine Nikola est la deuxième génération de bobine développés par IHI" +
                            "placé sur le terrain de jeu.\nElles ont généralement une Moyenne portée mais  des dégats considérable" +
                            "Elle est cependant un peu plus couteuse que sa prédécesseuse.\n");
                }
        );

        labelTourOppenheimer.setOnMouseClicked(
                e -> {
                    URL urlImageTour3 = Main.class.getResource("image/Inventaire/Oppenheimer.png");
                    Image imageTour3 = new Image(String.valueOf(urlImageTour3));
                    ImageView imageViewTour3 = new ImageView(imageTour3);
                    imageViewTour3.setFitWidth(200);
                    imageViewTour3.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewTour3);
                    labelDescription.setText("La bobine Oppenheimer est la troisième et dernière génération de bobine développés par IHI" +
                            "placé sur le terrain de jeu.\nElles ont une très grande portée ainsi que des dégâts immenses" +
                            "Elle est cependant la plus couteuse des bobines D'Interstellar Havoc Industries.\n");
                }
        );
    }
}
