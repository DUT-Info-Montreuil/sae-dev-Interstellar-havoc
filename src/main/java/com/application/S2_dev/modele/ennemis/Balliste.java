package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

// La classe Balliste hérite de la classe Ennemi.
public class Balliste extends Ennemi {

    private static final int RANGE = 150; // Portée de la tour
    private static final int DAMAGE = 5; // Dommages infligés aux tours

    // Constructeur de la classe Balliste.
    public Balliste(int x, int y){
        super(x, y);
    }

    // Méthode d'attaque de la Balliste sur une tour spécifiée.
    @Override
    public void attack(Tour tour) {
        if (isInRange(tour)) {
            // Inflige des dommages à la tour
            tour.damage(DAMAGE);
        }
    }

    // Méthode permettant de vérifier si la tour spécifiée est dans la portée de la Balliste.
    private boolean isInRange(Tour tower) {
        // Vérifie si l'ennemi est dans la portée de tir
        double distance = calculateDistance(tower.getX(), tower.getY());
        return distance <= RANGE;
    }

    // Méthode permettant de calculer la distance entre la Balliste et les coordonnées spécifiées.
    private double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
}
