
package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.données.PixelMoveTimeEvent;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.SimpleIntegerProperty;

public class Behemoth extends Ennemi {
    public Behemoth(int x, int y, Terrain terrain){
        super(x, y, terrain);
        this.i = 0;
        this.degats= 20; // Portée de la tour
        this.portee=50; // Dommages infligés aux tours
        this.vie =  new SimpleIntegerProperty(100);
    }


    @Override
    public void attaque() {
        if(terrain.getCase1(celluleSuivante.getI(),celluleSuivante.getJ())==1) {
            int diffX = celluleCourante.getI() - celluleSuivante.getI();
            int diffY = celluleCourante.getJ() - celluleSuivante.getJ();
            PixelMoveTimeEvent.initAnimation(this,diffX,diffY);
        }
        else if(terrain.getCase1(celluleSuivante.getI(),celluleSuivante.getJ())==2) {
            System.out.println("nononoooo");
        }
    }
}