package com.application.S2_dev.modele.tours;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.sound.Sound;
import javafx.beans.property.SimpleIntegerProperty;

public class EdisonCoil extends Tour {

    private int degats = 20; // Dommages infligés aux ennemis
    private int taux_tir = 5; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge = 0;

    public EdisonCoil(int x, int y, int niveau) {

        super(new SimpleIntegerProperty(250),(25 + (niveau * 3)),(6-niveau),"EdisonCoil", x, y, TowerType.Edison, niveau, 100 * niveau, 50 + (niveau * 5));

        this.vieMax = getVie();

        if (taux_tir < 1)
            taux_tir = 1;
    }

    @Override


    public void playAttackSound() {
        Sound s = new Sound(Main.class.getResource("sons/bruit.wav"));
        s.start();
    }
}
