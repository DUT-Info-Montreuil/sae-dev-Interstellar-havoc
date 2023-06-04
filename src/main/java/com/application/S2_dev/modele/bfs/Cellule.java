package com.application.S2_dev.modele.bfs;

public class Cellule {

    int x;
    int y;
    int distance;  	// Distance
    Cellule precedente;  // Cellule parente dans le chemin

    Cellule(int x, int y, int distance, Cellule precedente) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.precedente = precedente;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
