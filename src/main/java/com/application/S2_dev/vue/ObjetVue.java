package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.data.TerrainType;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.objet.Bombe;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import com.application.S2_dev.modele.tours.EdisonCoil;
import com.application.S2_dev.modele.tours.NikolaCoil;
import com.application.S2_dev.modele.tours.OppenheimerCoil;
import com.application.S2_dev.modele.tours.Tour;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.net.URL;

public class ObjetVue implements ListChangeListener<Objet> {
    private Label labelBombe;
    private Label labelMur;
    private Environnement environnement;
    private Objet objet;
    URL urlBombe, urlMur;
    Image ImageBombe,ImageMur;
    Pane panneau_de_jeu;
    ImageView imageObjet;
    Terrain terrain;
    String objetSelectionne;


    public ObjetVue(Pane pane, Environnement environnement, Label Bombe, Label Mur, Terrain terrain){
        this.environnement = environnement;
        this.panneau_de_jeu = pane;
        this.labelBombe = Bombe;
        this.labelMur = Mur;
        this.terrain = terrain;

        urlBombe = Main.class.getResource("image/objet/Bombe.png");
        ImageBombe = new javafx.scene.image.Image(String.valueOf(urlBombe));

        urlMur = Main.class.getResource("image/objet/Mur.png");
        ImageMur = new javafx.scene.image.Image(String.valueOf(urlMur));

    }

    @Override
    public void onChanged(Change<? extends Objet> c) {
        while (c.next()) {
            System.out.println("les ajouts : " + c.getAddedSubList());
            System.out.println("les suppressions : " + c.getRemoved());
        }
        for (int i = 0; i < c.getRemoved().size(); i++) {
            ImageView sprite = (ImageView) panneau_de_jeu.lookup("#" + c.getRemoved().get(i).getId());
            panneau_de_jeu.getChildren().remove(sprite);
        }
        for (int i = 0; i < c.getAddedSubList().size(); i++) {

        }
    }


    public void AjoutObjet(){
        labelBombe.setOnMouseClicked(event -> {
           objetSelectionne = "Bombe";
        });

        labelMur.setOnMouseClicked( h -> {
          objetSelectionne = "Mur";
        });

        panneau_de_jeu.setOnMouseClicked( h -> {
            int[] pos = terrain.getPosInMap((int)h.getX(), (int)h.getY());
            apparitionObjet(pos[0], pos[1]);
            System.out.println(pos[0]+pos[1]);



        });
    }
    void creerObjet(Objet objet) {

        if (objet instanceof Bombe) {
            imageObjet = new ImageView(ImageBombe);
        } else if (objet instanceof Mur) {
            imageObjet = new ImageView(ImageMur);

        }
        if (imageObjet != null) {
            imageObjet.setId(objet.getId());
            panneau_de_jeu.getChildren().add(imageObjet);
        }
    }
    public void apparitionObjet(int ligne, int colonne) {

        if (emplacementBombe(ligne, colonne)) {
            if(objetSelectionne.equals("Bombe")){
                objet = new Bombe();
            }
            else if (objetSelectionne.equals("Mur")){
                objet = new Mur();
            }
            double x = colonne * 16;
            double y = ligne * 16;
            creerObjet(objet);
            imageObjet.setX(x);
            imageObjet.setY(y);
            environnement.ajoutObjet(objet);
        }
        else {
            System.out.println("Emplacement de la bombe impossible");
        }
    }
    public boolean emplacementBombe(int ligne, int colonne) {
        if (terrain.getCase(ligne, colonne) == TerrainType.PATH){
            return true;
        }
    return false;
    }
}
