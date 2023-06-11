package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.data.TerrainType;
import com.application.S2_dev.modele.data.TowerType;
import com.application.S2_dev.modele.ennemis.Balliste;
import com.application.S2_dev.modele.ennemis.Behemoth;
import com.application.S2_dev.modele.ennemis.Ennemi;
import com.application.S2_dev.modele.ennemis.Scavenger;
import com.application.S2_dev.modele.map.Environnement;
import com.application.S2_dev.modele.map.Terrain;
import com.application.S2_dev.modele.tours.*;
import com.application.S2_dev.vue.EnnemiVue;
import com.application.S2_dev.vue.TerrainVue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

public class ControlleurTerrainJeu implements Initializable {    @FXML
    
    TilePane tilePane;
    
    @FXML
    Pane pane;
    
    @FXML
    private Label idBobineEdison;
    
    @FXML
    private Label idBobineNikola;
    
    @FXML
    private Label idBobineOppenheimer;
    
    @FXML
    private Label labelScavenger;
    
    @FXML
    private Label labelBehemoth;
    
    @FXML
    private Label labelBalliste;
    
    @FXML
    private Label labelCredit;
    
    @FXML
    private Label labelVie;
    
    private Timeline gameLoop;
    private int temps;
    private EnnemiVue ennemiVue;
    TerrainVue terrainVue;
    Terrain terrain;
    Environnement env;

    private TowerType selectedTowerType = null; // Type de tour sélectionné
    private int argent = 500; // Quantité d'argent du joueur
    private int tempsAvantNouveauSpawn = 15; // Temps avant l'apparition d'un nouvel ennemi
    private boolean jeuEnPause = false; // Indicateur de jeu en pause
    private int nombreVies = 5; // Nombre de vies du joueur

    private ImageView niveauChoisi = null; // Image affichée pour le niveau sélectionné
    private Tour tourCliquee = null; // Tour sélectionnée par le joueur

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

        labelCredit.setText(argent + ""); // Affichage de la quantité d'argent du joueur
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        terrain = new Terrain(); // Création du terrain
        terrainVue = new TerrainVue(tilePane, terrain); // Création de la vue du terrain
        env = new Environnement(pane); // Création de l'environnement
        terrainVue.afficherTerrain(); // Affichage du terrain
        env.init(); // Initialisation de l'environnement

        // Création des sprites pour chaque ennemi présent dans l'environnement
        for (int i = 0; i < env.getEnnemis().size(); i++) {
            creerSprite(env.getEnnemis().get(i));
        }

