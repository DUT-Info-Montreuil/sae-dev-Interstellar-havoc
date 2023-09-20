package com.application.S2_dev.modele.tours;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.sound.Sound;
import javafx.beans.property.SimpleIntegerProperty;

public class OppenheimerCoil extends Tour {

    private int DEGATS = 25; // Dommages infligés aux ennemis
    private int TAUX_TIR = 10; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge = 0;

    public OppenheimerCoil(int x, int y, int niveau) {
        super("OppenheimerCoil", x, y, TowerType.Oppenheimer, niveau, 100 * niveau, 150 + (niveau * 5));
        this.vie = new SimpleIntegerProperty(350);
        this.vieMax = 350;
        this.TAUX_TIR = 10 - niveau;
        this.DEGATS = 55 + (niveau * 3);

        if (TAUX_TIR < 1)
            TAUX_TIR = 1;
    }
    @Override
    public void attaquerEnnemi(Ennemi ennemi) {
        if (tempsRecharge == 0) {
            // Inflige des dommages à l'ennemi
            ennemi.subirDegats(DEGATS);
            tempsRecharge = TAUX_TIR;
            playAttackSound();
        }
        if (tempsRecharge > 0)
            tempsRecharge--;
    }
    public void playAttackSound() {
        Sound s = new Sound(Main.class.getResource("sons/bruit.wav"));
        s.start();
    }

}
