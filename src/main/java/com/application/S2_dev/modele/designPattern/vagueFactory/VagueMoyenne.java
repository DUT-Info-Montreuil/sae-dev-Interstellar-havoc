package com.application.S2_dev.modele.designPattern.vagueFactory;

import com.application.S2_dev.modele.acteurs.ennemis.Balliste;
import com.application.S2_dev.modele.acteurs.ennemis.Behemoth;
import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.acteurs.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.designPattern.vagueFactory.VagueStrategy;

import java.util.Random;

public class VagueMoyenne implements VagueStrategy {
    private final Random random = new Random();

    @Override
    public void ajouterVague(Environnement environnement, Terrain terrain) {
        int ennemisMax = 5; // Un nombre modéré d'ennemis pour une difficulté moyenne
        int ennemisActuels = environnement.getEnnemis().size();

        if (ennemisActuels < ennemisMax) {
            int ennemisAAjouter = ennemisMax - ennemisActuels;

            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                // Génération aléatoire d'ennemis, avec une probabilité plus élevée pour les ennemis plus faibles
                int spawnType = random.nextInt(100) + 1;
                Ennemi en;

                if (spawnType <= 50) { // 50% de chance de générer un Balliste
                    en = new Balliste(5, 21, terrain);
                } else if (spawnType <= 80) { // 30% de chance de générer un Scavenger
                    en = new Scavenger(5, 21, terrain);
                } else { // 20% de chance de générer un Behemoth
                    en = new Behemoth(5, 21, terrain);
                }

                environnement.getEnnemis().add(en);
            }
        }
    }
}
