package com.application.S2_dev.modele.map;

import com.application.S2_dev.Parametre;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.objet.Block;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.*;

public class Environnement {
    private Random random = new Random();
    private Terrain terrain;
    private  ObservableList<Ennemi> ennemis;
    private  ObservableList<Objet> objets;
    private ObservableList<Tour> tours;
    private IntegerProperty joueursAtteints ;
    public BooleanProperty aProximiteTour ;

    public Environnement(Terrain terrain) {
        this.terrain = terrain;
        this.ennemis = FXCollections.observableArrayList();
        this.tours = FXCollections.observableArrayList();
        this.objets = FXCollections.observableArrayList();
        this.joueursAtteints = new SimpleIntegerProperty(0) ;
        aProximiteTour = new SimpleBooleanProperty(false);
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
            Ennemi ennemi = ennemis.get(i);
            ennemi.agir(Parametre.largeurCase, Parametre.hauteurCase);
            if (!ennemi.estVivant()) {
                System.out.println("mort de : " + ennemi.getId());
                ennemis.remove(ennemi);
            } else if (ennemi.destinationFinaleAtteinte()) {
                ennemis.remove(ennemi);
                joueursAtteints.setValue(joueursAtteints.getValue()+1);
                System.out.println("Joueurs atteints : " + joueursAtteints);
            } else {
                for (Tour t : tours) {
                    ennemi.attaquerTour(t);
                    System.out.println("pv de tour "+ t.getVie());
                }
                for (Objet o : objets) {
                    if (o instanceof Mur || o instanceof Block) {
                        ennemi.attaqueObjet(o);
                      //  System.out.println("pv " + o.getPv());
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
            }
        }
        ajouterVague();
    }

    public BooleanProperty aProximiteTourProperty() {
        return this.aProximiteTour;
    }

    /**
     * Renvoie une liste d'ennemis à portée de cette tour
     * @param tour Objet Tour représentant la tour
     * @return liste d'ennemis à portée
     */
    public List<Ennemi> getEnnemisDansPortee(Tour tour) {

        List<Ennemi> temp = new ArrayList<>();

        for (Ennemi e : ennemis) {
            if (tour.estDansportee(e)) {
                this.aProximiteTour.setValue(true);
                temp.add(e);
            }
            else{
                this.aProximiteTour.setValue(false);
            }
        }

        return temp;
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

    public void ajouterTour(Tour tour) {
        tours.add(tour);
    }
    public void ajoutObjet(Objet objet) {
        this.objets.add(objet);
    }
    public int getJoueursAtteints() {
        return this.joueursAtteints.getValue();
    }
    public IntegerProperty getJoueursAtteintsProperty() {
        return joueursAtteints;
    }

}















/*
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
 */

