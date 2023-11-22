package com.application.S2_dev.modele.acteurs.objet;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.designPattern.strategieObjet.*;

public class Bombe extends Objet {

    public Bombe(Environnement environnement, Terrain terrain) {
        super(environnement, terrain);
        this.prix = 50; // prix de l'objet
        this.setPortee(150);
        setComportement(new ComportementBombe());
    }
}



