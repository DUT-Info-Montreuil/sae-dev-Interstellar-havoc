package com.application.S2_dev.modele.tours;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.sound.Sound;
import javafx.beans.property.SimpleIntegerProperty;

public class OppenheimerCoil extends Tour {

    private int DEGATS; // Dommages infligés aux ennemis
    private int TAUX_TIR ; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge = 0;

    public OppenheimerCoil(int x, int y, int niveau) {
        super(new SimpleIntegerProperty(350),(55+(niveau*3)),(10-niveau),"OppenheimerCoil", x, y, TowerType.Oppenheimer, niveau, 100 * niveau, 150 + (niveau * 5));
        this.vieMax = vie.getValue();

        if (TAUX_TIR < 1)
            TAUX_TIR = 1;
    }


    @Override
    public void playAttackSound() {
        Sound s = new Sound(Main.class.getResource("sons/bruit.wav"));
        s.start();
    }

}
