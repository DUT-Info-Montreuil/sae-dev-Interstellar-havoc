package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;

public class NikolaCoil extends Tour {

    private int DEGATS = 10; // Dommages infligés aux ennemis
    private int TAUX_TIR = 2; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge = 0;

    public NikolaCoil(int x, int y, int niveau) {

        super("NikolaCoil", x, y, TowerType.Nikola, niveau, 100 * niveau, 50 + (niveau * 5));

        this.TAUX_TIR = 2 - niveau;
        this.DEGATS = 10 + (niveau * 3);

        if (TAUX_TIR < 1)
            TAUX_TIR = 1;
    }

    @Override
    public void attaquerTour(Ennemi ennemi) {
        if (tempsRecharge == 0) {
            // Inflige des dommages à l'ennemi
            ennemi.subirDegats(DEGATS);
            tempsRecharge = TAUX_TIR;
        }
        if (tempsRecharge > 0)
            tempsRecharge--;
    }
}
