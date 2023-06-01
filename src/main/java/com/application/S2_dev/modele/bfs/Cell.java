package com.application.S2_dev.modele.bfs;

// Classe représentant une cellule dans la matrice
public class Cell {
    int x; 			// Coordonnée x de la cellule
    int y; 			// Coordonnée y de la cellule
    int dist;  		// Distance de la cellule par rapport au point de départ
    Cell prev;  	// Cellule parente dans le chemin

    // Constructeur de la classe Cell
    Cell(int x, int y, int dist, Cell prev) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.prev = prev;
    }

    // Méthode toString pour afficher les coordonnées de la cellule
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    // Getter pour obtenir la coordonnée x de la cellule
    public int getX() {
        return x;
    }

    // Getter pour obtenir la coordonnée y de la cellule
    public int getY() {
        return y;
    }
}
