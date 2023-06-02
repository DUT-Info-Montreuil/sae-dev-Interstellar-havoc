
package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

public class Behemoth extends Ennemi {
    
    private static final int RANGE = 100; // Range of the tower
    private static final int DAMAGE = 25; // Damage inflicted on towers
    
    public Behemoth(int x, int y){
        super(x, y);
    }
    
    @Override
    public void attack(Tour tour) {
        if (isInRange(tour)) {
            // Inflict damage on the enemy
            tour.damage(DAMAGE);
        }
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