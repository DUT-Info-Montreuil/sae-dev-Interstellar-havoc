package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import java.util.UUID;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

/**
 * Classe Tour
 * Implémente une tour dans le jeu
 */
public abstract class Tour {

    private String id;
    private String nom;
    private DoubleProperty x;
    private DoubleProperty y;
    private TowerType type;
    private int vie;
    public ImageView vue = null;
    private int niveau;
    private int prix;
    private int[] limites;
    private int xCarte, yCarte;
    private int portee; // Portée de la tour

    /**
     * Construit un objet Tour
     * @param x Position X
     * @param y Position Y
     * @param type Type de tour
     * @param niveau Niveau de la tour
     * @param prix Prix de la tour
     * @param portee Portée de la tour
     */
    public Tour(String nom, double x, double y, TowerType type, int niveau, int prix, int portee) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.type = type;
        this.vie = 100;
        this.niveau = niveau;
        this.prix = prix;
        this.limites = new int[4];
        this.xCarte = (int)(x/16);
        this.yCarte = (int)(y/16);
        this.portee = portee;
    }

    /**
     * Doit être implémentée dans la sous-classe
     * @param ennemi Objet Ennemi
     */
    public abstract void attaquerTour(Ennemi ennemi);

    /**
     * Inflige des dégâts à la tour
     * @param valeur Valeur des dégâts
     */
    public void infligerDegats(int valeur) {
        vie -= valeur;
    }

    /**
     * Vérifie si la tour est détruite
     * @return true si oui, sinon false
     */
    public boolean estDetruite() {
        return vie <= 0;
    }

    public boolean estDansLimites(int x, int y) {
        return x > limites[0] && x < (limites[0] + limites[2]) && y > limites[1] && y < (limites[1] + limites[3]);
    }

    /**
     * Vérifie si l'ennemi donné est dans la portée de la tour
     * @param ennemi Objet Ennemi
     * @return true si dans la portée, sinon false
     */
    public boolean estDansportee(Ennemi ennemi) {
        // Vérifie si l'ennemi est dans la portée de tir
        double distance = calculerDistance(ennemi.getX(), ennemi.getY());
        return distance <= portee;
    }

    private double calculerDistance(double xVal, double yVal) {
        return Math.sqrt(Math.pow((xVal-getX()), 2) + Math.pow((yVal-getY()), 2));
    }

    /********************** Getter/Setter **********************/

    public String getNom() {
        return nom;
    }

    public int getXCarte() {
        return xCarte;
    }

    public int getYCarte() {
        return yCarte;
    }

    public void setVue(ImageView vue) {
        this.vue = vue;
    }

    public ImageView getVue() {
        return vue;
    }

    public int getNiveau() {
        return niveau;
    }

    public int getPrix() {
        return prix - (100 - vie);
    }

    public void setLimites(int x, int y, int largeur, int hauteur) {
        limites[0] = x;
        limites[1] = y;
        limites[2] = largeur;
        limites[3] = hauteur;
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
        return x.get();
    }

    public void meur(){
        this.vie = 0;
    }
    public double getY() {
        return y.get();
    }

    public TowerType getType() {
        return type;
    }
}