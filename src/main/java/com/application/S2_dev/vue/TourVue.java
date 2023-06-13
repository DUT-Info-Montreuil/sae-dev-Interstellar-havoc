package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.Parametre;
import com.application.S2_dev.modele.data.TerrainType;
import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import com.application.S2_dev.modele.tours.EdisonCoil;
import com.application.S2_dev.modele.tours.NikolaCoil;
import com.application.S2_dev.modele.tours.OppenheimerCoil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

import javax.swing.*;
import java.net.URL;

public class TourVue implements ListChangeListener<Tour> {
    private Environnement env;
    Pane pane;
    private TilePane tilePane;
    private Terrain terrain;
    private Label idBobineEdison, idBobineOppenheimer, idBobineNikola;
    private Label idSelectedTower, labelCredit;
    private TowerType selectedTowerType;
    private int timeBeforeNewSpawn = 15;
    private Tour clickedTower = null;
    private ImageView levelChooser = null;
    private int money ;
    private int temps;
    private int tempsAvantNouveauSpawn = 10; // Temps avant l'apparition d'un nouvel ennemi
    private boolean jeuEnPause = false; // Indicateur de jeu en pause
    private ImageView niveauChoisi = null; // Image affichée pour le niveau sélectionné
    private Tour tourCliquee = null; // Tour sélectionnée par le joueur

    public TourVue(Environnement environnement, TilePane tilePane, Terrain terrain , Pane pane, Label idBobineEdison, Label idBobineOppenheimer, Label idBobineNikola, Label labelCredit, Label idSelectedTower){
        this.env = environnement;
        this.tilePane = tilePane;
        this.terrain = terrain;
        this.pane = pane;
        this.idBobineEdison = idBobineEdison;
        this.idBobineNikola = idBobineNikola;
        this.idBobineOppenheimer = idBobineOppenheimer;
        this.money = Integer.parseInt(labelCredit.getText());
        this.labelCredit = labelCredit;
        this.idSelectedTower = idSelectedTower;
    }
    @Override
    public void onChanged(Change<? extends Tour> c) {
        while (c.next()) {
            System.out.println("les ajouts Tour: " + c.getAddedSubList());
            System.out.println("les suppressions Tour: " + c.getRemoved());
        }
        for(int i =0; i<c.getAddedSubList().size();i++){
           // soustraireArgent(c.getAddedSubList().get(i).getPrix());
        }
        for (int i = 0; i < c.getRemoved().size(); i++) {
            ImageView sprite = (ImageView) pane.lookup("#" + c.getRemoved().get(i).getId());
            pane.getChildren().remove(sprite);
        }
    }
    public void lancerTourVue() {
        temps = 0;

            if (jeuEnPause)
                return;

            final int ennemiSquadSize = env.getEnnemis().size();

            env.unTour();

            if (env.getEnnemis().size() < ennemiSquadSize)
                ajouterArgent(100);

            int Scavenger = 0, Behemoth = 0, Balliste = 0;
            for (Ennemi e : env.getEnnemis()) {
                if (e instanceof Scavenger)
                    Scavenger++;
                else if (e instanceof Behemoth)
                    Behemoth++;
                else if (e instanceof Balliste)
                    Balliste++;
            }

            if (!env.getTour().contains(tourCliquee)) {
                pane.getChildren().remove(this.niveauChoisi);
                niveauChoisi = null;
            }

            if (tempsAvantNouveauSpawn == 0) {
                //spawnEnnemi();
                tempsAvantNouveauSpawn = 15;
                return;
            }

            tempsAvantNouveauSpawn--;


    }

