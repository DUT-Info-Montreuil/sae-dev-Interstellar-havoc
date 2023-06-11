package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

// La classe Balliste hérite de la classe Ennemi.
public class Balliste extends Ennemi {

    private static final int portee = 150; // Portée de la tour
    private static final int degats = 5; // Dommages infligés aux tours

    // Constructeur de la classe Balliste.
    public Balliste(int x, int y){
        super(x, y);
    }

    @Override
    public void attaquerTour(Tour tour) {
        if (estDansPortee(tour)) {
            // Infliger des dégâts à la tour
            tour.infligerDegats(degats);
        }
    }

    private boolean estDansPortee(Tour tour) {
        // Vérifier si l'ennemi est à portée de tir
        double distance = calculerDistance(tour.getX(), tour.getY());
        return distance <= portee;
    }

    private double calculerDistance(double x, double y) {
        // Calculer la distance entre l'ennemi et une position donnée
        return Math.sqrt(Math.pow((x - getX()), 2) + Math.pow((y - getY()), 2));
    }
}
