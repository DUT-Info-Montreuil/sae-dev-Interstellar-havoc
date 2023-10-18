package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class Hydrogene extends Objet {

    public Hydrogene(Environnement environnement, Terrain terrain) {

        super(environnement, terrain);
        prix = 300; // prix de l'objet
        setComportement(new ComportementHydrogène());
    }

}
