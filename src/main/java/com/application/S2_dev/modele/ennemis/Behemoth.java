package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

public class Behemoth extends Ennemi {

    public Behemoth(int x, int y){
        super(x, y);
        this.portee=100; // Portée de la tour
        this.degats=10; // Dommages infligés aux tours
    }

    @Override
    public void attaquerTour(Tour tour) {
        if (estDansPortee(tour)) {
            // Infliger des dégâts à la tour
            tour.infligerDegats(degats);
        }
    }
    @Override
    public boolean estDansPortee(Tour tour) {
        // Vérifier si l'ennemi est à portée de tir
        double distance = calculerDistance(tour.getX(), tour.getY());
        return distance <= portee;
    }
}