    public void TestClickTourel() {

        // Gestion des clics sur les images des tours
        idBobineEdison.setOnMouseClicked(h -> {
            if (niveauChoisi == null) {
                selectedTowerType = TowerType.Edison; // Tour Edison sélectionnée
            }
        });

        idBobineOppenheimer.setOnMouseClicked(h -> {
            if (niveauChoisi == null) {
                selectedTowerType = TowerType.Oppenheimer; // Tour Oppenheimer sélectionnée
            }
        });

        idBobineNikola.setOnMouseClicked(h -> {
            if (niveauChoisi == null) {
                selectedTowerType = TowerType.Nikola; // Tour Nikola sélectionnée
            }
        });

        tilePane.setOnMouseClicked(h -> {
            int[] pos = terrain.getPosInMap((int) h.getX(), (int) h.getY());

            // Vérification si une tour est présente aux coordonnées du clic
            for (int i = 0; i < env.getTour().size(); i++) {
                Tour tour = env.getTour().get(i);
                if (tour.estDansLimites((int) h.getX(), (int) h.getY())) {
                    return; // Sortie de la fonction si une tour est présente
                }
            }

            placerTour(pos[0], pos[1], 1); // Placement d'une tour à la position spécifiée
        });

        labelCredit.setText(Parametre.argentDebutJoueur+ ""); // Affichage de la quantité d'Parametre.argentDebutJoueurdu joueur
    }
    /**
     * Place une tour sur la carte
     * @param ligne Position de ligne dans la carte en tuiles
     * @param cologne Position de cologneonne dans la carte en tuiles
     * @param level Niveau de la tour
     */
    public void placerTour(int ligne, int cologne, int level) {
        // Vérifier si la tour peut être placée aux coordonnées spécifiées
        if (peutPlacerTourA(ligne, cologne)) {

            Tour tour;

            // Créer l'objet tour en fonction du type de tour
            switch (selectedTowerType) {
                case Nikola:
                    tour = new NikolaCoil((int)cologne*16, (int)ligne*16, level);
                    break;
                case Edison:
                    tour = new EdisonCoil((int)cologne*16, (int)ligne*16, level);
                    break;
                case Oppenheimer:
                    tour = new OppenheimerCoil((int)cologne*16, (int)ligne*16, level);
                    break;
                default:
                    return;
            }

            if (Parametre.argentDebutJoueur< tour.getPrix()) {
                JOptionPane.showMessageDialog(null, "Pas assez d'Parametre.argentDebutJoueur!");
                return;
            }

            // Ajouter la tour au terrain et l'afficher sur le panneau
            env.addTower(tour);
            creerSprite(tour);
            soustraireArgent(tour.getPrix());
        } else {
            // Placement de la tour non autorisé aux coordonnées spécifiées
            System.out.println("Placement de la tour non autorisé aux coordonnées (" + ligne + ", " + cologne + ")");
        }
    }
    void soustraireArgent(int valeur) {
        Parametre.argentDebutJoueur-= valeur; // Soustrait la valeur donnée à la variable argent
        labelCredit.setText(Parametre.argentDebutJoueur+ ""); // Met à jour le texte de l'étiquette pour afficher la nouvelle valeur de l'argent
    }

    void rembourserArgent(int valeur) {
        Parametre.argentDebutJoueur+= valeur; // Ajoute la valeur donnée à la variable argent
        labelCredit.setText(Parametre.argentDebutJoueur+ ""); // Met à jour le texte de l'étiquette pour afficher la nouvelle valeur de l'argent
    }

    void ajouterArgent(int valeur) {
        Parametre.argentDebutJoueur+= valeur; // Ajoute la valeur donnée à la variable argent
        labelCredit.setText(Parametre.argentDebutJoueur+ ""); // Met à jour le texte de l'étiquette pour afficher la nouvelle valeur de l'argent
    }

