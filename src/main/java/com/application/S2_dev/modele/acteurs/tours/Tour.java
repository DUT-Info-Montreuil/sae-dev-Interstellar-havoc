package com.application.S2_dev.modele.acteurs.tours;

import com.application.S2_dev.modele.acteurs.Acteur;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;

import java.util.UUID;

/**
 * Classe Tour
 * Implémente une tour dans le jeu
 */
public abstract class Tour extends Acteur {
    private final String nom;
    private final TowerType type;
    public int vieMax;
    public ImageView vue = null;
    private final int niveau;
    private final int prix;
    private final int[] limites;
    private final int xCarte;
    private final int yCarte;
    private final int portee; // Portée de la tour
    protected int degatsT ; // Dommages infligés aux ennemis
    private int TAUX_TIR ; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge ;


    public Tour(int tauxDeTir, String nom, double valX, double valY, TowerType type, int niveau, int prix, int portee) {
        super(valX, valY);
        this.TAUX_TIR = tauxDeTir;
        this.nom = nom;
        this.type = type;
        this.niveau = niveau;
        this.prix = prix;
        this.limites = new int[4];
        this.xCarte = (int) (valX / 16);
        this.yCarte = (int) (valY / 16);
        this.portee = portee;
        this.vie = new SimpleIntegerProperty(10);
    }


    public boolean estDansLimites(int x, int y) {
        return x > limites[0] && x < (limites[0] + limites[2]) && y > limites[1] && y < (limites[1] + limites[3]);
    }
    @Override
    public void attaquerActeur(Acteur ennemi) {
        if (this.tempsRecharge == 0) {
            System.out.println("JATTAQUE");
            // Inflige des dommages à l'ennemi
            ennemi.infligerDegats(degatsT);
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
    public void setVie() {
        this.vie.setValue(vieMax);
    }
    public TowerType getType() {
        return type;
    }
}
