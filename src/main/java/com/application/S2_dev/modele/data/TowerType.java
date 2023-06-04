package com.application.S2_dev.modele.data;

// L'énumération TowerType représente les différents types de tours possibles.
public enum TowerType {
    Nikola(2), // Type de tour : Nikola (valeur : 2)
    Edison(0), // Type de tour : Edison (valeur : 0)
    Oppenheimer(1); // Type de tour : Oppenheimer (valeur : 1)

    private int valeur; // Valeur associée à chaque type de tour

    // Constructeur de l'énumération avec un paramètre de valeur
    private TowerType(int valeur) {
        this.valeur = valeur;
    }

    // Méthode permettant d'obtenir la valeur associée à un type de tour
    public int getValeur() {
        return valeur;
    }
}
