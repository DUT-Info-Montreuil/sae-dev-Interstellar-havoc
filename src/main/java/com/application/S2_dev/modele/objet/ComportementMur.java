package com.application.S2_dev.modele.objet;

public class ComportementMur implements Comportement {
    @Override
    public void agit(Objet objet) {

    }
    @Override
    public void degats(int value, Objet objet) {
        objet.pv -= value;
    }
}
