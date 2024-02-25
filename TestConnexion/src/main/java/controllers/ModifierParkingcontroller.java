package controllers;
import Entites.Parking;
import Service.ServiceParking;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierParkingcontroller {

    @FXML
    private ChoiceBox<String> choiceBoxStatus;

    @FXML
    private Spinner<Integer> spinnerNbrPlace;

    @FXML
    private TextField txtadresse;

    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtidpark;
    private Parking parkingAEditer;
    private final ServiceParking ser = new ServiceParking();
    private int idpark;
    private ObservableList<Parking> observableList;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
spinnerNbrPlace.setValueFactory(valueFactory);}
    @FXML
    void afficherParking(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParking.fxml"));
            Parent root = loader.load();
            AfficherParkingController dc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) spinnerNbrPlace.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void modifierParking(ActionEvent event) throws SQLException {
        Parking parkingModifie = creerParkingModifie();
        if (parkingModifie != null) {
            ser.update(parkingModifie);
            observableList.setAll(ser.readAll());
            Stage stage = (Stage) txtnom.getScene().getWindow();
            stage.close();
        }
    }

    private Parking creerParkingModifie() {
        int idpark = Integer.parseInt(txtidpark.getText());
        String nom = txtnom.getText();
        String adresse = txtadresse.getText();
        int nbrPlaces = spinnerNbrPlace.getValue();
        String status = choiceBoxStatus.getValue();
        if (nom.isEmpty() || adresse.isEmpty() || status.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        // Créez l'objet Parking modifié
        Parking parkingModifie = new Parking(idpark, nom, adresse, nbrPlaces, status);
        return parkingModifie;
    }


    public void initDonneesParking(Parking parking) {
        txtidpark.setText(String.valueOf(parking.getIdpark()));
        txtnom.setText(parking.getNompark());
        txtadresse.setText(parking.getAdressepark());
        spinnerNbrPlace.getValueFactory().setValue(parking.getNbrplace());
        choiceBoxStatus.setValue(parking.getStatuspark());
    }

    public void setObservableList(ObservableList<Parking> observableList) {
        this.observableList = observableList;
    }

}
