package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.objet.Bombe;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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


    public ObjetVue(Pane pane, Environnement environnement, Label Bombe, Label Mur){
        this.environnement = environnement;
        this.panneau_de_jeu = pane;
        this.labelBombe = Bombe;
        this.labelMur = Mur;

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
           // creerObjet();
           // AjoutObjet();
        }
    }


    public void AjoutObjet(){
        labelBombe.setOnMouseClicked(event -> {
            objet = new Bombe();
            environnement.ajoutObjet(objet);
            creerObjet();
        });

        labelMur.setOnMouseClicked( h -> {
            objet = new Mur();
            environnement.ajoutObjet(objet);
            creerObjet();
        });

        panneau_de_jeu.setOnMouseClicked( h -> {
            double X = h.getX();
            double Y = h.getY();
            imageObjet.setX(X);
            imageObjet.setY(Y);
            panneau_de_jeu.getChildren().add(imageObjet);
        });
    }
    void creerObjet() {

        if (objet instanceof Bombe) {
            imageObjet = new ImageView(ImageBombe);
        } else if (objet instanceof Mur) {
            imageObjet = new ImageView(ImageMur);

        }
        if (imageObjet != null) {
            imageObjet.setId(objet.getId());
        }
    }
}
