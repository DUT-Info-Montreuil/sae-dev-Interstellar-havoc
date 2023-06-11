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

        URL urlTowerBase = Main.class.getResource("image/map/base.png");
        Image base = new Image(String.valueOf(urlTowerBase));

        for(int i = 0; i<terrain.getTerrain().length; i++) {
            for (int j = 0; j < terrain.getTerrain()[i].length; j++) {
                switch (terrain.getCase(i,j)) {
                    case terrain:
                        ImageView im1 = new ImageView(imageHerbe);
                        tilePane.getChildren().add(im1);
                        break;
                    case chemin:
                        ImageView im2 = new ImageView(imageChemin);
                        tilePane.getChildren().add(im2);
                        break;
                    case cheminBloque:
                        ImageView im3 = new ImageView(imageCheminBloque);
                        tilePane.getChildren().add(im3);
                        break;
                    case base_tourelle:
                        ImageView im4 = new ImageView(base);
                        tilePane.getChildren().add(im4);
                        break;
                }
            }
        }
    }
}



