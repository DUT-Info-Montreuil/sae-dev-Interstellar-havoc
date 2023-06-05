package com.application.S2_dev.modele.objet;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class Objet {
    private DoubleProperty x;
    private DoubleProperty y;
    public int pv;
    private String id;
    public static int compteur = 0;

    public Objet(){
        this.x = new SimpleDoubleProperty();
        this.y = new SimpleDoubleProperty();
        this.id = "E" + compteur;
        compteur++;
    }

    public String getId() {
        return id;
    }

    public DoubleProperty getXProperty () {
        return x;
    }

    public DoubleProperty getYProperty () {
        return y;
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

    @Override
    public String toString () {
        return "id " + id;
    }


}
