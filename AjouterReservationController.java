package controllers;
import Entites.MoyenTransport;
import Entites.Reservation;
import Entites.ReservationConfirmationApp;
import Entites.ReservationTransport;
import Service.ServiceMoyenTransport;
import Service.ServiceReservation;
import Service.ServiceReservationTransport;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjouterReservationController {
    @FXML
    private DatePicker txtdate;

    @FXML
    private TextField txtheure;

    @FXML
    private TextField txtid_transport;

    @FXML
    private TextField txtid_user;

    @FXML
    private TextField txtprix;
    @FXML
    private TextField txtdestination;

    @FXML
    private TextField txtfrequence;
    @FXML
    private TextField txtheure_dep;
    @FXML
    private TextField txtlieu;
    @FXML
    private TextField txttype;


    private final ServiceReservationTransport serr = new ServiceReservationTransport();

    @FXML
    void ajouterreservation(ActionEvent event) {
        try {
            // Récupérer les données de la réservation à partir des champs de saisie
            LocalDate localDate = txtdate.getValue();
            Date date_reservation = java.sql.Date.valueOf(localDate);
            String heure_reservation = txtheure.getText();
            String prix = txtprix.getText();
            int id_user = Integer.parseInt(txtid_user.getText());
            int id_transport = Integer.parseInt(txtid_transport.getText());

            // Créer un objet Reservation avec les données récupérées
            ReservationTransport reservation = new ReservationTransport(date_reservation, heure_reservation, prix, id_user, id_transport);

            // Ajouter la réservation à la base de données
            serr.ajouterPST(reservation);

            // Envoi de l'e-mail de confirmation
            String to = "yasmine1jamoussi@gmail.com"; // Adresse e-mail du destinataire
            String subject = "Confirmation de réservation";
            String body = "Votre réservation a été confirmée avec succès.";

            ReservationConfirmationApp.sendEmail(to, subject, body);

            // Afficher une confirmation à l'utilisateur
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Reservation ajoutée avec succès et email de confirmation envoyé.");
            alert1.showAndWait();

        } catch (SQLException | NumberFormatException e) {
            // Gérer les erreurs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void afficherreservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservationTransport.fxml"));
            Parent root = loader.load();
            AfficherReservationTransport dc = loader.getController();
            //dc.setLbtype(txtprix.getText());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Fermer la fenêtre actuelle si nécessaire
            // ((Stage) txtnbrplace.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }

    }
    public void initDonneesMoyen(MoyenTransport moyenTransport) {
        //txtid.setText(String.valueOf(moyenTransport.getId_transport()));
        txttype.setText(moyenTransport.getType_transport());
        txtlieu.setText(moyenTransport.getLieu());
        txtdestination.setText(moyenTransport.getDestination());
        txtheure_dep.setText(moyenTransport.getHeure_depart());
        txtfrequence.setText(String.valueOf(moyenTransport.getFrequence()));
    }


}
