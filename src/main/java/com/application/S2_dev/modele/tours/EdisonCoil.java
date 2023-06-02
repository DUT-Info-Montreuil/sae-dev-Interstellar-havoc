
package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;

public class EdisonCoil extends Tour {
    private static final int RANGE = 50; // Range of the tower
    private static final int DAMAGE = 25; // Damage inflicted on enemies
    private static final int FIRE_RATE = 6; // Firing rate of the tower (shots per second)
    
    private int cooldownTime = 0;

    public EdisonCoil(int x, int y) {
        super(x, y, TowerType.Edison);
    }

    @Override
    public void attack(Ennemi ennemi) {
        if (isInRange(ennemi) && cooldownTime == 0) {
            // Inflict damage on the enemy
            ennemi.takeDamage(DAMAGE);
            cooldownTime = FIRE_RATE;
        }
        if (cooldownTime > 0)
            cooldownTime--;
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
