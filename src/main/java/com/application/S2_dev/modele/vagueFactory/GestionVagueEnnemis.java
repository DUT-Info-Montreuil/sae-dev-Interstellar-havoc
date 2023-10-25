package com.application.S2_dev.modele.vagueFactory;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.vagueFactory.VagueStrategy;

public class GestionVagueEnnemis {
    private final Environnement environnement;
    private final Terrain terrain;
    private VagueStrategy vagueStrategy;

    public GestionVagueEnnemis(Environnement environnement, Terrain terrain,VagueStrategy vg) {
        this.environnement = environnement;
        this.terrain = terrain;
        this.vagueStrategy = vg;
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
