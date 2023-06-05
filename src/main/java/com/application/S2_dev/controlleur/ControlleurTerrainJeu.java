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
    private Label idBobineEdison1;
    
    @FXML
    private Label idBobineNikola1;
    
    @FXML
    private Label idBobineOppenheimer1;
    
    @FXML
    private Label idSelectedTower;
    
    @FXML
    private Label scavenger, behemoth, balliste, credit, life;
    
    private Timeline gameLoop;
    private int temps;
    private EnnemiVue ennemiVue;
    TerrainVue terrainVue;
    Terrain terrain;
    Environnement env;

    private TowerType selectedTowerType = null;
    private int money = 500;
    private int timeBeforeNewSpawn = 15;
    private boolean gamePaused = false;
    private int lifeCount = 5;
    
    private ImageView levelChooser = null;
    private Tour clickedTower = null;

    public void TestClickTourel(){
        
        idBobineEdison1.setOnMouseClicked( h -> {
            if (levelChooser == null) {
                selectedTowerType = TowerType.Edison;
                idSelectedTower.setText("Edison Coil");
            }
        });
        
        idBobineOppenheimer1.setOnMouseClicked( h -> {
            if (levelChooser == null) {
                selectedTowerType = TowerType.Oppenheimer;
                idSelectedTower.setText("Oppenheimer Coil");
            }
        });
        
        idBobineNikola1.setOnMouseClicked( h -> {
            if (levelChooser == null) {
                selectedTowerType = TowerType.Nikola;
                idSelectedTower.setText("Nikola Coil");
            }
        });
        
        tilePane.setOnMouseClicked(h->{
            int[] pos = terrain.getPosInMap((int)h.getX(), (int)h.getY());
            
            for (int i = 0; i < env.getTour().size(); i++) {
                Tour tour = env.getTour().get(i);
                if (tour.isInBounds((int) h.getX(), (int) h.getY())) {
                    return;
                }
            }
            
            spawnTower(pos[0], pos[1], 1);
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
                
                if (!env.getTour().contains(clickedTower)) {
                    pane.getChildren().remove(this.levelChooser);
                    levelChooser = null;
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
                
                if (lifeVal < 0) {
                    JOptionPane.showMessageDialog(null, "You lost!");
                    exit();
                }
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
            
            tourView.setOnMouseClicked(h->{
                clickedTower = t;
                showLevelChooser();
                System.out.println("Level chooser shown");
            });
        }
        
        return new double[]{tourView.getImage().getHeight(), tourView.getImage().getWidth()};
    }
    
    void showLevelChooser() {
        
        URL urlChooser = Main.class.getResource("image/tour/level_choose.png");
        
        Image levelLent = new Image(String.valueOf(urlChooser));
        ImageView LCLent = new ImageView(levelLent);
        
        LCLent.setX(clickedTower.getX());
        LCLent.setY(clickedTower.getY());

        if (LCLent != null) {
            pane.getChildren().add(LCLent);
            levelChooser = LCLent;
            
            levelChooser.setOnMouseClicked(h->{
                int newX = (int) (h.getX() - levelChooser.getX());
                int newY = (int) (h.getY() - levelChooser.getY());
                
                if (newX > 0 && newX < 16 && newY > 16 && newY < 32) {
                    // level 1 required
                    if (env.getTour().contains(clickedTower)) {
                        
                        int col = clickedTower.getMapX();
                        int row = clickedTower.getMapY();
                        
                        if (((money + clickedTower.getPrice()) - 100) < 0) {
                            JOptionPane.showMessageDialog(null, "Not enough money");
                        } else {
                            // remove the tower and get the money back
                            refundMoney(clickedTower.getPrice());
                            pane.getChildren().remove(clickedTower.getView());
                            env.getTour().remove(clickedTower);
                            spawnTower(row, col, 1);
                        }
                    }
                } else if (newX > 64 && newX < 80 && newY > 16 && newY < 32) {
                    // level 2 required
                    if (env.getTour().contains(clickedTower)) {
                        
                        int col = clickedTower.getMapX();
                        int row = clickedTower.getMapY();
                        
                        if (((money + clickedTower.getPrice()) - 200) < 0) {
                            JOptionPane.showMessageDialog(null, "Not enough money");
                        } else {
                            // remove the tower and get the money back
                            refundMoney(clickedTower.getPrice());
                            pane.getChildren().remove(clickedTower.getView());
                            env.getTour().remove(clickedTower);
                            spawnTower(row, col, 2);
                        }
                    }
                } else if (newX > 32 && newX < 48 && newY > 64 && newY < 80) {
                    // level 3 required
                    if (env.getTour().contains(clickedTower)) {
                        
                        int col = clickedTower.getMapX();
                        int row = clickedTower.getMapY();
                        
                        if (((money + clickedTower.getPrice()) - 300) < 0) {
                            JOptionPane.showMessageDialog(null, "Not enough money");
                        } else {
                            // remove the tower and get the money back
                            refundMoney(clickedTower.getPrice());
                            pane.getChildren().remove(clickedTower.getView());
                            env.getTour().remove(clickedTower);
                            spawnTower(row, col, 3);
                        }
                    }
                }
                
                // remove level chooser
                pane.getChildren().remove(levelChooser);
                levelChooser = null;
                return;
            });
        }
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
