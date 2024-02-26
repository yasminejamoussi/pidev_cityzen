package controllers;
import Entites.MoyenTransport;
import Service.ServiceMoyenTransport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterMoyenTrannsportController {
    @FXML
    private TextField txtdestination;

    @FXML
    private TextField txtfrequence;

    @FXML
    private TextField txtheure_depart;

    @FXML
    private TextField txtlieu;

    @FXML
    private TextField txttype_transport;
    private final ServiceMoyenTransport ser = new ServiceMoyenTransport();

    @FXML
    void AjouterMoyenTransport(ActionEvent event) {
        try {
            String type_transport = txttype_transport.getText();
            String lieu = txtlieu.getText();
            String destination = txtdestination.getText();
            String heure_depart = txtheure_depart.getText();
            int frequence = Integer.parseInt(txtfrequence.getText());
            MoyenTransport p1 = new MoyenTransport(type_transport, lieu, destination, heure_depart,frequence);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Moyen Transport ajouté avec succès");
            alert1.showAndWait();

            ser.ajouterPST(p1);
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    @FXML
    void AfficherMoyenTransport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMoyenTransport.fxml"));
            Parent root = loader.load();
            AfficherMoyenTransportController dc = loader.getController();
            dc.setLbtype(txttype_transport.getText());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            // Fermer la fenêtre actuelle si nécessaire
            // ((Stage) txtnbrplace.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }

    }
    @FXML
    void reservation(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        currentStage.close(); // Close the current stage

        // Load and show the new interface
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterReservation.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception, if any
        }

    }


}
