package controllers;

import java.awt.*;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Entites.Utilisateur;
import Service.ServiceUtilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

public class AfficherUsers {

    @FXML
    private TableColumn<Utilisateur, Integer> colid;

    @FXML
    private TableColumn<Utilisateur, String> colmail;

    @FXML
    private TableColumn<Utilisateur, String> colmdp;

    @FXML
    private TableView<Utilisateur> tableview;

    @FXML
    private TextField txtrecherche;

    private final ServiceUtilisateur ser = new ServiceUtilisateur();

    @FXML
    void initialize() {
        try {
            refreshTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Ajouter un gestionnaire d'événements pour le bouton de recherche
        txtrecherche.setOnKeyPressed(this::handleSearchKeyPressed);

        // Ajouter un écouteur de changement de texte pour la recherche interactive
        txtrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Effectuer la recherche avec le nouveau texte
                List<Utilisateur> searchResults = ser.rechercherParNom(newValue);

                // Mettre à jour la table avec les résultats de la recherche
                ObservableList<Utilisateur> obers = FXCollections.observableList(searchResults);
                tableview.setItems(obers);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void refreshTable() throws SQLException {
        List<Utilisateur> list = ser.readAll();
        ObservableList<Utilisateur> obers = FXCollections.observableList(list);
        tableview.setItems(obers);
        colid.setCellValueFactory(new PropertyValueFactory<>("ID_Util"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("Mail_Util"));
        colmdp.setCellValueFactory(new PropertyValueFactory<>("MDP_Util"));
    }

    @FXML
    void Trie(ActionEvent event) {
        try {
            List<Utilisateur> sortedList = ser.diplayListsortedbymail();
            ObservableList<Utilisateur> obers = FXCollections.observableList(sortedList);
            tableview.setItems(obers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode appelée lorsqu'une touche est pressée dans le champ de recherche
    private void handleSearchKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                // Récupérer le texte de recherche
                String searchTerm = txtrecherche.getText();

                // Effectuer la recherche
                List<Utilisateur> searchResults = ser.rechercherParNom(searchTerm);

                // Mettre à jour la table avec les résultats de la recherche
                ObservableList<Utilisateur> obers = FXCollections.observableList(searchResults);
                tableview.setItems(obers);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
