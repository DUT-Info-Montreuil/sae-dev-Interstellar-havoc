package com.application.S2_dev.modele.acteurs.objet;

import com.application.S2_dev.modele.designPattern.strategieObjet.ComportementMur;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.acteurs.objet.*;
import com.application.S2_dev.modele.map.Terrain;
public class Mur extends Objet {
    public Mur(Environnement environnement, Terrain terrain) {
        super(environnement, terrain);
        this.prix = 50; // prix de l'objet
        setComportement(new ComportementMur());
    }


}
