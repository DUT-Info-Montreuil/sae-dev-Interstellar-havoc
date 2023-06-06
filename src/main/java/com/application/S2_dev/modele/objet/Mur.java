package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.map.Terrain;

import java.util.Arrays;

public class Mur extends Objet{
    private static Terrain terrain;
    private int pv1 = 50;
    public Mur(){
        super();
        this.pv1 = pv;
    }

    public static void takeDamage(int damage){
        //this.pv1 -= damage;
    }

    public static void PlacerMur(int i , int j ){
        terrain.placementMur(i,j);
    }
}
