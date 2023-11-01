package com.application.S2_dev.modele.map;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.acteurs.tours.Tour;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.net.URL;

public class BlastComponent extends Canvas {
    private final Tour tour;
    private final Ennemi ennemi;
    private ImageView vueBlast;

    public BlastComponent(Tour tour, Ennemi ennemi) {
        this.tour = tour;
        this.ennemi = ennemi;
        this.vueBlast = null;
    }

    public void add(Pane pane) {

        URL uriBlast = Main.class.getResource("image/ennemis/explode.png");

        // Charge l'image de la tour
        Image imageBlast = new Image(String.valueOf(uriBlast));
        vueBlast = new ImageView(imageBlast);

        // Lie les propriétés de translation de la tour aux propriétés de translation de la tour
        vueBlast.translateXProperty().bind(ennemi.getXProperty());
        vueBlast.translateYProperty().bind(ennemi.getYProperty());

        // Vérifie si l'ImageView est valide
        if (vueBlast != null) {
            vueBlast.setId(tour.getId());
            pane.getChildren().add(vueBlast);
        }
    }

    public void remove(Pane pane) {
        pane.getChildren().remove(vueBlast);
    }
}

