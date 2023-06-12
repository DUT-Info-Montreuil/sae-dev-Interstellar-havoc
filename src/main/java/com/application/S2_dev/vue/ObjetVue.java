package com.application.S2_dev.vue;

import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.objet.Bombe;
import com.application.S2_dev.modele.objet.Maintenance;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ObjetVue implements ListChangeListener<Objet> {
    private Label labelBombe;
    private Label labelMur;
    private Label labelMaintenance;
    private Environnement environnement;
    private Objet objet;

    public ObjetVue(Environnement environnement,  Label labelBombe,  Label labelMur){
        this.environnement = environnement;
        this.labelBombe =  labelBombe;
        this.labelMur = labelMur;

    }

    @Override
    public void onChanged(Change<? extends Objet> change) {

    }

    public void AjoutObjet(){
        labelBombe.setOnMouseClicked( h -> {
            objet = new Bombe();
        });

        labelMur.setOnMouseClicked( h -> {
            objet = new Mur();
        });
        labelMaintenance.setOnMouseClicked( h -> {
            System.out.println("AHAHAHAH");
            Maintenance.reparer();
        }) ;

    }

   /* void creerObjet(Objet o) {
        ImageView scavenger = null;
        ImageView balliste = null;
        ImageView behemoth = null;


        if (o instanceof Bombe) {
            scavenger = new ImageView(ImageScavenger);
            scavenger.translateXProperty().bind(e.getXProperty());
            scavenger.translateYProperty().bind(e.getYProperty());

        } else if (o instanceof Mur) {
            balliste = new ImageView(ImageBalliste);
            balliste.translateXProperty().bind(e.getXProperty());
            balliste.translateYProperty().bind(e.getYProperty());
            // System.out.println(balliste.getTranslateX()+" et y "+ balliste.getTranslateY());


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
    }*/
}
