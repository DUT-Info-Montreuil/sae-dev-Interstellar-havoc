package com.application.S2_dev.modele.map;

import com.application.S2_dev.Main;
import com.application.S2_dev.controlleur.ControlleurTerrainJeu;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
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

    public Environnement() {

        this.mort = new ArrayList<>();
        this.ennemis = FXCollections.observableArrayList();
        terr = new Terrain();
    }

    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }

    public ArrayList<Ennemi> getMort() {
        return mort;
    }

    public void ajouterVague() {
        boolean spawnPossible = true;
       // int compteurSpawnEnnemi = 5;
        int ennemisMax = 5;
        int ennemisActuels = ennemis.size();

        if (ennemisActuels >= ennemisMax) {
            spawnPossible = false;
        }

        if (spawnPossible) {
            int ennemisAAjouter =  ennemisMax - ennemisActuels;

            if (ennemisActuels == 0) {
                Ennemi en = new Balliste();
                ennemis.add(en);
                ennemisAAjouter--;
            }

            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                int spawnRate = random.nextInt(150) + 1;
                switch (spawnRate) {
                    case 1:
                        Ennemi en = new Behemoth();
                        ennemis.add(en);
                        break;
                    case 2:
                        Ennemi en1 = new Scavenger();
                        ennemis.add(en1);
                        break;
                    case 3:
                        Ennemi en2 = new Balliste();
                        ennemis.add(en2);
                        break;
                }
            }
        }
    }
    public void unTour() {

        for(int i = 0; i< ennemis.size(); i++) {
            Ennemi e = ennemis.get(i);
            e.agit(16, 16);
        }

        for (int i = ennemis.size() - 1; i >= 0; i--) {
            Ennemi en = ennemis.get(i);
            if (!en.estVivant()) {
                System.out.println("mort de : " + en);
                ennemis.remove(i);
                this.mort.add(en);
            }
        }
        System.out.println("Taille " + ennemis.size());
        ajouterVague();

    }

}

