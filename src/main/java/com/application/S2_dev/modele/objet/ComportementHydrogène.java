package com.application.S2_dev.modele.objet;

import static com.application.S2_dev.modele.objet.Objet.environnement;

public class ComportementHydrogène implements Comportement{

    @Override
    public void agit(Objet obj) {
        for (int i = 0; i < environnement.getEnnemis().size(); i++) {
            environnement.getEnnemis().get(i).meur(); // mort de tous les ennemis
        }
        for (int i = 0; i < environnement.getTour().size(); i++) {
            environnement.getTour().get(i).meur(); // mort de toites les tourelles
        }
        obj.pv = 0;
    }

    @Override
    public void degats(int value, Objet objet) {

    }
}
