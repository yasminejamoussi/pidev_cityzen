package controllers;

import Entites.Facture;
import Service.ServiceFacture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AfficherFactureController {


    @FXML
    private TableColumn<Facture, Date> column_Date_Facture;

    @FXML
    private TableColumn<Facture, String> column_Description;

    @FXML
    private TableColumn<Facture, String> column_Etat_Facture;

    @FXML
    private TableColumn<Facture, Integer> column_Id_Utilisateur;

    @FXML
    private TableColumn<Facture, Float> column_Montant_Facture;

    @FXML
    private TableColumn<Facture, Integer> column_Id_Facture;


    @FXML
    private TableView<Facture> tableview;

    private final ServiceFacture ser=new ServiceFacture();
    @FXML
    void  initialize()
    {

        try {
            List<Facture> list=ser.readAll();
            ObservableList<Facture> obers= FXCollections.observableList(list);
            tableview.setItems(obers);
            column_Id_Facture.setCellValueFactory(new PropertyValueFactory<>("Id_Facture"));
            column_Id_Utilisateur.setCellValueFactory(new PropertyValueFactory<>("Id_Utilisateur"));
            column_Date_Facture.setCellValueFactory(new PropertyValueFactory<>("Date_Facture"));
            column_Montant_Facture.setCellValueFactory(new PropertyValueFactory<>("Montant_Facture"));
            column_Etat_Facture.setCellValueFactory(new PropertyValueFactory<>("Etat_Facture"));
            column_Description.setCellValueFactory(new PropertyValueFactory<>("Description"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private void ouvrirInterfaceModification(Facture facture) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierFacture.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierFactureController modifierController = loader.getController();
            modifierController.setObservableList(tableview.getItems());
            modifierController.initDonneesFacture(facture);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void modifierFacture(ActionEvent event) {
        Facture factureSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (factureSelectionne != null) {
            ouvrirInterfaceModification(factureSelectionne);

        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur");
            alert1.setContentText("Aucune facture séléctionné");
            alert1.showAndWait();
        }
    }

    @FXML
    void supprimerFacture(ActionEvent event) {
        try {
        Facture factureSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (factureSelectionne != null) {
            ser.delete(factureSelectionne);
            initialize();
        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur");
            alert1.setContentText("Aucune facture séléctionné");
            alert1.showAndWait();
        }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}