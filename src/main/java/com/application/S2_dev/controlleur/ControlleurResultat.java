package com.application.S2_dev.controlleur;

import com.application.S2_dev.Main;
import com.application.S2_dev.modele.connexion.Connexion;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ControlleurResultat  implements Initializable {
    @FXML
    private Tab tabEnnemis;

    @FXML
    private Tab tabEnnemisTues;

    @FXML
    private Tab tabToursPlacees;

    @FXML
    private TabPane tab;
    private Connexion connexion;
    int limit = 20;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connexion = new Connexion();
        initEnnemis();
        initParties();
        initJoueurs();
    }

    private void initEnnemis() {
        String sql = "SELECT * FROM ennemi_partie inner join ennemi on id_ennemi_partie = id_ennemi;";
        ObservableList<Map<String, Object>> items = connexion.executeQuery(sql);
        afficherResultat(items, tabEnnemisTues);
    }

    private void initParties() {
        String sql = "SELECT * FROM partie INNER JOIN joueur using(id_joueur);";
        ObservableList<Map<String, Object>> items = connexion.executeQuery(sql);
        this.afficherResultat(items, tabEnnemis);

    }

    private void initJoueurs() {
        String sql = "SELECT * FROM joueur;";
        ObservableList<Map<String, Object>> items = connexion.executeQuery(sql);
        afficherResultat(items, tabToursPlacees);
    }
    @FXML
    void quitter(ActionEvent event) {
        exit();
    }
    public void exit() {
        Parent root;
        try {
            Stage stage1 = (Stage) tab.getScene().getWindow();
            stage1.close(); // Fermeture de la page de jeu
            root = FXMLLoader.load(Main.class.getResource("fxml/Menu/Menu.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Menu de jeu");
            stage.setScene(new Scene(root, 1250, 800));
            stage.show(); // Affichage du menu de jeu
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void afficherResultat(ObservableList<Map<String, Object>> items, Tab tab) {
        TableView<Map<String, Object>> tableView = new TableView<>();
        tab.setContent(tableView);

        if (!items.isEmpty()) {
            Map<String, Object> firstItem = items.get(0);
            for (String columnName : firstItem.keySet()) {
                TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
                column.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get(columnName)));
                tableView.getColumns().add(column);
            }
        }

        int rowCount = Math.min(items.size(), limit);
        List<Map<String, Object>> lastRows = items.subList(items.size() - rowCount, items.size());
        tableView.getItems().addAll(lastRows);
    }
}
