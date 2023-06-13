package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.Parametre;
import com.application.S2_dev.modele.data.TerrainType;
import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.*;
import com.application.S2_dev.vue.ObjetVue;
import com.application.S2_dev.vue.EnnemiVue;
import com.application.S2_dev.vue.TerrainVue;
import com.application.S2_dev.vue.TourVue;
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
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

public class ControlleurTerrainJeu implements Initializable {    @FXML

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
    private Label labelScavenger;

    @FXML
    private Label labelBehemoth;

    @FXML
    private Label labelBalliste;

    @FXML
    private Label labelCredit;

    @FXML
    private Label labelLife;

    private Timeline gameLoop;

    private EnnemiVue ennemiVue;
    private int temps;
    TerrainVue terrainVue;
    Terrain terrain;
    Environnement env;

    @FXML
    private Label idSelectedTower;
    @FXML
    private Label labelBombe;
    @FXML
    private Label LabelHydrogene;
    @FXML
    private Label labelMur;
    @FXML
    private Label labelMaintenace;
    private ActionEvent event;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        env = new Environnement(terrain); // Création de l'environnement
        terrainVue = new TerrainVue(tilePane, terrain);
        terrainVue.afficherTerrain();
        labelCredit.setText("500");
        EnnemiVue ennemiVue = new EnnemiVue(pane, labelScavenger, labelBalliste, labelBehemoth, labelCredit);
        env.getEnnemis().addListener(ennemiVue);

        ObjetVue objetVue = new ObjetVue(pane,env, labelBombe, LabelHydrogene, labelMur, labelCredit, terrain, terrainVue);
        env.getObjets().addListener(objetVue);
        objetVue.AjoutObjet();

        TourVue tourVue = new TourVue(env,tilePane,terrain,pane,idBobineEdison,idBobineOppenheimer,idBobineNikola,labelCredit, idSelectedTower);
        env.getTour().addListener(tourVue);
        tourVue.lancerTourVue();
        initAnimation();
        tourVue.TestClickTourel();

        labelLife.setText("5");
        this.env.getReachedProperty().addListener((observableValue, oldValue, nouvelleValeur) -> {
            this.labelLife.setText(String.valueOf(nouvelleValeur));
            if(env.getReachedPlayers()==0){
                gameLoop.stop();
                JOptionPane.showMessageDialog(null, "Vous avez perdu !");
                exit();
            }
        });
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


    @FXML
    void ButtonInventaire (ActionEvent event){
        Parent root;
        this.gameLoop.play();
        try {
            this.gameLoop.pause();
            root = FXMLLoader.load(Main.class.getResource("/com/application/S2_dev/fxml/Inventaire/Inventaire.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Inventaire");
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
            stage.setOnHidden(
                    e ->
                            this.gameLoop.play()

            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ButtonQuitter(ActionEvent event) {
        exit();
    }

    public void exit() {
        Parent root;
        try {
            gameLoop.stop();
            Stage stage1 = (Stage) tilePane.getScene().getWindow();
            stage1.close();
            root = FXMLLoader.load(Main.class.getResource("fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tower Defence");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
        }
        catch (IOException e) {
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


