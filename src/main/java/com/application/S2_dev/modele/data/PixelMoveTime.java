package com.application.S2_dev.modele.data;

import com.application.S2_dev.Parametre;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.swing.*;

public class PixelMoveTime {
    public void initAnimation(Ennemi e) {
        Timeline time= new Timeline();
        time.setCycleCount(16);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.02), ev -> {
//            if (cellulePrecedente != null) {
//                int diffX = celluleCourante.getX() - cellulePrecedente.getX();
//                int diffY = celluleCourante.getY() - cellulePrecedente.getY();
//
//                if (diffX != 0) {
//                    this.setY(this.getY() + (diffX > 0 ? largeurCase : -largeurCase));
//                }
//
//                if (diffY != 0) {
//                    this.setX(this.getX() + (diffY > 0 ? hauteurCase : -hauteurCase));
//                }
        }
        );
        time.getKeyFrames().add(kf);
        time.play();
    }
}
