package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public class GestionVagueEnnemis {
    private final Environnement environnement;
    private final Terrain terrain;
    private VagueStrategy vagueStrategy;

    public GestionVagueEnnemis(Environnement environnement, Terrain terrain) {
        this.environnement = environnement;
        this.terrain = terrain;
    }

    public void setVagueStrategy(VagueStrategy vagueStrategy) {
        this.vagueStrategy = vagueStrategy;
    }

    public void ajouterVague() {
        if (vagueStrategy != null) {
            vagueStrategy.ajouterVague(environnement, terrain);
        }
    }
}
