package com.application.S2_dev.modele.map;

import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import javafx.scene.layout.Pane;

import java.util.Random;

public class GestionVagueEnnemis {
    private final Environnement environnement;
    private final Terrain terrain;
    private final Pane pane;
    private final Random random = new Random();

    public GestionVagueEnnemis(Environnement environnement, Terrain terrain, Pane pane) {
        this.environnement = environnement;
        this.terrain = terrain;
        this.pane = pane;
    }

    public void ajouterEnnemi(Ennemi ennemi) {
        if (ennemi != null) {
            environnement.getEnnemis().add(ennemi);
        }
    }

    public void ajouterVague() {
        boolean spawnPossible = true;
        int ennemisMax = 5; // Maximum d'ennemis sur le terrain
        int ennemisActuels = environnement.getEnnemis().size();

        if (ennemisActuels >= ennemisMax) {
            spawnPossible = false;
        }

        if (spawnPossible) {
            int ennemisAAjouter = ennemisMax - ennemisActuels; // ennemis à ajouter dans la liste

            /* on créer un ennemi si la liste est vide */
            if (ennemisActuels == 0) {
                Ennemi en = new Balliste(5, 21, terrain);
                ajouterEnnemi(en);
                ennemisAAjouter--;
            }

            /* On parcourt le nombre d'ennemis à ajouter pour on fait un random */
            for (int compteur = 0; compteur < ennemisAAjouter; compteur++) {
                int spawnRate = random.nextInt(150) + 1;
                switch (spawnRate) {
                    case 1:
                        Ennemi behemoth = new Behemoth(5, 21, terrain);
                        ajouterEnnemi(behemoth);
                        break;
                    case 2:
                        Ennemi scavenger = new Scavenger(5, 21, terrain);
                        ajouterEnnemi(scavenger);
                        break;
                    case 3:
                        Ennemi balliste = new Balliste(5, 21, terrain);
                        ajouterEnnemi(balliste);
                        break;
                }
            }
        }
    }
}
