package com.application.S2_dev.modele.acteurs.ennemis;

import com.application.S2_dev.modele.map.Terrain;
import javafx.beans.property.SimpleIntegerProperty;

public class Scavenger extends Ennemi {

    public Scavenger(int x, int y, Terrain terrain){
        super(x, y, terrain,true);
        this.degats= 10; // Portée de la tour
        this.portee=40; // Dommages infligés aux tours
        this.vie =  new SimpleIntegerProperty(100);
    }


}
