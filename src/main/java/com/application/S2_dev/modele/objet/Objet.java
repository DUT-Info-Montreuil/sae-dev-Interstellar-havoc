package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Objet {
    protected Comportement comportement;
    protected static Environnement environnement;
    protected static Terrain terrain;
    private final DoubleProperty x;
    private final DoubleProperty y;
    public int pv;
    private final String id;
    public static int compteur = 0;
    public static int prix;
    protected static int PORTE=0;
    public Objet(Environnement environnement, Terrain terrain) {

        Objet.environnement = environnement;
        Objet.terrain = terrain;
        this.x = new SimpleDoubleProperty();
        this.y = new SimpleDoubleProperty();
        this.id = "Ob" + compteur;
        compteur++;
        this.pv = 20;

    }

    public int getPv() {
        return pv;
    }

    public String getId() {
        return id;
    }

    public double getX() {
        return x.getValue();
    }

    public double getY() {
        return y.getValue();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public boolean estVivant() {
        return pv > 0;
    }

    public void agit(){if(comportement!=null)comportement.agit(this);}

    public void degat(int value){if(comportement!=null)comportement.degats(value,this);}

    public int getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return "id " + id;
    }
    public void setComportement(Comportement c) {
        comportement = c;
    }

    public Comportement getComportement() {
        return comportement;
    }
}
