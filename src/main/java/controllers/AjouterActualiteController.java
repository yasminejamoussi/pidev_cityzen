package controllers;
import Entites.Actualite;
import Service.ServiceActualite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javafx.scene.control.TextArea;

import java.sql.SQLException;

public class AjouterActualiteController {
   /* @FXML
    private Label erreurAuteur;

    @FXML
    private Label erreurTitre;*/
    @FXML
    private TextField txtauteur;

    @FXML
    private ComboBox<String> txtcategorie;

    @FXML
    private DatePicker txtdateA;

   @FXML
   private TextArea txtact;

    @FXML
    private TextField txttitre;

    private final ServiceActualite ser = new ServiceActualite();
    @FXML
    public void initialize() {
        // Ajouter des éléments au ComboBox
        ObservableList<String> categories = FXCollections.observableArrayList("Mobilité et transport", "Environnement et durabilité", "Urbanisme", "Événements culturels et sociaux");
        txtcategorie.setItems(categories);
    }

    @FXML
    void AjouterActualite(ActionEvent event) {
        try {
            String titre = txttitre.getText();
            String contenu = txtact.getText();
            // Récupérer la date sélectionnée du DatePicker
            LocalDate localDate = txtdateA.getValue();
            // Convertir LocalDate en Date
            Date dateA = java.sql.Date.valueOf(localDate);
            String categorie = txtcategorie.getValue();
            String auteur = txtauteur.getText();

            Actualite a1 = new Actualite(titre, contenu, dateA, categorie, auteur);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Actualité ajoutée avec succès");
            alert1.showAndWait();

            ser.ajouter(a1);
            // Une fois ajouté avec succès, vider les champs de l'interface
            txttitre.clear();
            txtact.clear();
            txtdateA.setValue(null); // Remettre la date à null
            txtcategorie.getSelectionModel().clearSelection(); // Remettre la sélection de catégorie à null
            txtauteur.clear();
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void afficherActualite(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherActualite.fxml"));
            Parent root = loader.load();
            AfficherActualiteController dc = loader.getController();
            dc.setLbtitre(txttitre.getText());
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
