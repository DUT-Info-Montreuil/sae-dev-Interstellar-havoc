package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.EnnemiFactory.BallisteFactory;
import com.application.S2_dev.modele.EnnemiFactory.BehemothFactory;
import com.application.S2_dev.modele.EnnemiFactory.EnnemiFactory;
import com.application.S2_dev.modele.EnnemiFactory.ScavengerFactory;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.acteurs.tours.Tour;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;

import java.util.*;

public class Environnement {
    private Random random = new Random(); // Random pour calculer les vagues d'ennemis
    private Terrain terrain;
    private  ObservableList<Ennemi> ennemis; // liste des ennemis present sur le terrain
    private  ObservableList<Objet> objets; // liste des objets present sur le terrain
    private ObservableList<Tour> tours; // liste des tourelles presente sur le terrain
    private IntegerProperty ennemisAtteints ; // Nombre d'ennemi qui ont atteint la base finale
    public BooleanProperty aProximiteTour ; // ennemis a proximité d'une tour
    private Map<Ennemi, BlastComponent> blast;
    private Pane pane;

    public Environnement(Terrain terrain, Pane pane) {
        this.terrain = terrain;
        this.ennemis = FXCollections.observableArrayList();
        this.tours = FXCollections.observableArrayList();
        this.objets = FXCollections.observableArrayList();
        this.ennemisAtteints = new SimpleIntegerProperty(0) ;
        this.aProximiteTour = new SimpleBooleanProperty(false);
        this.blast = new HashMap<>();
        this.pane = pane;
    }

    // Methode qui genere des vagues d'ennemis
    //classe vague design strat
    public void ajouterVague() {
        boolean spawnPossible = true;
        int ennemisMax = 5; // Maximum d'ennemis sur le terrain
        int ennemisActuels = ennemis.size();
        EnnemiFactory ennemiBallisteFactory = new BallisteFactory();
        EnnemiFactory ennemiBehemothFactory =  new BehemothFactory();
        EnnemiFactory ennemiScavengerFactory = new ScavengerFactory();

        if (ennemisActuels >= ennemisMax) {
            spawnPossible = false;
        }
        if (spawnPossible) {
            int ennemisAAjouter =  ennemisMax - ennemisActuels; // ennemis à ajouter dans la liste

            /* on créer un ennemi si la liste est vide */
            if (ennemisActuels == 0) {
                Ennemi ennemi = ennemiBallisteFactory.créerEnnemi(terrain);
                ennemis.add(ennemi);
                ennemisAAjouter--;
            }
            /* On parcour le nombre d'ennemi à ajouter pour on fait un random */
            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                int spawnRate = random.nextInt(150) + 1;
                switch (spawnRate) {
                    case 1:
                        Ennemi ennemi = ennemiBehemothFactory.créerEnnemi(terrain);
                        ennemis.add(ennemi);
                        break;
                    case 2:
                        Ennemi ennemi1 = ennemiScavengerFactory.créerEnnemi(terrain);
                        ennemis.add(ennemi1);
                        break;
                    case 3:
                        Ennemi ennemi2 = ennemiBallisteFactory.créerEnnemi(terrain);
                        ennemis.add(ennemi2);
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
                ennemi.attaquerActeur(t); // attaque des tours
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
            if (tour.estVivant()) {
                // Récupère les ennemis à portée et les attaque
                List<Ennemi> ennemisDansPortee = getEnnemisDansPortee(tour);
                for (Ennemi e : ennemisDansPortee) {
                    // Remarque : l'attaque est basée sur le taux de tir de la tour
                    tour.attaquerActeur(e);
                }
            } else {
                System.out.println("Tour détruite : " + tour.getId());
                tours.remove(tour);
            }
        }
        for (Tour tour : tours) {

            List<Ennemi> tempEnnemis = this.getEnnemisDansPortee(tour);

            for (Ennemi e : ennemis) {
                if (!blast.containsKey(e) && tempEnnemis.contains(e)) {
                    BlastComponent bc = new BlastComponent(tour, e);
                    bc.add(pane);
                    blast.put(e, bc);
                } else if (blast.containsKey(e)) {
                    if (!tempEnnemis.contains(e)) {
                        BlastComponent bc = blast.get(e);
                        bc.remove(pane);
                        blast.remove(e);
                    }
                }
            }
        }

        // remove blast animation if the ennemi is dead now
        List<Ennemi> tempList = new ArrayList<>();
        for (Ennemi e : blast.keySet()) {
            if (!ennemis.contains(e)) {
                BlastComponent bc = blast.get(e);
                bc.remove(pane);
                tempList.add(e);
            }
        }

        for (Ennemi e : tempList) {
            blast.remove(e);
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
            if (tour.estDansPortee(e)) {
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


