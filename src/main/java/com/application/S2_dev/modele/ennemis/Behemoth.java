package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

// La classe Behemoth hérite de la classe Ennemi.
public class Behemoth extends Ennemi {

    private static final int RANGE = 100; // Portée de la tour
    private static final int DAMAGE = 25; // Dommages infligés aux tours

    // Constructeur de la classe Behemoth.
    public Behemoth(int x, int y){
        super(x, y);
    }

    // Méthode d'attaque du Behemoth sur une tour spécifiée.
    @Override
    public void attack(Tour tour) {
        if (isInRange(tour)) {
            // Inflige des dommages à la tour
            tour.subirDegats(DAMAGE);
        }
    }

    // Méthode permettant de vérifier si la tour spécifiée est dans la portée du Behemoth.
    private boolean isInRange(Tour tower) {
        // Vérifie si l'ennemi est dans la portée de tir
        double distance = calculateDistance(tower.getX(), tower.getY());
        return distance <= RANGE;
    }

    // Méthode permettant de calculer la distance entre le Behemoth et les coordonnées spécifiées.
    private double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
}
