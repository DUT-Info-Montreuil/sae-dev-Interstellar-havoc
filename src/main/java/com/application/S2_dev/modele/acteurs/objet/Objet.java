package com.application.S2_dev.modele.acteurs.objet;

import com.application.S2_dev.modele.acteurs.Acteur;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import com.application.S2_dev.modele.designPattern.strategieObjet.*;
import javafx.beans.property.SimpleIntegerProperty;

public class Objet extends Acteur {
    protected Comportement comportement;
    public static Environnement environnement;
    protected static Terrain terrain;
    public int pv;
    public static int compteur = 0;
    public static int prix;
    protected static int PORTE=0;
    public Objet(Environnement environnement, Terrain terrain) {
        super(0,0);

        this.environnement = environnement;
        this.terrain = terrain;
        this.vie = new SimpleIntegerProperty(20);

    }




    @Override
    public void attaquerActeur(Acteur acteur) {

    }
    public void agit(){if(comportement!=null)comportement.agit(this);}

    public void degat(int value){if(comportement!=null)comportement.degats(value,this);}

    public int getPrix() {
        return prix;
    }


    public void setComportement(Comportement c) {
        comportement = c;
    }

    public Comportement getComportement() {
        return comportement;
    }
}
