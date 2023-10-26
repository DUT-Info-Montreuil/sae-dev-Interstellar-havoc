package com.application.S2_dev.modele.acteurs.tours;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.sound.Sound;
import javafx.beans.property.SimpleIntegerProperty;

public class NikolaCoil extends Tour {



    public NikolaCoil(int x, int y, int niveau) {
        super(  x, y, TowerType.Nikola, niveau);
        this.vie = new SimpleIntegerProperty(150);
        this.degatsT = (5 + (niveau * 3));
        this.vieMax = getVie();
        this.TAUX_TIR = (2-niveau);
        this.nom = "NikolaCoil";
        this.prix = 100 * niveau;
        this.portee =  80 + (niveau * 5);
    }

}
