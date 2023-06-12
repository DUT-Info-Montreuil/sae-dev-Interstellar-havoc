package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.tours.Tour;

public class Maintenance {
    private Tour tour;

    public void reparer(Tour tour){
        if (this.tour.estEndommager()){
            this.tour.setHealth();
        }
    }
}
