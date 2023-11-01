package com.application.S2_dev.modele.acteurs.ennemis;

import com.application.S2_dev.modele.map.Terrain;
import javafx.beans.property.SimpleIntegerProperty;

public class Balliste extends Ennemi {

    public Balliste(int Posx, int Posy, Terrain terrain) {
        super(Posx, Posy, terrain);
        this.degats = 5; // Portée de la tour
        this.portee = 30; // Dommages infligés aux tours
        this.vie = new SimpleIntegerProperty(60);
    }

}