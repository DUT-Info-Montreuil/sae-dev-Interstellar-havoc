package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class Hydrogene extends Objet {

    private int PORTE = 1050;

    public Hydrogene(Environnement environnement, Terrain terrain) {

        super(environnement, terrain);
        this.prix = 200;
    }

    @Override
    public void agit() {
        for (int i = 0; i < environnement.getEnnemis().size(); i++) {
                environnement.getEnnemis().get(i).meur();

        }
        for(int i = 0; i<environnement.getTour().size(); i++){
            environnement.getTour().get(i).meur();
        }
        this.pv = 0;
    }


}
