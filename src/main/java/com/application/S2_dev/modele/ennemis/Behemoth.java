package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

// La classe Behemoth hérite de la classe Ennemi.
public class Behemoth extends Ennemi {

    private static final int RANGE = 100; // Portée de la tour
    private static final int DAMAGE = 25; // Dommages infligés aux tours

    // Constructeur de la classe Behemoth.
    public Behemoth(int x, int y) {
        super(x, y,100);
    }



    // Méthode d'attaque du Behemoth sur une tour spécifiée.
    @Override
    public void attaquerTour(Tour tour) {
        if (estAPortee(tour)) {
            // Inflige des dommages à la tour
            tour.subirDegats(DAMAGE);
        }
    }

}