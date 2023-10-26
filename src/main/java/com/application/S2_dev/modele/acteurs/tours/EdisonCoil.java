package com.application.S2_dev.modele.acteurs.tours;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.sound.Sound;
import javafx.beans.property.SimpleIntegerProperty;

public class EdisonCoil extends Tour {


    public EdisonCoil(int x, int y, int niveau) {

        super( x, y, TowerType.Edison, niveau);
        this.vie = new SimpleIntegerProperty(250);
        this.degatsT = 15 + (niveau * 3);
        this.vieMax = getVie();
        this.TAUX_TIR = (6-niveau);
        this.nom = "EdisonCoil";
        this.prix = 100 * niveau;
        this.portee =   100 + (niveau * 5);
    }

}
