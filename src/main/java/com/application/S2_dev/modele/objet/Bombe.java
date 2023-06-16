package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class Bombe extends Objet {
    private int PORTE = 20;
    public Bombe(Environnement environnement, Terrain terrain){

        super(environnement, terrain);
        this.prix = 50; // prix de l'objet
    }
    @Override
    public void degat(int value){
        /* Cet objet n'est pas endommageable */
    }
    @Override
    public void agit() {
        for(int i =0; i<environnement.getEnnemis().size();i++) {
            if (ennemisProximite(environnement.getEnnemis().get(i))) {
                environnement.getEnnemis().get(i).meur();
                this.pv = 0; // mort de l'ennemi à proximité de la bombe
            }
        }
    }
    private double calculaterDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }
    private boolean ennemisProximite(Ennemi ennemi) {
        // ennemis à proximité de la bombe
        double distance = calculaterDistance(ennemi.getX(), ennemi.getY());
        return distance <= PORTE;
    }
}
