
package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;

public class NikolaCoil extends Tour {
    
    private static final int RANGE = 50; // Range of the tower
    private static final int DAMAGE = 10; // Damage inflicted on enemies
    private static final int FIRE_RATE = 2; // Firing rate of the tower (shots per second)

    public NikolaCoil(int x, int y) {
        super(x, y, TowerType.Nikola);
    }
    
    @Override
    public void attack(Ennemi ennemi) {
        if (isInRange(ennemi)) {
            // Inflict damage on the enemy
            ennemi.takeDamage(DAMAGE);
        }
    }

    private boolean isInRange(Ennemi ennemi) {
        // Check if the enemy is within the firing range
        double distance = calculateDistance(ennemi.getX(), ennemi.getY());
        return distance <= RANGE;
    }

    private double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
}
