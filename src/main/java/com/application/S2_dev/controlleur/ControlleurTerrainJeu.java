package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.*;
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
    @FXML
    private Label labelBalliste;

    @FXML
    private Label labelBehemoth;

    @FXML
    private Label labelScavenger;
    private Timeline gameLoop;
    private int temps;
    private EnnemiVue ennemiVue;
    TerrainVue terrainVue;
    Terrain terrain;
    Environnement env;

    private TowerType selectedTowerType;
    public void TestClickTourel() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Gestionnaire de clics pour la bobine Edison
        idBobineEdison.setOnMouseClicked(h -> {
            double x = h.getX();
            double y = h.getY();
            selectedTowerType = TowerType.Edison;
        });

        // Gestionnaire de clics pour la bobine Oppenheimer
        idBobineOppenheimer.setOnMouseClicked(h -> {
            System.out.println("je clique");
            double x = h.getX();
            double y = h.getY();
            selectedTowerType = TowerType.Oppenheimer;
            System.out.println(" selectedTowerType" + selectedTowerType);
        });

        // Gestionnaire de clics pour la bobine Nikola
        idBobineNikola.setOnMouseClicked(h -> {
            double x = h.getX();
            double y = h.getY();
            selectedTowerType = TowerType.Nikola;
        });

        // Gestionnaire de clics pour le TilePane (terrain)
        tilePane.setOnMouseClicked(h -> {
            spawnTower(h.getX(), h.getY());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        terrainVue = new TerrainVue(tilePane, terrain);
        env = new Environnement(pane);
        terrainVue.afficherTerrain();
        EnnemiVue en = new EnnemiVue(pane, labelScavenger, labelBalliste, labelBehemoth);
        env.getEnnemis().addListener(en);
        initAnimation();
        this.TestClickTourel();
    }

    /**
     * Boucle de jeu
     */
    public void initAnimation() {
        gameLoop = new Timeline();
        temps = 0;

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.5),
                ev -> {
                    env.unTour();
                });
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }

    public void spawnTower(double x, double y) {
        // Vérifier si la tour peut être placée aux coordonnées spécifiées
        if (env.canPlaceTowerAt(x, y)) {

            Tour tower;

            // Créer l'objet tour en fonction du type de tour sélectionné
            switch (selectedTowerType) {
                case Nikola:
                    tower = new NikolaCoil((int) x, (int) y);
                    break;
                case Edison:
                    tower = new EdisonCoil((int) x, (int) y);
                    break;
                case Oppenheimer:
                    tower = new OppenheimerCoil((int) x, (int) y);
                    break;
                default:
                    tower = null;
                    break;
            }

            // Ajouter la tour au terrain et l'afficher sur le pane
            env.addTower(tower);
            creerSprite(tower);

        } else {
            // Le placement de la tour n'est pas autorisé aux coordonnées spécifiées
            System.out.println("Placement de la tour non autorisé aux coordonnées (" + x + ", " + y + ")");
        }
    }

    double[] creerSprite(Tour t) {
        URL urlTour;

        // Sélectionner l'URL de l'image de la tour en fonction du type de tour
        switch (t.getType()) {
            case Nikola:
                urlTour = Main.class.getResource("image/tour/bobineNicolas.png");
                break;
            case Edison:
                urlTour = Main.class.getResource("image/tour/bobineEdison.png");
                break;
            case Oppenheimer:
                urlTour = Main.class.getResource("image/tour/bobineOppenheimer.png");
                break;
            default:
                urlTour = null;
                break;
        }

        // Charger l'image de la tour à partir de l'URL
        Image tour = new Image(String.valueOf(urlTour));
        ImageView tourView = new ImageView(tour);

        // Lier les propriétés de translation de la tour aux propriétés de position de la tour
        tourView.translateXProperty().bind(t.getXProperty());
        tourView.translateYProperty().bind(t.getYProperty());

        // Vérifier si l'objet tourView n'est pas null
        if (tourView != null) {
            tourView.setId(t.getId());
            t.getXProperty().set(t.getX() - (tourView.getImage().getWidth() / 2));
            t.getYProperty().set(t.getY() - (tourView.getImage().getHeight() / 2));
            t.setView(tourView);
            pane.getChildren().add(tourView);
        }

        // Retourner les dimensions de l'image de la tour
        return new double[]{tourView.getImage().getHeight(), tourView.getImage().getWidth()};

    }

    @FXML
    void ButtonInventaire(ActionEvent event) {
        Parent root;
        // Reprendre la boucle de jeu
        this.gameLoop.play();
        try {
            // Mettre en pause la boucle de jeu
            this.gameLoop.pause();
            // Charger la vue de l'inventaire à partir du fichier FXML
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Inventaire/Inventaire.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Inventaire");
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
            // Revenir à la boucle de jeu lorsque la fenêtre de l'inventaire est fermée
            stage.setOnHidden(e -> this.gameLoop.play());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ButtonQuitter(ActionEvent event) {
        Parent root;
        try {
            // Arrêter la boucle de jeu
            gameLoop.stop();
            // Fermer la fenêtre actuelle
            Stage stage1 = (Stage) tilePane.getScene().getWindow();
            stage1.close();
            // Charger la vue du menu à partir du fichier FXML
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Menu de jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
            //((Node)(event.getSource())).getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ButtonPlay(ActionEvent event) {
        // Reprendre la boucle de jeu
        this.gameLoop.play();
    }

    @FXML
    void ButtonPause(ActionEvent event) {
        // Mettre en pause la boucle de jeu
        this.gameLoop.pause();
    }

}


