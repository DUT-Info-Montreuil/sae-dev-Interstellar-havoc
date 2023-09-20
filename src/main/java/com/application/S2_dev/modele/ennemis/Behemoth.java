
package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.SimpleIntegerProperty;

public class Behemoth extends Ennemi {
    public Behemoth(int x, int y, Terrain terrain){
        super(x, y, terrain);
        this.degats= 20; // Portée de la tour
        this.portee=50; // Dommages infligés aux tours
        this.vie =  new SimpleIntegerProperty(100);
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
        double distance = this.calculerDistance(tour.getY(), tour.getX());

        return distance <= portee;
    }

}