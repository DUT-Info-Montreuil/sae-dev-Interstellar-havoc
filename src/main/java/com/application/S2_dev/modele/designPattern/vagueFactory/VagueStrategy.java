package com.application.S2_dev.modele.designPattern.vagueFactory;

import com.application.S2_dev.modele.designPattern.EnnemiFactory.EnnemiFactory;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;

public interface VagueStrategy  {

    void ajouterVague(Environnement environnement, Terrain terrain);
}
