package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import java.net.URL;

public class TerrainVue {
    private TilePane tilePane;
    private Terrain terrain ;
    private URL urlImageMur = Main.class.getResource("image/map/mur.png");
    private Image imageMur = new Image(String.valueOf(urlImageMur));
    private URL urlImageTerrain = Main.class.getResource("image/map/terrain.jpg");
    private Image imageTerrain = new Image(String.valueOf(urlImageTerrain));
    private URL urlImageChemin = Main.class.getResource("image/map/chemin.jpg");
    private Image imageChemin = new Image(String.valueOf(urlImageChemin));

    private URL urlBaseFinale = Main.class.getResource("image/map/base.png");
    private Image baseFinale = new Image(String.valueOf(urlBaseFinale));

    private URL urlBlockage = Main.class.getResource("image/map/blockage.jpg");
    private Image block = new Image(String.valueOf(urlBlockage));

    private URL urlTowerBase = Main.class.getResource("image/map/tour.png");
    private Image base = new Image(String.valueOf(urlTowerBase));
    private Environnement env;

    public TerrainVue(TilePane tilePane, Terrain terr, Environnement env) {
        this.tilePane = tilePane;
        this.terrain = terr;
        this.env = env;

    }

    /**
     * Affiche le terrain en ajoutant les images correspondantes aux différentes cases.
     */
    public void afficherTerrain(){
        for(int i = 0; i<terrain.getTerrain().length; i++) {
            for (int j = 0; j < terrain.getTerrain()[i].length; j++) {
                switch (terrain.getCase1(i,j)) {
                    case 0:
                        ImageView im = new ImageView(imageTerrain);
                        tilePane.getChildren().add(im);
                        break;
                    case 1:
                        ImageView im1 = new ImageView(imageChemin);
                        tilePane.getChildren().add(im1);
                        break;
                    case 2:
                        ImageView imm = new ImageView(imageMur);
                        tilePane.getChildren().add(imm);
                        break;
                    case 3:
                        ImageView im3 = new ImageView(baseFinale);
                        tilePane.getChildren().add(im3);
                        break;
                    case 4:
                        ImageView im4 = new ImageView(block);
                        tilePane.getChildren().add(im4);
                        break;
                    case 5:
                        ImageView im5 = new ImageView(base);
                        tilePane.getChildren().add(im5);
                        break;
                }
            }
        }
    }

    /* .*/
    /**
     * Met à jour l'image d'une case spécifique du terrain
     */
    public void setImage(int ligne, int colonne, int nouvelleValeur) {
        int index = ligne * terrain.getTerrain()[0].length + colonne;
        tilePane.getChildren().remove(index);

        switch (nouvelleValeur) {
            case 0:
                ImageView im = new ImageView(imageTerrain);
                tilePane.getChildren().add(index, im);
                break;
            case 1:
                ImageView im1 = new ImageView(imageChemin);
                tilePane.getChildren().add(index, im1);
                break;
            case 2:
                ImageView imm = new ImageView(imageMur);
                tilePane.getChildren().add(index, imm);
                break;
            case 4:
                //System.out.println("et 1");
                ImageView imm3 = new ImageView(block);
                tilePane.getChildren().add(index, imm3);
                // System.out.println("et 22");
                break;
            default:

        }
        terrain.getCheminPlusCourt();
    }
}



