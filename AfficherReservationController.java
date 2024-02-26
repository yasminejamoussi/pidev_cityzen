package controllers;
import Entites.MoyenTransport;
import Entites.Reservation;
import Service.ServiceMoyenTransport;
import Service.ServiceReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AfficherReservationController {
    @FXML
    private TableColumn<Reservation, Date> coldate;

    @FXML
    private TableColumn<Reservation, String> colheure;

    @FXML
    private TableColumn<Reservation, Integer> colid;

    @FXML
    private TableColumn<Reservation, Integer> colid_transport;

    @FXML
    private TableColumn<Reservation, Integer> colid_user;

    @FXML
    private TableColumn<Reservation, String> colprix;
    @FXML
    private TableView<Reservation> tableview;
    private final ServiceReservation serr=new ServiceReservation();
    @FXML
    void  initialize()
    {

        try {
            List<Reservation> list=serr.readAll();
            ObservableList<Reservation> obers= FXCollections.observableList(list);
            tableview.setItems(obers);
            colid.setCellValueFactory(new PropertyValueFactory<>("id_reservation"));
            coldate.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
            colheure.setCellValueFactory(new PropertyValueFactory<>("heure_reservation"));
            colprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            colid_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            colid_transport.setCellValueFactory(new PropertyValueFactory<>("id_transport"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void supprimerreservation(javafx.event.ActionEvent event) throws SQLException {
        Reservation reservationSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (reservationSelectionne != null) {
            // Appel de votre service ou méthode pour supprimer le parking
            serr.delete(reservationSelectionne);
            // Rafraîchissez la table après la suppression
            initialize();
        } else {
            // Aucun parking sélectionné, affichez un message d'erreur ou avertissement
            System.out.println("Aucun Reservation sélectionné.");
        }

    }
    private final ServiceReservation serrr = new ServiceReservation();
    @FXML
    void modifierRes(javafx.event.ActionEvent event) throws SQLException  {
        Reservation reservationSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (reservationSelectionne!= null) {
            ouvrirInterfaceModificationn(reservationSelectionne);

        } else {
            System.out.println("Aucun reservation sélectionné.");
        }
    }
    private void ouvrirInterfaceModificationn(Reservation reservation) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReservation.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierReservationController modifierController = loader.getController();
            modifierController.setObservableListt(tableview.getItems());
            modifierController.initDonneesReservationn(reservation);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Reservation getreservationSelectionne() {
        return tableview.getSelectionModel().getSelectedItem();
    }




}
