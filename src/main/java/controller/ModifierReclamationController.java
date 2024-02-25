package controller;

import Entites.Reclamation;
import Service.ServiceForum;
import Service.ServiceReclamation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ModifierReclamationController {

    @FXML
    private TextField txtnid;
    //private Parking parkingAEditer;
    private final ServiceReclamation ser = new ServiceReclamation();
    private int id;
    private ObservableList<Reclamation> observableList;




    @FXML
    private TextField txtnemail;

    @FXML
    private TextField txtnnom;

    @FXML
    private TextField txtnprenom;

    @FXML
    private TextField txtnsujet;



    public void modifierreclamation(ActionEvent actionEvent) throws SQLException {
        Reclamation parkingModifie = creerParkingModifie();
        if (parkingModifie != null) {
            ser.update(parkingModifie);
            observableList.setAll(ser.readAll());
            Stage stage = (Stage) txtnnom.getScene().getWindow();
            stage.close();
        }
    }

    private Reclamation creerParkingModifie() {
        int id = Integer.parseInt(txtnid.getText());
        String nom = txtnnom.getText();
        String prenom = txtnprenom.getText();
        String email = txtnemail.getText();
        String reclamation = txtnsujet.getText();
        if ( nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || reclamation.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }
        // Créez l'objet Parking modifié
        Reclamation reclamationModifie = new Reclamation(id, nom, prenom, email, reclamation);
        return reclamationModifie;
    }
    public void initDonneesParking(Reclamation reclamation) {
        txtnid.setText(String.valueOf(reclamation.getId()));
        txtnnom.setText(reclamation.getNom());
        txtnprenom.setText(reclamation.getPrenom());
        txtnemail.setText(reclamation.getEmail());
        txtnsujet.setText(reclamation.getReclamation());

    }

    public void setObservableList(ObservableList<Reclamation> observableList) {
        this.observableList = observableList;
    }

}