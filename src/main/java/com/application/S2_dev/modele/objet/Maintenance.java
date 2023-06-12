package com.application.S2_dev.modele.objet;

import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.tours.Tour;

import java.util.ArrayList;

public class Maintenance {
    private Tour tour;
    private static Environnement env;
    private static ArrayList<Tour>morts;

    public static void reparer(){
        morts=env.getTours();
        for(int compteurMort=0;compteurMort<morts.size();compteurMort++){
            if (morts.get(compteurMort).estEndommager()){
                morts.get(compteurMort).setHealth();
            }else{
                System.out.println("La tour : " + morts.get(compteurMort).getId());
            }
        }

    }
}
