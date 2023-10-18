package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;



public interface Comportement {
        void agit(Objet obj);
        void degats(int value, Objet objet);

    private double calculaterDistance(double x, double y,Objet Obj) {
        return Math.sqrt(Math.pow((x - Obj.getX()), 2) + Math.pow((y - Obj.getY()), 2));
    }

    default boolean ennemisProximite(Ennemi ennemi, Objet obj) {
        // ennemis à proximité de la bombe
        double distance = calculaterDistance(ennemi.getX(), ennemi.getY(),obj);
        return distance <= ennemi.getPortee();
    }



}