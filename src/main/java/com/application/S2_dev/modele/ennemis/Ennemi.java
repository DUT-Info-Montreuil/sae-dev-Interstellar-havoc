package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Ennemi {

    private DoubleProperty x; // Position X de l'ennemi
    private DoubleProperty y; // Position Y de l'ennemi
    private Terrain terrain; // Terrain sur lequel évolue l'ennemi
    private int i = 0; // Index de la cellule dans le chemin le plus court
    private String id; // Identifiant de l'ennemi
    public static int compteur = 0; // Compteur d'ennemis pour générer l'identifiant unique
    private int vie; // Points de vie de l'ennemi
    protected int degats;
    protected int portee;
    private Boolean enCoursAttaque;
    public Ennemi(double valX, double valY, Terrain terr) {
        x = new SimpleDoubleProperty(valX);
        y = new SimpleDoubleProperty(valY);
        this.terrain = terr;
        this.id = "E" + compteur;
        compteur++;
        this.vie = 100;
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

    public void subirDegats(int degats) {
        vie -= degats;
    }

    public boolean AttaquerObjet(){
        return !enCoursAttaque;
    }

    public boolean estVivant() {
        return vie > 0;
    }
    public abstract void attaquerTour(Tour tour);
    public abstract boolean estDansPortee(Tour tour);

    private boolean objetProximite(Objet objet) {
        double distance = calculaterDistance(objet.getX(), objet.getY());
        return distance <= 10;
    }
    private double calculaterDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
    public  void attaqueObjet(Objet objet){
        if(objetProximite(objet)) {
            System.out.println("je suis a coté");
            enCoursAttaque = true;
            ((Mur) objet).degat(1);
        }
        enCoursAttaque = false;
    }
    public void agir(double largeurCase, double hauteurCase) {
        Cellule celluleCourante = null;
        Cellule celluleSuivante = null;
        celluleCourante = terrain.getPlusCourtChemin().get(i);
        celluleSuivante = i > 0 ? terrain.getPlusCourtChemin().get(i - 1) : null;

        if (celluleSuivante != null) {
            if(terrain.getCase1(celluleSuivante.getI(),celluleSuivante.getJ())==1) {
                int diffX = celluleCourante.getI() - celluleSuivante.getI();
                int diffY = celluleCourante.getJ() - celluleSuivante.getJ();

                if (diffX != 0) {
                    this.setY(this.getY() + (diffX > 0 ? largeurCase : -largeurCase));
                }

                if (diffY != 0) {
                    this.setX(this.getX() + (diffY > 0 ? hauteurCase : -hauteurCase));
                }
            }
            else if(terrain.getCase1(celluleSuivante.getI(),celluleSuivante.getJ())==2){
                while(AttaquerObjet()){
                    return;
                }
            }
        }
        i++;
        this.toString();
    }

    public void setX(double x1) {
        this.x.setValue(x1);
    }

    public void setY(double y1) {
        this.y.setValue(y1);
    }

    @Override
    public String toString() {
        return "id "+id;
    }

    public void meur(){
        this.vie = 0;
    }

    public boolean destinationFinaleAtteinte() {
        return i >= this.terrain.getPlusCourtChemin().size();
    }

    protected double calculerDistance(double x, double y) {
        // Calculer la distance entre l'ennemi et une position donnée
        return Math.sqrt(Math.pow((x - getX()), 2) + Math.pow((y - getY()), 2));
    }

}













/*  public ImageView getVue() {
        return vue;
    }

    public void ajouterImageSecondaire(Image imageSecondaire) {
        this.imageSecondaire = imageSecondaire;
    }
*/

