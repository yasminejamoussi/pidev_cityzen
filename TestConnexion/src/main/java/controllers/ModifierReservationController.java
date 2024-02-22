package controllers;

import Entites.Parking;
import Entites.Reservation;
import Service.ServiceReservation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModifierReservationController {

    @FXML
    private Spinner<Integer> spinnerheuredebut;

    @FXML
    private Spinner<Integer> spinnerheurefin;

    @FXML
    private TextField txtidpark;

    @FXML
    private TextField txtidres;

    @FXML
    private TextField txtidutilisateur;
    private int idres;
    private Parking parkingSelectionne;
    private final ServiceReservation ser = new ServiceReservation();
    private ObservableList<Reservation> observableList;
    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        spinnerheuredebut.setValueFactory(valueFactory);
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
        spinnerheurefin.setValueFactory(valueFactory1);}
    @FXML
    void modifierReservation(ActionEvent event) throws SQLException {
        Reservation reservationModifie = creerReservatiobModifie();
        if (reservationModifie != null) {
            ser.update(reservationModifie);
            observableList.setAll(ser.readAll());
            Stage stage = (Stage) txtidpark.getScene().getWindow();
            stage.close();
        }
    }
    private Reservation creerReservatiobModifie() {
        int idres = Integer.parseInt(txtidres.getText());
        int idutilisateur = Integer.parseInt(txtidutilisateur.getText());
        int heuredebut = spinnerheuredebut.getValue();
        int heurefin = spinnerheurefin.getValue();

        Reservation reservationModifie = new Reservation(idres, idutilisateur, parkingSelectionne, heuredebut, heurefin);
        return reservationModifie;
    }
    public void initDonneesParking(Reservation reservation) {
        txtidres.setText(String.valueOf(reservation.getIdres()));
        txtidpark.setText(String.valueOf(reservation.getParking().getIdpark()));
        txtidutilisateur.setText(String.valueOf(reservation.getIdutilisateur()));
        spinnerheuredebut.getValueFactory().setValue(reservation.getHeuredebut());
        spinnerheurefin.getValueFactory().setValue(reservation.getHeurefin());
        parkingSelectionne = reservation.getParking();
    }

    public void setObservableList(ObservableList<Reservation> observableList) {
        this.observableList = observableList;
    }
}