        initAnimation(); // Initialisation de l'animation
        this.TestClickTourel(); // Gestion des clics sur les tours
    }

    /**
     * Boucle de jeu
     */
    public void initAnimation() {
        gameLoop = new Timeline();
        temps = 0;
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.3), ev -> {

            if (jeuEnPause)
                return;

            final int ennemiSquadSize = env.getEnnemis().size();

            env.unTour();

            if (env.getEnnemis().size() < ennemiSquadSize)
                ajouterArgent(100);

            int s = 0, be = 0, ba = 0;
            for (Ennemi e : env.getEnnemis()) {
                if (e instanceof Scavenger)
                    s++;
                else if (e instanceof Behemoth)
                    be++;
                else if (e instanceof Balliste)
                    ba++;
            }

            if (!env.getTour().contains(tourCliquee)) {
                pane.getChildren().remove(this.niveauChoisi);
                niveauChoisi = null;
            }

            labelScavenger.setText(s + "");
            labelBehemoth.setText(be + "");
            labelBalliste.setText(ba + "");

            int valeurVie = nombreVies - env.getJoueursAtteints();
            labelVie.setText(valeurVie + "");

            if (tempsAvantNouveauSpawn == 0) {
                spawnEnnemi();
                tempsAvantNouveauSpawn = 15;
                return;
            }

            tempsAvantNouveauSpawn--;

            if (valeurVie < 0) {
                JOptionPane.showMessageDialog(null, "Vous avez perdu !");
                exit();
            }
        }
        );
        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
    }

    public void spawnEnnemi() {

        int rand = new Random().nextInt(3); // Génère un nombre aléatoire entre 0 et 2

        switch (rand) {
            case 0:
                Ennemi e1 = new Balliste(5, 21); // Crée un nouvel ennemi de type Balliste
                env.ajouterEnnemi(e1); // Ajoute l'ennemi à l'environnement
                creerSprite(e1); // Crée le sprite de l'ennemi
                break;
            case 1:
                Ennemi e2 = new Behemoth(5, 21); // Crée un nouvel ennemi de type Behemoth
                env.ajouterEnnemi(e2); // Ajoute l'ennemi à l'environnement
                creerSprite(e2); // Crée le sprite de l'ennemi
                break;
            case 2:
                Ennemi e3 = new Scavenger(5, 21); // Crée un nouvel ennemi de type Scavenger
                env.ajouterEnnemi(e3); // Ajoute l'ennemi à l'environnement
                creerSprite(e3); // Crée le sprite de l'ennemi
                break;
            default:
                break;
        }
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

            if (argent < tour.getPrix()) {
                JOptionPane.showMessageDialog(null, "Pas assez d'argent !");
                return;
            }

            // Ajouter la tour au terrain et l'afficher sur le panneau
            env.ajouterTour(tour);
            creerSprite(tour);
            soustraireArgent(tour.getPrix());
        } else {
            // Placement de la tour non autorisé aux coordonnées spécifiées
            System.out.println("Placement de la tour non autorisé aux coordonnées (" + ligne + ", " + cologne + ")");
        }
    }


    void soustraireArgent(int valeur) {
        argent -= valeur; // Soustrait la valeur donnée à la variable argent
        labelCredit.setText(argent + ""); // Met à jour le texte de l'étiquette pour afficher la nouvelle valeur de l'argent
    }

    void rembourserArgent(int valeur) {
        argent += valeur; // Ajoute la valeur donnée à la variable argent
        labelCredit.setText(argent + ""); // Met à jour le texte de l'étiquette pour afficher la nouvelle valeur de l'argent
    }

    void ajouterArgent(int valeur) {
        argent += valeur; // Ajoute la valeur donnée à la variable argent
        labelCredit.setText(argent + ""); // Met à jour le texte de l'étiquette pour afficher la nouvelle valeur de l'argent
    }


    void creerSprite(Ennemi ennemi) {
        // URL des images des ennemis
        URL urlEnnemiLent = null, urlSEnnemiLent = null;

        // Vérifie le type d'ennemi pour déterminer les URL des images correspondantes
        if (ennemi instanceof Balliste) {
            urlEnnemiLent = Main.class.getResource("image/ennemis/Balliste.png");
            urlSEnnemiLent = Main.class.getResource("image/ennemis/Balliste_Damaged.png");
        } else if (ennemi instanceof Behemoth) {
            urlEnnemiLent = Main.class.getResource("image/ennemis/Behemoth.png");
            urlSEnnemiLent = Main.class.getResource("image/ennemis/Behemoth_Damaged.png");
        } else if (ennemi instanceof Scavenger) {
            urlEnnemiLent = Main.class.getResource("image/ennemis/Scavenger.png");
            urlSEnnemiLent = Main.class.getResource("image/ennemis/Scavenger_Damaged.png");
        }

        // Charge les images des ennemis
        Image imageEnnemiLent = new Image(String.valueOf(urlEnnemiLent));
        Image imageSEnnemiLent = new Image(String.valueOf(urlSEnnemiLent));
        ImageView vueEnnemiLent = new ImageView(imageEnnemiLent);

        // Lie les propriétés de translation de l'ennemi aux propriétés de translation de l'ennemi
        vueEnnemiLent.translateXProperty().bind(ennemi.getXProperty());
        vueEnnemiLent.translateYProperty().bind(ennemi.getYProperty());

        // Vérifie si l'ImageView est valide
        if (vueEnnemiLent != null) {
            vueEnnemiLent.setId(ennemi.getId());
            ennemi.setVue(vueEnnemiLent);
            ennemi.ajouterImageSecondaire(imageSEnnemiLent);
            pane.getChildren().add(vueEnnemiLent);
        }
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

                        if (((argent + tourCliquee.getPrix()) - 100) < 0) {
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

                        if (((argent + tourCliquee.getPrix()) - 200) < 0) {
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

                        if (((argent + tourCliquee.getPrix()) - 300) < 0) {
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


    @FXML
    void ButtonInventaire(ActionEvent event) {
        Parent root;
        // Reprendre la boucle de jeu
        this.gameLoop.play();
        try {
            // Mettre en pause la boucle de jeu
            this.gameLoop.pause();
            // Charger la vue de l'inventaire à partir du fichier FXML
            root = FXMLLoader.load(Main.class.getResource("fxml/Inventaire/Inventaire.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Inventaire");
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
            // Revenir à la boucle de jeu lorsque la fenêtre de l'inventaire est fermée
            stage.setOnHidden(e -> this.gameLoop.play());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ButtonQuitter(ActionEvent event) {
        exit();
    }
    
    public void exit() {
        Parent root;
        try {
            gameLoop.stop();
            Stage stage1 = (Stage) tilePane.getScene().getWindow();
            stage1.close();
            root = FXMLLoader.load(Main.class.getResource("fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Tower Defence");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ButtonPlay(ActionEvent event) {
        // Reprendre la boucle de jeu
        this.gameLoop.play();
    }

    @FXML
    void ButtonPause(ActionEvent event) {
        // Mettre en pause la boucle de jeu
        this.gameLoop.pause();
    }
    
    public boolean peutPlacerTourA(int ligne, int cologne) {
        if (terrain.getCase(ligne, cologne) == TerrainType.base_tourelle)
            return true;
        else return false;
    }

}


