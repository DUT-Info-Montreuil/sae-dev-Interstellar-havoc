
package com.application.S2_dev.modele.acteurs.ennemis;

import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.données.PixelMoveTimeEvent;
import com.application.S2_dev.modele.map.Terrain;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.LinkedList;

public class Behemoth extends Ennemi {
    public Behemoth(int x, int y, Terrain terrain){
        super(x, y, terrain,false);
        this.degats= 20; // Portée de la tour
        this.portee=50; // Dommages infligés aux tours
        this.vie =  new SimpleIntegerProperty(150);
    }
}