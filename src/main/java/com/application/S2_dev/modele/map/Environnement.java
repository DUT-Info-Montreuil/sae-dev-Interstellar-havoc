package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Ennemi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Environnement {

    // Référence au pane utilisé pour afficher les ennemis (non utilisé dans le code fourni)
    Pane pane;

    // Taux d'apparition des ennemis (non utilisé dans le code fourni)
    private int spawnRate;

    // Référence au terrain (non utilisé dans le code fourni)
    private Terrain terr;

    // Liste des ennemis morts (non utilisée dans le code fourni)
    private ArrayList<Ennemi> mort;

    // Liste des ennemis en jeu
    private ObservableList<Ennemi> ennemis;

    public Environnement() {
        mort = new ArrayList<>();
        ennemis = FXCollections.observableArrayList();
        terr = new Terrain();
    }

    // Retourne la liste des ennemis en jeu
    public ObservableList<Ennemi> getEnnemis() {
        return ennemis;
    }

    // Retourne la liste des ennemis morts
    public ArrayList<Ennemi> getMort() {
        return mort;
    }

    // Ajoute un ennemi à la liste des ennemis en jeu
    public void ajouter(Ennemi ennemi) {
        ennemis.add(ennemi);
    }

    // Initialise l'environnement en créant plusieurs ennemis (dans cet exemple, 5 Ballistes)
    public void init() {
        for (int i = 0; i < 5; i++) {
            Ennemi ennemi = new Balliste();
            ennemis.add(ennemi);
        }
    }

    // Effectue un tour de jeu en faisant agir tous les ennemis en jeu
    public void unTour() {
        for (int i = 0; i < ennemis.size(); i++) {
            Ennemi ennemi = ennemis.get(i);
            ennemi.agit(16, 16);
            //  pane.getChildren().add(e.getImLent()); // Ajoute l'ennemi au pane (non utilisé dans le code fourni)
        }

        // Vérifie si les ennemis sont vivants ou non
        for (int i = ennemis.size() - 1; i >= 0; i--) {
            Ennemi ennemi = ennemis.get(i);
            if (!ennemi.estVivant()) {
                System.out.println("Mort de l'ennemi : " + ennemi);
                ennemis.remove(i);
                mort.add(ennemi);
            }
        }
    }
}
