package controllers;

import Entites.Parking;
import Entites.Reservation;
import Service.ServiceReservation;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class AfficherReservationController {
    @FXML
    private TextField txtheuredebut;

    @FXML
    private TextField txtheurefin;

    @FXML
    private TextField txtidpark;
    @FXML
    private TableColumn<Reservation, Time> colheuredebut;

    @FXML
    private TableColumn<Reservation, Time> colheurefin;

    @FXML
    private TableColumn<Reservation, Integer> colidpark;

    @FXML
    private TableColumn<Reservation, Integer> colidutil;

    @FXML
    private TableView<Reservation> tablewiew;
    @FXML
    private TableColumn<Reservation, Integer> colidres;

    @FXML
    private TextField txtrecherchereservation;

    private final ServiceReservation ser = new ServiceReservation();
    @FXML
    void initialize() {
        try {
            List<Reservation> list = ser.readAll();
            ObservableList<Reservation> obers = FXCollections.observableList(list);
            tablewiew.setItems(obers);
            colidres.setCellValueFactory(new PropertyValueFactory<>("idres"));
            colidutil.setCellValueFactory(new PropertyValueFactory<>("idutilisateur"));
            colidpark.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getParking().getIdpark()).asObject());
            colheuredebut.setCellValueFactory(new PropertyValueFactory<>("heuredebut"));
            colheurefin.setCellValueFactory(new PropertyValueFactory<>("heurefin"));
            txtrecherchereservation.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    searchReservation(newValue);
                }
            });

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void searchReservation(String query) {
        List<Reservation> resultatsRecherche = ser.search(query);
        ObservableList<Reservation> obers = FXCollections.observableList(resultatsRecherche);
        tablewiew.setItems(obers);
    }
    public void supprimerReservation(javafx.event.ActionEvent actionEvent) {
        Reservation reservationSelectionne = tablewiew.getSelectionModel().getSelectedItem();
        if (reservationSelectionne != null) {
            ser.delete(reservationSelectionne);
            initialize();
        } else {
            System.out.println("Aucune resérvation sélectionnée.");
        }
    }
    public Reservation getReservationSelectionne() {
        return tablewiew.getSelectionModel().getSelectedItem();
    }

    @FXML
    void modifierReservation(ActionEvent event) {
        Reservation reservationSelectionne = tablewiew.getSelectionModel().getSelectedItem();
        if (reservationSelectionne != null) {
            ouvrirInterfaceModification(reservationSelectionne);

        } else {
            System.out.println("Aucun parking sélectionné.");
        }
    }
    private void ouvrirInterfaceModification(Reservation reservation) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReservation.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierReservationController modifierController = loader.getController();
            modifierController.setObservableList(tablewiew.getItems());
            modifierController.initDonneesParking(reservation);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}