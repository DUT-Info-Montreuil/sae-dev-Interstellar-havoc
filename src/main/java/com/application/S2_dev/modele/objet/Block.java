package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class Block extends Objet{
    private int ligne;
    private int colonne;
    public Block(Environnement environnement, Terrain terrain, int ligne, int colonne){
        super(environnement, terrain);
        this.prix = 50;
        this.ligne = ligne;
        this.colonne = colonne;

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

    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }
}
