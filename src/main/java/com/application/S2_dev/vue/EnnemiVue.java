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


    public EnnemiVue(Pane pane) {
        this.panneau_de_jeu = pane;


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
            System.out.println("sprite cree");

        }
        LabelnbVivantScavenger.setText(String.valueOf(nbVivantScavenger));
        LabelnbVivantBalliste.setText(String.valueOf(nbVivantBalliste));
        LabelnbVivantBehemoth.setText(String.valueOf(nbVivantBehemoth));
    }

    void creerSprite(Ennemi e) {
        // ImageView scavenger = null;
        URL urlEnnemiLent = Main.class.getResource("image/pika.png");
        Image ennemiLent = new Image(String.valueOf(urlEnnemiLent));
        ImageView ImLent = new ImageView(ennemiLent);

        ImLent.translateXProperty().bind(e.getXProperty());
        ImLent.translateYProperty().bind(e.getYProperty());


        if (ImLent != null) {
            ImLent.setId(e.getId());
            panneau_de_jeu.getChildren().add(ImLent);
        }

    }
}
