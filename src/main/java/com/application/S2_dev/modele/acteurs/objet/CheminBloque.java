package com.application.S2_dev.modele.acteurs.objet;

import com.application.S2_dev.modele.designPattern.strategieObjet.ComportementMur;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class CheminBloque extends Objet {
    private final int ligne, colonne;

    public CheminBloque(Environnement environnement, Terrain terrain, int ligne, int colonne) {
        super(environnement, terrain);
        this.prix = 50;
        this.ligne = ligne;
        this.colonne = colonne;
        setComportement(new ComportementMur());
    }
    public void PlacerMur(int i, int j) {
        /* Placement du chemin apres destruction du mur */
        terrain.placementMur(i, j);
    }
    public int getLigne() {
        return ligne;
    }
    public int getColonne() {
        return colonne;
    }

}
