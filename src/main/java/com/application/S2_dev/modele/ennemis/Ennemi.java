package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.données.PixelMoveTimeEvent;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Ennemi {
    private DoubleProperty x, y; // Positions X et Y de l'ennemi
    protected Terrain terrain; // Terrain sur lequel évolue l'ennemi
    protected int i; // Index de la cellule dans le chemin le plus court
    private String id; // Identifiant de l'ennemi
    public static int compteur = 0; // Compteur d'ennemis pour générer l'identifiant unique
    public IntegerProperty vie; // Points de vie de l'ennemi
    protected int degats;
    protected int portee;
    protected Boolean enCoursAttaque;
    protected Cellule celluleSuivante; // Cellule suivante de l'ennemi (x et y)
    protected Cellule celluleCourante; // Cellule courante de l'ennemi (x et y)

    //Constructeur de l'ennemi
    public Ennemi(double valX, double valY, Terrain terr) {
        x = new SimpleDoubleProperty(valX);
        y = new SimpleDoubleProperty(valY);
        this.terrain = terr;
        this.id = "E" + compteur;
        compteur++;
    }
    public double calculerDistance(double x, double y) {
        // Calculer la distance entre l'ennemi et une position donnée
        return Math.sqrt(Math.pow((x - getX()), 2) + Math.pow((y - getY()), 2));
    }
    public boolean estDansPortee(Tour tour) {
        // Vérifier si l'ennemi est à portée de tir
        double distance = this.calculerDistance(tour.getY(), tour.getX());
        return distance <= portee;
    }
    public void attaquerTour(Tour tour) {
        if (estDansPortee(tour)) {
            // Infliger des dégâts à la tour
            tour.infligerDegats(degats);
        }
    }
    public boolean objetProximite(Objet objet) {
        // Verifie si un objet est a proximité de l'ennemi
        double distance = calculerDistance(objet.getX(), objet.getY());
        return distance <= 10;
    }
    protected boolean AttaquerObjet(){
        return !enCoursAttaque;
    }
    // Si un objet est a proximité, l'ennemi attaque
    public  void attaqueObjet(Objet objet){
        if(objetProximite(objet)) {
            enCoursAttaque = true;
            objet.degat(1);
        }
        enCoursAttaque = false;
    }
    public void subirDegats(int degats) {
        vie.setValue(vie.getValue() - degats) ;
    }
    public boolean estVivant() {
        return vie.getValue() > 0;
    }
    public void meur(){
        this.vie.setValue(0);
    }
    public boolean destinationFinaleAtteinte() {
        // Verifie si l'ennemi a atteint la base finale
        return i >= this.terrain.getPlusCourtChemin().size();
    }
    public void agir() {
        celluleCourante = terrain.getPlusCourtChemin().get(i);
        celluleSuivante = i > 0 ? terrain.getPlusCourtChemin().get(i - 1) : null;
        if (celluleSuivante != null) {
            this.attaque();
        }


        i++;
        this.toString();
    }

    public abstract void attaque();
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
    public IntegerProperty getVieProperty() {
        return vie;
    }
    public int getVie() {
        return vie.getValue();
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
    @Override
    public String toString() {
        return "id "+id;
    }
}