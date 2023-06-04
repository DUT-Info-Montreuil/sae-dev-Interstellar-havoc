package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import java.util.UUID;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

public abstract class Tour {
    
    private String identifiant;
    private DoubleProperty x;
    private DoubleProperty y;
    private TowerType type;
    private int pointDeVie;
    public ImageView vue = null;

    public Tour(double x, double y, TowerType type) {
        this.identifiant = UUID.randomUUID().toString();
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.type = type;
        this.pointDeVie = 100;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public DoubleProperty getXProprieteX() {
        return x;
    }

    public DoubleProperty getXProprieteY() {
        return y;
    }
    
    public double getX() {
        return x.get();
    }
    
    public double getY() {
        return y.get();
    }
    
    public TowerType getType() {
        return type;
    }
    
    public abstract void attaquer(Ennemi e);
    
    public void subirDegats(int valeur) {
        pointDeVie -= valeur;
    }
    
    public boolean  estDetruite() {
        return pointDeVie <= 0;
    }
    
    public void setVue(ImageView vue) {
        this.vue = vue;
    }
    
    public ImageView getVue() {
        return vue;
    }
}