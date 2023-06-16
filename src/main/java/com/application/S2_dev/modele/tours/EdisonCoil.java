package com.application.S2_dev.modele.tours;

import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Ennemi;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EdisonCoil extends Tour {

    private int degats; // Dommages infligés aux ennemis
    private int taux_tir; // Taux de tir de la tour (coups par seconde)
    private int tempsRecharge = 0;

    public EdisonCoil(int x, int y, int niveau) {

        super("EdisonCoil", x, y, TowerType.Edison, niveau, 100 * niveau, 50 + (niveau * 5));
        this.vie = new SimpleIntegerProperty(250);

        this.taux_tir = 6 - niveau;
        this.degats = 25 + (niveau * 3);

        if (taux_tir < 1)
            taux_tir = 1;
    }

    @Override
    public void attaquerTour(Ennemi ennemi) {
        // Le temps de recharge fait attaquer la tour les ennemis en fonction de son taux de tir
        if (tempsRecharge == 0) {
            // Inflige des dommages à l'ennemi
            ennemi.subirDegats(degats);
            tempsRecharge = taux_tir;
        }
        if (tempsRecharge > 0)
            tempsRecharge--;
    }
    @Override
    public IntegerProperty getVieMax(){
        this.vieMax.setValue(250);
        return this.vieMax;
    }
}
