package controllers;
import Entites.MoyenTransport;
import Entites.Reservation;
import Service.ServiceMoyenTransport;
import Service.ServiceReservation;
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

    private final ServiceReservation serr = new ServiceReservation();

    @FXML
    void ajouterreservation(ActionEvent event) {
        try {
            // Récupérer la date sélectionnée du DatePicker
            LocalDate localDate = txtdate.getValue();
            // Convertir LocalDate en Date
            Date date_reservation = java.sql.Date.valueOf(localDate);
            String heure_reservation  = txtheure.getText();
            String prix = txtprix.getText();
            int id_user = Integer.parseInt(txtid_user.getText());
            int id_transport = Integer.parseInt(txtid_transport.getText());
            Reservation p1 = new Reservation(date_reservation, heure_reservation, prix, id_user,id_transport);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Reservation ajouté avec succès");
            alert1.showAndWait();

            serr.ajouterPST(p1);
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    void afficherreservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml"));
            Parent root = loader.load();
            AfficherReservationController dc = loader.getController();
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

}
