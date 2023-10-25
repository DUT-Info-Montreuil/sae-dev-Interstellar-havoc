package com.application.S2_dev.modele.vagueFactory;

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
