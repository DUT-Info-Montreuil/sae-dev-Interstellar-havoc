package com.application.S2_dev.modele.designPattern.strategieObjet;

import com.application.S2_dev.modele.acteurs.Acteur;

import static com.application.S2_dev.modele.acteurs.objet.Objet.environnement;


public class ComportementHydrogène implements Comportement{

    @Override
    public void agit(Acteur obj) {
        for (int i = 0; i < environnement.getEnnemis().size(); i++) {
            environnement.getEnnemis().get(i).meurt(); // mort de tous les ennemis
        }
        for (int i = 0; i < environnement.getTour().size(); i++) {
            environnement.getTour().get(i).meurt(); // mort de toites les tourelles
        }
        obj.meurt();
    }

    @Override
    public void degats(int value, Acteur objet) {

    }
}
