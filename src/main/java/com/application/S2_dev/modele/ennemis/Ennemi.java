package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cell;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.LinkedList;
import javafx.scene.image.ImageView;

public abstract class Ennemi {
    private DoubleProperty x;
    private DoubleProperty y;
    private Terrain terr;
    int i = 1;
    private String id;
    public static int compteur = 0;
    private int health;
    private ImageView view = null;
    public static BFS bfs;
    private LinkedList<Cell> shortestPath;
    private boolean enCoursAttaque = false;



    public Ennemi(double valX, double valY, Terrain terr) {
        x = new SimpleDoubleProperty(valX);
        y = new SimpleDoubleProperty(valY);
        this.terr = terr;
        this.id="E"+compteur;
        compteur++;
        this.health = 100;
        int[] start = {1, 0};
        int[] end = {12, 60};
        bfs = new BFS();
        shortestPath = bfs.shortestPath(terr.getTerrain(), start, end);
    }
    public void meur(){
        this.health = 0;
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
    public  void attaqueObjet(Objet objet){
        if(objetProximite(objet)) {
            System.out.println("je suis a coté");
            enCoursAttaque = true;
                ((Mur) objet).degat(1);

            enCoursAttaque = false;
        }
    }

    public boolean AttaquerObjet(){
        return !enCoursAttaque;
    }
    private double calculaterDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
    private boolean objetProximite(Objet objet) {
        double distance = calculaterDistance(objet.getX(), objet.getY());
        return distance <= 10;
    }

    public void agit(double tileWidth, double tileHeight){

        LinkedList<Cell> cheminPlusCourt = terr.getCheminPlusCourt();

        Cell currentCell = cheminPlusCourt.get(i);
        Cell previousCell = i > 0 ? cheminPlusCourt.get(i - 1) : null;
        if (terr.getCase1(previousCell.getX(),previousCell.getY())==1) {
            if (previousCell != null) {
                if (currentCell.getX() != previousCell.getX()) {

                    if (currentCell.getX() > previousCell.getX()) {
                        this.setY(this.getY() + tileWidth);
                    } else if (currentCell.getX() < previousCell.getX()) {
                        this.setY(this.getY() - tileWidth);
                    }
                }
                if (currentCell.getY() != previousCell.getY()) {
                    if (currentCell.getY() > previousCell.getY()) {
                        this.setX(this.getX() + tileWidth);
                    } else if (currentCell.getY() < previousCell.getY()) {
                        this.setX(this.getX() - tileWidth);
                    }
                }
                if (currentCell.getY() == 60 && currentCell.getX() == 12) {
                    attaquerTour();

                }
            }

        this.move(currentCell);
        }
        else{
               while(AttaquerObjet()){
                    return;
               }

        }
        i++;

        this.toString();
    }

    public void attaquerTour () {
        System.out.println("Je vais attaquer");
    }


    public void setX(double x1) {
        this.x.setValue(x1);
    }

    public void setY(double y1) {
        this.y.setValue(y1);
    }

    public boolean finalDestReached() {
        return i >= this.shortestPath.size();
    }

    @Override
    public String toString() {
        return "id "+id;
    }
}

