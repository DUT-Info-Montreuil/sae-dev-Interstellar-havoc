package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;

// La classe Balliste hérite de la classe Ennemi.
public class Balliste extends Ennemi {

    // Constructeur de la classe Balliste.
    public Balliste(int Posx, int Posy, Terrain terrain){
        super(Posx, Posy, terrain);
        this.degats= 5; // Portée de la tour
        this.portee=30; // Dommages infligés aux tours
        this.vie = 20;
    }
    @Override
    public void attaquerTour(Tour tour) {
        System.out.println("tour !!!!!!" + tour.getX());
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