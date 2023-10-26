package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.BallisteFactory;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.BehemothFactory;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.EnnemiFactory;
import com.application.S2_dev.modele.acteurs.objet.Objet;
import com.application.S2_dev.modele.acteurs.tours.Tour;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.ScavengerFactory;
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
    private  static Environnement uniqueInstance = null;
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

    public static Environnement getInstance(Terrain terrain, Pane pane) {
        if(uniqueInstance == null){
            uniqueInstance = new Environnement(terrain, pane);
        }
        return uniqueInstance;
    }

    // Methode qui genere des vagues d'ennemis
    //classe vague design strat
    public void ajouterVague() {
        GestionnaireProbabilite gestionnaireProbabilite = new GestionnaireProbabilite();
        GestionVagueEnnemis gestionVagueEnnemis = new GestionVagueEnnemis(this, terrain, gestionnaireProbabilite.choisirVagueStrategy());

        gestionVagueEnnemis.ajouterVague();
    }
    private void traiterEnnemiSuivantSiMemePosition(int index) {
        /* On verifie sur l'ennemi d'apres est a la meme position que l'ennemis actuel */
        if (index + 1 < ennemis.size()) {
            Ennemi ennemi = ennemis.get(index);
            Ennemi prochainEnnemi = ennemis.get(index + 1);
            if (prochainEnnemi.getCelluleCourante() == ennemi.getCelluleCourante()) {
                prochainEnnemi.agir(); // l'ennemi agit pour éviter qu'il ne s'entasse
                traiterEnnemi(prochainEnnemi);
            }
        }
    }
    private void traiterEnnemi(Ennemi ennemi) {
        if (!ennemi.estVivant()) {
            ennemis.remove(ennemi);
        } else if (ennemi.destinationFinaleAtteinte()) {
            ennemis.remove(ennemi);
            ennemisAtteints.setValue(ennemisAtteints.getValue() + 1);
        } else {
            for (Tour t : tours) {
                ennemi.attaquerActeur(t);
            }
            for (Objet o : objets) {
                ennemi.attaqueObjet(o);
            }
        }
    }


    private void gérerEnnemis() {
        for (int i = 0; i < ennemis.size(); i++) {
            Ennemi ennemi = ennemis.get(i);
            ennemi.agir();
            traiterEnnemi(ennemi);
            traiterEnnemiSuivantSiMemePosition(i);

        }
    }

    private void gérerBlastComponents(Tour tour) {
        List<Ennemi> ennemisDansPortee = tour.getEnnemisDansPortee(aProximiteTour, ennemis);
        Set<Ennemi> ennemisAvecBlast = new HashSet<>(blast.keySet());

        for (Ennemi ennemi : ennemisDansPortee) {
            if (!blast.containsKey(ennemi)) {
                BlastComponent bc = new BlastComponent(tour, ennemi);
                bc.add(pane);
                blast.put(ennemi, bc);
            }
        }

        ennemisAvecBlast.removeAll(ennemisDansPortee);

        for (Ennemi ennemi : ennemisAvecBlast) {
            BlastComponent bc = blast.get(ennemi);
            bc.remove(pane);
            blast.remove(ennemi);
        }
    }
    private void attaquerEnnemisAProximité(Tour tour) {
        List<Ennemi> ennemisDansPortee = tour.getEnnemisDansPortee(aProximiteTour, ennemis);
        for (Ennemi ennemi : ennemisDansPortee) {
            tour.attaquerActeur(ennemi);
        }
    }
    private void gérerTours() {
        List<Tour> toursÀSupprimer = new ArrayList<>();

        for (Tour tour : tours) {
            if (tour.estVivant()) {
                attaquerEnnemisAProximité(tour);
            } else {
                System.out.println("Tour détruite : " + tour.getId());
                toursÀSupprimer.add(tour);
            }
            gérerBlastComponents(tour);
        }

        tours.removeAll(toursÀSupprimer);
    }

    private void gérerObjets() {
        Iterator<Objet> iterator = objets.iterator();
        while (iterator.hasNext()) {
            Objet objet = iterator.next();
            objet.agit(); // Explosion des bombes
            if (!objet.estVivant()) {
                iterator.remove(); // Retirez les objets morts de la liste en utilisant l'itérateur
            }
        }
    }

    public void unTour() {
        gérerEnnemis();
        gérerObjets();
        gérerTours();
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

import com.application.S2_dev.modele.vagueFactory.GestionVagueEnnemis;
import com.application.S2_dev.modele.vagueFactory.GestionnaireProbabilite;
}


