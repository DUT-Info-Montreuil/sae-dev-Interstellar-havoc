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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terrain = new Terrain();
        terrainVue = new TerrainVue(tilePane, terrain);
        env  = new Environnement();
        terrainVue.afficherTerrain();
        EnnemiVue en = new EnnemiVue(pane, labelScavenger, labelBalliste, labelBehemoth);
        env.getEnnemis().addListener(en);
        initAnimation();
        this.TestClickTourel();
    }

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
                    alert.setOnShowing(
                            e ->
                                    this.gameLoop.pause()

                    );
                    alert.setOnHidden(e -> {
                        this.gameLoop.play();
                    });
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
                    alert.setOnShowing(
                            e ->
                                    this.gameLoop.pause()

                    );
                    alert.setOnHidden(e -> {
                        this.gameLoop.play();
                    });
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
                    alert.setOnShowing(
                            e ->
                                    this.gameLoop.pause()

                    );
                    alert.setOnHidden(e -> {
                        this.gameLoop.play();
                    });
                    alert.showAndWait();
                }
        );
    }


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

    void creerSprite(Ennemi e) {
        URL urlEnnemiLent = Main.class.getResource("image/ennemis/Scavenger.png");
        Image ennemiLent = new Image(String.valueOf(urlEnnemiLent));
        ImageView ImLent = new ImageView(ennemiLent);

        ImLent.translateXProperty().bind(e.getXProperty());
        ImLent.translateYProperty().bind(e.getYProperty());

        if (ImLent != null) {
            ImLent.setId(e.getId());
            pane.getChildren().add(ImLent);
        }
    }

    @FXML
    void ButtonInventaire(ActionEvent event) {
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

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ButtonQuitter(ActionEvent event) {
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
            //((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void ButtonPlay(ActionEvent event) {
        this.gameLoop.play();
    }
    @FXML
    void ButtonPause(ActionEvent event) {
        this.gameLoop.pause();
    }
}