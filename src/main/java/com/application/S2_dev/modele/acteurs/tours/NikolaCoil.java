package com.application.S2_dev.modele.acteurs.tours;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.sound.Sound;
import javafx.beans.property.SimpleIntegerProperty;

public class NikolaCoil extends Tour {


    private int TAUX_TIR = 2; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge = 0;

    public NikolaCoil(int x, int y, int niveau) {
        super((2-niveau), "NikolaCoil", x, y, TowerType.Nikola, niveau, 100 * niveau, 50 + (niveau * 5));
        this.vie = new SimpleIntegerProperty(150);
        this.degatsT = (10 + (niveau * 3));
        this.vieMax = getVie();
        if (TAUX_TIR < 1)
            TAUX_TIR = 1;
    }
    @Override
    public void playAttackSound() {
        Sound s = new Sound(Main.class.getResource("sons/bruit.wav"));
        s.start();
    }
}
