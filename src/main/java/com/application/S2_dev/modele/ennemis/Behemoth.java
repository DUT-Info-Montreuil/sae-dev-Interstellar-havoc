package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.SimpleIntegerProperty;

public class Behemoth extends Ennemi {
    public Behemoth(int x, int y, Terrain terrain) {
        super(x, y, terrain);
        this.degats = 20; // Portée de la tour
        this.portee = 50; // Dommages infligés aux tours
        this.vie = new SimpleIntegerProperty(100);
    }



}