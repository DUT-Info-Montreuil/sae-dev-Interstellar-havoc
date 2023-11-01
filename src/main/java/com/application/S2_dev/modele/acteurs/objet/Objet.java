package com.application.S2_dev.modele.acteurs.objet;

import com.application.S2_dev.modele.acteurs.Acteur;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.designPattern.strategieObjet.*;
import javafx.beans.property.SimpleIntegerProperty;

public class Objet extends Acteur {
    protected Comportement comportement;
    public static Environnement environnement;
    protected static Terrain terrain;
    public Objet(Environnement environnement, Terrain terrain) {
        super(0,0);
        this.environnement = environnement;
        this.terrain = terrain;
        this.vie = new SimpleIntegerProperty(20);
        this.id = "O" + compteur;

    }
    public void agit(){if(comportement!=null)comportement.agit(this);}
    public void degat(int value){if(comportement!=null)comportement.degats(value,this);}
    public void setComportement(Comportement c) {
        comportement = c;
    }
    @Override
    public void attaquerActeur(Acteur acteur) {

    }
}
