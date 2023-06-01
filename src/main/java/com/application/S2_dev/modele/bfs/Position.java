package com.application.S2_dev.modele.bfs;

import java.util.Objects;

// Classe représentant une position dans la matrice
public class Position {
    private int x; 	// Coordonnée x de la position
    private int y; 	// Coordonnée y de la position

    // Constructeur de la classe Position
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getter pour obtenir la coordonnée x de la position
    public int getX() {
        return x;
    }

    // Getter pour obtenir la coordonnée y de la position
    public int getY() {
        return y;
    }

    // Méthode equals pour comparer deux positions
    @Override
    public boolean equals(Object o) {
        // Vérifie si l'objet en paramètre est la même instance que l'objet actuel
        if (this == o)
            return true;
        // Vérifie si l'objet en paramètre appartient à la classe Position
        if (!(o instanceof Position))
            return false;
        // Cast de l'objet en paramètre en Position
        Position position = (Position) o;
        // Vérifie si les coordonnées x et y sont égales entre les deux positions
        return x == position.x && y == position.y;
    }

    // Méthode hashCode pour générer le code de hachage de la position
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
