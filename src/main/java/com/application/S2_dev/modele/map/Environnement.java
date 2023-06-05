package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.tours.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import javafx.application.Platform;

public class Environnement {

    Pane pane;
    private int spawnRate;
    private Terrain terr;
    private ArrayList<Ennemi> mort;
    private ArrayList<Tour> mort2;
    private  ObservableList<Ennemi> ennemis;
    private ArrayList<Tour> tours;
    private int reached = 0;

    public Environnement(Pane gameLayout) {
        this.mort = new ArrayList<>();
        this.mort2 = new ArrayList<>();
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
    
    public ArrayList<Tour> getTour() {
        return tours;
    }

    public void ajouter(Ennemi a) {
        this.ennemis.add(a);
    }

    public void init() {
        Ennemi e1 = new Balliste(5, 21);
        ennemis.add(e1);
    }

    public void unTour() {
        
        for(int i = 0; i < ennemis.size(); i++) {
            Ennemi e = ennemis.get(i);
            e.agit(16, 16);
        }

        for (int i = 0; i < ennemis.size(); i++) {
            Ennemi e = ennemis.get(i);
            if (!e.estVivant()) {
                System.out.println("mort de : " + e.getId());
                ennemis.remove(e);
                this.mort.add(e);
            } else if (e.finalDestReached()) {
                ennemis.remove(e);
                this.mort.add(e);
                reached++;
                System.out.println("Players reached: " + reached);
            } else {
                for (Tour t : tours) {
                    e.attack(t);
                }
            }
        }
        
        /**
         * attacks enemies if in range
         */
        for (int i = 0; i < tours.size(); i++) {
            Tour tour = tours.get(i);
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
        
        // remove unwanted objects
        for (Ennemi e : mort) {
            pane.getChildren().remove(e.getView());
        }
        
        for (Tour t : mort2) {
            pane.getChildren().remove(t.getView());
        }
        
        mort.clear();
        mort2.clear();
    }
    
    public void addTower(Tour tour) {
        tours.add(tour);
    }
    
    public int getReachedPlayers() {
        return reached;
    }

}

