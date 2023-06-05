package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;

import java.util.LinkedList;

public abstract class Ennemi {


    // Propriétés pour les coordonnées x et y
    private DoubleProperty x;
    private DoubleProperty y;

    // Objet Terrain pour le déplacement
    private Terrain terr;

    // Indice de la cellule actuelle dans le chemin
    int i = 0;

    // Points de vie de l'ennemi
    private int pv;

    // Identifiant de l'ennemi
    private String id;

    // Compteur pour générer l'identifiant
    public static int compteur = 0;

    // Santé de l'ennemi
    private int health;
    private ImageView view = null;

    // Dommages et portée de l'ennemi
    private int dgts;
    private int portee;

    public Ennemi(double valX, double valY,int dgts){
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

        public void move (Cellule cellule){
            if (cellule != null) {
            }
        }

        public void subirDegats ( int damage){
            health -= damage;
        }

        public boolean estVivant () {
            return health > 0;
        }



    public void agit(double tileWidth, double tileHeight) {
        int[] start = {1, 0};
        int[] end = {12, 60};

        BFS bfs = new BFS();
        LinkedList<Cellule> shortestPath = bfs.plusCourtChemin(terr.getTerrain(), start, end);

        Cellule currentCellule = shortestPath.get(i);
        Cellule previousCellule = i > 0 ? shortestPath.get(i - 1) : null;
        if (previousCellule != null) {
            // Vérifie si la position x de la cellule courante est différente de la position x de la cellule précédente
            if (currentCellule.getX() != previousCellule.getX()) {
                // Si la position x de la cellule courante est supérieure à la position x de la cellule précédente,
                // déplace l'ennemi vers le bas (augmentation de la coordonnée y)
                if (currentCellule.getX() > previousCellule.getX()) {
                    this.setY(this.getY() + tileWidth);
                }
                // Si la position x de la cellule courante est inférieure à la position x de la cellule précédente,
                // déplace l'ennemi vers le haut (diminution de la coordonnée y)
                else if (currentCellule.getX() < previousCellule.getX()) {
                    this.setY(this.getY() - tileWidth);
                }
            }
            // Vérifie si la position y de la cellule courante est différente de la position y de la cellule précédente
            if (currentCellule.getY() != previousCellule.getY()) {
                // Si la position y de la cellule courante est supérieure à la position y de la cellule précédente,
                // déplace l'ennemi vers la droite (augmentation de la coordonnée x)
                if (currentCellule.getY() > previousCellule.getY()) {
                    this.setX(this.getX() + tileWidth);
                }
                // Si la position y de la cellule courante est inférieure à la position y de la cellule précédente,
                // déplace l'ennemi vers la gauche (diminution de la coordonnée x)
                else if (currentCellule.getY() < previousCellule.getY()) {
                    this.setX(this.getX() - tileWidth);
                }
            }
            // Vérifie si l'ennemi est arrivé à la position finale (cellule de coordonnées [12, 60])

        }

        this.move(currentCellule); // Appelle la méthode de déplacement de l'ennemi
        i++;
        this.toString(); // Appelle la méthode toString() pour afficher l'identifiant de l'ennemi
    }

    public void attaquerTour (Tour tour) {
        if (estAPortee(tour)) {
            // Inflige des dommages à la tour
            tour.subirDegats(dgts);
        }
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


        // Méthode permettant de vérifier si la tour spécifiée est dans la portée de la Balliste.
    public boolean estAPortee(Tour tower) {
        // Vérifie si l'ennemi est dans la portée de tir
        double distance = calculerDistance(tower.getX(), tower.getY());
        return distance <= portee;
    }
    // Méthode permettant de calculer la distance entre la Balliste et les coordonnées spécifiées.
    public double calculerDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
    }


