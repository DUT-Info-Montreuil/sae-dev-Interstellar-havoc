package com.application.S2_dev.modele.vagueFactory;

import com.application.S2_dev.modele.acteurs.ennemis.Balliste;
import com.application.S2_dev.modele.acteurs.ennemis.Behemoth;
import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.acteurs.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

import java.util.Random;

public class VagueDifficile implements com.application.S2_dev.modele.vagueFactory.VagueStrategy {
    private final Random random = new Random();

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
                    en = new Balliste(5, 21, terrain);
                } else if (spawnType <= 60) { // 30% de chance de générer un Scavenger
                    en = new Scavenger(5, 21, terrain);
                } else { // 40% de chance de générer un Behemoth
                    en = new Behemoth(5, 21, terrain);
                }

                environnement.getEnnemis().add(en);
            }
        }
    }
}
