package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
public class Mur extends Objet{
    public Mur(Environnement environnement,Terrain terrain){
        super(environnement, terrain);
        this.prix = 50;

    }
    public void degat(int value) {
        this.pv -= value;
    }
    public void PlacerMur(int i , int j ){
        this.terrain.placementMur(i,j);
    }
    @Override
    public void agit() {
    }
}
