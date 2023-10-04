package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.données.PixelMoveTimeEvent;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Ennemi {

    private final DoubleProperty x; // Position X de l'ennemi
    private final DoubleProperty y; // Position Y de l'ennemi
    private final Terrain terrain; // Terrain sur lequel évolue l'ennemi
    private int i = 0; // Index de la cellule dans le chemin le plus court
    private final String id; // Identifiant de l'ennemi
    public static int compteur = 0; // Compteur d'ennemis pour générer l'identifiant unique
    public IntegerProperty vie; // Points de vie de l'ennemi
    protected int degats;
    protected int portee;
    private Boolean enCoursAttaque;
    private Cellule celluleSuivante; // Cellule suivante de l'ennemi (x et y)
    private Cellule celluleCourante; // Cellule courante de l'ennemi (x et y)

    //Constructeur de l'ennemi
    public Ennemi(double valX, double valY, Terrain terr) {
        x = new SimpleDoubleProperty(valX);
        y = new SimpleDoubleProperty(valY);
        this.terrain = terr;
        this.id = "E" + compteur;
        compteur++;
    }

    public int getVie() {
        return vie.getValue();
    }

    public IntegerProperty getVieProperty() {
        return vie;
    }


    public void subirDegats(int degats) {
        vie.setValue(vie.getValue() - degats);
    }

    public boolean estVivant() {
        return vie.getValue() > 0;
    }


    // Verifie si un objet est a proximité de l'ennemi
    protected double calculerDistance(double x, double y) {
        // Calculer la distance entre l'ennemi et une position donnée
        return Math.sqrt(Math.pow((x - getX()), 2) + Math.pow((y - getY()), 2));
    }

    private boolean objetProximite(Objet objet) {
        double distance = calculerDistance(objet.getX(), objet.getY());
        return distance <= 10;
    }

    public boolean AttaquerObjet() {
        return !enCoursAttaque;
    }

    // Si un objet est a proximité, l'ennemi attaque
    public void attaqueObjet(Objet objet) {
        if (objetProximite(objet)) {
            enCoursAttaque = true;
            objet.degat(1);
        }
        enCoursAttaque = false;
    }

    public void agir() {
        //celluleCourante =null;
        //celluleSuivante = null;
        celluleCourante = terrain.getPlusCourtChemin().get(i);
        celluleSuivante = i > 0 ? terrain.getPlusCourtChemin().get(i - 1) : null;

        if (celluleSuivante != null) {
            if (terrain.getCase1(celluleSuivante.getI(), celluleSuivante.getJ()) == 1) {
                int diffX = celluleCourante.getI() - celluleSuivante.getI();
                int diffY = celluleCourante.getJ() - celluleSuivante.getJ();
                PixelMoveTimeEvent.initAnimation(this, diffX, diffY);
            } else if (terrain.getCase1(celluleSuivante.getI(), celluleSuivante.getJ()) == 2) {

                while (AttaquerObjet()) {
                    return;
                }
            }

        }


        i++;
        this.toString();
    }

    public void attaquerTour(Tour tour) {
        if (estDansPortee(tour)) {
            // Infliger des dégâts à la tour
            tour.infligerDegats(degats);
        }
    }

    public boolean estDansPortee(Tour tour) {
        // Vérifier si l'ennemi est à portée de tir
        double distance = this.calculerDistance(tour.getY(), tour.getX());
        return distance <= portee;
    }
    public void meur() {
        this.vie.setValue(0);
    }

    // Verifie si l'ennemi a atteint la base finale
    public boolean destinationFinaleAtteinte() {
        return i >= this.terrain.getPlusCourtChemin().size();
    }

    @Override
    public String toString() {
        return "id " + id;
    }

    /* les getter et setter */
    public void setX(double x1) {
        this.x.setValue(x1);
    }

    public void setY(double y1) {
        this.y.setValue(y1);
    }

    public Cellule getCelluleCourante() {
        return celluleCourante;
    }

    public String getId() {
        return id;
    }

    public DoubleProperty getXProperty() {
        return x;
    }

    public DoubleProperty getYProperty() {
        return y;
    }

    public double getX() {
        return x.getValue();
    }

    public double getY() {
        return y.getValue();
    }
}