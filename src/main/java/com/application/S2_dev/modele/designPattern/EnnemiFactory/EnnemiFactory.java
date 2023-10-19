package com.application.S2_dev.modele.designPattern.EnnemiFactory;

import com.application.S2_dev.modele.acteurs.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Terrain;

public interface EnnemiFactory {
    Ennemi créerEnnemi(Terrain terrain);
}
