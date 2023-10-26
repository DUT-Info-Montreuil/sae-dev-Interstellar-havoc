package com.application.S2_dev.modele.acteurs.tours;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.sound.Sound;
import javafx.beans.property.SimpleIntegerProperty;

public class OppenheimerCoil extends Tour {


    public OppenheimerCoil(int x, int y, int niveau) {
        super( x, y, TowerType.Oppenheimer, niveau);
        this.vie = new SimpleIntegerProperty(350);
        this.degatsT = (35+(niveau*3));
        this.vieMax = vie.getValue();
        this.TAUX_TIR = (10-niveau);
        this.nom = "OppenheimerCoil";
        this.prix = 100 * niveau;
        this.portee = 150 + (niveau * 5);
    }



}
