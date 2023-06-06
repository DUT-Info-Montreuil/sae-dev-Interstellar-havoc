package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cell;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

import java.util.LinkedList;

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
    private static final int RANGE = 20;
    private static final int DAMAGE = 25;
    private LinkedList<Cell> shortestPath;

    public Ennemi(double valX, double valY){
        this.x = new SimpleDoubleProperty(valX);
        this.y = new SimpleDoubleProperty(valY);
        terr = new Terrain();//a voir
        this.id = "E" + compteur;
        compteur++;
        this.health = 100;
        int[] start = {1, 0};
        int[] end = {27, 29};
        bfs = new BFS();
        shortestPath = bfs.shortestPath(terr.getTerrain(), start, end);
    }

        public String getId () {
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

        public void move (Cell cell){
            if (cell != null) {
                // System.out.println("(X: " + x.getValue() + ", Y: " + y.getValue() + ")");
            }
        }

        public void takeDamage ( int damage){
            health -= damage;
        }

        public boolean estVivant () {
            return health > 0;
        }

        public abstract void attack (Tour tour);

    public void agit(double tileWidth, double tileHeight){
        int[] start = {1, 0};
        int[] end = {12, 60};

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
                attaquerTour();

            }
        }
        this.move(currentCell);
        i++;
        this.toString();



    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void attaquerTour () {
            System.out.println("Je vais attaquer");
        }

        public void attaquerObjet(Objet objet) {
        if(objet instanceof Mur) if (isInRange(objet)) {
            Mur.takeDamage(DAMAGE);
        }
    }

    private boolean isInRange(Objet objet) {
        // Check if the enemy is within the firing range
        double distance = calculateDistance(objet.getX(), objet.getY());
        return distance <= RANGE;
    }
    private double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }

        public void setX ( double x1){
            this.x.setValue(x1);
        }

        public void setY ( double y1){
            this.y.setValue(y1);
        }

        @Override
        public String toString () {
            return "id " + id;
        }

        public void setView (ImageView view){
            this.view = view;
        }

        public ImageView getView() {
            return view;
        }
    public boolean finalDestReached() {
        return i >= this.shortestPath.size();
    }
    }


