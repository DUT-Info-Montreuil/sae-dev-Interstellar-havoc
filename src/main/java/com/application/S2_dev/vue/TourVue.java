package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.data.TerrainType;
import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.EdisonCoil;
import com.application.S2_dev.modele.tours.NikolaCoil;
import com.application.S2_dev.modele.tours.OppenheimerCoil;
import com.application.S2_dev.modele.tours.Tour;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import javax.swing.*;
import java.net.URL;

public class TourVue {
    Environnement environnement;
    Label idBobineEdison;
    Label idBobineOppenheimer;
    Label idBobineNikola;
    Label labelCredit;
    Label labelLife;
    Label idSelectedTower;
    TilePane tilePane;
    Terrain terrain;
    private TowerType selectedTowerType;
    private int timeBeforeNewSpawn = 15;
    private int lifeCount = 5;
    private Tour clickedTower = null;
    private ImageView levelChooser = null;

    private int money = 500;
    Pane pane;
    public TourVue(Environnement environnement, TilePane tilePane, Terrain terrain , Pane pane, Label idBobineEdison, Label idBobineOppenheimer, Label idBobineNikola, Label labelCredit, Label labelLife, Label idSelectedTower){
        this.environnement = environnement;
        this.tilePane = tilePane;
        this.terrain = terrain;
        this.pane = pane;
        this.idBobineEdison = idBobineEdison;
        this.idBobineNikola = idBobineNikola;
        this.idBobineOppenheimer = idBobineOppenheimer;
        this.labelCredit = labelCredit;
        this.labelLife = labelLife;
        this.idSelectedTower = idSelectedTower;

    }
    public void lancerTourVue() {

        final int ennemiSquadSize = environnement.getEnnemis().size();

        environnement.unTour();

        if (environnement.getEnnemis().size() < ennemiSquadSize)
            addMoney(100);

        int s = 0, be = 0, ba = 0;
        for (Ennemi e : environnement.getEnnemis()) {
            if (e instanceof Scavenger)
                s++;
            else if (e instanceof Behemoth)
                be++;
            else if (e instanceof Balliste)
                ba++;
        }

        if (!environnement.getTour().contains(clickedTower)) {
            pane.getChildren().remove(this.levelChooser);
            levelChooser = null;
        }

       /* labelScavenger.setText(s + "");
        labelBehemoth.setText(be + "");
        labelBalliste.setText(ba + "");*/

        int lifeVal = lifeCount - environnement.getReachedPlayers();
        labelLife.setText(lifeVal + "");

        if (timeBeforeNewSpawn == 0) {
           // spawnEnnemi();
            timeBeforeNewSpawn = 15;
            return;
        }

        timeBeforeNewSpawn--;

    }


    public void TestClickTourel(){
        idBobineEdison.setOnMouseClicked( h -> {
            if (levelChooser == null) {
                selectedTowerType = TowerType.Edison;
                 idSelectedTower.setText("Edison Coil");
            }
        });

        idBobineOppenheimer.setOnMouseClicked( h -> {
            if (levelChooser == null) {
                selectedTowerType = TowerType.Oppenheimer;
                   idSelectedTower.setText("Oppenheimer Coil");
            }
        });

        idBobineNikola.setOnMouseClicked( h -> {
            if (levelChooser == null) {
                selectedTowerType = TowerType.Nikola;
                 idSelectedTower.setText("Nikola Coil");
            }
        });

        tilePane.setOnMouseClicked(h->{
            int[] pos = terrain.getPosInMap((int)h.getX(), (int)h.getY());

            for (int i = 0; i < environnement.getTour().size(); i++) {
                Tour tour = environnement.getTour().get(i);
                if (tour.isInBounds((int) h.getX(), (int) h.getY())) {
                    return;
                }
            }

            spawnTower(pos[0], pos[1], 1);
        });

        labelCredit.setText(money + "");
    }
    void showLevelChooser() {

        URL urlChooser = Main.class.getResource("/image/tour/level_choose.png");

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
                    if (environnement.getTour().contains(clickedTower)) {

                        int col = clickedTower.getMapX();
                        int row = clickedTower.getMapY();

                        if (((money + clickedTower.getPrice()) - 100) < 0) {
                            JOptionPane.showMessageDialog(null, "Not enough money");
                        } else {
                            // remove the tower and get the money back
                            refundMoney(clickedTower.getPrice());
                            pane.getChildren().remove(clickedTower.getView());
                            environnement.getTour().remove(clickedTower);
                            spawnTower(row, col, 1);
                        }
                    }
                } else if (newX > 64 && newX < 80 && newY > 16 && newY < 32) {
                    // level 2 required
                    if (environnement.getTour().contains(clickedTower)) {

                        int col = clickedTower.getMapX();
                        int row = clickedTower.getMapY();

                        if (((money + clickedTower.getPrice()) - 200) < 0) {
                            JOptionPane.showMessageDialog(null, "Not enough money");
                        } else {
                            // remove the tower and get the money back
                            refundMoney(clickedTower.getPrice());
                            pane.getChildren().remove(clickedTower.getView());
                            environnement.getTour().remove(clickedTower);
                            spawnTower(row, col, 2);
                        }
                    }
                } else if (newX > 32 && newX < 48 && newY > 64 && newY < 80) {
                    // level 3 required
                    if (environnement.getTour().contains(clickedTower)) {

                        int col = clickedTower.getMapX();
                        int row = clickedTower.getMapY();

                        if (((money + clickedTower.getPrice()) - 300) < 0) {
                            JOptionPane.showMessageDialog(null, "Not enough money");
                        } else {
                            // remove the tower and get the money back
                            refundMoney(clickedTower.getPrice());
                            pane.getChildren().remove(clickedTower.getView());
                            environnement.getTour().remove(clickedTower);
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
            environnement.addTower(tower);
            creerSprite(tower);
            subtractMoney(tower.getPrice());
        } else {
            // Tower placement is not allowed at the specified coordinates
            System.out.println("Tower placement not allowed at coordinates (" + row + ", " + col + ")");
        }
    }
    double[] creerSprite(Tour t) {

        URL urlTour;

        switch (t.getType()) {
            case Nikola:
                urlTour = Main.class.getResource("image/tour/bobineEdison.png");
                break;
            case Edison:
                urlTour = Main.class.getResource("image/tour/bobineNicolas.png");
                break;
            case Oppenheimer:
                urlTour = Main.class.getResource("image/tour/bobineOppenheimer.png");
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
    public boolean canPlaceTowerAt(int row, int col) {
        if (terrain.getCase(row, col) == TerrainType.TOWER_BASE)
            return true;
        else return false;
    }
    void refundMoney(int value) {
        money += value;
        labelCredit.setText(money + "");
    }
    void addMoney(int value) {
        money += value;
        labelCredit.setText(money + "");
    }
    void subtractMoney(int value) {
        money -= value;
        labelCredit.setText(money + "");
    }
}
