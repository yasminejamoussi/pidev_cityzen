package controllers;

import Entites.Parking;
import Entites.Reservation;
import Service.ServiceParking;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherReservationUtilisateurController {
    @FXML
    private TableColumn<Parking, String> coladresse;

    @FXML
    private TableColumn<Parking, String> colnom;

    @FXML
    private TableView<Parking> tablewiew;

    private final ServiceParking ser = new ServiceParking();
    @FXML
    void initialize() {
        List<Parking> list = ser.getAvailableParkings();
        ObservableList<Parking> obers = FXCollections.observableList(list);
        tablewiew.setItems(obers);

        colnom.setCellValueFactory(new PropertyValueFactory<>("nompark"));
        coladresse.setCellValueFactory(new PropertyValueFactory<>("adressepark"));

    }

    public void ajouterReservation(javafx.event.ActionEvent actionEvent) {
        Parking parkingSelectionne = tablewiew.getSelectionModel().getSelectedItem();
        if (parkingSelectionne != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FaireReservation.fxml"));
            Parent root;
            try {
                root = loader.load();
                FaireReservationController faireReservationController = loader.getController();
                faireReservationController.initDonneesReservation(parkingSelectionne);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun parking sélectionné.");
        }
    }
}
