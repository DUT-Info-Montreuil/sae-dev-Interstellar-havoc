package com.application.S2_dev.modele.designPattern.vagueFactory;

import com.application.S2_dev.modele.designPattern.vagueFactory.VagueDifficile;
import com.application.S2_dev.modele.designPattern.vagueFactory.VagueMoyenne;
import com.application.S2_dev.modele.designPattern.vagueFactory.VagueSimple;
import com.application.S2_dev.modele.designPattern.vagueFactory.VagueStrategy;

import java.util.Random;

public class GestionnaireProbabilite {
    private Random random = new Random();

    public VagueStrategy choisirVagueStrategy() {
        int valeur = random.nextInt(100) + 1;

        if (valeur <= 30) {
            return new VagueSimple();
        } else if (valeur <= 60) {
            return new VagueMoyenne();
        } else {
            return new VagueDifficile();
        }
    }
}
