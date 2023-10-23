package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.Boutique;
import com.application.S2_dev.modele.Parametre;
import com.application.S2_dev.modele.TourFactory.TourFactory;
import com.application.S2_dev.modele.données.TerrainType;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.acteurs.tours.Tour;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import javax.swing.*;
import java.net.URL;

public class TourVue implements ListChangeListener<Tour> {
    private Environnement env;
    private Pane pane;
    private TilePane tilePane;
    private Terrain terrain;
    private Label idBobineEdison, idBobineOppenheimer, idBobineNikola;
    private TowerType selectedTowerType;
    private Timeline gameLoop;
    private Boutique boutique;
    private ImageView niveauChoisi = null; // Image affichée pour le niveau sélectionné
    private Tour tourCliquee = null; // Tour sélectionnée par le joueur
    private Alert MessagePlacementTour = new Alert(Alert.AlertType.INFORMATION);

    public TourVue(Environnement environnement, TilePane tilePane, Terrain terrain , Pane pane, Label idBobineEdison, Label idBobineOppenheimer, Label idBobineNikola, Timeline gameLoop, Boutique boutique){
        this.env = environnement;
        this.tilePane = tilePane;
        this.terrain = terrain;
        this.pane = pane;
        this.idBobineEdison = idBobineEdison;
        this.idBobineNikola = idBobineNikola;
        this.idBobineOppenheimer = idBobineOppenheimer;
        this.gameLoop = gameLoop;
        this.boutique = boutique;
    }
    @Override
    public void onChanged(Change<? extends Tour> c) {
        while (c.next()) {
            System.out.println("les ajouts Tour: " + c.getAddedSubList());
            System.out.println("les suppressions Tour: " + c.getRemoved());

            for (int i = 0; i < c.getRemoved().size(); i++) {
                ImageView sprite = (ImageView) pane.lookup("#" + c.getRemoved().get(i).getId());
                pane.getChildren().remove(sprite);
                ProgressBar progressBar = (ProgressBar) pane.lookup("#" + c.getRemoved().get(i).getId());
                pane.getChildren().remove(progressBar);
            }
        }
    }
    public void lancerTourVue() {
        MessagePlacementTour.setTitle("Placement de la tour");
        MessagePlacementTour.setHeaderText(null);

        final int ennemiSquadSize = env.getEnnemis().size();

        if (env.getEnnemis().size() < ennemiSquadSize)
            ajouterArgent(100);

        if (!env.getTour().contains(tourCliquee)) {
            pane.getChildren().remove(this.niveauChoisi);
            niveauChoisi = null;
        }
        TestClickTourel();
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
            int[] pos = terrain.getPosDansCarte((int) h.getX(), (int) h.getY());

            // Vérification si une tour est présente aux coordonnées du clic
            for (int i = 0; i < env.getTour().size(); i++) {
                Tour tour = env.getTour().get(i);
                if (tour.estDansLimites((int) h.getX(), (int) h.getY())) {
                    return; // Sortie de la fonction si une tour est présente
                }
            }

            placerTour(pos[0], pos[1], 1); // Placement d'une tour à la position spécifiée
        });
    }
    /**
     * Place une tour sur la carte
     * @param ligne Position de ligne dans la carte en tuiles
     * @param colonne Position de colonne dans la carte en tuiles
     * @param level Niveau de la tour
     */
    public void placerTour(int ligne, int colonne, int level) {
        // Vérifier si la tour peut être placée aux coordonnées spécifiées
        if (peutPlacerTourA(ligne, colonne)) {
            Tour tour = TourFactory.creerTour(colonne,ligne,level,selectedTowerType);

            if (boutique.getPrix()< tour.getPrix()) {
                boutique.MessageArgent();
                return;
            }

            // Ajouter la tour au terrain et l'afficher sur le panneau
            creerSprite(tour);
            env.ajouterTour(tour);
            soustraireArgent(tour.getPrix());
        } else {
            // Placement de la tour non autorisé aux coordonnées spécifiées
            System.out.println("Placement de la tour non autorisé aux coordonnées (" + ligne + ", " + colonne + ")");
        }
    }
    void soustraireArgent(int valeur) {
        boutique.setPrix(boutique.getPrix()-valeur);
    }

    void rembourserArgent(int valeur) {
        boutique.setPrix(boutique.getPrix()+valeur);
    }

    void ajouterArgent(int valeur) {
        boutique.setPrix(boutique.getPrix()+valeur);
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

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(tour.getVie());
        double largeurSouhaitee = 50; // Définissez la largeur souhaitée en pixels
        double hauteurSouhaitee = 10; // Définissez la hauteur souhaitée en pixels

        progressBar.setPrefWidth(largeurSouhaitee);
        progressBar.setPrefHeight(hauteurSouhaitee);

        // Lie les propriétés de translation de la tour aux propriétés de translation de la tour
        vueTour.translateXProperty().bind(tour.getXProperty());
        vueTour.translateYProperty().bind(tour.getYProperty());
        progressBar.prefWidthProperty().bind(tour.vieProperty());

        // Vérifie si l'ImageView est valide
        if (vueTour != null) {
            vueTour.setId(tour.getId());
           // progressBar.setId(tour.getId());
            int largeur = (int) vueTour.getImage().getWidth();
            int hauteur = (int) vueTour.getImage().getHeight();
            int x = (int) (tour.getX() - (largeur / 2));
            int y = (int) (tour.getY() - (hauteur / 2));
            tour.getXProperty().set(x);
            tour.getYProperty().set(y);
            tour.setLimites(x, y, largeur, hauteur);
            tour.setVue(vueTour);

            // Position du progresseBar au-dessus de la tour
            double progressBarX = x + (largeur / 2) - (progressBar.getWidth() / 2);
            double progressBarY = y - progressBar.getHeight();
            progressBar.setLayoutX(progressBarX);
            progressBar.setLayoutY(progressBarY);

            pane.getChildren().add(vueTour);
            pane.getChildren().add(progressBar);

            // Définit le gestionnaire d'événements sur le clic de l'ImageView de la tour
            vueTour.setOnMouseClicked(h -> {
                if (niveauChoisi == null) {
                    tourCliquee = tour;
                    showniveauChoisi();
                    System.out.println("Niveau du choix affiché");
                }
            });
            MessagePlacementTour.setContentText("Tour de niveau " + tour.getNiveau() + " " + tour.getNom() + " placée");
            MessagePlacementTour.setOnShowing(e -> {
                this.gameLoop.pause();
            });
            MessagePlacementTour.setOnHidden(e -> {
                this.gameLoop.play();
            });
            MessagePlacementTour.showAndWait();

        }

        // Retourne les dimensions de l'image de la tour
        return new double[]{vueTour.getImage().getHeight(), vueTour.getImage().getWidth()};
    }
    void showniveauChoisi() {
        // URL de l'image du niveau choisi
        URL urlChoixNiveau = Main.class.getResource("image/tour/level_chooser.png");

        // Charge l'image du niveau choisi
        Image imageNiveau = new Image(String.valueOf(urlChoixNiveau));
        ImageView vueNiveau = new ImageView(imageNiveau);

        // Positionne l'image du niveau choisi aux coordonnées de la tour sélectionnée
        vueNiveau.setX(tourCliquee.getX()-32);
        vueNiveau.setY(tourCliquee.getY()-24);

        // Vérifie si l'ImageView est valide
        if (vueNiveau != null) {
            pane.getChildren().add(vueNiveau); // Ajoute l'image du niveau choisi à la scène
            niveauChoisi = vueNiveau; // Assigne l'image du niveau choisi à la variable niveauChoisi

            niveauChoisi.setOnMouseClicked(h -> {
                int newX = (int) (h.getX() - niveauChoisi.getX());
                int newY = (int) (h.getY() - niveauChoisi.getY());

                // Vérifie les coordonnées du clic pour déterminer l'action correspondante
                if (newX > 0 && newX < 40 && newY > 44 && newY < 80) {
                    // Niveau upgrade
                    if (env.getTour().contains(tourCliquee)) {
                        int cologne = tourCliquee.getXCarte();
                        int ligne = tourCliquee.getYCarte();
                        int niveau = tourCliquee.getNiveau() + 1;
                        if (((Parametre.argentDebutJoueur+ tourCliquee.getPrix()) - (100*niveau)) < 0) {
                            JOptionPane.showMessageDialog(null, "Pas assez d'argent");
                        } else {
                            // Supprime la tour et rembourse l'argent
                            rembourserArgent(tourCliquee.getPrix());
                            env.getTour().remove(tourCliquee);
                            placerTour(ligne, cologne, niveau);
                        }
                    }
                } else if (newX > 88 && newX < 128 && newY > 44 && newY < 80) {
                    // Supprimer la tour
                    if (env.getTour().contains(tourCliquee)) {
                        int cologne = tourCliquee.getXCarte();
                        int ligne = tourCliquee.getYCarte();

                        // Supprime la tour et rembourse l'argent
                        rembourserArgent(tourCliquee.getPrix());
                      //  pane.getChildren().remove(tourCliquee.getVue());
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
