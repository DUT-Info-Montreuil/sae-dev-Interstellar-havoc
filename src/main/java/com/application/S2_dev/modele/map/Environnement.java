package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;
//import java.util.random.*;

public class Environnement {
    Random random = new Random();
    Pane pane;
    private int spawnRate;
    private Terrain terr;

    private ArrayList<Ennemi> mort;
    private  ObservableList<Ennemi> ennemis;

    public Environnement(Pane pane) {
        this.mort = new ArrayList<>();
        this.ennemis = FXCollections.observableArrayList();
        terr = new Terrain();
        this.pane=pane;
    }

    public ObservableList<Ennemi> getEnnemis() {

        return ennemis;
    }

    public ArrayList<Ennemi> getMort() {

        return mort;
    }

    public void ajouterVague() {
        int compteurSpawnEnnemi =3;
        for(int compteur =0;compteur<=compteurSpawnEnnemi;compteur++){
            int spawnRate = random.nextInt(5)+1;
            switch (spawnRate){
                case 1 :
                    Ennemi b = new Scavenger();
                case 2 :
                    Ennemi a = new Scavenger();
                case 3 :
                    Ennemi c = new Balliste();
                case 4 :
                    Ennemi d = new Behemoth();
                case 5 :
                    Ennemi e = new Balliste();
            }

        }

    }

    public void init() {
        ajouterVague();
    }

    public void unTour() {
        for(int i = 0; i< ennemis.size(); i++) {
            Ennemi e = ennemis.get(i);
            e.agit(16, 16);
            //  pane.getChildren().add(e.getImLent());
        }

        for (int i = ennemis.size() - 1; i >= 0; i--) {
            Ennemi en = ennemis.get(i);
            if (!en.estVivant()) {
                System.out.println("mort de : " + en);
                ennemis.remove(i);
                this.mort.add(en);
            }
        }

    }

}

