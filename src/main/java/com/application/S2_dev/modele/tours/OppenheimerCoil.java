
package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;

public class OppenheimerCoil extends Tour {
    
    private int RANGE = 150; // Range of the tower
    private int DAMAGE = 25; // Damage inflicted on enemies
    private int FIRE_RATE = 10; // Firing rate of the tower (shots per second)
    private int cooldownTime = 0;

    public OppenheimerCoil(int x, int y, int level) {
        super(x, y, TowerType.Oppenheimer, level, 100*level);
        this.FIRE_RATE = 10 - level;
        this.DAMAGE = 25 + (level*3);
        this.RANGE = 150 + (level*5);
        
        if (FIRE_RATE < 1)
            FIRE_RATE = 1;
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
