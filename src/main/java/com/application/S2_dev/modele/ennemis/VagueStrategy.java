package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public interface VagueStrategy  {
    void ajouterVague(Environnement environnement, Terrain terrain);
}
