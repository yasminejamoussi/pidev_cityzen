package controllers;

import Entites.Etat_Facture;
import Entites.Type_F;
import Entites.Type_Facture;
import Service.ServiceType_Facture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterType_FactureController {

    @FXML
    private TextField txt_Id_Facture;

    @FXML
    private AnchorPane txt_Id_Utilisateur;


    @FXML
    private ChoiceBox<Type_F> choice_Type_Facture;


    private final ServiceType_Facture ser = new ServiceType_Facture();

    @FXML
    void  initialize(){
        ObservableList<Type_F> choices = FXCollections.observableArrayList(Type_F.values());
        choice_Type_Facture.setItems(choices);
    }
    @FXML
    void ajouterType_Facture(ActionEvent event) {
        try {
            int Id_Facture = Integer.parseInt(txt_Id_Facture.getText());
            String Type_Facture = String.valueOf(choice_Type_Facture.getValue());
            Type_Facture tf1 = new Type_Facture(Id_Facture, Type_Facture);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Type facture ajouté avec succès");
            alert1.showAndWait();

            ser.ajouter(tf1);
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void afficherType_Facture(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherType_Facture.fxml"));
            Parent root = loader.load();
            AfficherType_FactureController dc = loader.getController();
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
