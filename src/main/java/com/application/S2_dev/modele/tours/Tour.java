package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

import java.util.UUID;

/**
 * Classe Tour
 * Implémente une tour dans le jeu
 */
public abstract class Tour {

    private final String id;
    private final String nom;
    private final DoubleProperty x;
    private final DoubleProperty y;
    private final TowerType type;
    public IntegerProperty vie;
    public int vieMax;
    public ImageView vue = null;
    private final int niveau;
    private final int prix;
    private final int[] limites;
    private final int xCarte;
    private final int yCarte;
    private final int portee; // Portée de la tour
    private int degats ; // Dommages infligés aux ennemis
    private int TAUX_TIR ; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge ;



    /**
     * Construit un objet Tour
     *
     * @param x      Position X
     * @param y      Position Y
     * @param type   Type de tour
     * @param niveau Niveau de la tour
     * @param prix   Prix de la tour
     * @param portee Portée de la tour
     */
    public Tour(IntegerProperty vie,int degats,int tauxDeTir,String nom, double x, double y, TowerType type, int niveau, int prix, int portee) {
        this.vie = vie;
        this.degats=degats;
        this.TAUX_TIR = tauxDeTir;
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.type = type;
        this.niveau = niveau;
        this.prix = prix;
        this.limites = new int[4];
        this.xCarte = (int) (x / 16);
        this.yCarte = (int) (y / 16);
        this.portee = portee;
    }

    /**
     * Doit être implémentée dans la sous-classe
     *
     * @param ennemi Objet Ennemi
     */


    /**
     * Inflige des dégâts à la tour
     *
     * @param valeur Valeur des dégâts
     */
    public void infligerDegats(int valeur) {
        vie.setValue(vie.getValue() - valeur);
    }

    /**
     * Vérifie si la tour est détruite
     *
     * @return true si oui, sinon false
     */
    public boolean estDetruite() {
        return vie.getValue() <= 0;
    }

    public boolean estDansLimites(int x, int y) {
        return x > limites[0] && x < (limites[0] + limites[2]) && y > limites[1] && y < (limites[1] + limites[3]);
    }

    /**
     * Vérifie si l'ennemi donné est dans la portée de la tour
     *
     * @param ennemi Objet Ennemi
     * @return true si dans la portée, sinon false
     */
    public boolean estDansportee(Ennemi ennemi) {
        // Vérifie si l'ennemi est dans la portée de tir
        double distance = calculerDistance(ennemi.getX(), ennemi.getY());
        return distance <= portee;
    }

    private double calculerDistance(double xVal, double yVal) {
        return Math.sqrt(Math.pow((xVal - getX()), 2) + Math.pow((yVal - getY()), 2));
    }
    public void attaquerEnnemi(Ennemi ennemi) {
        if (this.tempsRecharge == 0) {
            // Inflige des dommages à l'ennemi
            ennemi.subirDegats(degats);
            tempsRecharge = TAUX_TIR;
            this.playAttackSound();
        }
        if (tempsRecharge > 0)
            tempsRecharge--;
    }

    protected abstract void playAttackSound();


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


    public int getNiveau() {
        return niveau;
    }

    public int getPrix() {
        return prix - (100 - vie.getValue());
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
        return this.x.getValue();
    }

    public void meur() {
        this.vie.setValue(0);
    }

    public int getVie() {
        return this.vie.getValue();
    }

    public void setVie() {
        this.vie.setValue(vieMax);
    }

    public IntegerProperty vieProperty() {
        return vie;
    }

    public ImageView getVue() {
        return vue;
    }

    public double getY() {
        return this.y.getValue();
    }

    public TowerType getType() {
        return type;
    }
}
