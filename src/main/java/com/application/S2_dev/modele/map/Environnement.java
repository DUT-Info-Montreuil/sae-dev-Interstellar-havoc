package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.objet.Bombe;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.tours.Tour;
import com.application.S2_dev.modele.ennemis.Scavenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.Random;

public class Environnement {
    Random random = new Random();
    Pane pane;
    private Terrain terr;
    private ArrayList<Ennemi> mort;
    private ArrayList<Tour> mort2;
    private  ObservableList<Ennemi> ennemis;
    private  ObservableList<Objet> objets;
    private ArrayList<Tour> tours;
    private int reached = 0;


    public Environnement(Pane gameLayout) {
        this.mort = new ArrayList<>();
        this.mort2 = new ArrayList<>();
        this.ennemis = FXCollections.observableArrayList();
        this.tours = new ArrayList<>();
        terr = new Terrain();
        this.pane = gameLayout;
        this.objets = FXCollections.observableArrayList();
    }

    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }

    public void ajouterVague() {
        boolean spawnPossible = true;
        int ennemisMax = 5;
        int ennemisActuels = ennemis.size();

        if (ennemisActuels >= ennemisMax) {
            spawnPossible = false;
        }

        if (spawnPossible) {
            int ennemisAAjouter =  ennemisMax - ennemisActuels;

            if (ennemisActuels == 0) {
                Ennemi en = new Balliste(5,21);
                ennemis.add(en);
                ennemisAAjouter--;
            }

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
            if (!e.estVivant()) {
                System.out.println("mort de : " + e.getId());
                ennemis.remove(e);
            }
            else {
                for (Tour t : tours) {
                    e.attack(t);
                }
            }
        }

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
        ajouterVague();

    }
    public void unTour1() {

        for (int i = 0; i < ennemis.size(); i++) {
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
        for (int i = 0; i < objets.size(); i++) {
            if (objets.get(i) instanceof Bombe) {
                if (!objets.get(i).estVivant()) {
                    for (Ennemi e : ennemis) {
                        ((Bombe) objets.get(i)).degat(e);
                    }
                }
            }
            ajouterVague();
            mort.clear();
            mort2.clear();
        }
    }
    public ArrayList<Tour> getTour() {
        return tours;
    }
    public ObservableList<Objet> getObjets() {
        return objets;
    }

    public void addTower(Tour tour) {
        tours.add(tour);
    }
    public void ajoutObjet(Objet objet) {
        objets.add(objet);
    }

    public boolean canPlaceTowerAt(double x, double y) {
        return true;
    }
    public int getReachedPlayers() {
        return reached;
    }

}

