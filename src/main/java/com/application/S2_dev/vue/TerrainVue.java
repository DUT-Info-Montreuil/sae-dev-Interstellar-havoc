package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.map.Terrain;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import java.net.URL;

public class TerrainVue {
    private TilePane tilePane;
    Terrain terrain ;

    public TerrainVue(TilePane tilePane, Terrain terr) {
        this.tilePane = tilePane;
        this.terrain = terr;
    }

    public void afficherTerrain(){
        URL urlImageHerbe = Main.class.getResource("image/map/terrain.png");
        Image imageHerbe = new Image(String.valueOf(urlImageHerbe));

        URL urlImageChemin = Main.class.getResource("image/map/chemin.png");
        Image imageChemin = new Image(String.valueOf(urlImageChemin));

        URL urlImageCheminBloque = Main.class.getResource("image/map/cheminBloque.png");
        Image imageCheminBloque = new Image(String.valueOf(urlImageCheminBloque));

        URL urlBat = Main.class.getResource("image/map/base.png");
        Image bat = new Image(String.valueOf(urlBat));

        for(int i = 0; i<terrain.getTerrain().length; i++) {
            for (int j = 0; j < terrain.getTerrain()[i].length; j++) {
                switch (terrain.getCase(i,j)) {
                    case 0:
                        ImageView im = new ImageView(imageHerbe);
                        tilePane.getChildren().add(im);
                        break;
                    case 1:
                        ImageView im1 = new ImageView(imageChemin);
                        tilePane.getChildren().add(im1);
                        break;
                    case 2:
                        ImageView imm = new ImageView(imageCheminBloque);
                        tilePane.getChildren().add(imm);
                        break;
                    case 3:
                        ImageView im3 = new ImageView(bat);
                        tilePane.getChildren().add(im3);
                        break;
                }
            }
        }
    }
}



