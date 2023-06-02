package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.ennemis.Ennemi;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;

public class EnnemiVue implements ListChangeListener<Ennemi> {
    private Pane panneau_de_jeu;
    private Label LabelnbVivantScavenger, LabelnbVivantBalliste,  LabelnbVivantBehemoth;
    int nbVivantScavenger = 0, nbVivantBalliste=0, nbVivantBehemoth=0;
    URL urlScavenger, urlBalliste, urlBehemoth;
    Image ImageScavenger,ImageBalliste, ImageBehemoth;


    public EnnemiVue(Pane pane, Label labelScavenger, Label labelBalliste, Label labelBehemoth) {
        this.panneau_de_jeu = pane;
        this.LabelnbVivantScavenger =  labelScavenger;
        this.LabelnbVivantBalliste = labelBalliste;
        this.LabelnbVivantBehemoth =  labelBehemoth;

        urlScavenger = Main.class.getResource("image/ennemis/Scavenger.png");
        ImageScavenger = new javafx.scene.image.Image(String.valueOf(urlScavenger));

        urlBalliste = Main.class.getResource("image/ennemis/Balliste.png");
        ImageBalliste = new Image(String.valueOf(urlBalliste));

        urlBehemoth = Main.class.getResource("image/ennemis/Behemoth.png");
        ImageBehemoth = new Image(String.valueOf(urlBehemoth));


    }
    @Override
    public void onChanged(Change<? extends Ennemi> c) {
        System.out.println("Changement");
        while (c.next()) {
            System.out.println("les ajouts : " + c.getAddedSubList());
            System.out.println("les suppressions : " + c.getRemoved());
        }
        for (int i = 0; i < c.getRemoved().size(); i++) {
            ImageView sprite = (ImageView) panneau_de_jeu.lookup("#" + c.getRemoved().get(i).getId());
            panneau_de_jeu.getChildren().remove(sprite);
        }

        for (int i = 0; i < c.getAddedSubList().size(); i++) {
            creerSprite(c.getAddedSubList().get(i));
            if (c.getAddedSubList().get(i) instanceof Scavenger) {
                nbVivantScavenger++;
            } else if (c.getAddedSubList().get(i) instanceof Balliste) {
                nbVivantBalliste++;
            }
            else if(c.getAddedSubList().get(i) instanceof Behemoth){
                nbVivantBehemoth++;
            }

        }
        LabelnbVivantScavenger.setText(String.valueOf(nbVivantScavenger));
        LabelnbVivantBalliste.setText(String.valueOf(nbVivantBalliste));
        LabelnbVivantBehemoth.setText(String.valueOf(nbVivantBehemoth));
    }

    void creerSprite(Ennemi e) {
        ImageView scavenger = null;
        ImageView balliste = null;
        ImageView behemoth = null;


        if (e instanceof Scavenger) {
            scavenger = new ImageView(ImageScavenger);
            scavenger.translateXProperty().bind(e.getXProperty());
            scavenger.translateYProperty().bind(e.getYProperty());

        } else if (e instanceof Balliste) {
            balliste = new ImageView(ImageBalliste);
            balliste.translateXProperty().bind(e.getXProperty());
            balliste.translateYProperty().bind(e.getYProperty());
            // System.out.println(balliste.getTranslateX()+" et y "+ balliste.getTranslateY());

        ImLent.translateXProperty().bind(e.getXProperty());
        ImLent.translateYProperty().bind(e.getYProperty());

        } else if (e instanceof Behemoth) {
            behemoth = new ImageView(ImageBehemoth);
            behemoth.translateXProperty().bind(e.getXProperty());
            behemoth.translateYProperty().bind(e.getYProperty());

        if (ImLent != null) {
            ImLent.setId(e.getId());
            panneau_de_jeu.getChildren().add(ImLent);
        }
        if (scavenger != null) {
            scavenger.setId(e.getId());
            panneau_de_jeu.getChildren().add(scavenger);
        }
        if (balliste != null) {
            balliste.setId(e.getId());
            panneau_de_jeu.getChildren().add(balliste);
        }
        if (behemoth != null) {
            behemoth.setId(e.getId());
            panneau_de_jeu.getChildren().add(behemoth);
        }
    }
}
