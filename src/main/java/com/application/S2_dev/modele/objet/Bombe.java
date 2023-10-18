package com.application.S2_dev.modele.objet;


import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class Bombe extends Objet {


    public Bombe(Environnement environnement, Terrain terrain) {

        super(environnement, terrain);
        prix = 50; // prix de l'objet
        setComportement(new ComportementBombe());
    }
}



