package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBack {

    @FXML
    void gestionActualite(ActionEvent event) {

    }

    @FXML
    void gestionFacture(ActionEvent event) {

    }

    @FXML
    void gestionParking(ActionEvent event) {

    }

    @FXML
    void gestionReclamation(ActionEvent event) {

    }

    @FXML
    void gestionTransport(ActionEvent event) {

    }

    @FXML
    void gestionUtilisateur(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUtilisateur.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
