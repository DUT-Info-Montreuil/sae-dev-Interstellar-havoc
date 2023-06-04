
package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;

public class NikolaCoil extends Tour {
    
    private static final int portee = 50; // Portée de la tour
    private static final int degats = 10; // Dégâts infligés aux ennemis
    private static final int cadence_de_tir = 2; // Cadence de tir de la tour (coups par seconde)

    public NikolaCoil(int x, int y) {
        super(x, y, TowerType.Nikola);
    }
    
    @Override
    public void attaquer(Ennemi ennemi) {
        if (estDansPortee(ennemi)) {
            // Infliger des dégâts à l'ennemi
            ennemi.subirDegats(degats);
        }
    }

    private boolean estDansPortee(Ennemi ennemi) {
        // Vérifier si l'ennemi est dans la portée de la tour
        double distance = calculerDistance(ennemi.getX(), ennemi.getY());
        return distance <= portee;
    }

    private double calculerDistance(double x, double y) {
        // Calculer la distance entre la tour et l'ennemi
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
}
