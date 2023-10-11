package com.application.S2_dev.modele.objet;

import static com.application.S2_dev.modele.objet.Objet.environnement;

public class ComportementBombe implements Comportement {
    @Override
    public void agit( Objet obj) {
        for (int i = 0; i < environnement.getEnnemis().size(); i++) {
            if (ennemisProximite(environnement.getEnnemis().get(i),obj)) {
                environnement.getEnnemis().get(i).meur();
                obj.pv = 0;
            }
        }
    }

    @Override
    public void degats(int value, Objet objet) {
        /*Ne prend pas de dégats*/
    }


}