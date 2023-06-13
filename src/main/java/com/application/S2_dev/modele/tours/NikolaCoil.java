
package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;

public class NikolaCoil extends Tour {

    private int RANGE = 50; // Range of the tower
    private int DAMAGE = 10; // Damage inflicted on enemies
    private int FIRE_RATE = 2; // Firing rate of the tower (shots per second)
    private int cooldownTime = 0;

    public NikolaCoil(int x, int y, int level) {
        super(x, y, TowerType.Nikola, level,500, 500*level);
        this.FIRE_RATE = 2 - level;
        this.DAMAGE = 10 + (level*3);
        this.RANGE = 50 + (level*5);

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

    @Override
    public boolean estEndommager(){
        return this.getHealth()<200;
    }
    @Override
    public void setHealth(){this.health=200;}

    private boolean isInRange(Ennemi ennemi) {
        // Check if the enemy is within the firing range
        double distance = calculateDistance(ennemi.getX(), ennemi.getY());
        return distance <= RANGE;
    }

    private double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
}
