package com.application.S2_dev.modele.EnnemiFactory;

import com.application.S2_dev.modele.Parametre;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.map.Terrain;

public class BallisteFactory implements EnnemiFactory{
    @Override
    public Ennemi créerEnnemi(Terrain terrain) {
       return new Balliste(Parametre.posXennemi, Parametre.posYennemi, terrain) ;
    }
}
