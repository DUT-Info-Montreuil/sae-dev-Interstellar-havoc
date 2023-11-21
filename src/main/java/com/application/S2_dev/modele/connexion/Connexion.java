package com.application.S2_dev.modele.connexion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Connexion {
    private static Connection bdd;
    private TableView tableView ;
    private Statement stm;
    private ResultSet rs;
    public Connexion() {
        this.tableView = new TableView();
    }

    public static void initConnexion() {
        String serveur = "localhost";
        String utilisateur = "postgres";
        String motDePasse = "4091999Mm@";
        String baseDeDonnees = "interstellarhavoc";

        String url = "jdbc:postgresql://" + serveur + "/" + baseDeDonnees;

        try {
            bdd = DriverManager.getConnection(url, utilisateur,motDePasse);
            if (bdd != null) {
                System.out.println("Connexion réussie à la base de données !");
            } else {
                System.out.println("Échec de la connexion à la base de données !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
    public void executerRequete(String sql){
        try{
            stm = this.bdd.createStatement();
            rs = stm.executeQuery(sql);

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public ObservableList<Map<String, Object>> executeQuery(String sql) {
        ObservableList<Map<String, Object>> items = FXCollections.observableArrayList();

        try {
            stm = this.bdd.createStatement();
            rs = stm.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int columCount = md.getColumnCount();

            while (rs.next()) {
                Map<String, Object> item1 = new HashMap<>();
                for (int index = 1; index <= columCount; index++) {
                    item1.putAll(afficher(md.getColumnType(index), index, rs, md));
                }
                items.add(item1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return items;
    }

    public Map <String, Object> afficher(int val, int index, ResultSet rs, ResultSetMetaData md ){
        Map <String, Object> item1 = new HashMap<>();
        try{
            switch (val){
                case java.sql.Types.INTEGER:
                case java.sql.Types.NUMERIC:
                    int value = rs.getInt(md.getColumnName(index));
                    item1.put(md.getColumnName(index), value);
                    break;
                case java.sql.Types.BOOLEAN:
                    Boolean bool = rs.getBoolean(md.getColumnName(index));
                    item1.put(md.getColumnName(index), bool);
                    break;
                case java.sql.Types.DATE:
                    Date date = rs.getDate(md.getColumnName(index));
                    item1.put(md.getColumnName(index), date);
                    break;
                case java.sql.Types.VARCHAR:
                    String string = rs.getString(md.getColumnName(index));
                    item1.put(md.getColumnName(index), string);
                    break;
                case java.sql.Types.TIME:
                    Time time = rs.getTime(md.getColumnName(index));
                    item1.put(md.getColumnName(index),time);
                    break;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return item1;
    }
    public ResultSet getRs() {
        return rs;
    }
}
