package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class AcceuilFront {

    @FXML
    void CompteFront(MouseEvent event) {

    }

    @FXML
    void MenuFront(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MenuFront.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void RéclamationFront(MouseEvent event) {

    }

    @FXML
    void VieSocialeFront(MouseEvent event) {

    }

    @FXML
    void factureFront(ActionEvent event) {

    }

    @FXML
    void parkingFront(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservationUtilisateur.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void transportFront(ActionEvent event) {

    }

}
