package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.vue.EnnemiVue;
import com.application.S2_dev.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlleurTerrainJeu implements Initializable {
    @FXML
    TilePane tilePane;
    @FXML
    Pane pane;
    @FXML
    private Label idBobineEdison;
    @FXML
    private Label idBobineNikola;
    @FXML
    private Label idBobineOppenheimer;
    private Timeline gameLoop;
    private int temps;
    private EnnemiVue ennemiVue;
    TerrainVue terrainVue;
    Terrain terrain;
    Environnement env;

    /**
     * Méthode pour afficher les caractéristiques des bobines
     * lorsqu'elles sont cliquées.
     */
    public void TestClickTourel(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);


        idBobineEdison.setOnMouseClicked(
                h ->   {
                    alert.setTitle("Caracteristique de la Bobine Edison");
                    alert.setContentText("Prix : 3000\n" +
                                    "Puissance : 50 \n" +
                            "Pv : 40");
                    alert.setHeaderText("");
                    URL url = Main.class.getResource("fxml/TerrainJeu/tourel1.png");
                    Image image = new Image(String.valueOf(url));
                    ImageView imageView = new ImageView(image);
                    alert.setGraphic(imageView);
                    alert.showAndWait();
                }

        );
        idBobineOppenheimer.setOnMouseClicked(
                h ->  {
                    alert.setTitle("Caracteristique de la Bobine Oppenheimer");
                    alert.setContentText("Prix : 3000\n" +
                            "Puissance : 50 \n" +
                            "Pv : 40");
                    alert.setHeaderText("");
                    URL url = Main.class.getResource("fxml/TerrainJeu/tourel2.png");
                    Image image = new Image(String.valueOf(url));
                    ImageView imageView = new ImageView(image);
                    alert.setGraphic(imageView);
                    alert.showAndWait();
                }
        );
        idBobineNikola.setOnMouseClicked(
                h ->  {
                    alert.setTitle("Caracteristique de la Bobine Nikola");
                    alert.setContentText("Prix : 3000\n" +
                            "Puissance : 50 \n" +
                            "Pv : 40");
                    alert.setHeaderText("");
                    URL url = Main.class.getResource("fxml/TerrainJeu/tourel3.png");
                    Image image = new Image(String.valueOf(url));
                    ImageView imageView = new ImageView(image);
                    alert.setGraphic(imageView);
                    alert.showAndWait();
                }
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Crée une nouvelle instance de terrain et de terrainVue
        terrain = new Terrain();
        terrainVue = new TerrainVue(tilePane, terrain);
        env = new Environnement();

        // Affiche le terrain
        terrainVue.afficherTerrain();

        // Initialise l'environnement
        env.init();

        // Crée et affiche les sprites des ennemis sur le terrain
        for (int i = 0; i < env.getEnnemis().size(); i++) {
            creerSprite(env.getEnnemis().get(i));
        }

        // Initialise l'animation du jeu
        initAnimation();

        // Associe les actions de clic sur les labels des bobines aux fonctionnalités correspondantes
        this.TestClickTourel();
    }

    /**
     * Initialise l'animation du jeu en utilisant la classe Timeline de JavaFX.
     * La méthode unTour() de l'environnement est appelée toutes les 0.3 secondes.
     */
    public void initAnimation() {
        gameLoop = new Timeline();
        temps = 0;

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.3),
                ev -> {
                    env.unTour();
                });
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }

    /**
     * Crée un sprite pour un ennemi donné en utilisant une image spécifique.
     * Le sprite est ajouté au pane du terrain.
     * @param e Ennemi à afficher
     */
    void creerSprite(Ennemi e) {
        // Charge l'image de l'ennemi
        URL urlEnnemiLent = Main.class.getResource("image/ennemis/pika.png");
        Image ennemiLent = new Image(String.valueOf(urlEnnemiLent));
        ImageView ImLent = new ImageView(ennemiLent);

        // Lie les propriétés de translation du sprite aux propriétés de position de l'ennemi
        ImLent.translateXProperty().bind(e.getXProperty());
        ImLent.translateYProperty().bind(e.getYProperty());

        // Vérifie si le sprite est valide avant de l'ajouter au pane
        if (ImLent != null) {
            ImLent.setId(e.getId());
            pane.getChildren().add(ImLent);
        }
    }

    /**
     * Met à jour l'affichage des sprites des ennemis sur le terrain.
     * Cette méthode est appelée à chaque rafraîchissement de l'écran.
     */
    void rafraichirAffichage() {
        for (Ennemi acteur : env.getEnnemis()) {
            // Recherche le sprite de l'ennemi dans le pane
            ImageView sprite = (ImageView) pane.lookup("#" + acteur.getId());

            // Si le sprite existe, met à jour sa position en fonction de l'ennemi
            if (sprite != null) {
                sprite.setTranslateX(acteur.getX());
                sprite.setTranslateY(acteur.getY());
            }
            // Sinon, crée un nouveau sprite pour l'ennemi et l'ajoute au pane
            else {
                creerSprite(acteur);
            }
        }
    }

    /**
     * Gère l'événement de clic sur le bouton "Quitter".
     * Arrête l'animation du jeu, ferme la fenêtre actuelle et ouvre le menu principal.
     * @param event Événement de clic sur le bouton
     */
    @FXML
    void ButtonQuitter(ActionEvent event) {
        Parent root;
        try {
            gameLoop.stop();
            Stage stage1 = (Stage) tilePane.getScene().getWindow();
            stage1.close();
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Menu/Menu.fxml"));
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
}
