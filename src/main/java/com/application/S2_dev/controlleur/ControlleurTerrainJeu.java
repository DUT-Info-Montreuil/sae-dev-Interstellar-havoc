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
        // Crée une instance de la classe Terrain
        terrain = new Terrain();

        // Crée une instance de la classe TerrainVue en passant le TilePane et le terrain en paramètres
        terrainVue = new TerrainVue(tilePane, terrain);

        // Crée une instance de la classe Environnement en passant le pane en paramètre
        env = new Environnement(pane);

        // Affiche le terrain en utilisant la classe TerrainVue
        terrainVue.afficherTerrain();

        // Crée une instance de la classe EnnemiVue en passant le pane et les labels en paramètres
        EnnemiVue en = new EnnemiVue(pane, labelScavenger, labelBalliste, labelBehemoth);

        // Ajoute un écouteur sur la liste des ennemis dans l'environnement
        env.getEnnemis().addListener(en);

        // Initialise l'animation du jeu
        initAnimation();

        // Appelle la méthode TestClickTourel
        this.TestClickTourel();
    }

    /**
     * Boucle de jeu
     */
    public void initAnimation() {
        // Crée une nouvelle Timeline pour l'animation
        gameLoop = new Timeline();
        temps = 0;

        // Définit le nombre de répétitions de l'animation (infini dans ce cas)
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        // Crée un KeyFrame avec une durée de 0.5 seconde
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.5),
                ev -> {
                    // À chaque frame de l'animation, appelle la méthode unTour de l'environnement
                    env.unTour();
                });

        // Ajoute le KeyFrame à la Timeline
        gameLoop.getKeyFrames().add(kf);

        // Lance l'animation
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
        tourView.translateXProperty().bind(t.getXProprieteX());
        tourView.translateYProperty().bind(t.getXProprieteY());

        // Vérifier si l'objet tourView n'est pas null
        if (tourView != null) {
            tourView.setId(t.getIdentifiant());
            t.getXProprieteX().set(t.getX() - (tourView.getImage().getWidth() / 2));
            t.getXProprieteY().set(t.getY() - (tourView.getImage().getHeight() / 2));
            t.setVue(tourView);
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

