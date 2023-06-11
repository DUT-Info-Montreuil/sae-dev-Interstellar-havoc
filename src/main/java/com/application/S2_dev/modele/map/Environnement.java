package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.*;
import com.application.S2_dev.modele.tours.Tour;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class Environnement {

    Pane pane;
    private int tauxApparition;
    private Terrain terrain;
    private ArrayList<Ennemi> ennemisElimines;
    private ArrayList<Tour> toursEliminees;
    private  ObservableList<Ennemi> ennemis;
    private ArrayList<Tour> tours;
    private int joueursAtteints = 0;

    public Environnement(Pane gameLayout) {
        this.ennemisElimines = new ArrayList<>();
        this.toursEliminees = new ArrayList<>();
        this.ennemis = FXCollections.observableArrayList();
        this.tours = new ArrayList<>();
        terrain = new Terrain();
        this.pane = gameLayout;
    }

    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }

    public ArrayList<Ennemi> getennemisElimines() {
        return ennemisElimines;
    }

    public ArrayList<Tour> getTour() {
        return tours;
    }

    public void ajouterEnnemi(Ennemi ennemi) {
        this.ennemis.add(ennemi);
    }


    public void init() {
        Ennemi e1 = new Balliste(5, 21);
        ennemis.add(e1);
    }

    public void unTour() {

        for(int i = 0; i < ennemis.size(); i++) {
            Ennemi ennemi = ennemis.get(i);
            ennemi.agir(16, 16);
        }

        for (int i = 0; i < ennemis.size(); i++) {
            Ennemi ennemi = ennemis.get(i);
            if (!ennemi.estVivant()) {
                System.out.println("Mort de : " + ennemi.getId());
                ennemis.remove(ennemi);
                this.ennemisElimines.add(ennemi);
            } else if (ennemi.destinationFinaleAtteinte()) {
                ennemis.remove(ennemi);
                this.ennemisElimines.add(ennemi);
                joueursAtteints++;
                System.out.println("Joueurs atteints : " + joueursAtteints);
            } else {
                for (Tour tour : tours) {
                    ennemi.attaquerTour(tour);
                }
            }
        }

        /**
         * Attaque les ennemis s'ils sont à portée
         */
        for (int i = 0; i < tours.size(); i++) {
            Tour tour = tours.get(i);
            if (!tour.estDetruite()) {
                // Récupère les ennemis à portée et les attaque
                List<Ennemi> ennemisDansPortee = getEnnemisDansPortee(tour);
                for (Ennemi e : ennemisDansPortee) {
                    // Remarque : l'attaque est basée sur le taux de tir de la tour
                    tour.attaquerTour(e);
                }
            } else {
                System.out.println("Tour détruite : " + tour.getId());
                tours.remove(tour);
                this.toursEliminees.add(tour);
            }

        }

        // Supprime les objets indésirables
        for (Ennemi e : ennemisElimines) {
            pane.getChildren().remove(e.getVue());
        }

        for (Tour t : toursEliminees) {
            pane.getChildren().remove(t.getVue());
        }

        ennemisElimines.clear();
        toursEliminees.clear();

        // Ajoute l'indicateur de dégâts
        List<Ennemi> ennemisEndommages = getEnnemisLibres();

        for (Ennemi e : ennemis) {
            if (ennemisEndommages.contains(e)) {
                e.basculerVersVueSecondaire(true);
            } else {
                e.basculerVersVueSecondaire(false);
            }
        }
    }

    /**
     * Renvoie une liste d'ennemis à portée de cette tour
     * @param tour Objet Tour représentant la tour
     * @return liste d'ennemis à portée
     */
    public List<Ennemi> getEnnemisDansPortee(Tour tour) {

        List<Ennemi> temp = new ArrayList<>();

        for (Ennemi e : ennemis) {
            if (tour.estDansportee(e))
                temp.add(e);
        }

        return temp;
    }


    /**
     * @return liste d'ennemis qui ne sont pas à portée d'une tour
     */
    public List<Ennemi> getEnnemisLibres() {
        List<Ennemi> temp = new ArrayList<>();
        for (Ennemi ennemi : ennemis) {
            boolean res = false;
            for (Tour t : tours) {
                if(t.estDansportee(ennemi)) {
                    res = true;
                    break;
                }
            }
            if (res) {
                temp.add(ennemi);
            }
        }
        return temp;
    }


    public void ajouterTour(Tour tour) {
        tours.add(tour);
    }

    public int getJoueursAtteints() {
        return joueursAtteints;
    }
}

