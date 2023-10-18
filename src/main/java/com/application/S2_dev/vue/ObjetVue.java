package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.Boutique;
import com.application.S2_dev.modele.données.TerrainType;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.acteurs.objet.*;
import com.application.S2_dev.modele.acteurs.objet.Mur;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ObjetVue implements ListChangeListener<Objet> {

    private Environnement environnement;
    private TerrainVue terrainVue;
    private Pane panneau_de_jeu;
    private Terrain terrain;
    private Objet objet;
    private Label labelBombe, labelMur, LabelHydrogene, LabelMaintenance;
    private URL urlBombe, urlHydrogene, urlExplosion;
    private Image ImageBombe, ImageHydrogene, ImageExplosion;
    private ImageView imageObjet;
    private String objetSelectionne;
    private  int[] pos;
    private Boutique boutique;
    private static Clip clipFond ;// music pour les annimations


    public ObjetVue(Pane pane, Environnement environnement, Label Bombe, Label LabelHydrogene, Label Mur, Terrain terrain, TerrainVue terrainVue, Boutique boutique, Label labelMaintenance){
        this.environnement = environnement;
        this.panneau_de_jeu = pane;
        this.labelBombe = Bombe;
        this.LabelHydrogene = LabelHydrogene;
        this.labelMur = Mur;
        this.terrain = terrain;
        this.terrainVue =  terrainVue;
        this.boutique = boutique;
        this.LabelMaintenance = labelMaintenance;


        /* URL et image des sprites */
        urlBombe = Main.class.getResource("image/objet/bombe.png");
        ImageBombe = new javafx.scene.image.Image(String.valueOf(urlBombe));

        urlHydrogene = Main.class.getResource("image/objet/hydrogene.png");
        ImageHydrogene = new javafx.scene.image.Image(String.valueOf(urlHydrogene));

        urlExplosion = Main.class.getResource("image/objet/explosion.png");
        ImageExplosion = new javafx.scene.image.Image(String.valueOf(urlExplosion));

    }

    @Override
    public void onChanged(Change<? extends Objet> c) {
        while (c.next()) {
            System.out.println("les ajouts Objet: " + c.getAddedSubList());
            System.out.println("les suppressions Objet: " + c.getRemoved());
        }
        for(int i =0; i<c.getAddedSubList().size(); i++){
            if(c.getAddedSubList().get(i) instanceof Bombe){
                boutique.setPrix(boutique.getPrix()-((Bombe) c.getAddedSubList().get(i)).getPrix()); // Mise à jour du prix
            }
            else if(c.getAddedSubList().get(i) instanceof Hydrogene){
                boutique.setPrix(boutique.getPrix()-((Hydrogene) c.getAddedSubList().get(i)).getPrix()); // Mise à jour du prix
            }
            else if(c.getAddedSubList().get(i) instanceof Mur){
                boutique.setPrix(boutique.getPrix()-((Mur) c.getAddedSubList().get(i)).getPrix()); // Mise à jour du prix
            }
            else if(c.getAddedSubList().get(i) instanceof Maintenance){
                boutique.setPrix(boutique.getPrix()-((Maintenance) c.getAddedSubList().get(i)).getPrix()); // Mise à jour du prix
            }
        }
        for (int i = 0; i < c.getRemoved().size(); i++) {
            if(c.getRemoved().get(i) instanceof Bombe) {
                animationBombe(c.getRemoved().get(i)); /* Chargement de l'image d'animation pour la bombe */
            }
            if(c.getRemoved().get(i) instanceof Mur) {
                /* Placement du chemin apres la destruction du mur */
                ((Mur) objet).PlacerMur(pos[0], pos[1]);
                terrainVue.setImage(pos[0], pos[1],1);
            }
            if(c.getRemoved().get(i) instanceof CheminBloque) {
                /* Placement du chemin apres la destruction du mur */
                int ligne = ((CheminBloque)c.getRemoved().get(i)).getLigne();
                int colonne = ((CheminBloque)c.getRemoved().get(i)).getColonne();
                ((CheminBloque) c.getRemoved().get(i)).PlacerMur(ligne, colonne);
                terrainVue.setImage(ligne, colonne,1);

            }
            if(c.getRemoved().get(i) instanceof Hydrogene) {
                animationBombe(c.getRemoved().get(i)); /* Chargement de l'image d'animation pour la bombe */
                URL urlImageVaiL = Main.class.getResource("sons/bruit.wav");
                String s = urlImageVaiL.getPath();
                PlayMusicFond(s); /* music d'animation */
            }
            ImageView sprite = (ImageView) panneau_de_jeu.lookup("#" + c.getRemoved().get(i).getId());
            panneau_de_jeu.getChildren().remove(sprite); /* Suppression du sprite du panneau de jeu */
        }
    }
    public void animationBombe(Objet objet){
        /* Animation de la bombe.*/
        ImageView imageObjet = new ImageView(ImageExplosion);
        imageObjet.setX(objet.getX());
        imageObjet.setY(objet.getY());
        if(objet instanceof Hydrogene){
            imageObjet.setScaleX(20);
            imageObjet.setScaleY(20);
        }
        imageObjet.setOpacity(0);
        panneau_de_jeu.getChildren().add(imageObjet);
        // Apparation de l'image d"explosion
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(imageObjet.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(imageObjet.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(1), new KeyValue(imageObjet.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(imageObjet.opacityProperty(), 0))
        );
        timeline.play();
        timeline.setOnFinished(event -> panneau_de_jeu.getChildren().remove(imageObjet)); /* Suppression de l'image du panneau de jeu */
    }
    public void AjoutObjet(){
        /* Méthode appelée lors de l'ajout d'un objet.*/
        labelBombe.setOnMouseClicked(event -> {
            objetSelectionne = "Bombe";
            setObjetSelectionne();
        });

        LabelHydrogene.setOnMouseClicked(event -> {
            objetSelectionne = "Hydrogene";
            setObjetSelectionne();
        });

        labelMur.setOnMouseClicked( h -> {
            objetSelectionne = "Mur";
            setObjetSelectionne();
        });

        LabelMaintenance.setOnMouseClicked( h -> {
            if(environnement.getTour().size() == 0){
                boutique.MessagePasDeTour();
            }
            else {
                objetSelectionne = "Maintenance";
                boutique.MessageMaintenance();
                setObjetSelectionne();
            }

        });

        panneau_de_jeu.setOnMouseClicked( h -> {
            pos = terrain.getPosDansCarte((int)h.getX(), (int)h.getY());
            if (boutique.getPrix() >= objet.getPrix()) {
                apparitionObjet(pos[0], pos[1]);
            }
            else{
                boutique.MessageArgent();
            }
        });
        AfficherCheminBloque();
    }
    void creerObjet(Objet objet) {
        /* Crée un objet en fonction de l'objet sélectionné.*/
            if (objet instanceof Bombe) {
                imageObjet = new ImageView(ImageBombe);
            } else if (objet instanceof Hydrogene) {
                imageObjet = new ImageView(ImageHydrogene);
            }
            if (imageObjet != null) {
                imageObjet.setId(objet.getId());
                panneau_de_jeu.getChildren().add(imageObjet);
            }
        }
    public void apparitionCheminBloque(int ligne, int colonne) {
        /* Apparition du chemin bloqué sur la grille.*/
        double x = colonne * 16;
        double y = ligne * 16;
        objet = new CheminBloque(environnement, terrain, ligne,colonne);
        objet.setX(x);
        objet.setY(y);
        environnement.ajoutObjet(objet);
    }
    public void AfficherCheminBloque(){
        /*affichage du chemin bloque */
        for(int i = 0; i<terrain.getTerrain().length; i++) {
            for (int j = 0; j < terrain.getTerrain()[i].length; j++) {
                if (terrain.getCase1(i, j) == 2) {
                    apparitionCheminBloque(i,j);
                }
            }
        }
    }
    public void setObjetSelectionne(){
       /* Définit l'objet sélectionné en fonction de l'option choisie.*/
        if (objetSelectionne.equals("Bombe")) {
            objet = new Bombe(environnement, terrain);
        }
        else if (objetSelectionne.equals("Hydrogene")) {
            objet = new Hydrogene(environnement, terrain);
        }
        else if (objetSelectionne.equals("Mur")) {
            objet = new Mur(environnement, terrain);
        }
        else if (objetSelectionne.equals("Maintenance")) {
            objet = new Maintenance(environnement, terrain);

        }
    }
    public void apparitionObjet(int ligne, int colonne) {
        /* Apparition d'un objet sur la grille.*/
        double x = colonne * 16;
        double y = ligne * 16;
            if (emplacementObjet(ligne, colonne)) {
                    if (objet instanceof Bombe) {
                        creerObjet(objet);
                        imageObjet.setX(x);
                        imageObjet.setY(y);

                    } else if (objet instanceof Hydrogene) {
                        creerObjet(objet);
                        imageObjet.setX(x);
                        imageObjet.setY(y);

                    } else if (objet instanceof Mur) {
                        ((Mur) objet).PlacerMur(ligne, colonne);
                        terrainVue.setImage(ligne, colonne, 2);

                    } else if (objet instanceof Maintenance) {

                        for(int i = 0; i < environnement.getTour().size(); i++){
                            environnement.getTour().get(i).setVie();
                        }


                    }
                    objet.setX(x);
                    objet.setY(y);
                    environnement.ajoutObjet(objet);


            }else {
                System.out.println("Emplacement impossible");
            }
    }
    public boolean emplacementObjet(int ligne, int colonne) {
        /* verifie sur l'emplacement de l'objet est possible */
        if (terrain.getCase(ligne, colonne) == TerrainType.chemin){
            return true;
        }
        return false;
    }
    public static void PlayMusicFond(String location){
        /* music de fond */
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(location));
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
        try {
            clipFond = (Clip) AudioSystem.getLine(info);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clipFond.open(audioInputStream);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clipFond.start();
    }
}
