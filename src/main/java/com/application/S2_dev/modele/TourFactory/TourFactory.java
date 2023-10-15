package com.application.S2_dev.modele.TourFactory;


import com.application.S2_dev.modele.acteurs.tours.EdisonCoil;
import com.application.S2_dev.modele.acteurs.tours.NikolaCoil;
import com.application.S2_dev.modele.acteurs.tours.OppenheimerCoil;
import com.application.S2_dev.modele.acteurs.tours.Tour;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.map.Terrain;

public interface TourFactory {

    public static Tour creerTour(int colonne, int ligne, int level, TowerType selectedTowerType) {

        // Créer l'objet tour en fonction du type de tour
        Tour tour = null;
        switch(selectedTowerType) {
            case Nikola:
                tour = new NikolaCoil((int) colonne * 16, (int) ligne * 16, level);
                break;
            case Edison:
                tour = new EdisonCoil((int) colonne * 16, (int) ligne * 16, level);
                System.out.println("VIE DE LA TOUR " + tour.getVie());
                break;
            case Oppenheimer:
                tour = new OppenheimerCoil((int) colonne * 16, (int) ligne * 16, level);
                break;
        }
        assert (tour!=null);
        return tour;
    }
}

