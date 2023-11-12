package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.GestionnaireInsertionsJeu;
import com.application.S2_dev.modele.Parametre;
import com.application.S2_dev.modele.Boutique;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.vue.ObjetVue;
import com.application.S2_dev.vue.EnnemiVue;
import com.application.S2_dev.vue.TerrainVue;
import com.application.S2_dev.vue.TourVue;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;


public class ControlleurTerrainJeu implements Initializable {

    @FXML
    private TilePane tilePane;
    @FXML
    private Pane pane;

    /* label des tourelles */
    @FXML
    private Label idBobineEdison;
    @FXML
    private Label idBobineNikola;
    @FXML
    private Label idBobineOppenheimer;

    /* label present en haut du jeu */
    @FXML
    private Label labelScavenger; // ennemi
    @FXML
    private Label labelBehemoth; // ennemi
    @FXML
    private Label labelBalliste; // ennemi
    @FXML
    private Label labelCredit;
    @FXML
    private Label labelLife;

    /* label des objets */
    @FXML
    private Label labelBombe;
    @FXML
    private Label LabelHydrogene;
    @FXML
    private Label labelMur;
    @FXML
    private Label labelMaintenace;
    @FXML
    private Label labelTimer;

    private Timeline gameLoop;
    private EnnemiVue ennemiVue;
    private TerrainVue terrainVue;
    private ObjetVue objetVue;
    private Terrain terrain;
    public Environnement env;
    private Boutique boutique;

    private Duration tempsEcoule = Duration.ZERO;
    private Timeline timer;
    private GestionnaireInsertionsJeu gestionnaireInsertionsJeu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.timer();
        terrain = Terrain.getInstance(); // Création du terrain
        env = Environnement.getInstance(terrain,pane); // Création de l'environnement

        terrainVue = new TerrainVue(tilePane, terrain, env);
        terrainVue.afficherTerrain(); // Affichage du terrain
        initAnimation();
        boutique = Boutique.getInstance(gameLoop); // Boutique pour l'achat de chaque des defences ou objets

        /* Affichage des ennemis */
        ennemiVue = new EnnemiVue(pane, labelScavenger, labelBalliste, labelBehemoth, env, boutique);
        env.getEnnemis().addListener(ennemiVue);
        this.gestionnaireInsertionsJeu = new GestionnaireInsertionsJeu(ennemiVue,timer,ennemiVue.getScore());
        gestionnaireInsertionsJeu.debutDuJeu();

        /* Affichage des objets */
        objetVue = new ObjetVue(pane, env, labelBombe, LabelHydrogene, labelMur, terrain, terrainVue, boutique, labelMaintenace);
        env.getObjets().addListener(objetVue);
        objetVue.AjoutObjet();

        /* Affichage des tourelles */
        TourVue tourVue = new TourVue(env, tilePane, terrain, pane, idBobineEdison, idBobineOppenheimer, idBobineNikola, gameLoop, boutique);
        env.getTour().addListener(tourVue);
        tourVue.lancerTourVue();

        this.mettreAJourCredit();
        this.mettreAJourVie();

    }
    public void mettreAJourCredit() {
        labelCredit.setText(String.valueOf(boutique.getPrix()));
        boutique.prixProperty().addListener((observableValue, oldValue, nouvelleValeur) -> {
            labelCredit.setText(String.valueOf(nouvelleValeur));
        });
    }

    private void mettreAJourVie() {
        /* Initialisation du label de vie de la base à défendre */
        labelLife.setText(String.valueOf(Parametre.nombreVies));
        env.getennemisAtteintsProperty().addListener((observableValue, oldValue, nouvelleValeur) -> {
            labelLife.setText(String.valueOf(Parametre.nombreVies - (int) nouvelleValeur));
            if (env.getennemisAtteints() == Parametre.nombreVies) {
                gameLoop.stop();
                JOptionPane.showMessageDialog(null, "Vous avez perdu !");
                exit(); /* Fermeture de la fenêtre du jeu */
            }
        });
    }

    private void timer(){
        timer = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            tempsEcoule = tempsEcoule.add(Duration.seconds(1));
            labelTimer.setText(formatDuration(tempsEcoule));
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }
    private String formatDuration(Duration duration) {
        long heures = (long) duration.toHours();
        long minutes = (long) duration.toMinutes() % 60;
        long secondes = (long) duration.toSeconds() % 60;
        return String.format("%02d:%02d:%02d", heures, minutes, secondes);
    }

    public void initAnimation() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), ev -> {
            env.unTour();
        });
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }
    @FXML
    void ButtonInventaire(ActionEvent event) {
        /* Inventaire de jeu */
        Parent root;
        this.gameLoop.play();
        try {
            this.gameLoop.pause(); // pause de jeu
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Inventaire/Inventaire.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Inventaire");
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
            stage.setOnHidden(e -> this.gameLoop.play()); // Lancement du jeu apres la fermeture de l'inventaire
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ButtonQuitter(ActionEvent event) {
        exit(); // Quitter le jeu
    }

    public void exit() {
        timer.stop();
        Parent root;
        try {
            gameLoop.stop(); // Arret de la gameLoop
            gestionnaireInsertionsJeu.finDuJeu();
            Stage stage1 = (Stage) tilePane.getScene().getWindow();
            stage1.close(); // Fermeture de la page de jeu
            root = FXMLLoader.load(Main.class.getResource("fxml/Resultat.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Resultat du jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show(); // Affichage du menu de jeu

            System.out.println(ennemiVue.getCompteurBehemtohTues());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ButtonConsigne(ActionEvent event) {
        Parent root;
        try {
            this.gameLoop.pause(); // Pause du jeu
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Consigne/Consigne.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Consigne du jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show(); // Affichage de la page consigne
            stage.setOnHidden(e -> this.gameLoop.play()); // Lancement du jeu apres la fermeture du FXML consigne
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
