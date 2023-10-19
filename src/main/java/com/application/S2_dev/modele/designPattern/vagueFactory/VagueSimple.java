package com.application.S2_dev.modele.designPattern.vagueFactory;

import com.application.S2_dev.modele.acteurs.ennemis.Balliste;
import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.designPattern.vagueFactory.VagueStrategy;

public class VagueSimple implements VagueStrategy {
    @Override
    public void ajouterVague(Environnement environnement, Terrain terrain) {
        int ennemisMax = 3; // Réduire le nombre d'ennemis pour une vague simple
        int ennemisActuels = environnement.getEnnemis().size();

        if (ennemisActuels < ennemisMax) {
            int ennemisAAjouter = ennemisMax - ennemisActuels;

            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                // Pour une vague simple, nous pourrions principalement générer des ennemis plus faibles
                Ennemi en = new Balliste(5, 21, terrain);
                environnement.getEnnemis().add(en);
            }
        }
    }
}