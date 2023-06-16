package com.application.S2_dev.modele;

import com.application.S2_dev.Parametre;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;

public class Boutique {
    private IntegerProperty prix ;
    private Timeline gameLoop;
    public Boutique(Timeline gameLoop){
        prix = new SimpleIntegerProperty(Parametre.argentDebutJoueur);
        this.gameLoop = gameLoop;
    }

    public int getPrix() {
        return prix.getValue();
    }

    public IntegerProperty prixProperty() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix.setValue(prix);
    }
    public void MessageArgent(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Vous n'avez pas assez d'argent");
        alert.setOnShowing(e -> {
            this.gameLoop.pause();
        });
        alert.setOnHidden(e -> {
            this.gameLoop.play();
        });
        alert.showAndWait();
    }
}
