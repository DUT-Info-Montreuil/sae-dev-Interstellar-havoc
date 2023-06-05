package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.tours.Tour;

public class Scavenger extends Ennemi {

    private static final int Portee = 50; // Portée de la tour
    private static final int Degats = 5; // Dommages infligés aux tours

    public Scavenger(int x, int y) {
        super(x, y,70);
    }
    @Override
    public void attaquerTour(Tour tour) {
        if (estAPortee(tour)) {
            // Inflige des dommages à la tour
            tour.subirDegats(Degats);
        }
    }

}
