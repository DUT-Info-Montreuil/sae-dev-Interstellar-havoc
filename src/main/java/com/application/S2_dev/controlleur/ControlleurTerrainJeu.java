package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.vue.EnnemiVue;
import com.application.S2_dev.vue.ObjetVue;
import com.application.S2_dev.vue.TerrainVue;
import com.application.S2_dev.vue.TourVue;
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
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControlleurTerrainJeu implements Initializable {
    @FXML
    Pane pane;
    @FXML
    TilePane tilePane;
    @FXML
    private Label idBobineEdison;
    @FXML
    private Label idBobineNikola;
    @FXML
    private Label idBobineOppenheimer;
    @FXML
    private Label idSelectedTower;
    @FXML
    private Label labelBalliste;
    @FXML
    private Label labelBehemoth;
    @FXML
    private Label labelScavenger;
    @FXML
    private Label labelBombe;
    @FXML
    private Label LabelHydrogene;
    @FXML
    private Label labelMur;
    @FXML
    private Label labelMaintenace;
    @FXML
    private Label labelCredit;
    @FXML
    private Label labelLife;
    private Timeline gameLoop;
    private int temps;
    private TerrainVue terrainVue;
    private Terrain terrain;
    private ActionEvent event;
    private Environnement environnement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain = new Terrain();
        terrainVue = new TerrainVue(tilePane, terrain);
        environnement = new Environnement(terrain);
        terrainVue.afficherTerrain();
        labelCredit.setText("500");
        EnnemiVue ennemiVue = new EnnemiVue(pane, labelScavenger, labelBalliste, labelBehemoth, labelCredit);
        environnement.getEnnemis().addListener(ennemiVue);


        ObjetVue objetVue = new ObjetVue(pane,environnement, labelBombe, LabelHydrogene, labelMur, labelCredit, terrain, terrainVue);
        environnement.getObjets().addListener(objetVue);
        objetVue.AjoutObjet();

        TourVue tourVue = new TourVue(environnement,tilePane,terrain,pane,idBobineEdison,idBobineOppenheimer,idBobineNikola,labelCredit, idSelectedTower);
        environnement.getTour().addListener(tourVue);
        tourVue.lancerTourVue();
        initAnimation();
        tourVue.TestClickTourel();

        labelLife.setText("5");
        this.environnement.getReachedProperty().addListener((observableValue, oldValue, nouvelleValeur) -> {
            this.labelLife.setText(String.valueOf(nouvelleValeur));
            if(environnement.getReachedPlayers()==0){
                gameLoop.stop();
                JOptionPane.showMessageDialog(null, "Vous avez perdu !");
                ButtonQuitter(event);
            }
        });

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
    void ButtonQuitter (ActionEvent event){
        Parent root;
        try {
            gameLoop.stop();
            Stage stage1 = (Stage) tilePane.getScene().getWindow();
            stage1.close();
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
    void ButtonPlay (ActionEvent event){
        this.gameLoop.play();
    }
    @FXML
    void ButtonPause (ActionEvent event){
        this.gameLoop.pause();
    }
    public void initAnimation() {

        gameLoop = new Timeline();
        temps = 0;

        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.5),
                ev -> {
                    environnement.unTour();

                });
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }

}


