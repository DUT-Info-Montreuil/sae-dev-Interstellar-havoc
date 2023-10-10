package com.application.S2_dev.modele.EnnemiFactory;

import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Terrain;

public interface EnnemiFactory {
    Ennemi créerEnnemi(Terrain terrain);
}
