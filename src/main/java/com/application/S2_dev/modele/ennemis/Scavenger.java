package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

public class Scavenger extends Ennemi {

    private static final int RANGE = 50; // Portée de la tour
    private static final int DAMAGE = 5; // Dommages infligés aux tours

    public Scavenger(int x, int y) {
        super(x, y);
    }

    @Override
    public void attack(Tour tour) {
        if (isInRange(tour)) {
            // Inflige des dommages à la tour
            tour.subirDegats(DAMAGE);
        }
    }

    private boolean isInRange(Tour tower) {
        // Vérifie si l'ennemi est dans la portée de tir
        double distance = calculateDistance(tower.getX(), tower.getY());
        return distance <= RANGE;
    }

    private double calculateDistance(double x, double y) {
        // Calcule la distance entre l'ennemi et les coordonnées (x, y)
        return Math.sqrt(Math.pow((x - getX()), 2) + Math.pow((y - getY()), 2));
    }
}
