
package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;

public class Behemoth extends Ennemi {
    public Behemoth(int x, int y, Terrain terrain){
        super(x, y, terrain);
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