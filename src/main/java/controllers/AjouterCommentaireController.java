package controllers;

import Entites.Actualite;
import Entites.Commentaire;
import Service.ServiceActualite;
import Service.ServiceCommentaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class AjouterCommentaireController {

    @FXML
    private TextArea txtcontenuC;

    @FXML
    private DatePicker txtdateC;
    private final ServiceCommentaire ser = new ServiceCommentaire();
    @FXML
    public void AjouterCommentaire(javafx.event.ActionEvent actionEvent) {
        try {

            String contenuC = txtcontenuC.getText();
            // Récupérer la date sélectionnée du DatePicker
            LocalDate localDate = txtdateC.getValue();
            // Convertir LocalDate en Date
            Date dateC = java.sql.Date.valueOf(localDate);
            Commentaire c1 = new Commentaire( contenuC, dateC);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Commentaire ajouté avec succès");
            alert1.showAndWait();

            ser.ajouter(c1);
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void AfficherCommentaire(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommentaire.fxml"));
            Parent root = loader.load();
            AfficherCommentaireController dc = loader.getController();
            dc.setLbcontenu(txtcontenuC.getText());
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
