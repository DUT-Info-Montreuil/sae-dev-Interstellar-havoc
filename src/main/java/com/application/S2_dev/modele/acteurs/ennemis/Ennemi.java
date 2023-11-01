package com.application.S2_dev.modele.acteurs.ennemis;

import com.application.S2_dev.modele.acteurs.Acteur;
import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.données.PixelMoveTimeEvent;
import com.application.S2_dev.modele.acteurs.objet.Objet;
import com.application.S2_dev.modele.map.Terrain;

public abstract class Ennemi extends Acteur {

    protected Terrain terrain; // Terrain sur lequel évolue l'ennemi
    protected int i; // Index de la cellule dans le chemin le plus court
    protected Boolean enCoursAttaque;
    protected Cellule celluleCourante , celluleSuivante ; // Cellules courante et suivante de l'ennemi (x et y)

    //Constructeur de l'ennemi
    public Ennemi(double valX, double valY, Terrain terrain) {
        super(valX, valY);
        this.terrain = terrain;
        this.i = 0;
        this.id = "E" + compteur;
    }
    @Override
    public void attaquerActeur(Acteur tour) {
        if (this.estDansPortee(tour)) {
            // Infliger des dégâts à la tour
            tour.infligerDegats(degats);
        }
    }

    public boolean objetProximite(Objet objet) {
        // Verifie si un objet est a proximité de l'ennemi
        double distance = calculerDistance(objet.getX(), objet.getY());
        return distance <= 10;
    }
    protected boolean AttaquerObjet(){
        return !enCoursAttaque;
    }
    // Si un objet est a proximité, l'ennemi attaque
    public  void attaqueObjet(Objet objet){
        if(objetProximite(objet)) {
            enCoursAttaque = true;
            objet.degat(1);
        }
        enCoursAttaque = false;
    }
    public boolean destinationFinaleAtteinte() {
        // Verifie si l'ennemi a atteint la base finale
        return i >= this.terrain.getPlusCourtChemin().size();
    }
    public void agir() {
        celluleCourante = terrain.getPlusCourtChemin().get(i);
        celluleSuivante = i > 0 ? terrain.getPlusCourtChemin().get(i - 1) : null;

        if (celluleSuivante != null) {
            if (terrain.getCase1(celluleSuivante.getI(), celluleSuivante.getJ()) == 1) {
                int diffX = celluleCourante.getI() - celluleSuivante.getI();
                int diffY = celluleCourante.getJ() - celluleSuivante.getJ();
                PixelMoveTimeEvent.initAnimation(this, diffX, diffY);
            }
            if (terrain.getCase1(celluleSuivante.getI(), celluleSuivante.getJ()) == 2) {
                while (AttaquerObjet()) {
                    return;
                }
            }
        }
                i++;
        this.toString();
    }
    public Cellule getCelluleCourante() {
        return celluleCourante;
    }
}










   /* public void agir(ObservableList<Ennemi> fileEnnemis) {
        celluleCourante = terrain.getPlusCourtChemin().get(i);
        celluleSuivante = i > 0 ? terrain.getPlusCourtChemin().get(i - 1) : null;

        if (celluleSuivante != null) {
            if (terrain.getCase1(celluleSuivante.getI(), celluleSuivante.getJ()) == 1) {
                int diffX = celluleCourante.getI() - celluleSuivante.getI();
                int diffY = celluleCourante.getJ() - celluleSuivante.getJ();

                // Votre code pour vérifier la superposition avec d'autres ennemis
                if (!superpositionAvecAutresEnnemis(fileEnnemis)) {
                    PixelMoveTimeEvent.initAnimation(this, diffX, diffY);
                }
            }
            if (terrain.getCase1(celluleSuivante.getI(), celluleSuivante.getJ()) == 2) {
                while (AttaquerObjet()) {
                    return;
                }
            }
        }
        i++;
        this.toString();
    }

    // Votre méthode pour vérifier la superposition avec d'autres ennemis
    private boolean superpositionAvecAutresEnnemis(ObservableList<Ennemi> fileEnnemis) {
        for (Ennemi ennemi : fileEnnemis) {
            if (ennemi != this && ennemi.getCelluleCourante() == celluleSuivante) {
                return true;
            }
        }
        return false;
    }*/


