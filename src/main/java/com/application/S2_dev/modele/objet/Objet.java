package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Objet {
    Environnement environnement;
    public static Terrain terrain;
    private DoubleProperty x, y;
    public int pv;
    private String id;
    public static int compteur = 0;
    public static int prix;

    public Objet(Environnement environnement, Terrain terrain){
        this.environnement = environnement;
        this.terrain = terrain;
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

    public double getX () {
        return x.getValue();
    }

    public double getY () {
        return y.getValue();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public boolean estVivant () {
        return pv > 0;
    }
    public abstract void agit();
    public int getPrix() {
        return prix;
    }
    @Override
    public String toString () {
        return "id " + id;
    }

}
