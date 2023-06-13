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
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Ennemi {

    private DoubleProperty x; // Position X de l'ennemi
    private DoubleProperty y; // Position Y de l'ennemi
    private Terrain terrain; // Terrain sur lequel évolue l'ennemi
    private int i = 1; // Index de la cellule dans le chemin le plus court
    private String id; // Identifiant de l'ennemi
    public static int compteur = 0; // Compteur d'ennemis pour générer l'identifiant unique
    private int vie; // Points de vie de l'ennemi
    protected int degats;
    protected int portee;
    public static BFS bfs;
    private LinkedList<Cell> shortestPath;
    private boolean enCoursAttaque = false;

    public Ennemi(double valX, double valY, Terrain terr) {
        x = new SimpleDoubleProperty(valX);
        y = new SimpleDoubleProperty(valY);
        this.terrain = terr;
        this.vie = 100;
        this.id="E"+compteur;
        compteur++;
        int[] start = {1, 0};
        int[] end = {12, 60};
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

    public void subirDegats(int degats) {
        vie -= degats;
    }

    public boolean estVivant() {
        return vie > 0;
    }
    public abstract void attaquerTour(Tour tour);
    public abstract boolean estDansPortee(Tour tour);

    private boolean objetProximite(Objet objet) {
        double distance = calculaterDistance(objet.getX(), objet.getY());
        return distance <= 10;
    }
    private double calculaterDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
    public  void attaqueObjet(Objet objet){
        if(objetProximite(objet)) {
            System.out.println("je suis a coté");
            enCoursAttaque = true;
            ((Mur) objet).degat(1);
        }
        enCoursAttaque = false;
    }

    public boolean AttaquerObjet(){
        return !enCoursAttaque;
    }

    public void agit(double tileWidth, double tileHeight){

        LinkedList<Cell> cheminPlusCourt = terrain.getCheminPlusCourt();

        Cell currentCell = cheminPlusCourt.get(i);
        Cell previousCell = i > 0 ? cheminPlusCourt.get(i - 1) : null;
        if (terrain.getCase1(previousCell.getX(),previousCell.getY())==1) {
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
            }

            this.move(currentCell);
        }
        else if (terrain.getCase1(previousCell.getX(),previousCell.getY())==2){
            while(AttaquerObjet()){
                return;
            }
        }
        i++;
        this.toString();
        // System.out.println("pos x "+ (this.getX()-16) + " pos Y "+ this.getY());
    }

    public void move(Cell cell) {
        if (cell != null) {
            // System.out.println("(X: " + x.getValue() + ", Y: " + y.getValue() + ")");
        }
    }
    public void setX(double x1) {
        this.x.setValue(x1);
    }

    public void setY(double y1) {
        this.y.setValue(y1);
    }

    @Override
    public String toString() {
        return "id " + id;
    }

    public boolean finalDestReached() {
        return i >= this.shortestPath.size();
    }

    public void meur(){
        this.vie = 0;
    }

    protected double calculerDistance(double x, double y) {
        // Calculer la distance entre l'ennemi et une position donnée
        return Math.sqrt(Math.pow((x - getX()), 2) + Math.pow((y - getY()), 2));
    }

}

