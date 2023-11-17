package com.application.S2_dev.modele.designPattern.strategieObjet;

import com.application.S2_dev.modele.acteurs.Acteur;
import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;

public interface Comportement {
    void agit(Acteur obj);
    void degats(int value, Acteur objet);

    private double calculaterDistance(double x, double y,Acteur Obj) {
        return Math.sqrt(Math.pow((x - Obj.getX()), 2) + Math.pow((y - Obj.getY()), 2));
    }
    default boolean ennemisProximite(Ennemi ennemi, Acteur obj) {
        // ennemis à proximité de la bombe
        double distance = calculaterDistance(ennemi.getX(), ennemi.getY(),obj);
        return distance <= obj.getPortee();
    }
}