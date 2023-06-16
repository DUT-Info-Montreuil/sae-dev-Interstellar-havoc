package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class Hydrogene extends Objet {

    public Hydrogene(Environnement environnement, Terrain terrain) {

        super(environnement, terrain);
        this.prix = 300; // prix de l'objet
    }
    @Override
    public void degat(int value) {
        /* Cet objet n'est pas endommageable */
    }
    @Override
    public void agit() {
        for (int i = 0; i < environnement.getEnnemis().size(); i++) {
                environnement.getEnnemis().get(i).meur(); // mort de tous les ennemis
        }
        for(int i = 0; i<environnement.getTour().size(); i++){
            environnement.getTour().get(i).meur(); // mort de toites les tourelles
        }
        this.pv = 0;
    }
}
