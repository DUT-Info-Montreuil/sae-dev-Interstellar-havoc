package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class CheminBloque extends Objet {
    private final int ligne;
    private final int colonne;

    public CheminBloque(Environnement environnement, Terrain terrain, int ligne, int colonne) {
        super(environnement, terrain);
        prix = 50;
        this.ligne = ligne;
        this.colonne = colonne;
    }



    public void PlacerMur(int i, int j) {
        /* Placement du chemin apres destruction du mur */
        terrain.placementMur(i, j);
    }


    /* les getter et setter */
    public int getLigne() {
        return ligne;
    }

    public int getColonne() {
        return colonne;
    }

    @Override
    public void agit() {

    }
}
