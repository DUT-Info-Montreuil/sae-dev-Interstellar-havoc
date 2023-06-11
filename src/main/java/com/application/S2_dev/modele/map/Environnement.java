package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.tours.Tour;
import com.application.S2_dev.modele.ennemis.Scavenger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Random;

public class Environnement {
    Random random = new Random();
    Terrain terrain;
    private  ObservableList<Ennemi> ennemis;
    private  ObservableList<Objet> objets;
    private ObservableList<Tour> tours;
    private IntegerProperty reached;
    int[] start = {1, 0};
    int[] end = {12, 60};

    public Environnement(Terrain terrain) {
        this.terrain = terrain;
        this.ennemis = FXCollections.observableArrayList();
        this.tours = FXCollections.observableArrayList();
        this.objets = FXCollections.observableArrayList();
        this.reached = new SimpleIntegerProperty(5) ;


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
                Ennemi en = new Balliste(5,21, terrain);
                ennemis.add(en);
                ennemisAAjouter--;
            }

            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                int spawnRate = random.nextInt(150) + 1;
                switch (spawnRate) {
                    case 1:
                        Ennemi en = new Behemoth(5,21, terrain);
                        ennemis.add(en);
                        break;
                    case 2:
                        Ennemi en1 = new Scavenger(5,21, terrain);
                        ennemis.add(en1);
                        break;
                    case 3:
                        Ennemi en2 = new Balliste(5,21,terrain);
                        ennemis.add(en2);
                        break;
                }
            }
        }
    }
    public void unTour() {

            for (int i = 0; i < ennemis.size(); i++) {
                Ennemi e = ennemis.get(i);
                    e.agit(16, 16);
                    if (!e.estVivant()) {
                        System.out.println("mort de : " + e.getId());
                        ennemis.remove(e);
                    }
                    else if (e.finalDestReached()) {
                        ennemis.remove(e);
                        this.reached.setValue(reached.getValue() - 1);
                        System.out.println("Players reached: " + reached);
                    }
                    else {
                        for (Tour t : tours) {
                            e.attack(t);
                        }
                        for (Objet o : objets) {
                            if(o instanceof Mur) {
                                e.attaqueObjet(o);
                                System.out.println("pv "+ o.getPv());
                            }
                        }
                    }
            }

            for (int i = 0; i < objets.size(); i++) {
                Objet objet = objets.get(i);
                objet.agit();
                if (!objet.estVivant()) {
                    objets.remove(objet);
                }
            }

            for (int i = 0; i < tours.size(); i++) {
                Tour tour = tours.get(i);
                if (!tour.isDestroyed()) {
                    for (Ennemi e : ennemis) {
                        tour.attack(e);
                    }
                }
                else {
                    System.out.println("Tower destroyed: " + tour.getId());
                    tours.remove(tour);
                }
            }
            ajouterVague();
    }

    public ObservableList<Tour> getTour() {
        return tours;
    }
    public ObservableList<Objet> getObjets() {
        return objets;
    }
    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }

    public void addTower(Tour tour) {
        tours.add(tour);
    }
    public void ajoutObjet(Objet objet) {
        this.objets.add(objet);
    }

    public int getReachedPlayers() {
        return this.reached.getValue();
    }
    public IntegerProperty getReachedProperty() {
        return reached;
    }

}

