package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.data.TerrainType;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.objet.Bombe;
import com.application.S2_dev.modele.objet.Hydrogene;
import com.application.S2_dev.modele.objet.Mur;
import com.application.S2_dev.modele.objet.Objet;
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
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ObjetVue implements ListChangeListener<Objet> {

    private Environnement environnement;
    private TerrainVue terrainVue;
    private Pane panneau_de_jeu;
    private Terrain terrain;
    private Objet objet;
    private Label labelBombe, labelMur, LabelHydrogene;
    private URL urlBombe, urlHydrogene, urlExplosion;
    private Image ImageBombe, ImageHydrogene, ImageExplosion;
    private ImageView imageObjet;
    private String objetSelectionne;
    int[] pos;
    int money ;
    private Label prix;
    private static Clip clipFond ;


    public ObjetVue(Pane pane, Environnement environnement, Label Bombe, Label LabelHydrogene, Label Mur, Label prix, Terrain terrain, TerrainVue terrainVue){
        this.environnement = environnement;
        this.panneau_de_jeu = pane;
        this.labelBombe = Bombe;
        this.LabelHydrogene = LabelHydrogene;
        this.labelMur = Mur;
        this.terrain = terrain;
        this.terrainVue =  terrainVue;
        this.money = Integer.parseInt(prix.getText());
        this.prix = prix;

        urlBombe = Main.class.getResource("image/objet/Bombe.png");
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
            soustraireMoney(c.getAddedSubList().get(i).getPrix());
        }
        for (int i = 0; i < c.getRemoved().size(); i++) {

            ImageView sprite = (ImageView) panneau_de_jeu.lookup("#" + c.getRemoved().get(i).getId());
            panneau_de_jeu.getChildren().remove(sprite);
            if(c.getRemoved().get(i) instanceof Bombe || c.getRemoved().get(i) instanceof Hydrogene ) {
                ImageView imageObjet = new ImageView(ImageExplosion);
                imageObjet.setX(c.getRemoved().get(i).getX());
                imageObjet.setY(c.getRemoved().get(i).getY());
                if(c.getRemoved().get(i) instanceof Hydrogene){
                    imageObjet.setScaleX(20);
                    imageObjet.setScaleY(20);
                }
                imageObjet.setOpacity(0);
                panneau_de_jeu.getChildren().add(imageObjet);

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(imageObjet.opacityProperty(), 0)),
                        new KeyFrame(Duration.seconds(0.5), new KeyValue(imageObjet.opacityProperty(), 1)),
                        new KeyFrame(Duration.seconds(1), new KeyValue(imageObjet.opacityProperty(), 1)),
                        new KeyFrame(Duration.seconds(1.5), new KeyValue(imageObjet.opacityProperty(), 0))
                );
                timeline.play();
                timeline.setOnFinished(event -> panneau_de_jeu.getChildren().remove(imageObjet));
            }
            if(c.getRemoved().get(i) instanceof Mur) {
                    System.out.println("j'ai remit l'image");
                    ((Mur) objet).PlacerMur(pos[0], pos[1]);
                   terrainVue.setImage(pos[0], pos[1]);
            }

            if(c.getRemoved().get(i) instanceof Hydrogene) {
                URL urlImageVaiL = Main.class.getResource("sons/bruit.wav");
                String s = urlImageVaiL.getPath();
                PlayMusicFond(s);
            }
        }
    }
    public void AjoutObjet(){
        labelBombe.setOnMouseClicked(event -> {
           objetSelectionne = "Bombe";
        });

        LabelHydrogene.setOnMouseClicked(event -> {
            objetSelectionne = "Hydrogene";
            System.out.println("hydrooo");
        });

        labelMur.setOnMouseClicked( h -> {
          objetSelectionne = "Mur";
        });

        panneau_de_jeu.setOnMouseClicked( h -> {
            pos = terrain.getPosDansCarte((int)h.getX(), (int)h.getY());
            apparitionObjet(pos[0], pos[1]);
        });
    }
    void creerObjet(Objet objet) {

        if (objet instanceof Bombe) {
            imageObjet = new ImageView(ImageBombe);
        }
        else if (objet instanceof Hydrogene) {
            imageObjet = new ImageView(ImageHydrogene);
        }
        if (imageObjet != null) {
            imageObjet.setId(objet.getId());
            panneau_de_jeu.getChildren().add(imageObjet);
        }
    }
    public void apparitionObjet(int ligne, int colonne) {
        double x = colonne * 16;
        double y = ligne * 16;
        if(Integer.parseInt(prix.getText()) >0) {
            if (emplacementBombe(ligne, colonne)) {
                if (objetSelectionne.equals("Bombe")) {
                    objet = new Bombe(environnement, terrain);
                    creerObjet(objet);
                    imageObjet.setX(x);
                    imageObjet.setY(y);

                } else if (objetSelectionne.equals("Hydrogene")) {
                    objet = new Hydrogene(environnement, terrain);
                    creerObjet(objet);
                    imageObjet.setX(x);
                    imageObjet.setY(y);

                } else if (objetSelectionne.equals("Mur")) {
                    objet = new Mur(environnement, terrain);
                    ((Mur) objet).PlacerMur(ligne, colonne);
                    terrainVue.setImage(ligne, colonne);

                }
                objet.setX(x);
                objet.setY(y);
                environnement.ajoutObjet(objet);
            } else {
                System.out.println("Emplacement impossible");
            }
        }
        else if(objet.getPrix()>money){
            JOptionPane.showMessageDialog(null, "Pas assez d'argent !");
        }
    }
    public boolean emplacementBombe(int ligne, int colonne) {
        if (terrain.getCase(ligne, colonne) == TerrainType.chemin){
            return true;
        }
    return false;
    }
    void soustraireMoney(int val) {
        money -= val;
        prix.setText(String.valueOf(money));
    }
    public static void PlayMusicFond(String location){
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
