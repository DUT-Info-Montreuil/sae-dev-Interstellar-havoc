package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
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
    private Random random = new Random(); // Random pour calculer les vagues d'ennemis
    private Terrain terrain;
    private  ObservableList<Ennemi> ennemis; // liste des ennemis present sur le terrain
    private  ObservableList<Objet> objets; // liste des objets present sur le terrain
    private ObservableList<Tour> tours; // liste des tourelles presente sur le terrain
    private IntegerProperty ennemisAtteints ; // Nombre d'ennemi qui ont atteint la base finale
    public BooleanProperty aProximiteTour ; // ennemis a proximité d'une tour

    public Environnement(Terrain terrain) {
        this.terrain = terrain;
        this.ennemis = FXCollections.observableArrayList();
        this.tours = FXCollections.observableArrayList();
        this.objets = FXCollections.observableArrayList();
        this.ennemisAtteints = new SimpleIntegerProperty(0) ;
        this.aProximiteTour = new SimpleBooleanProperty(false);
    }

    // Methode qui genere des vagues d'ennemis
    public void ajouterVague() {
        boolean spawnPossible = true;
        int ennemisMax = 5; // Maximum d'ennemis sur le terrain
        int ennemisActuels = ennemis.size();

        if (ennemisActuels >= ennemisMax) {
            spawnPossible = false;
        }
        if (spawnPossible) {
            int ennemisAAjouter =  ennemisMax - ennemisActuels; // ennemis à ajouter dans la liste

            /* on créer un ennemi si la liste est vide */
            if (ennemisActuels == 0) {
                Ennemi en = new Balliste(5,21, terrain);
                ennemis.add(en);
                ennemisAAjouter--;
            }
            /* On parcour le nombre d'ennemi à ajouter pour on fait un random */
            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                int spawnRate = random.nextInt(150) + 1;
                switch (spawnRate) {
                    case 1:
                        Ennemi Behemoth = new Behemoth(5,21, terrain);
                        ennemis.add(Behemoth);
                        break;
                    case 2:
                        Ennemi Scavenger = new Scavenger(5,21, terrain);
                        ennemis.add(Scavenger);
                        break;
                    case 3:
                        Ennemi Balliste = new Balliste(5,21,terrain);
                        ennemis.add(Balliste);
                        break;
                }
            }
        }
    }
    private void traiterEnnemi(Ennemi ennemi) {
        /* methode qui traite les actions de l'ennemi pour eviter de la redondence de code */
        if (!ennemi.estVivant()) {
            System.out.println("Mort de : " + ennemi.getId());
            ennemis.remove(ennemi); // on retire les morts de la liste
        } else if (ennemi.destinationFinaleAtteinte()) {
            ennemis.remove(ennemi);
            ennemisAtteints.setValue(ennemisAtteints.getValue() + 1); // on incremente les ennemisAtteints
            System.out.println("Joueurs atteints : " + ennemisAtteints);
        } else {
            for (Tour t : tours) {
                ennemi.attaquerTour(t); // attaque des tours
                System.out.println("PV de la tour " + t.getVie());
            }
            for (Objet o : objets) {
                ennemi.attaqueObjet(o); // attaque des objets (mur et chemin bloquer)
            }
        }
    }
    public void unTour() {

        for (int i = 0; i < ennemis.size(); i++) {
            Ennemi ennemi = ennemis.get(i);
            ennemi.agir();

            /* On verifie sur l'ennemi d'apres est a la meme position que l'ennemis actuel */
            if (i + 1 < ennemis.size()) {
                Ennemi prochainEnnemi = ennemis.get(i + 1);
                if (prochainEnnemi.getCelluleCourante() == ennemi.getCelluleCourante()) {
                    prochainEnnemi.agir(); // l'ennemi agit pour eviter qu'il s'entasse
                    traiterEnnemi(prochainEnnemi);
                }
            }
            traiterEnnemi(ennemi);
        }

        for (int i = 0; i < objets.size(); i++) {
            Objet objet = objets.get(i);
            objet.agit(); // explision des bombes
            if (!objet.estVivant()) {
                objets.remove(objet); // retirer les mort de la liste
            }
        }
        for (int i = 0; i < tours.size(); i++) {
            Tour tour = tours.get(i);
            if (!tour.estDetruite()) {
                // Récupère les ennemis à portée et les attaque
                List<Ennemi> ennemisDansPortee = getEnnemisDansPortee(tour);
                for (Ennemi e : ennemisDansPortee) {
                    // Remarque : l'attaque est basée sur le taux de tir de la tour
                    tour.attaquerEnnemi(e);
                }
            } else {
                System.out.println("Tour détruite : " + tour.getId());
                tours.remove(tour);
            }
        }
        ajouterVague(); // Ajout des vages d'ennemi
    }
    public void ajouterTour(Tour tour) {
        tours.add(tour); /* Ajouter des tours à la liste */
    }
    public void ajoutObjet(Objet objet) {
        this.objets.add(objet); /* Ajouter des objets a la liste */
    }
    public BooleanProperty aProximiteTourProperty() {
        return this.aProximiteTour;
    }

    /* les getter et setter */
    public int getennemisAtteints() {
        return this.ennemisAtteints.getValue();
    }
    public IntegerProperty getennemisAtteintsProperty() {
        return ennemisAtteints;
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
}


