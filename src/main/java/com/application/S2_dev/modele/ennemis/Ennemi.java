package com.application.S2_dev.modele.ennemis;

import com.application.S2_dev.modele.bfs.BFS;
import com.application.S2_dev.modele.bfs.Cellule;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.LinkedList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Ennemi {

    private DoubleProperty x; // Position X de l'ennemi
    private DoubleProperty y; // Position Y de l'ennemi
    private Terrain terrain; // Terrain sur lequel évolue l'ennemi
    private int i = 0; // Index de la cellule dans le chemin le plus court
    private String id; // Identifiant de l'ennemi
    public static int compteur = 0; // Compteur d'ennemis pour générer l'identifiant unique
    private int vie; // Points de vie de l'ennemi
    private ImageView vue = null; // Vue de l'ennemi (utilisée pour l'affichage graphique)
    private Image imagePrincipale = null, imageSecondaire = null; // Images de l'ennemi (utilisées pour l'affichage graphique)
    private BFS bfs; // Algorithme de recherche du plus court chemin
    private LinkedList<Cellule> plusCourtChemin; // Plus court chemin vers la destination

    public Ennemi(double valX, double valY) {
        x = new SimpleDoubleProperty(valX);
        y = new SimpleDoubleProperty(valY);
        terrain = new Terrain();
        this.id = "E" + compteur;
        compteur++;
        this.vie = 100;
        int[] depart = {1, 0};
        int[] fin = {12, 60};
        bfs = new BFS();
        plusCourtChemin = bfs.plusCourtChemin(terrain.getTerrain(), depart, fin);
    }

    public String getId() {
        return id;
    }

    public DoubleProperty getXProperty() {
        return x;
    }

    public DoubleProperty getYProperty() {
        return y;
    }

    public double getX() {
        return x.getValue();
    }

    public double getY() {
        return y.getValue();
    }

    public void deplacer(Cellule cellule) {
        if (cellule != null) {
            // Effectuer l'action de déplacement vers la cellule donnée
        }
    }

    public void subirDegats(int degats) {
        vie -= degats;
    }

    public boolean estVivant() {
        return vie > 0;
    }
    public abstract void attaquerTour(Tour tour);

    public void agir(double largeurCase, double hauteurCase) {
        if (i >= plusCourtChemin.size())
            return;

        Cellule celluleCourante = null;
        Cellule cellulePrecedente = null;

        try {
            celluleCourante = plusCourtChemin.get(i);
            cellulePrecedente = i > 0 ? plusCourtChemin.get(i - 1) : null;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        if (cellulePrecedente != null) {
            if (celluleCourante.getX() != cellulePrecedente.getX()) {
                if (celluleCourante.getX() > cellulePrecedente.getX()) {
                    this.setY(this.getY() + largeurCase);
                } else if (celluleCourante.getX() < cellulePrecedente.getX()) {
                    this.setY(this.getY() - largeurCase);
                }
            }
            if (celluleCourante.getY() != cellulePrecedente.getY()) {
                if (celluleCourante.getY() > cellulePrecedente.getY()) {
                    this.setX(this.getX() + hauteurCase);
                } else if (celluleCourante.getY() < cellulePrecedente.getY()) {
                    this.setX(this.getX() - hauteurCase);
                }
            }
        }

        this.deplacer(celluleCourante);
        i++;
        this.toString();
    }

    public void setX(double x1) {
        this.x.setValue(x1);
    }

    public void setY(double y1) {
        this.y.setValue(y1);
    }

    @Override
    public String toString() {
        return "id " + id;
    }

    public void setVue(ImageView vue) {
        this.vue = vue;
        this.imagePrincipale = vue.getImage();
    }

    public ImageView getVue() {
        return vue;
    }

    public void ajouterImageSecondaire(Image imageSecondaire) {
        this.imageSecondaire = imageSecondaire;
    }

    /**
     * Bascule entre l'image principale et l'image secondaire
     * @param val true si vous souhaitez changer l'image pour l'image secondaire
     */
    public void basculerVersVueSecondaire(boolean val) {
        if (val)
            vue.setImage(imageSecondaire);
        else
            vue.setImage(imagePrincipale);
    }

    public boolean destinationFinaleAtteinte() {
        return i >= this.plusCourtChemin.size();
    }

}

