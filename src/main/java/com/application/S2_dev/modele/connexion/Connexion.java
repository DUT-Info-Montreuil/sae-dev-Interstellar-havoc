package com.application.S2_dev.modele.connexion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;



public class Connexion {
    private static Connection bdd;
    private TableView tableView ;

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
    public void sql(String sql){
        try{
            Statement stm = this.bdd.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            rs.close();
            stm.close();
            this.bdd.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void executeQuery(String sql, Tab tab){
        try{
            Statement stm = this.bdd.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList() ;
            ResultSetMetaData md = rs.getMetaData();
            int columCount = md.getColumnCount();

            while(rs.next()){
                Map <String, Object> item1 = new HashMap<>();
                for(int index =1; index<= columCount; index++){
                    item1.putAll(afficher(md.getColumnType(index), index, rs, md));
                }
                items.add(item1);
            }
            tableView.getItems().addAll(items);
            for(int i =1; i<= columCount; i++ ){
                TableColumn<Map,String> Column = new TableColumn<>(md.getColumnName(i));
                Column.setCellValueFactory(new MapValueFactory<>(md.getColumnName(i)));
                tableView.getColumns().add(Column);
            }
            tab.setContent(tableView);

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public Map <String, Object> afficher(int val, int index, ResultSet rs, ResultSetMetaData md ){
        Map <String, Object> item1 = new HashMap<>();
        try{
            switch (val){
                case java.sql.Types.INTEGER:
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
                case java.sql.Types.NUMERIC:
                    int values = rs.getInt(md.getColumnName(index));
                    item1.put(md.getColumnName(index), values);
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

    public static Connection getBdd() {
        return bdd;
    }
}



/*
cd "Program Files (x86)\PostgreSQL\10\bin"
.\psql -U postgres -d postgres
*/
