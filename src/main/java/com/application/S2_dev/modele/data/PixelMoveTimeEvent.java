package com.application.S2_dev.modele.data;

import com.application.S2_dev.Parametre;
import com.application.S2_dev.modele.ennemis.Ennemi;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PixelMoveTimeEvent {
    public static void initAnimation(Ennemi e,int diffX, int diffY) {
        Timeline time= new Timeline();
        time.setCycleCount(16);
        int y = diffX>0 ? 1 : -1;
        int x = diffY>0 ? 1 : -1;
        KeyFrame kf = new KeyFrame(
                Duration.seconds(Parametre.tempsUnTour/16), ev -> {
                if (diffX != 0) {
                    e.setY(e.getY() + y);
                }

                if (diffY != 0) {
                    e.setX(e.getX() + x);
                }
            }
        );
        time.getKeyFrames().add(kf);
        time.play();
    }
}
