package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

public abstract class Tour {
    private String id;
    private DoubleProperty x;
    private DoubleProperty y;
    private TowerType type;
    int health;
    public ImageView view = null;
    private int level;
    private int price;
    private int[] bounds;
    private int mapX, mapY;
    public static int compteur = 0;

    public Tour(double x, double y, TowerType type, int level,int pv, int price) {
        this.id = "T" + compteur;
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.type = type;
        this.health = pv;
        this.level = level;
        this.price = price;
        this.bounds = new int[4];
        this.mapX = (int)(x/16);
        this.mapY = (int)(y/16);
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
    public void meur(){
        this.health = 0;
    }

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

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel () {
        return level;
    }

    public int getPrice() {
        return price;
    }
    public int getHealth(){return health;}


    public void setPrice(int price) {
        this.price = price;
    }

    public void setBounds(int x, int y, int width, int height) {
        bounds[0] = x;
        bounds[1] = y;
        bounds[2] = width;
        bounds[3] = height;
    }

    public boolean isInBounds(int x, int y) {
        return x > bounds[0] && x < (bounds[0] + bounds[2]) && y > bounds[1] && y < (bounds[1]+bounds[3]);
    }

    public boolean matchIndex(int row, int col) {
        return (col*16) == getX() && (row*16) == getY();
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int mapX) {
        this.mapX = mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int mapY) {
        this.mapY = mapY;
    }
    public void setHealth(){this.health=100;}
    @Override
    public String toString () {
        return "id " + id;
    }


    public boolean estEndommager() {
        return this.health<100;
    }
}