package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class Maintenance extends Objet{
    public Maintenance(Environnement environnement, Terrain terrain){
        super(environnement, terrain);
        this.prix = 300; // prix de l'objet

    }

    @Override
    public void agit() {
    }

    @Override
    public void degat(int value) {
    }
}
