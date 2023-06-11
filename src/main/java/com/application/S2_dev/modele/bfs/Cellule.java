package com.application.S2_dev.modele.bfs;
public class Cellule {

    int x;
    int y;
    int distance;  	// distance
    Cellule precedente;  // cellule parent dans le chemin

    public Cellule(int x, int y, int distance, Cellule precedente) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.precedente = precedente;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    /**
     * Retourne la coordonnée X de la cellule.
     *
     * @return la coordonnée X
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la coordonnée Y de la cellule.
     *
     * @return la coordonnée Y
     */
    public int getY() {
        return y;
    }
}
