package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;


public class Mur extends Objet {
    public Mur(Environnement environnement, Terrain terrain) {
        super(environnement, terrain);
        prix = 50; // prix de l'objet
        setComportement(new ComportementMur());
    }

    public void PlacerMur(int i, int j) {
        /* Placement du chemin apres destruction du mur */
        terrain.placementObjetMur(i, j);
    }


}
