package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.Parametre;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.vue.ObjetVue;
import com.application.S2_dev.vue.EnnemiVue;
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
import java.io.IOException;
import java.net.URL;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        env = new Environnement(terrain); // Création de l'environnement
        terrainVue = new TerrainVue(tilePane, terrain, env);
        terrainVue.afficherTerrain();
        labelCredit.setText("500");
        EnnemiVue ennemiVue = new EnnemiVue(pane, labelScavenger, labelBalliste, labelBehemoth, labelCredit, env);
        env.getEnnemis().addListener(ennemiVue);

        ObjetVue objetVue = new ObjetVue(pane,env, labelBombe, LabelHydrogene, labelMur, labelCredit, terrain, terrainVue);
        env.getObjets().addListener(objetVue);
        objetVue.AjoutObjet();
        initAnimation();
        TourVue tourVue = new TourVue(env,tilePane,terrain,pane,idBobineEdison,idBobineOppenheimer,idBobineNikola, labelCredit, gameLoop);
        env.getTour().addListener(tourVue);
        tourVue.lancerTourVue();
        tourVue.TestClickTourel();

        labelLife.setText("5");
        this.env.getJoueursAtteintsProperty().addListener((observableValue, oldValue, nouvelleValeur) -> {
            this.labelLife.setText(String.valueOf(Parametre.nombreVies-(int)nouvelleValeur));
            if(env.getJoueursAtteints()==Parametre.nombreVies){
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


