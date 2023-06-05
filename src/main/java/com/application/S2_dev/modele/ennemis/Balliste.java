package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

// La classe Balliste hérite de la classe Ennemi.
public class Balliste extends Ennemi {

    private static final int portee = 150; // Portée de la tour
    private static final int degats = 5; // Dommages infligés aux tours

    // Constructeur de la classe Balliste.
    public Balliste(int x, int y){
        super(x, y,50);
    }

    // Méthode d'attaque de la Balliste sur une tour spécifiée.
    @Override
    public void attaquerTour(Tour tour) {
        if (estAPortee(tour)) {
            // Inflige des dommages à la tour
            tour.subirDegats(degats);
        }
    }






}
