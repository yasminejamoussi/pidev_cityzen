package controllers;
import Entites.Etat_Facture;
import Service.ServiceFacture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import Entites.Facture;
import controllers.AfficherFactureController;
import javafx.stage.Stage;

public class AjouterFactureController {
    @FXML
    private TextField txt_Date_Facture;

    @FXML
    private TextField txt_Description;


    @FXML
    private ChoiceBox<Etat_Facture> choice_Etat_Facture;
    @FXML
    private TextField txt_Id_Utilisateur;

    @FXML
    private TextField txt_Montant_Facture;

    private final ServiceFacture ser = new ServiceFacture();
    @FXML
    void  initialize(){
        ObservableList<Etat_Facture> choices = FXCollections.observableArrayList(Etat_Facture.values());
        choice_Etat_Facture.setItems(choices);
    }

    @FXML
    void ajouterFacture(ActionEvent event) {
        LocalDate currentDate =LocalDate.now();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = Date.valueOf(currentDate);



        try {
            int Id_Utilisateur = Integer.parseInt(txt_Id_Utilisateur.getText());
            Date Date_Facture = sqlDate;
            float Montant_Facture = Float.parseFloat(txt_Montant_Facture.getText());
            String Etat_Facture = String.valueOf(choice_Etat_Facture.getValue());
            String Description = txt_Description.getText();
           ser.ajouter(new Facture(Id_Utilisateur, Date_Facture, Montant_Facture, Etat_Facture, Description));
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Facture ajouté avec succès");
            alert1.showAndWait();
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void afficherFacture(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFacture.fxml"));
            Parent root = loader.load();
            AfficherFactureController dc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            ((Stage) txt_Id_Utilisateur.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }
}
