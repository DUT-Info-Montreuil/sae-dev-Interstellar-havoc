package com.application.S2_dev.vue;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.Boutique;
import com.application.S2_dev.modele.données.TerrainType;
import com.application.S2_dev.modele.données.TowerType;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.Tour;
import com.application.S2_dev.modele.tours.EdisonCoil;
import com.application.S2_dev.modele.tours.NikolaCoil;
import com.application.S2_dev.modele.tours.OppenheimerCoil;
import javafx.animation.Timeline;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
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
            System.out.println("Placement de la tour non autorisé aux coordonnées (" + ligne + ", " + cologne + ")");
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
            progressBar.setId(tour.getId());
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
                if (env.getTour().contains(tourCliquee)) {
                    int cologne = tourCliquee.getXCarte();
                    int ligne = tourCliquee.getYCarte();
                    int niveauTour = tourCliquee.getNiveau();

                    if (newX > 0 && newX < 16 && newY > 16 && newY < 32) {
                        // Niveau 1 requis
                        if (niveauTour == 0) {
                            int prixTourNiveau1 = 100;
                            if ((boutique.getPrix() + tourCliquee.getPrix() - prixTourNiveau1) < 0) {
                                boutique.MessageArgent();
                            } else {
                                rembourserArgent(tourCliquee.getPrix());
                                env.getTour().remove(tourCliquee);
                                placerTour(ligne, cologne, 1);
                            }
                        }
                    } else if (newX > 64 && newX < 80 && newY > 16 && newY < 32) {
                        // Niveau 2 requis
                        if (niveauTour == 1 ) {
                            int prixTourNiveau2 = 200;
                            if ((boutique.getPrix() + tourCliquee.getPrix() - prixTourNiveau2) < 0) {
                                //boutique.MessageArgent();
                            } else {
                                rembourserArgent(tourCliquee.getPrix());
                                env.getTour().remove(tourCliquee);
                                placerTour(ligne, cologne, 2);
                            }
                        } else if(niveauTour != 3){
                            MessagePlacementTour.setContentText("Vous ne pouvez pas passer au niveau 2 sans avoir le niveau précédent.");
                            MessagePlacementTour.setOnShowing(e -> {
                                this.gameLoop.pause();
                            });
                            MessagePlacementTour.setOnHidden(e -> {
                                this.gameLoop.play();
                            });
                            MessagePlacementTour.showAndWait();
                        }
                    } else if (newX > 32 && newX < 48 && newY > 64 && newY < 80) {
                        // Niveau 3 requis
                        if (niveauTour == 2) {
                            int prixTourNiveau3 = 300;
                            if ((boutique.getPrix() + tourCliquee.getPrix() - prixTourNiveau3) < 0) {
                                // boutique.MessageArgent();
                            } else {
                                rembourserArgent(tourCliquee.getPrix());
                                env.getTour().remove(tourCliquee);
                                placerTour(ligne, cologne, 3);
                            }
                        } else if (niveauTour != 3) {
                            MessagePlacementTour.setContentText("Vous ne pouvez pas passer au niveau 3 sans avoir le niveau précédent.");
                            MessagePlacementTour.setOnShowing(e -> {
                                this.gameLoop.pause();
                            });
                            MessagePlacementTour.setOnHidden(e -> {
                                this.gameLoop.play();
                            });
                            MessagePlacementTour.showAndWait();
                        }
                    } else if (newX > 32 && newX < 48 && newY > 32 && newY < 48) {
                        // Supprimer la tour
                        rembourserArgent(tourCliquee.getPrix());
                        env.getTour().remove(tourCliquee);
                    }
                    if (niveauTour == 3 ) {
                        MessagePlacementTour.setContentText("Le upgrade est au maximum !\n" +
                                "Cependant vous pouvez changer le type de tour.");
                        MessagePlacementTour.setOnShowing(e -> {
                            this.gameLoop.pause();
                        });
                        MessagePlacementTour.setOnHidden(e -> {
                            this.gameLoop.play();
                        });
                        MessagePlacementTour.showAndWait();
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
