package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.data.TerrainType;
import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.*;
import com.application.S2_dev.vue.EnnemiVue;
import com.application.S2_dev.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

public class ControlleurTerrainJeu implements Initializable {
    
    @FXML
    TilePane tilePane;
    
    @FXML
    Pane pane;
    
    @FXML
    private Label idBobineEdison1, idBobineEdison2, idBobineEdison3;
    
    @FXML
    private Label idBobineNikola1, idBobineNikola2, idBobineNikola3;
    
    @FXML
    private Label idBobineOppenheimer1, idBobineOppenheimer2, idBobineOppenheimer3;
    
    @FXML
    private Label scavenger, behemoth, balliste, credit, life;
    
    private Timeline gameLoop;
    private int temps;
    private EnnemiVue ennemiVue;
    TerrainVue terrainVue;
    Terrain terrain;
    Environnement env;

    private TowerType selectedTowerType = null;
    private int currentLevel = 1;
    private int money = 500;
    private int timeBeforeNewSpawn = 15;
    private boolean gamePaused = false;
    private int lifeCount = 5;

    public void TestClickTourel(){
        
        idBobineEdison1.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Edison;
            currentLevel = 1;
        });
        idBobineEdison2.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Edison;
            currentLevel = 2;
        });
        idBobineEdison3.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Edison;
            currentLevel = 3;
        });
        
        idBobineOppenheimer1.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Oppenheimer;
            currentLevel = 1;
        });
        idBobineOppenheimer2.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Oppenheimer;
            currentLevel = 2;
        });
        idBobineOppenheimer3.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Oppenheimer;
            currentLevel = 3;
        });
        
        idBobineNikola1.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Nikola;
            currentLevel = 1;
        });
        idBobineNikola2.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Nikola;
            currentLevel = 2;
        });
        idBobineNikola3.setOnMouseClicked( h -> {
            selectedTowerType = TowerType.Nikola;
            currentLevel = 3;
        });
        
        tilePane.setOnMouseClicked(h->{
            
            gamePaused = true;
            
            int[] pos = terrain.getPosInMap((int)h.getX(), (int)h.getY());
            
            for (int i = 0; i < env.getTour().size(); i++) {
                Tour tour = env.getTour().get(i);
                if (tour.isInBounds((int) h.getX(), (int) h.getY())) {
                    int res = JOptionPane.showConfirmDialog(null, "Do you want to remove the tower?");
                    if (res == 0) {
                        // remove the tower and get the money back
                        refundMoney(tour.getPrice());
                        pane.getChildren().remove(tour.getView());
                        env.getTour().remove(i);
                    }
                    return;
                }
            }
            
            spawnTower(pos[0], pos[1], currentLevel);
            
            gamePaused = false;
        });
        
        credit.setText(money + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        terrain = new Terrain();
        terrainVue = new TerrainVue(tilePane, terrain);
        env  = new Environnement(pane);
        terrainVue.afficherTerrain();
        env.init();
        
        for (int i = 0; i < env.getEnnemis().size(); i++) {
            creerSprite(env.getEnnemis().get(i));
        }

        initAnimation();
        this.TestClickTourel();
    }
    
    /**
     * game loop
     */
    public void initAnimation() {
        gameLoop = new Timeline();
        temps = 0;
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.3), ev -> {
                
                if (gamePaused)
                    return;
                    
                final int ennemiSquadSize = env.getEnnemis().size();
                
                env.unTour();
                
                if (env.getEnnemis().size() < ennemiSquadSize)
                    addMoney(100);
                
                int s = 0, be = 0, ba = 0;
                for (Ennemi e : env.getEnnemis()) {
                    if (e instanceof Scavenger)
                        s++;
                    else if (e instanceof Behemoth)
                        be++;
                    else if (e instanceof Balliste)
                        ba++;
                }
                
                scavenger.setText(s + "");
                behemoth.setText(be + "");
                balliste.setText(ba + "");
                
                int lifeVal = lifeCount - env.getReachedPlayers();
                life.setText(lifeVal + "");
                
                if (timeBeforeNewSpawn == 0) {
                    spawnEnnemi();
                    timeBeforeNewSpawn = 15;
                    return;
                }
                
                timeBeforeNewSpawn--;
            }
        );
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }
    
    public void spawnEnnemi() {
        
        int rand = new Random().nextInt(3);
        
        switch (rand) {
            case 0:
                Ennemi e1 = new Balliste(5, 21);
                env.ajouter(e1);
                creerSprite(e1);
                break;
            case 1:
                Ennemi e2 = new Behemoth(5, 21);
                env.ajouter(e2);
                creerSprite(e2);
                break;
            case 2:
                Ennemi e3 = new Scavenger(5, 21);
                env.ajouter(e3);
                creerSprite(e3);
                break;
            default:
                break;
        }
        
    }
    
    /**
     * Spawn a tower in the map
     * @param row Row position in tile map
     * @param col Col position in tile map
     */
    public void spawnTower(int row, int col, int level) {
        // Check if the tower can be placed at the specified coordinates
        if (canPlaceTowerAt(row, col)) {
            
            Tour tower;
            
            // Create the tower object based on the tower type
            switch (selectedTowerType) {
                case Nikola:
                    tower = new NikolaCoil((int)col*16, (int)row*16, level);
                    break;
                case Edison:
                    tower = new EdisonCoil((int)col*16, (int)row*16, level);
                    break;
                case Oppenheimer:
                    tower = new OppenheimerCoil((int)col*16, (int)row*16, level);
                    break;
                default:
                    return;
            }

            if (money < tower.getPrice()) {
                JOptionPane.showMessageDialog(null, "Not enough money!");
                return;
            }
            
            // Add the tower to the terrain and display it on the pane
            env.addTower(tower);
            creerSprite(tower);
            subtractMoney(tower.getPrice());
        } else {
            // Tower placement is not allowed at the specified coordinates
            System.out.println("Tower placement not allowed at coordinates (" + row + ", " + col + ")");
        }
    }

    void subtractMoney(int value) {
        money -= value;
        credit.setText(money + "");
    }
    
    void refundMoney(int value) {
        money += value;
        credit.setText(money + "");
    }
    
    void addMoney(int value) {
        money += value;
        credit.setText(money + "");
    }
    
    void creerSprite(Ennemi e) {
        
        URL urlEnnemiLent = null;
        
        if (e instanceof Balliste)
            urlEnnemiLent = Main.class.getResource("image/ennemis/pika.png");
        else if (e instanceof Behemoth)
            urlEnnemiLent = Main.class.getResource("image/ennemis/pika2.png");
        else if (e instanceof Scavenger)
            urlEnnemiLent = Main.class.getResource("image/ennemis/pika3.png");
        
        Image ennemiLent = new Image(String.valueOf(urlEnnemiLent));
        ImageView ImLent = new ImageView(ennemiLent);

        ImLent.translateXProperty().bind(e.getXProperty());
        ImLent.translateYProperty().bind(e.getYProperty());

        if (ImLent != null) {
            ImLent.setId(e.getId());
            e.setView(ImLent);
            pane.getChildren().add(ImLent);
        }
    }
    
    double[] creerSprite(Tour t) {
        
        URL urlTour;
        
        switch (t.getType()) {
            case Nikola:
                urlTour = Main.class.getResource("image/tour/tourel3.png");
                break;
            case Edison:
                urlTour = Main.class.getResource("image/tour/tourel1.png");
                break;
            case Oppenheimer:
                urlTour = Main.class.getResource("image/tour/tourel2.png");
                break;
            default:
                urlTour = null;
                break;
        }
        
        Image tour = new Image(String.valueOf(urlTour));
        ImageView tourView = new ImageView(tour);

        tourView.translateXProperty().bind(t.getXProperty());
        tourView.translateYProperty().bind(t.getYProperty());

        if (tourView != null) {
            tourView.setId(t.getId());
            int width = (int) tourView.getImage().getWidth();
            int height = (int) tourView.getImage().getHeight();
            int x = (int) (t.getX()-(width/2));
            int y = (int) (t.getY()-(height/2));
            t.getXProperty().set(x);
            t.getYProperty().set(y);
            t.setBounds(x, y, width, height);
            t.setView(tourView);
            pane.getChildren().add(tourView);
        }
        
        return new double[]{tourView.getImage().getHeight(), tourView.getImage().getWidth()};
    }

    void rafraichirAffichage() {
        for (Ennemi acteur : env.getEnnemis()) {
            ImageView sprite = (ImageView) pane.lookup("#"+acteur.getId());
            if (sprite!= null) {
                sprite.setTranslateX(acteur.getX());
                sprite.setTranslateY(acteur.getY());
            }
            else{
                creerSprite(acteur);
            }
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
    
    public boolean canPlaceTowerAt(int row, int col) {
        if (terrain.getCase(row, col) == TerrainType.TOWER_BASE)
            return true;
        else return false;
    }
}
