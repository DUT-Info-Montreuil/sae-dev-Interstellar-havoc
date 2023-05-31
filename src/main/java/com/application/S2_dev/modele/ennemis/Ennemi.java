package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cell;
import com.application.S2_dev.modele.map.Terrain;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.LinkedList;

public abstract class Ennemi {


    private DoubleProperty x;
    private DoubleProperty y;
    private Terrain terr;
    int i = 0;
    int pv;
    private String id;
    public static int compteur = 0;
    public Ennemi() {
        x = new SimpleDoubleProperty(5);
        y = new SimpleDoubleProperty(21);
        terr = new Terrain();
        this.pv = 5;
        this.id="E"+compteur;
        compteur++;
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
    public boolean estVivant() {

        return this.pv>0;
    }

    public void agit(double tileWidth, double tileHeight){
        int[] start = {1, 0};
        int[] end = {12, 60};

        BFS bfs = new BFS();
        LinkedList<Cell> shortestPath = bfs.shortestPath(terr.getTerrain(), start, end);

        Cell currentCell = shortestPath.get(i);
        Cell previousCell = i > 0 ? shortestPath.get(i - 1) : null;
      //  System.out.println("take  (X: " + currentCell.getX() + ", Y: " + currentCell.getY() + ")");
        if (previousCell != null) {
            if (currentCell.getX() != previousCell.getX()) {

                if (currentCell.getX() > previousCell.getX()) {
                    this.setY( this.getY() + tileWidth);
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
            if (currentCell.getY()==60&&currentCell.getX()==12){
                System.out.println("JE vais attaquer");
            }
        }


        // System.out.println("to traslate (X: " + this.getX() + ", Y: " + this.getY() + ")");
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
}

