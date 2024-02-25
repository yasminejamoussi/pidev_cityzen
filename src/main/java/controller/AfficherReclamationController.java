package controller;
import Entites.Forum;
import Entites.Reclamation;
import Service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EventObject;
import java.util.List;


import Entites.Forum;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entites.Reclamation;
import Utils.DataSource;

public class AfficherReclamationController {

    @FXML
    private TableColumn<Reclamation, String> colemail;
    @FXML
    private TableColumn<Reclamation,Integer> colid;
    @FXML
    private TableColumn<Reclamation, String> colmessage;
    public Reclamation reclamationselect;
    private TableView<Reclamation> tableviewAct;

    public Reclamation getReclamationselect() {
        return reclamationselect;
    }

    @FXML
    private TableColumn<Reclamation, String> colnom;

    @FXML
    private TableColumn<Reclamation, String> colprenom;

    @FXML
    private Label lbname;

    @FXML
    private TableView<Forum> tableviewff;
    @FXML
    private TableColumn<Forum, String> colreponse;

    @FXML
    private TableView<Reclamation> tableview;

    public Label getLbname() {
        return lbname;
    }

    private final ServiceReclamation ser = new ServiceReclamation();

    public void setLbname(String lbname) {
        this.lbname.setText(lbname);
    }

    @FXML
    void initialize() {
        try {
            List<Reclamation> list = ser.readAll();

            ObservableList<Reclamation> obse = FXCollections.observableList(list);
            tableview.setItems(obse);

            colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colmessage.setCellValueFactory(new PropertyValueFactory<>("reclamation"));
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));

            // Set up row factory to handle mouse clicks
            tableview.setRowFactory(tv -> {
                TableRow<Reclamation> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                        Reclamation selectedReclamation = row.getItem();
                        handleRowClick(selectedReclamation);
                    }
                });
                return row;
            });

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly
        }
    }
    @FXML
    private void handleRowClick(Reclamation selectedReclamation) {
        System.out.println("Selected Reclamation: " + selectedReclamation);
        if (selectedReclamation != null) {
            openAfficherReponseController(selectedReclamation);
        } else {
            System.out.println("Aucune Réclamation sélectionnée.");
        }    }

    private void openAfficherReponseController(Reclamation selectedReclamation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReponse.fxml"));
            Parent root = loader.load();
            AfficherReponseController controller = loader.getController();

            // Pass the selected Reclamation to the AfficherReponseController
            controller.setSelectedReclamation(selectedReclamation);
            controller.setTableView(tableview);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly
        }
    }



    public void supprimerreclamation(ActionEvent actionEvent) {

        Reclamation reclamationselect = tableview.getSelectionModel().getSelectedItem();
        if (reclamationselect != null) {

            ser.delete(reclamationselect);
            initialize();
        } else {
            // Aucun parking sélectionné, affichez un message d'erreur ou avertissement
            System.out.println("Aucun Moyen sélectionné.");
        }
    }

    public void modifier(ActionEvent actionEvent) {
        Reclamation reclamationSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (reclamationSelectionne != null) {
            ouvrirInterfaceModification(reclamationSelectionne);

        } else {
            System.out.println("Aucun parking sélectionné.");
        }
    }
    private void ouvrirInterfaceModification(Reclamation reclamation) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamation.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierReclamationController modifierController = loader.getController();
            modifierController.setObservableList(tableview.getItems());
            modifierController.initDonneesParking(reclamation);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Reclamation getReclamationSelectionne() {
        return tableview.getSelectionModel().getSelectedItem();
    }

    public void ajouter(ActionEvent actionEvent) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
            Parent root = loader.load();
            AjouterReclamationController dc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();


            // Fermer la fenêtre actuelle si nécessaire
            // ((Stage) txtnbrplace.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }

   /* @FXML
    public void toanother(javafx.scene.input.MouseEvent mouseEvent) {
        //Reclamation reclamationselect = tableview.getSelectionModel().getSelectedItem();
        tableview.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                // Récupérer la personne sélectionnée
                Reclamation selectedreclamation = tableview.getSelectionModel().getSelectedItem();
                if (selectedreclamation != null) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReponse.fxml"));
                        Parent root = loader.load();
                        AfficherReponseController dc = loader.getController();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        // Fermer la fenêtre actuelle si nécessaire
                        // ((Stage) txtnbrplace.getScene().getWindow()).close();
                    } catch (IOException e) {
                        e.printStackTrace(); // Gérer l'exception correctement
                    }
                }
            }

        });

    }*/


}