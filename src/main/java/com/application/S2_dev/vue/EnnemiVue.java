package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.Parametre;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.map.Environnement;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.net.URL;

public class EnnemiVue implements ListChangeListener<Ennemi> {
    private Pane panneau_de_jeu;
    Environnement env;
    private Label LabelnbVivantScavenger, LabelnbVivantBalliste,  LabelnbVivantBehemoth, Credit;
    int nbVivantScavenger = 0, nbVivantBalliste=0, nbVivantBehemoth=0, money;
    private URL urlScavenger,urlScavengerProximite, urlBalliste,urlBallisteProximite, urlBehemoth, urlBehemothProximite;
    private Image ImageScavenger, ImageScavengerProximite, ImageBalliste, ImageBallisteProximite, ImageBehemoth, ImageBehemothProximite;

    public EnnemiVue(Pane pane, Label labelScavenger, Label labelBalliste, Label labelBehemoth, Label Credit, Environnement env) {
        this.panneau_de_jeu = pane;
        this.LabelnbVivantScavenger =  labelScavenger;
        this.LabelnbVivantBalliste = labelBalliste;
        this.LabelnbVivantBehemoth =  labelBehemoth;
        this.Credit = Credit;
        this.money = Integer.parseInt(Credit.getText());
        this.env = env;


        urlScavenger = Main.class.getResource("image/ennemis/Scavenger.png");
        ImageScavenger = new javafx.scene.image.Image(String.valueOf(urlScavenger));

        urlScavengerProximite = Main.class.getResource("image/ennemis/Scavenger_Damaged.png");
        ImageScavengerProximite = new javafx.scene.image.Image(String.valueOf(urlScavengerProximite));

        urlBalliste = Main.class.getResource("image/ennemis/Balliste.png");
        ImageBalliste = new Image(String.valueOf(urlBalliste));

        urlBallisteProximite = Main.class.getResource("image/ennemis/Balliste_Damaged.png");
        ImageBallisteProximite = new Image(String.valueOf(urlBallisteProximite));

        urlBehemoth = Main.class.getResource("image/ennemis/Behemoth.png");
        ImageBehemoth = new Image(String.valueOf(urlBehemoth));

        urlBehemothProximite = Main.class.getResource("image/ennemis/Behemoth_Damaged.png");
        ImageBehemothProximite = new Image(String.valueOf(urlBehemothProximite));
    }
    @Override
    public void onChanged(Change<? extends Ennemi> c) {
        while (c.next()) {
            System.out.println("les ajouts Ennemis : " + c.getAddedSubList());
            System.out.println("les suppressions Ennemis: " + c.getRemoved());
        }
        for (int i = 0; i < c.getRemoved().size(); i++) {
            ImageView sprite = (ImageView) panneau_de_jeu.lookup("#" + c.getRemoved().get(i).getId());
            panneau_de_jeu.getChildren().remove(sprite);
            if (c.getRemoved().get(i) instanceof Scavenger) {
                nbVivantScavenger--;
                addMoney(100);
            } else if (c.getRemoved().get(i) instanceof Balliste) {
                nbVivantBalliste--;
                addMoney(50);
            }
            else if(c.getRemoved().get(i) instanceof Behemoth){
                nbVivantBehemoth--;
                addMoney(50);
            }

        }

        for (int i = 0; i < c.getAddedSubList().size(); i++) {
                creerSpriteProximite(c.getAddedSubList().get(i));

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

        } else if (e instanceof Behemoth) {
            behemoth = new ImageView(ImageBehemoth);
            behemoth.translateXProperty().bind(e.getXProperty());
            behemoth.translateYProperty().bind(e.getYProperty());

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
    void creerSpriteProximite(Ennemi e) {
        ImageView scavengerProximite;
        ImageView ballisteProximite;
        ImageView behemothProximite;

        if (e instanceof Scavenger) {
            behemothProximite = null;
            ballisteProximite = null;
            scavengerProximite = new ImageView(ImageScavenger);
            scavengerProximite.translateXProperty().bind(e.getXProperty());
            scavengerProximite.translateYProperty().bind(e.getYProperty());

            env.aProximiteTourProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue ) {
                    scavengerProximite.setImage(ImageScavengerProximite); // Image de proximité (zone rouge)
                } else {
                    scavengerProximite.setImage(ImageScavenger); // Image normale
                }
            });
        }
        else {
            scavengerProximite = null;
            if (e instanceof Balliste) {
                behemothProximite = null;
                ballisteProximite = new ImageView(ImageBalliste);
                ballisteProximite.translateXProperty().bind(e.getXProperty());
                ballisteProximite.translateYProperty().bind(e.getYProperty());

                // Ajouter un ChangeListener à la propriété aProximiteTour
                env.aProximiteTourProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue ) {
                        ballisteProximite.setImage(ImageBallisteProximite); // Image de proximité (zone rouge)
                    } else {
                        ballisteProximite.setImage(ImageBalliste); // Image normale
                    }
                });
            } else {
                ballisteProximite = null;
                if (e instanceof Behemoth) {
                    behemothProximite = new ImageView(ImageBehemoth);
                    behemothProximite.translateXProperty().bind(e.getXProperty());
                    behemothProximite.translateYProperty().bind(e.getYProperty());

                    // Ajouter un ChangeListener à la propriété aProximiteTour
                    env.aProximiteTourProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue ) {
                            behemothProximite.setImage(ImageBehemothProximite); // Image de proximité (zone rouge)
                        } else {
                            behemothProximite.setImage(ImageBehemoth); // Image normale
                        }
                    });
                } else {
                    behemothProximite = null;
                }
            }
        }
        if (scavengerProximite != null) {
            scavengerProximite.setId(e.getId());
            panneau_de_jeu.getChildren().add(scavengerProximite);
        }
        if (ballisteProximite != null) {
            ballisteProximite.setId(e.getId());
            panneau_de_jeu.getChildren().add(ballisteProximite);
        }
        if (behemothProximite != null) {
            behemothProximite.setId(e.getId());
            panneau_de_jeu.getChildren().add(behemothProximite);
        }
    }
    void addMoney(int value) {
        money += value;
        Credit.setText(String.valueOf(money));
    }
}
