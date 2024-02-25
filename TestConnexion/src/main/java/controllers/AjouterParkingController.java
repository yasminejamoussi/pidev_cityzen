package controllers;

import Entites.Parking;
import Service.ServiceParking;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;


public class AjouterParkingController {
    @FXML
    private TextField txtadresse;

    @FXML
    private Spinner<Integer> spinnerNbrPlace; // Utilisez le Spinner pour le nombre de places

    @FXML
    private TextField txtnom;

    @FXML
    private ChoiceBox<String> choiceBoxStatus;

    private final ServiceParking ser = new ServiceParking();

    @FXML
    public void initialize() {

        spinnerNbrPlace.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
    }
    @FXML
    void ajouterParking(ActionEvent event) {
        try {
            String nom = txtnom.getText();
            String adresse = txtadresse.getText();
            int nbrplace = spinnerNbrPlace.getValue();
            String status = choiceBoxStatus.getValue();

            if (!nom.matches("^[a-zA-Z]+$")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Le nom doit contenir uniquement des lettres.");
                alert.showAndWait();
                return;
            }

            Parking p1 = new Parking(nom, adresse, nbrplace, status);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Parking ajouté avec succès");
            alert1.showAndWait();

            ser.ajouter(p1);

            txtnom.clear();
            txtadresse.clear();
            spinnerNbrPlace.getValueFactory().setValue(0);
            choiceBoxStatus.getSelectionModel().clearSelection();

        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void afficherReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml"));
            Parent root = loader.load();
            AfficherReservationController dc = loader.getController();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) spinnerNbrPlace.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }


    @FXML
    void afficherParking(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParking.fxml"));
            Parent root = loader.load();
            AfficherParkingController dc = loader.getController();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            ((Stage) spinnerNbrPlace.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }
}
