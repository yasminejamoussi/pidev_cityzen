package controllers;
import Entites.Parking;
import Service.ServiceParking;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.TextField;

public class AfficherParkingController {

    @FXML
    private TableColumn<Parking, Integer> colidpark;

    @FXML
    private TableColumn<Parking, String> coladresse;

    @FXML
    private TableColumn<Parking, Integer> colnbrplace;

    @FXML
    private TableColumn<Parking, String> colnom;

    @FXML
    private TableColumn<Parking, String> colstatus;

    @FXML
    private TableView<Parking> tablewiew;

    @FXML
    private TextField txtrechercheparking;


    private final ServiceParking ser = new ServiceParking();

    @FXML
    void initialize() {
        try {
            List<Parking> list = ser.readAll();
            ObservableList<Parking> obers = FXCollections.observableList(list);
            tablewiew.setItems(obers);
            colidpark.setCellValueFactory(new PropertyValueFactory<>("idpark"));
            colnom.setCellValueFactory(new PropertyValueFactory<>("nompark"));
            coladresse.setCellValueFactory(new PropertyValueFactory<>("adressepark"));
            colnbrplace.setCellValueFactory(new PropertyValueFactory<>("nbrplace"));
            colstatus.setCellValueFactory(new PropertyValueFactory<>("statuspark"));
            txtrechercheparking.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    searchParking(newValue);
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerParking(ActionEvent event) {
        Parking parkingSelectionne = tablewiew.getSelectionModel().getSelectedItem();
        if (parkingSelectionne != null) {
            ser.delete(parkingSelectionne);
            initialize();
        } else {
            System.out.println("Aucun parking sélectionné.");
        }
    }

    private void searchParking(String query) {
        List<Parking> resultatsRecherche = ser.search(query);
        ObservableList<Parking> obers = FXCollections.observableList(resultatsRecherche);
        tablewiew.setItems(obers);
   }
    @FXML
    void modifierParking(ActionEvent event) {
        Parking parkingSelectionne = tablewiew.getSelectionModel().getSelectedItem();
        if (parkingSelectionne != null) {
            ouvrirInterfaceModification(parkingSelectionne);

        } else {
            System.out.println("Aucun parking sélectionné.");
        }
    }


    private void ouvrirInterfaceModification(Parking parking) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierParking.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierParkingcontroller modifierController = loader.getController();
            modifierController.setObservableList(tablewiew.getItems());
            modifierController.initDonneesParking(parking);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parking getParkingSelectionne() {
        return tablewiew.getSelectionModel().getSelectedItem();
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

            ((Stage) tablewiew.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }
    @FXML
    void stat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Stat.fxml"));
            Parent root = loader.load();
            Stat dc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            ((Stage) tablewiew.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
