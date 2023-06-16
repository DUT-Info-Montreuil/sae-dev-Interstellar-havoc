package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @FXML
    private Label labelHydrogene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Action lorsque labelMaintenace est cliqué
        labelMaintenace.setOnMouseClicked(
                e -> {
                    // Chargement de l'image et création de l'ImageView correspondant
                    URL urlImageMaintenance = Main.class.getResource("image/Inventaire/maintenance.png");
                    Image imageMaintenance = new Image(String.valueOf(urlImageMaintenance));
                    ImageView imageViewMaintenance = new ImageView(imageMaintenance);
                    imageViewMaintenance.setFitWidth(200);
                    imageViewMaintenance.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewMaintenance); // Ajout de l'image View sur une grande zone d'affichage
                    labelGrandeImage.setText(""); // j'efface le texte deja present dans le labelGrandeImage
                    // description de l'objet
                    labelDescription.setText("Les maintenances permettent de réparer les tours sur la Map " +
                            "Il est appliqué à toutes les Tours.");
                }
        );

        // Action lorsque labelMur est cliqué
        labelMur.setOnMouseClicked(
                e -> {
                    // Chargement de l'image et création de l'ImageView correspondant
                    URL urlImageMur = Main.class.getResource("image/Inventaire/mur.png");
                    Image imageMur = new Image(String.valueOf(urlImageMur));
                    ImageView imageViewMur = new ImageView(imageMur);
                    imageViewMur.setFitWidth(200);
                    imageViewMur.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewMur); // Ajout de l'image View sur une grande zone d'affichage
                    labelGrandeImage.setText(""); // j'efface le texte deja present dans le labelGrandeImage
                    // description de l'objet
                    labelDescription.setText("Les Murs sont des dispositifs spéciaux que les joueurs peuvent " +
                            "placer sur le terrain de jeu.\nElles permettent de bloquer la progression des ennemies " +
                            "\n Lorsque les ennemis s'approchent du Mur, ils le détruiront.");
                }
        );

        // Action lorsque labelBombe est cliqué
        labelBombe.setOnMouseClicked(
                e -> {
                    // Chargement de l'image et création de l'ImageView correspondant
                    URL urlImageBombe = Main.class.getResource("image/Inventaire/bombe.png");
                    Image imageBombe = new Image(String.valueOf(urlImageBombe));
                    ImageView imageViewBombe = new ImageView(imageBombe);
                    imageViewBombe.setFitWidth(200);
                    imageViewBombe.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewBombe); // Ajout de l'image View sur une grande zone d'affichage
                    labelGrandeImage.setText(""); // j'efface le texte deja present dans le labelGrandeImage
                    // description de l'objet
                    labelDescription.setText("Les bombes sont des dispositifs spéciaux que les joueurs peuvent " +
                            "placer sur le terrain de jeu.\n Lorsque les ennemis s'approchent de la bombe, elle explose violemment," +
                            " infligeant des dégâts considérables à tout ce qui se trouve à proximité.\n");
                }
        );

        // Action lorsque labelHydrogene est cliqué
        labelHydrogene.setOnMouseClicked(
                e -> {
                    // Chargement de l'image et création de l'ImageView correspondant
                    URL urlImageHydrogene = Main.class.getResource("image/Inventaire/hydrogene.png");
                    Image imageHydrogene = new Image(String.valueOf(urlImageHydrogene));
                    ImageView imageViewHydrogene = new ImageView(imageHydrogene);
                    imageViewHydrogene.setFitWidth(200);
                    imageViewHydrogene.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewHydrogene); // Ajout de l'image View sur une grande zone d'affichage
                    labelGrandeImage.setText(""); // j'efface le texte deja present dans le labelGrandeImage
                    // description de l'objet
                    labelDescription.setText("L'hydrogène est un dispositif spécial que le joueur peut " +
                            "placer sur le terrain de jeu.\nElle représente un autre type de bombe plus puissante que l'original. Elle a " +
                            "un pouvoir dévastateur et détruit tous les objets du terrain.\nLorsque vous la placez, elle explose violemment," +
                            " infligeant des dégâts considérables à tout ce qui se trouve sur le terrain.\n");
                }
        );

        // Action lorsque labelTourNikola est cliqué
        labelTourNikola.setOnMouseClicked(
                e -> {
                    // Chargement de l'image et création de l'ImageView correspondant
                    URL urlImageTour1 = Main.class.getResource("image/Inventaire/Nikola.png");
                    Image imageTour1 = new Image(String.valueOf(urlImageTour1));
                    ImageView imageViewTour1 = new ImageView(imageTour1);
                    imageViewTour1.setFitWidth(200);
                    imageViewTour1.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewTour1); // Ajout de l'image View sur une grande zone d'affichage
                    labelGrandeImage.setText(""); // j'efface le texte deja present dans le labelGrandeImage
                    // description de l'objet
                    labelDescription.setText("La bobine Edison est la première génération de bobine développée par IHI" +
                            "placée sur le terrain de jeu.\nElle a généralement une faible portée ainsi que des dégâts modérés. " +
                            "Elle est cependant très peu coûteuse.\n" +
                            "Lorsque vous cliquez sur la tour, il est possible de la changer ou de l'upgrader. La tour peut évoluer jusqu'à trois niveaux, \n" +
                            "cependant il faudra payer la différence");
                }
        );

        // Action lorsque labelTourEdison est cliqué
        labelTourEdison.setOnMouseClicked(
                e -> {
                    // Chargement de l'image et création de l'ImageView correspondant
                    URL urlImageTour2 = Main.class.getResource("image/Inventaire/Edison.png");
                    Image imageTour2 = new Image(String.valueOf(urlImageTour2));
                    ImageView imageViewTour2 = new ImageView(imageTour2);
                    imageViewTour2.setFitWidth(200);
                    imageViewTour2.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewTour2); // Ajout de l'image View sur une grande zone d'affichage
                    labelGrandeImage.setText(""); // j'efface le texte deja present dans le labelGrandeImage
                    // description de l'objet
                    labelDescription.setText("La bobine Nikola est la deuxième génération de bobine développée par IHI" +
                            "placée sur le terrain de jeu.\nElle a généralement une moyenne portée mais des dégâts considérables. " +
                            "Elle est cependant un peu plus coûteuse que sa prédécesseuse.\n" +
                            "Lorsque vous cliquez sur la tour, il est possible de la changer ou de l'upgrader. La tour peut évoluer jusqu'à trois niveaux, \n" +
                            "cependant il faudra payer la différence");
                }
        );

        // Action lorsque labelTourOppenheimer est cliqué
        labelTourOppenheimer.setOnMouseClicked(
                e -> {
                    // Chargement de l'image et création de l'ImageView correspondant
                    URL urlImageTour3 = Main.class.getResource("image/Inventaire/Oppenheimer.png");
                    Image imageTour3 = new Image(String.valueOf(urlImageTour3));
                    ImageView imageViewTour3 = new ImageView(imageTour3);
                    imageViewTour3.setFitWidth(200);
                    imageViewTour3.setFitHeight(200);
                    labelGrandeImage.setGraphic(imageViewTour3); // Ajout de l'image View sur une grande zone d'affichage
                    labelGrandeImage.setText(""); // j'efface le texte deja present dans le labelGrandeImage
                    // description de l'objet
                    labelDescription.setText("La bobine Oppenheimer est la troisième et dernière génération de bobine développée par IHI" +
                            "placée sur le terrain de jeu.\nElle a une très grande portée ainsi que des dégâts immenses. " +
                            "Elle est cependant la plus coûteuse des bobines d'Interstellar Havoc Industries.\n" +
                            "Lorsque vous cliquez sur la tour, il est possible de la changer ou de l'upgrader. La tour peut évoluer jusqu'à trois niveaux, \n" +
                            "cependant il faudra payer la différence");
                }
        );
    }
}
