
package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.Cell;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;

import java.util.LinkedList;

public class Behemoth extends Ennemi {
    private static final int RANGE = 100; // Range of the tower
    private static final int DAMAGE = 25; // Damage inflicted on towers
    
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

    private boolean isInRange(Tour tower) {
        // Check if the enemy is within the firing range
        double distance = calculateDistance(tower.getX(), tower.getY());
        return distance <= RANGE;
    }

    private double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
}