package com.application.S2_dev.modele;

import com.application.S2_dev.modele.connexion.Connexion;
import com.application.S2_dev.vue.EnnemiVue;
import javafx.animation.Timeline;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class GestionnaireInsertionsJeu {
    private Connexion connexion;
    private EnnemiVue ennemiVue;
    private Timeline temps;
    private int score ;
    private int idJoueur;
    private int idPartie;
    private int nombreBalliste;

    public GestionnaireInsertionsJeu(EnnemiVue ennemiVue, Timeline temps) {
        connexion = new Connexion();
        this.ennemiVue = ennemiVue;
        this.temps = temps;

    }
    public void finDuJeu(){
        connexion.initConnexion();
        insererDonneesJoueur();
        insererNouvellePartie();
        insererDonneesEnnemiBalliste();
        insererDonneesEnnemiBehemoth();
        insererDonneesEnnemiScavenger();
        this.insererEnnemiTue( ennemiVue.getCompteurScavengersTues(), ennemiVue.getCompteurBallisteTues(), ennemiVue.getCompteurBehemtohTues());
        this.insererTotalEnnemisSurTerrain(ennemiVue.getCompteurScavengersTotal(), ennemiVue.getCompteurBallisteTotal(), ennemiVue.getCompteurBehemtohTotal());
    }

    public void insererEnnemiTue( int scoreJoueur1, int scoreJoueur2, int scoreJoueur3) {
        String requeteInsert = "INSERT INTO ennemis_tues (Scavenger, nombre_tues_Scavenger, Balliste, nombre_tues_Balliste, " +
                "Behemoth, nombre_tues_Behemoth, date, heure) VALUES ('Scavenger', " + scoreJoueur1 +
                ", 'Balliste', " + scoreJoueur2 + ", 'Behemoth', " + scoreJoueur3 + ", CURRENT_DATE, CURRENT_TIME)";

        this.connexion.executerRequete(requeteInsert);
    }
    public void insererTotalEnnemisSurTerrain(int totalScavenger, int totalBalliste, int totalBehemoth) {
        String requeteInsert = "INSERT INTO total_ennemis_sur_terrain (nom_scavenger, total_scavenger, " +
                "nom_balliste, total_balliste, nom_behemoth, total_behemoth, date, heure) " +
                "VALUES ('Scavenger', " + totalScavenger + ", 'Balliste', " + totalBalliste + ", 'Behemoth', " + totalBehemoth + ", CURRENT_DATE, CURRENT_TIME)";
        this.connexion.executerRequete(requeteInsert);
    }

    private void insererNouvellePartie() {
        Time temps1 = new Time(0, 1, 1);
        try{
            String requeteDynamique = "INSERT INTO Partie(date, heure, score, temps, id_Joueur) VALUES (CURRENT_DATE, CURRENT_TIME, " + ennemiVue.getScore() + ", '" + temps1 + "', " + idJoueur + ") RETURNING idPartie;";
            connexion.executerRequete(requeteDynamique);
            ResultSet resultSet = connexion.getRs();
            if (resultSet.next()) {
                idPartie = resultSet.getInt("idPartie");
            }
        }
        catch (Exception ex){
            System.out.println("Erreur lors de l'insertion des données du joueur : " + ex.getMessage());
        }

    }

    private void insererDonneesJoueur(String nomJoueur) {
        int idJoueurExist = getIdJoueurParNom(nomJoueur);
        if (idJoueurExist != -1) {
            idJoueur = idJoueurExist;
            return;
        }
        try {
            String requeteDynamique = "INSERT INTO Joueur (Nom) VALUES ('" + nomJoueur + "') RETURNING id_Joueur;";
            connexion.executerRequete(requeteDynamique);
            ResultSet resultSet = connexion.getRs();
            if (resultSet.next()) {
                idJoueur = resultSet.getInt("id_Joueur");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'insertion des données du joueur : " + ex.getMessage());
        }
    }

    private void insererDonneesJoueur() {
        String dernierNomJoueur = getDernierNomDuCSV();
        // Si le dernier nom n'est pas vide, insérer dans la base de données
        if (!dernierNomJoueur.isEmpty()) {
            insererDonneesJoueur(dernierNomJoueur);
        }
    }

    private void insererDonneesEnnemiBalliste(){
        String requeteDynamique = "INSERT INTO ennemi_partie(id_ennemi_partie, idPartie, nombre) VALUES (2," + idPartie + " , " + ennemiVue.getCompteurBallisteTotal() + " ); ";
        connexion.executerRequete(requeteDynamique);
    }
    private void insererDonneesEnnemiBehemoth(){
        String requeteDynamique = "INSERT INTO ennemi_partie(id_ennemi_partie, idPartie, nombre) VALUES (1," + idPartie + " , " + ennemiVue.getCompteurBehemtohTotal() + " ); ";
        connexion.executerRequete(requeteDynamique);
    }
    private void insererDonneesEnnemiScavenger(){
        String requeteDynamique = "INSERT INTO ennemi_partie(id_ennemi_partie, idPartie, nombre) VALUES (0," + idPartie + " , " + ennemiVue.getCompteurScavengersTotal() + " ); ";
        connexion.executerRequete(requeteDynamique);
    }
    private String getDernierNomDuCSV() {
        String csvFile = "noms_joueurs.csv"; // Nom du fichier CSV
        String dernierNom = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                dernierNom = ligne;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dernierNom;
    }
    private int getIdJoueurParNom(String nomJoueur) {
        try {
            String requeteDynamique = "SELECT id_Joueur FROM Joueur WHERE Nom = '" + nomJoueur + "';";
            connexion.executerRequete(requeteDynamique);
            ResultSet resultSet = connexion.getRs();
            if (resultSet.next()) {
                return resultSet.getInt("id_Joueur");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération de l'ID du joueur : " + ex.getMessage());
        }
        return -1;
    }

}
