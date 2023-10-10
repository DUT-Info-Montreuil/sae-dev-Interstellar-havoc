package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.données.PixelMoveTimeEvent;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.SimpleIntegerProperty;

public class Scavenger extends Ennemi {

    public Scavenger(int x, int y, Terrain terrain){
        super(x, y, terrain);
        this.i = 0;
        this.degats= 10; // Portée de la tour
        this.portee=40; // Dommages infligés aux tours
        this.vie =  new SimpleIntegerProperty(50);
    }
    @Override
    public void attaque() {

    }

}
