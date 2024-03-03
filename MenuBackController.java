package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBackController {
    public void gestionFacture(ActionEvent actionEvent) {
    }

    public void gestionParking(ActionEvent actionEvent) {
    }

    public void gestionUtilisateur(ActionEvent actionEvent) {
    }

    public void gestionActualite(ActionEvent actionEvent) {
    }

    public void gestionReclamation(ActionEvent actionEvent) {
    }

    public void gestionTransport(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMoyenTransport.fxml"));
            Parent root = loader.load();
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
