package com.application.S2_dev.modele.bfs;

public class Cellule {

    int i, j;
    int distance;    // distance
    Cellule precedente;  // cellule parent dans le chemin

    public Cellule(int i, int j, int distance, Cellule precedente) {
        this.i = i;
        this.j = j;
        this.distance = distance;
        this.precedente = precedente;
    }

    @Override
    public String toString() {
        return "(" + i + "," + j + ")";
    }

    /**
     * Retourne la coordonnée i de la cellule.
     *
     * @return la coordonnée i
     */
    public int getI() {
        return i;
    }

    /**
     * Retourne la coordonnée j de la cellule.
     *
     * @return la coordonnée j
     */
    public int getJ() {
        return j;
    }
}
