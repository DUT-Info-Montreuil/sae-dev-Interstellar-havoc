package com.application.S2_dev.modele.acteurs.tours;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.acteurs.Acteur;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.sound.Sound;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe Tour
 * Implémente une tour dans le jeu
 */
public abstract class Tour extends Acteur {
    protected String nom;
    private final TowerType type;
    public int vieMax;
    public ImageView vue = null;
    private final int niveau;
    protected int prix;
    private final int[] limites;
    private final int xCarte;
    private final int yCarte;
    protected int portee; // Portée de la tour
    protected int degatsT ; // Dommages infligés aux ennemis
    protected int TAUX_TIR ; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge ;


    public Tour( double valX, double valY, TowerType type, int niveau) {
        super(valX, valY);
        this.type = type;
        this.niveau = niveau;
        this.limites = new int[4];
        this.xCarte = (int) (valX / 16);
        this.yCarte = (int) (valY / 16);
        this.vie = new SimpleIntegerProperty(10);
    }


    public List<Ennemi> getEnnemisDansPortee(BooleanProperty aProximiteTour,  ObservableList<Ennemi> ennemis ) {

        List<Ennemi> temp = new ArrayList<>();
        for (Ennemi e : ennemis) {
            if (this.estDansPorteeTour(e)) {
                aProximiteTour.setValue(true);
                temp.add(e);
            }
            else{
                aProximiteTour.setValue(false);
            }
        }
        return temp;
    }
    public boolean estDansLimites(int x, int y) {
        return x > limites[0] && x < (limites[0] + limites[2]) && y > limites[1] && y < (limites[1] + limites[3]);
    }
    @Override
    public void attaquerActeur(Acteur ennemi) {
        if (this.tempsRecharge == 0) {
            // Inflige des dommages à l'ennemi
            ennemi.infligerDegats(degatsT);
            tempsRecharge = TAUX_TIR;
            this.playAttackSound();
        }
        if (tempsRecharge > 0)
            tempsRecharge--;
    }
    public boolean estDansPorteeTour(Acteur acteur) {
        double distance = this.calculerDistance(acteur.getY(), acteur.getX());
        return distance <= portee;
    }
    public void playAttackSound() {
        Sound s = new Sound(Main.class.getResource("sons/bruit.wav"));
        s.start();
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
