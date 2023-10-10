package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.données.PixelMoveTimeEvent;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.SimpleIntegerProperty;

// La classe Balliste hérite de la classe Ennemi.
public class Balliste extends Ennemi {

    // Constructeur de la classe Balliste.
    public Balliste(int Posx, int Posy, Terrain terrain) {
        super(Posx, Posy, terrain);
        this.i = 0;
        this.degats = 5; // Portée de la tour
        this.portee = 30; // Dommages infligés aux tours
        this.vie = new SimpleIntegerProperty(20);
    }
    @Override
    public void attaque() {
        if(terrain.getCase1(celluleSuivante.getI(),celluleSuivante.getJ())==1) {
            int diffX = celluleCourante.getI() - celluleSuivante.getI();
            int diffY = celluleCourante.getJ() - celluleSuivante.getJ();
            PixelMoveTimeEvent.initAnimation(this,diffX,diffY);
        }
        else if(terrain.getCase1(celluleSuivante.getI(),celluleSuivante.getJ())==2) {
            while(AttaquerObjet()){
                return;
            }
        }
    }

}