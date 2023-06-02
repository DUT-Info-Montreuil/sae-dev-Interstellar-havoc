package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import java.util.UUID;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

public abstract class Tour {
    
    private String id;
    private DoubleProperty x;
    private DoubleProperty y;
    private TowerType type;
    private int health;
    public ImageView view = null;

    public Tour(double x, double y, TowerType type) {
        this.id = UUID.randomUUID().toString();
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.type = type;
        this.health = 100;
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
    
    public double getY() {
        return y.get();
    }
    
    public TowerType getType() {
        return type;
    }
    
    public abstract void attack(Ennemi e);
    
    public void damage(int value) {
        health -= value;
    }
    
    public boolean isDestroyed() {
        return health <= 0;
    }
    
    public void setView(ImageView view) {
        this.view = view;
    }
    
    public ImageView getView() {
        return view;
    }
}