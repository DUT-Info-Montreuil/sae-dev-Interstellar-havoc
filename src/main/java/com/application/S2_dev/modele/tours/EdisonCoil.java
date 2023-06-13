package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;

public class EdisonCoil extends Tour {

    private int DEGATS; // Dommages infligés aux ennemis
    private int TAUX_TIR; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge = 0;

    public EdisonCoil(int x, int y, int niveau) {

        super("EdisonCoil", x, y, TowerType.Edison, niveau, 100 * niveau, 50 + (niveau * 5));

        this.TAUX_TIR = 6 - niveau;
        this.DEGATS = 25 + (niveau * 3);

        if (TAUX_TIR < 1)
            TAUX_TIR = 1;
    }

    @Override
    public void attaquerTour(Ennemi ennemi) {
        // Le temps de recharge fait attaquer la tour les ennemis en fonction de son taux de tir
        if (tempsRecharge == 0) {
            // Inflige des dommages à l'ennemi
            ennemi.subirDegats(DEGATS);
            tempsRecharge = TAUX_TIR;
        }
        if (tempsRecharge > 0)
            tempsRecharge--;
    }
}