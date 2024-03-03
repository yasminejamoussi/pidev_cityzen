package controllers;
import Entites.MoyenTransport;
import Entites.Reservation;
import Service.ServiceMoyenTransport;
import Service.ServiceReservation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class ModifierReservationController {
    @FXML
    private DatePicker txtdate;

    @FXML
    private TextField txtheure;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtid_transport;

    @FXML
    private TextField txtid_user;

    @FXML
    private TextField txtprix;

    @FXML
    void ModifierReservation(ActionEvent event) throws SQLException {
        Reservation reservationModifie = creerReservationModifie();
        if (reservationModifie != null) {
            ser.update(reservationModifie);
            observableList.setAll(ser.readAll());
            Stage stage = (Stage) txtprix.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    void afficgerReservation(ActionEvent event)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml"));
            Parent root = loader.load();
            AfficherReservationController dc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // ((Stage) spinnerNbrPlace.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ServiceReservation ser = new ServiceReservation();
    //private int id_transport;
    private ObservableList<Reservation> observableList;

    private Reservation creerReservationModifie() {
        int id_reservation = Integer.parseInt(txtid.getText());
        LocalDate localDate = txtdate.getValue();
        // Convertir LocalDate en Date
        Date date_reservation = java.sql.Date.valueOf(localDate);
        String heure_reservation = txtheure.getText();
        String prix = txtprix.getText();
        int id_user = Integer.parseInt(txtid_user.getText());
        int id_transport = Integer.parseInt(txtid_transport.getText());
        if (!prix.matches("\\d.*")) {
            // La saisie ne commence pas par un chiffre
            // Afficher un message d'erreur ou effectuer une action appropriée
        }
        if ( heure_reservation.isEmpty() || prix.isEmpty() ) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        // Créez l'objet Parking modifié
       Reservation ReservationModifie = new Reservation(id_reservation, date_reservation, heure_reservation, prix, id_user,id_transport);
        return ReservationModifie;
    }


    public void initDonneesReservationn(Reservation reservation) {
        txtid.setText(String.valueOf(reservation.getId_reservation()));
        txtdate.setValue(reservation.getDate_reservation().toLocalDate());
        txtheure.setText(reservation.getHeure_reservation());
        txtprix.setText(reservation.getPrix());
        txtid_user.setText(String.valueOf(reservation.getId_user()));
        txtid_transport.setText(String.valueOf(reservation.getId_transport()));
    }


    public void setObservableListt(ObservableList<Reservation> observableList) {

        this.observableList = observableList;
    }
}
