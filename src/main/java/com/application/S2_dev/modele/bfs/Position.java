package com.application.S2_dev.modele.bfs;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Object o) {
        // Vérifie si les deux objets sont la même instance dans la mémoire
        if (this == o)
            return true;

        // Vérifie si l'objet 'o' est une instance de la classe Position
        if (!(o instanceof Position))
            return false;

        // Convertit 'o' en une variable de type Position pour accéder à ses attributs
        Position position = (Position) o;

        // Compare les attributs 'x' et 'y' de l'objet courant avec ceux de 'position'
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}

