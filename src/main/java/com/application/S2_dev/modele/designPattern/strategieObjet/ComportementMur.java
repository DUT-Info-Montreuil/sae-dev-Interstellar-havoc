package com.application.S2_dev.modele.designPattern.strategieObjet;

import com.application.S2_dev.modele.acteurs.Acteur;

public class ComportementMur implements Comportement {
    @Override
    public void agit(Acteur objet) {
        // Le mur n'agit pas
    }
    @Override
    public void degats(int value, Acteur objet) {
        objet.setVie(objet.getVie()-value);
    }
}
