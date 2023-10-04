package com.application.S2_dev.modele;

import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;

public class Boutique {
    private final IntegerProperty prix; // prix de base à chaque debut de jeu
    private final Timeline gameLoop;

    public Boutique(Timeline gameLoop) {
        prix = new SimpleIntegerProperty(Parametre.argentDebutJoueur);
        this.gameLoop = gameLoop;
    }

    public void MessageArgent() {
        /* Affichage d'un message en cas de manque d'argent */
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Vous n'avez pas assez d'argent");
        alert.setOnShowing(e -> {
            this.gameLoop.pause(); // Mettre en pause de la gameLoop pendant que le joueur lit le message
        });
        alert.setOnHidden(e -> {
            this.gameLoop.play();
        });
        alert.showAndWait();
    }

    public void MessageMaintenance() {
        /* Affichage d'un message en cas de manque d'argent */
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Vous etes sur le point de reparer toutes vos tour\n" +
                " Appuyer sur le chemin predefinit pour valider votre achat");
        alert.setOnShowing(e -> {
            this.gameLoop.pause(); // Mettre en pause de la gameLoop pendant que le joueur lit le message
        });
        alert.setOnHidden(e -> {
            this.gameLoop.play();
        });
        alert.showAndWait();
    }

    public void MessagePasDeTour() {
        /* Affichage d'un message en cas de manque d'argent */
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Vous n'avez aucune tour sur le terrain de jeu !");
        alert.setOnShowing(e -> {
            this.gameLoop.pause(); // Mettre en pause de la gameLoop pendant que le joueur lit le message
        });
        alert.setOnHidden(e -> {
            this.gameLoop.play();
        });
        alert.showAndWait();
    }

    /* les getter et setter */
    public int getPrix() {
        return prix.getValue();
    }

    public IntegerProperty prixProperty() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix.setValue(prix);
    }
}