    double[] creerSprite(Tour tour) {
        // URL de l'image de la tour
        URL urlTour;

        // Sélectionne l'URL de l'image en fonction du type de tour
        switch (tour.getType()) {
            case Nikola:
                urlTour = Main.class.getResource("image/tour/bobineNicolas.png");
                break;
            case Edison:
                urlTour = Main.class.getResource("image/tour/bobineEdison.png");
                break;
            case Oppenheimer:
                urlTour = Main.class.getResource("image/tour/bobineOppenheimer.png");
                break;
            default:
                urlTour = null;
                break;
        }

        // Charge l'image de la tour
        Image imageTour = new Image(String.valueOf(urlTour));
        ImageView vueTour = new ImageView(imageTour);

        // Lie les propriétés de translation de la tour aux propriétés de translation de la tour
        vueTour.translateXProperty().bind(tour.getXProperty());
        vueTour.translateYProperty().bind(tour.getYProperty());

        // Vérifie si l'ImageView est valide
        if (vueTour != null) {
            vueTour.setId(tour.getId());
            int largeur = (int) vueTour.getImage().getWidth();
            int hauteur = (int) vueTour.getImage().getHeight();
            int x = (int) (tour.getX() - (largeur / 2));
            int y = (int) (tour.getY() - (hauteur / 2));
            tour.getXProperty().set(x);
            tour.getYProperty().set(y);
            tour.setLimites(x, y, largeur, hauteur);
            tour.setVue(vueTour);
            pane.getChildren().add(vueTour);

            // Définit le gestionnaire d'événements sur le clic de l'ImageView de la tour
            vueTour.setOnMouseClicked(h -> {
                if (niveauChoisi == null) {
                    tourCliquee = tour;
                    showniveauChoisi();
                    System.out.println("Niveau du choix affiché");
                }
            });

            JOptionPane.showMessageDialog(null, "Tour de niveau " + tour.getNiveau() + " " + tour.getNom() + " placée");
        }

        // Retourne les dimensions de l'image de la tour
        return new double[]{vueTour.getImage().getHeight(), vueTour.getImage().getWidth()};
    }
    void showniveauChoisi() {
        // URL de l'image du niveau choisi
        URL urlChoixNiveau = Main.class.getResource("image/tour/level_choose.png");

        // Charge l'image du niveau choisi
        Image imageNiveau = new Image(String.valueOf(urlChoixNiveau));
        ImageView vueNiveau = new ImageView(imageNiveau);

        // Positionne l'image du niveau choisi aux coordonnées de la tour sélectionnée
        vueNiveau.setX(tourCliquee.getX());
        vueNiveau.setY(tourCliquee.getY());

        // Vérifie si l'ImageView est valide
        if (vueNiveau != null) {
            pane.getChildren().add(vueNiveau); // Ajoute l'image du niveau choisi à la scène
            niveauChoisi = vueNiveau; // Assigne l'image du niveau choisi à la variable niveauChoisi

            niveauChoisi.setOnMouseClicked(h -> {
                int newX = (int) (h.getX() - niveauChoisi.getX());
                int newY = (int) (h.getY() - niveauChoisi.getY());

                // Vérifie les coordonnées du clic pour déterminer l'action correspondante
                if (newX > 0 && newX < 16 && newY > 16 && newY < 32) {
                    // Niveau 1 requis
                    if (env.getTour().contains(tourCliquee)) {
                        int cologne = tourCliquee.getXCarte();
                        int ligne = tourCliquee.getYCarte();

                        if (((Parametre.argentDebutJoueur+ tourCliquee.getPrix()) - 100) < 0) {
                            JOptionPane.showMessageDialog(null, "Pas assez d'argent");
                        } else {
                            // Supprime la tour et rembourse l'argent
                            rembourserArgent(tourCliquee.getPrix());
                            pane.getChildren().remove(tourCliquee.getVue());
                            env.getTour().remove(tourCliquee);
                            placerTour(ligne, cologne, 1);
                        }
                    }
                } else if (newX > 64 && newX < 80 && newY > 16 && newY < 32) {
                    // Niveau 2 requis
                    if (env.getTour().contains(tourCliquee)) {
                        int cologne = tourCliquee.getXCarte();
                        int ligne = tourCliquee.getYCarte();

                        if (((Parametre.argentDebutJoueur+ tourCliquee.getPrix()) - 200) < 0) {
                            JOptionPane.showMessageDialog(null, "Pas assez d'argent");
                        } else {
                            // Supprime la tour et rembourse l'argent
                            rembourserArgent(tourCliquee.getPrix());
                            pane.getChildren().remove(tourCliquee.getVue());
                            env.getTour().remove(tourCliquee);
                            placerTour(ligne, cologne, 2);
                        }
                    }
                }else if (newX > 32 && newX < 48 && newY > 64 && newY < 80) {
                    // Niveau 3 requis
                    if (env.getTour().contains(tourCliquee)) {
                        int cologne = tourCliquee.getXCarte();
                        int ligne = tourCliquee.getYCarte();

                        if (((Parametre.argentDebutJoueur + tourCliquee.getPrix()) - 300) < 0) {
                            JOptionPane.showMessageDialog(null, "Pas assez d'argent");
                        } else {
                            // Supprime la tour et rembourse l'argent
                            rembourserArgent(tourCliquee.getPrix());
                            pane.getChildren().remove(tourCliquee.getVue());
                            env.getTour().remove(tourCliquee);
                            placerTour(ligne, cologne, 3);
                        }
                    }
                } else if (newX > 32 && newX < 48 && newY > 32 && newY < 48) {
                    // Supprimer la tour
                    if (env.getTour().contains(tourCliquee)) {
                        int cologne = tourCliquee.getXCarte();
                        int ligne = tourCliquee.getYCarte();

                        // Supprime la tour et rembourse l'argent
                        rembourserArgent(tourCliquee.getPrix());
                        pane.getChildren().remove(tourCliquee.getVue());
                        env.getTour().remove(tourCliquee);
                    }
                }

                // Supprime l'image du niveau choisi
                pane.getChildren().remove(niveauChoisi);
                niveauChoisi = null;
                return;

            });
        }
    }

    public boolean peutPlacerTourA(int ligne, int cologne) {
        if (terrain.getCase(ligne, cologne) == TerrainType.base_tourelle)
            return true;
        else return false;
    }


}
