package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cell;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.LinkedList;
import javafx.scene.image.ImageView;

public abstract class Ennemi {
    private DoubleProperty x;
    private DoubleProperty y;
    private Terrain terr;
    int i = 0;
    private String id;
    public static int compteur = 0;
    private int health;
    private ImageView view = null;
    private BFS bfs;
    private LinkedList<Cell> shortestPath;
    
    public Ennemi(double valX, double valY) {
        x = new SimpleDoubleProperty(valX);
        y = new SimpleDoubleProperty(valY);
        terr = new Terrain();
        this.id="E"+compteur;
        compteur++;
        this.health = 100;
        int[] start = {1, 0};
        int[] end = {27, 29};
        bfs = new BFS();
        shortestPath = bfs.shortestPath(terr.getTerrain(), start, end);
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
        return x.getValue();
    }

    public double getY() {
        return y.getValue();
    }

    public void move(Cell cell) {
        if (cell != null) {
           // System.out.println("(X: " + x.getValue() + ", Y: " + y.getValue() + ")");
        }
    }
    
    public void takeDamage(int damage) {
        health -= damage;
    }
    
    public boolean estVivant() {
        return health > 0;
    }
    
    public abstract void attack(Tour tour);

    public void agit(double tileWidth, double tileHeight){
        
        if (i >= shortestPath.size())
            return;
        
        Cell currentCell = null;
        Cell previousCell = null;
        
        try {
            currentCell = shortestPath.get(i);
            previousCell = i > 0 ? shortestPath.get(i - 1) : null;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        
        if (previousCell != null) {
            if (currentCell.getX() != previousCell.getX()) {
                if (currentCell.getX() > previousCell.getX()) {
                    this.setY(this.getY() + tileWidth);
                }else if (currentCell.getX() < previousCell.getX()) {
                   this.setY(this.getY() - tileWidth);
                }
            }
            if (currentCell.getY() != previousCell.getY()) {
                if (currentCell.getY() > previousCell.getY()) {
                    this.setX(this.getX() + tileWidth);
                }else if (currentCell.getY() < previousCell.getY()) {
                    this.setX(this.getX() - tileWidth);
                }
            }
        }
        
        this.move(currentCell);
        i++;
        this.toString();

    }

    public void setX(double x1) {
        this.x.setValue(x1);
    }

    public void setY(double y1) {
        this.y.setValue(y1);
    }

    @Override
    public String toString() {
        return "id "+id;
    }
    
    public void setView(ImageView view) {
        this.view = view;
    }
    
    public ImageView getView() {
        return view;
    }
    
    public boolean finalDestReached() {
        return i >= this.shortestPath.size();
    }
}

