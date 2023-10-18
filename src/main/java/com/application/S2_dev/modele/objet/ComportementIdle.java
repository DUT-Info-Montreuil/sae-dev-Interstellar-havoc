package com.application.S2_dev.modele.objet;

import static com.application.S2_dev.modele.objet.Objet.environnement;

public class ComportementIdle implements Comportement {
    @Override
    public void agit( Objet obj) {
        System.out.println("TEST");
    }

    @Override
    public void degats(int value, Objet objet) {
        /*Ne prend pas de dégats*/
    }


}