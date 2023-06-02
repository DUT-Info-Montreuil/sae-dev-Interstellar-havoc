package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cell;
import com.application.S2_dev.modele.map.Terrain;
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
    private int pv;
    private String id;
    public static int compteur = 0;
    private int health;
    private ImageView view = null;
    private int dgts;
    private int portee;

    public Ennemi(double valX, double valY){
            this.dgts = dgts;
            this.portee = portee;
        x = new SimpleDoubleProperty(valX);
        y = new SimpleDoubleProperty(valY);
            terr = new Terrain();//a voir
            this.pv = pv;
            this.id = "E" + compteur;
            compteur++;
            this.health = 100;
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

        BFS bfs = new BFS();
        LinkedList<Cell> shortestPath = bfs.shortestPath(terr.getTerrain(), start, end);

        Cell currentCell = shortestPath.get(i);
        Cell previousCell = i > 0 ? shortestPath.get(i - 1) : null;
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
       public void attaquerTour () {
            System.out.println("Je vais attaquer");
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
    }


