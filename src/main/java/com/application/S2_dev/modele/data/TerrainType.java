package com.application.S2_dev.modele.data;

// L'énumération TerrainType représente les différents types de terrain possibles.
public enum TerrainType {
    GRASS(0), // Type de terrain : Herbe (valeur : 0)
    PATH(1),  // Type de terrain : Chemin (valeur : 1)
    BLOCKED(2), // Type de terrain : Bloqué (valeur : 2)
    BATS(3);  // Type de terrain : Chauve-souris (valeur : 3)

    private int value;  // Valeur associée à chaque type de terrain

    // Constructeur de l'énumération avec un paramètre de valeur
    private TerrainType(int value) {
        this.value = value;
    }

    // Méthode permettant d'obtenir la valeur associée à un type de terrain
    public int getValue() {
        return value;
    }
}
