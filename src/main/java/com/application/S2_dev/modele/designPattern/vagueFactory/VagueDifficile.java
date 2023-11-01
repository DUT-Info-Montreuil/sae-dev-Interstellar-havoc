package com.application.S2_dev.modele.designPattern.vagueFactory;

import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.BallisteFactory;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.BehemothFactory;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.EnnemiFactory;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.ScavengerFactory;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

import java.util.Random;

public class VagueDifficile implements VagueStrategy {
    private final Random random = new Random();
    private EnnemiFactory balliste;
    private EnnemiFactory behemoth;
    private EnnemiFactory scavenger;

    public VagueDifficile() {
        this.balliste = new BallisteFactory();
        this.behemoth = new BehemothFactory();
        this.scavenger = new ScavengerFactory();
    }

    @Override
    public void ajouterVague(Environnement environnement, Terrain terrain) {
        int ennemisMax = 7; // Un nombre plus élevé d'ennemis pour une difficulté élevée
        int ennemisActuels = environnement.getEnnemis().size();

        if (ennemisActuels < ennemisMax) {
            int ennemisAAjouter = ennemisMax - ennemisActuels;

            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                // Génération aléatoire d'ennemis, avec une probabilité plus élevée pour les ennemis plus forts
                int spawnType = random.nextInt(100) + 1;
                Ennemi en;

                if (spawnType <= 30) { // 30% de chance de générer un Balliste
                    en = balliste.créerEnnemi(terrain);
                } else if (spawnType <= 60) { // 30% de chance de générer un Scavenger
                    en = scavenger.créerEnnemi(terrain);
                } else { // 40% de chance de générer un Behemoth
                    en = behemoth.créerEnnemi(terrain);
                }

                environnement.ajouterEnnemi(en);
            }
        }
    }
}
