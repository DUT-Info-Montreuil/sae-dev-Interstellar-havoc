package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Ennemi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Environnement {

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

    public void ajouter(Ennemi a) {

        this.ennemis.add(a);
    }

    public void init() {
      for(int i =0; i<5; i++){
          Ennemi e = new Balliste();
          ennemis.add(e);
      }
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

