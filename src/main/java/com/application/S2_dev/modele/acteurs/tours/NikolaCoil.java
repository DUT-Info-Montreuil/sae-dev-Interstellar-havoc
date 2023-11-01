package com.application.S2_dev.modele.acteurs.tours;

import com.application.S2_dev.modele.données.TowerType;
import javafx.beans.property.SimpleIntegerProperty;

public class NikolaCoil extends Tour {

    public NikolaCoil(int x, int y, int niveau) {
        super(x, y, TowerType.Nikola, niveau);
        this.nom = "NikolaCoil";
        this.vie = new SimpleIntegerProperty(150);
        this.degats = (5 + (niveau * 3));
        this.TAUX_TIR = (2-niveau);
        this.prix = 100 * niveau;
        this.portee =  80 + (niveau * 5);
    }

}
