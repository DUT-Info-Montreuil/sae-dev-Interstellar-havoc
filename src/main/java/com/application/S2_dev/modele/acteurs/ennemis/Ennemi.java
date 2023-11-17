package com.application.S2_dev.modele.acteurs.ennemis;

import com.application.S2_dev.modele.acteurs.Acteur;
import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.données.PixelMoveTimeEvent;
import com.application.S2_dev.modele.acteurs.objet.Objet;
import com.application.S2_dev.modele.map.Terrain;

import java.util.LinkedList;

public abstract class Ennemi extends Acteur {

    protected Terrain terrain; // Terrain sur lequel évolue l'ennemi
    private LinkedList<Cellule> chemin;


    protected int i; // Index de la cellule dans le chemin le plus court
    protected Boolean enCoursAttaque;

    protected boolean peutContournerMur; // Indique si l'ennemi peut contourner les murs

    protected Cellule celluleCourante , celluleSuivante ; // Cellules courante et suivante de l'ennemi (x et y)

    //Constructeur de l'ennemi
    public Ennemi(double valX, double valY, Terrain terrain, boolean peutContournerMur) {
        super(valX, valY);
        this.terrain = terrain;
        this.peutContournerMur = peutContournerMur;
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
        // Obtenez le chemin approprié en fonction de la capacité de l'ennemi à contourner les murs
        LinkedList<Cellule> chemin = terrain.getCheminPourEnnemi(peutContournerMur);

        // Vérifiez si l'index actuel dépasse la longueur du chemin
        return i >= chemin.size();
    }

    public void agir () {
        LinkedList<Cellule> chemin = terrain.getCheminPourEnnemi(peutContournerMur);
        if (i < chemin.size()) {
            celluleCourante = chemin.get(i);
            celluleSuivante = i > 0 ? chemin.get(i - 1) : null;
        }
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

    public Cellule getCelluleCourante () {
        return celluleCourante;
    }
    public void setChemin(LinkedList<Cellule> nouveauChemin) {
        this.chemin = nouveauChemin;
        this.i = 0; // Réinitialiser l'index si nécessaire
    }

    public LinkedList<Cellule> getChemin() {
        return this.chemin;
    }

    public boolean peutContournerMur() {
        return this.peutContournerMur;
    }

    protected boolean cheminBloqué() {
        return chemin == null || chemin.isEmpty();
    }

}










