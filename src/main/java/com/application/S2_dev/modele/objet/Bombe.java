package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.ennemis.Ennemi;

public class Bombe extends Objet {

    private static final int DEGAT = 100;
    private static final int PORTE = 50;
    public Bombe(){
        super();
        this.pv = 10;
    }

    public void degat(Ennemi ennemi){
        if (isInRange(ennemi)) {
            ennemi.setHealth(0);
            this.pv = 0;
        }

    }
    private double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow((x-getX()), 2) + Math.pow((y-getY()), 2));
    }

    private boolean isInRange(Ennemi ennemi) {
        double distance = calculateDistance(ennemi.getX(), ennemi.getY());
        return distance <= PORTE;
    }


}
