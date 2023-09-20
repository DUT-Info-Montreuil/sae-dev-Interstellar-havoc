package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.Boutique;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.map.Environnement;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.net.URL;

public class EnnemiVue implements ListChangeListener<Ennemi> {
    private Pane panneau_de_jeu;
    private Environnement env;
    private Label LabelnbVivantScavenger, LabelnbVivantBalliste,  LabelnbVivantBehemoth;
    private Boutique boutique; /* Boutique pour l'achat des ennemis */
    private int nbVivantScavenger = 0, nbVivantBalliste=0, nbVivantBehemoth=0; /* compteur des ennemis */
    private URL urlScavenger,urlScavengerProximite, urlBalliste,urlBallisteProximite, urlBehemoth, urlBehemothProximite;
    private Image ImageScavenger, ImageScavengerProximite, ImageBalliste, ImageBallisteProximite, ImageBehemoth, ImageBehemothProximite;

    public EnnemiVue(Pane pane, Label labelScavenger, Label labelBalliste, Label labelBehemoth, Environnement env, Boutique boutique) {
        this.panneau_de_jeu = pane;
        this.LabelnbVivantScavenger =  labelScavenger;
        this.LabelnbVivantBalliste = labelBalliste;
        this.LabelnbVivantBehemoth =  labelBehemoth;
        this.env = env;
        this.boutique = boutique;

        /* URL et image des sprites */
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
            panneau_de_jeu.getChildren().remove(sprite); /* Suppression du sprite du panneau de jeu */
            if (c.getRemoved().get(i) instanceof Scavenger) {
                nbVivantScavenger--; // Mise à jour du compteur
                boutique.setPrix(boutique.getPrix()+100); // La destruction des ennemis permet de gagner de l'argent
            } else if (c.getRemoved().get(i) instanceof Balliste) {
                nbVivantBalliste--; // Mise à jour du compteur
                boutique.setPrix(boutique.getPrix()+50); // La destruction des ennemis permet de gagner de l'argent
            }
            else if(c.getRemoved().get(i) instanceof Behemoth){
                nbVivantBehemoth--; // Mise à jour du compteur
                boutique.setPrix(boutique.getPrix()+200); // La destruction des ennemis permet de gagner de l'argent
            }

        }

        for (int i = 0; i < c.getAddedSubList().size(); i++) {
            creerSprite(c.getAddedSubList().get(i)); // Creation du sprite

            if (c.getAddedSubList().get(i) instanceof Scavenger) {
                nbVivantScavenger++; // Mise à jour du compteur
            } else if (c.getAddedSubList().get(i) instanceof Balliste) {
                nbVivantBalliste++; // Mise à jour du compteur
            }
            else if(c.getAddedSubList().get(i) instanceof Behemoth){
                nbVivantBehemoth++; // Mise à jour du compteur
            }

        }
        // Mise à jour des labels
        LabelnbVivantScavenger.setText(String.valueOf(nbVivantScavenger));
        LabelnbVivantBalliste.setText(String.valueOf(nbVivantBalliste));
        LabelnbVivantBehemoth.setText(String.valueOf(nbVivantBehemoth));
    }

    void creerSprite(Ennemi e) {
        /* Methode qui permet de créer les sprites des ennemis present dans l'environnement */
        ImageView scavengerProximite;
        ImageView ballisteProximite;
        ImageView behemothProximite;

        if (e instanceof Scavenger) {
            behemothProximite = null;
            ballisteProximite = null;
            scavengerProximite = new ImageView(ImageScavenger);
            scavengerProximite.translateXProperty().bind(e.getXProperty());
            scavengerProximite.translateYProperty().bind(e.getYProperty());

            /* nouvelle image pour les ennemis à porté d'une tour */
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

                /* nouvelle image pour les ennemis à porté d'une tour */
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

                    /* nouvelle image pour les ennemis à porté d'une tour */
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
            scavengerProximite.setId(e.getId()); /* Attribution d'un identifiant à l'objet ImageView */
            panneau_de_jeu.getChildren().add(scavengerProximite); /* Ajout du sprite au panneau de jeu */
        }
        if (ballisteProximite != null) {
            ballisteProximite.setId(e.getId()); /* Attribution d'un identifiant à l'objet ImageView */
            panneau_de_jeu.getChildren().add(ballisteProximite); /* Ajout du sprite au panneau de jeu */
        }
        if (behemothProximite != null) {
            behemothProximite.setId(e.getId()); /* Attribution d'un identifiant à l'objet ImageView */
            panneau_de_jeu.getChildren().add(behemothProximite); /* Ajout du sprite au panneau de jeu */
        }
    }
}
