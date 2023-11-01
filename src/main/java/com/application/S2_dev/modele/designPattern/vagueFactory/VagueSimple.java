package com.application.S2_dev.modele.designPattern.vagueFactory;

import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.BallisteFactory;
import com.application.S2_dev.modele.designPattern.EnnemiFactory.EnnemiFactory;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class VagueSimple implements VagueStrategy {
    private EnnemiFactory balliste;
    public VagueSimple() {
        balliste = new BallisteFactory();
    }

    @Override
    public void ajouterVague(Environnement environnement, Terrain terrain) {
        int ennemisMax = 3; // Réduire le nombre d'ennemis pour une vague simple
        int ennemisActuels = environnement.getEnnemis().size();

        if (ennemisActuels < ennemisMax) {
            int ennemisAAjouter = ennemisMax - ennemisActuels;

            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                // Pour une vague simple, nous pourrions principalement générer des ennemis plus faibles
                Ennemi en = balliste.créerEnnemi(terrain);
                environnement.ajouterEnnemi(en);
            }
        }
    }
}
