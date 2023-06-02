package com.application.S2_dev.modele.map;

import com.application.S2_dev.Main;
import com.application.S2_dev.controlleur.ControlleurTerrainJeu;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.tours.Tour;
import com.application.S2_dev.modele.ennemis.Scavenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import javafx.application.Platform;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.Random;
//import java.util.random.*;

public class Environnement {
    Random random = new Random();
    Pane pane;
    private int spawnRate;
    private Terrain terr;
    private ArrayList<Ennemi> mort;
    private ArrayList<Tour> mort2;
    private  ObservableList<Ennemi> ennemis;
    private ArrayList<Tour> tours;


    public Environnement(Pane gameLayout) {
        this.mort = new ArrayList<>();
        this.ennemis = FXCollections.observableArrayList();
        this.tours = new ArrayList<>();
        terr = new Terrain();
        this.pane = gameLayout;
    }

    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }

    public ArrayList<Ennemi> getMort() {
        return mort;
    }

    public void ajouterVague() {
        boolean spawnPossible = true;
        // int compteurSpawnEnnemi = 5;
        int ennemisMax = 5;
        int ennemisActuels = ennemis.size();

        if (ennemisActuels >= ennemisMax) {
            spawnPossible = false;
        }

        if (spawnPossible) {
            int ennemisAAjouter =  ennemisMax - ennemisActuels;

           /* if (ennemisActuels == 0) {
                Ennemi en = new Balliste(5,21);
                ennemis.add(en);
                ennemisAAjouter--;
            }*/

            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                int spawnRate = random.nextInt(150) + 1;
                switch (spawnRate) {
                    case 1:
                        Ennemi en = new Behemoth(5,21);
                        ennemis.add(en);
                        break;
                    case 2:
                        Ennemi en1 = new Scavenger(5,21);
                        ennemis.add(en1);
                        break;
                    case 3:
                        Ennemi en2 = new Balliste(5,21);
                        ennemis.add(en2);
                        break;
                }
            }
        }
    }
    public void unTour() {
        for(int i = 0; i< ennemis.size(); i++) {
            Ennemi e = ennemis.get(i);
            e.agit(16, 16);
        }

        for (Ennemi e : ennemis) {
            if (!e.estVivant()) {
                System.out.println("mort de : " + e.getId());
                this.mort.add(e);
            }
        }
        for(Ennemi e : mort){
            ennemis.remove(e);
        }

        /**
         * attacks enemies if in range
         */
        for (Tour tour : tours) {
            if (!tour.isDestroyed()) {
                for (Ennemi e : ennemis) {
                    tour.attack(e);
                }
            } else {
                System.out.println("Tower destroyed: " + tour.getId());
                tours.remove(tour);
                this.mort2.add(tour);
            }
        }
        ajouterVague();

    }
    public void unTour1() {


        for(int i = 0; i< ennemis.size(); i++) {
            Ennemi e = ennemis.get(i);
            e.agit(16, 16);
        }

        for (int i = ennemis.size() - 1; i >= 0; i--) {
            Ennemi en = ennemis.get(i);
            if (!en.estVivant()) {
                System.out.println("mort de : " + en);
                ennemis.remove(i);
                this.mort.add(en);
            }
        }
        System.out.println("Taille " + ennemis.size());
        ajouterVague();

    }

    public void addTower(Tour tour) {
        tours.add(tour);
    }

    public boolean canPlaceTowerAt(double x, double y) {
        return true;
    }

}

