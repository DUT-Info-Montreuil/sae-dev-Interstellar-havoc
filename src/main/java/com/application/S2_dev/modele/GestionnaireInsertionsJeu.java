package com.application.S2_dev.modele;

import com.application.S2_dev.modele.connexion.Connexion;
import com.application.S2_dev.vue.EnnemiVue;
import javafx.animation.Timeline;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class GestionnaireInsertionsJeu {
    private Connexion connexion;
    private EnnemiVue ennemiVue;
    private Timeline temps;
    private int score ;

    public GestionnaireInsertionsJeu(EnnemiVue ennemiVue, Timeline temps, int score) {
        connexion = new Connexion();
        this.ennemiVue = ennemiVue;
        this.temps = temps;
        this.score = score;
    }
    public void insererEnnemiTue( int scoreJoueur1, int scoreJoueur2, int scoreJoueur3) {
        String requeteInsert = "INSERT INTO ennemis_tues (Scavenger, nombre_tues_Scavenger, Balliste, nombre_tues_Balliste, " +
                "Behemoth, nombre_tues_Behemoth, date, heure) VALUES ('Scavenger', " + scoreJoueur1 +
                ", 'Balliste', " + scoreJoueur2 + ", 'Behemoth', " + scoreJoueur3 + ", CURRENT_DATE, CURRENT_TIME)";

        this.connexion.sql(requeteInsert);
    }
    public void insererTotalEnnemisSurTerrain(int totalScavenger, int totalBalliste, int totalBehemoth) {
        String requeteInsert = "INSERT INTO total_ennemis_sur_terrain (nom_scavenger, total_scavenger, " +
                "nom_balliste, total_balliste, nom_behemoth, total_behemoth, date, heure) " +
                "VALUES ('Scavenger', " + totalScavenger + ", 'Balliste', " + totalBalliste + ", 'Behemoth', " + totalBehemoth + ", CURRENT_DATE, CURRENT_TIME)";
        this.connexion.sql(requeteInsert);
    }
    public Time convert(Timeline timeline) {
        long milliseconds = (long) timeline.getTotalDuration().toMillis();
        long secondes = milliseconds / 1000;
        long minutes = secondes / 60;
        long heures = minutes / 60;
        secondes %= 60;
        minutes %= 60;

        return new Time((int)heures, (int)minutes, (int)secondes);
    }
    public void finDuJeu(){
        this.insererEnnemiTue( ennemiVue.getCompteurScavengersTues(), ennemiVue.getCompteurBallisteTues(), ennemiVue.getCompteurBehemtohTues());
        this.insererTotalEnnemisSurTerrain(ennemiVue.getCompteurScavengersTotal(), ennemiVue.getCompteurBallisteTotal(), ennemiVue.getCompteurBehemtohTotal());
    }
    public void debutDuJeu() {
        connexion.initConnexion();
        String requeteAjoutPartie = "INSERT INTO Partie(date, heure) VALUES (CURRENT_DATE, CURRENT_TIME) RETURNING idPartie;";

        try (PreparedStatement statement = connexion.getBdd().prepareStatement(requeteAjoutPartie)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id_Partie = resultSet.getInt("idPartie");
                Time temps1 = convert(temps);
                String r = "INSERT INTO Joueur (id, Nom) VALUES (1, 'Rabab');";
                connexion.sql(r);
                String requeteInsertPartieJouee = "INSERT INTO partie_jouee(id, idPartie, score, temps) VALUES ('1', ?, ?, ?);";
                try (PreparedStatement insertStatement = connexion.getBdd().prepareStatement(requeteInsertPartieJouee)) {
                    insertStatement.setInt(1, id_Partie);
                    insertStatement.setInt(2, score);
                    insertStatement.setTime(3, temps1); // Utilisez directement l'objet Time
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }






}
