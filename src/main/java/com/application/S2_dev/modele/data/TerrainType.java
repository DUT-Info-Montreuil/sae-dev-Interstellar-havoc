package com.application.S2_dev.modele.data;

// L'énumération TerrainType représente les différents types de terrain possibles.
public enum TerrainType {
    herbe(0), // Type de terrain : Herbe (valeur : 0)
    chemin(1),  // Type de terrain : Chemin (valeur : 1)
    bloque(2), // Type de terrain : Bloqué (valeur : 2)
    Chauve_souris(3);  // Type de terrain : Chauve-souris (valeur : 3)

    private int valeur;  // Valeur associée à chaque type de terrain

    // Constructeur de l'énumération avec un paramètre de valeur
    private TerrainType(int valeur) {
        this.valeur = valeur;
    }

    // Méthode permettant d'obtenir la valeur associée à un type de terrain
    public int getValeur() {
        return valeur;
    }
}
