package com.application.S2_dev.modele.designPattern.strategieObjet;

import com.application.S2_dev.modele.acteurs.Acteur;
import static com.application.S2_dev.modele.acteurs.objet.Objet.environnement;

public class ComportementBombe implements Comportement {
    @Override
    public void agit( Acteur obj) {
        for (int i = 0; i < environnement.getEnnemis().size(); i++) {
            if (ennemisProximite(environnement.getEnnemis().get(i),obj)) {
                environnement.getEnnemis().get(i).meurt();
                obj.meurt();
            }
        }
    }
    @Override
    public void degats(int value, Acteur objet) {
        /*Ne prend pas de dégats*/
    }

}