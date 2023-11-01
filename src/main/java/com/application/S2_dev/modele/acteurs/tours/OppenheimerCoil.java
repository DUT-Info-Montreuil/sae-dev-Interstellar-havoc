package com.application.S2_dev.modele.acteurs.tours;

import com.application.S2_dev.modele.données.TowerType;
import javafx.beans.property.SimpleIntegerProperty;

public class OppenheimerCoil extends Tour {

    public OppenheimerCoil(int x, int y, int niveau) {
        super( x, y, TowerType.Oppenheimer, niveau);
        this.nom = "OppenheimerCoil";
        this.vie = new SimpleIntegerProperty(350);
        this.degats = (35+(niveau*3));
        this.TAUX_TIR = (10-niveau);
        this.prix = 100 * niveau;
        this.portee = 150 + (niveau * 5);
    }



}
