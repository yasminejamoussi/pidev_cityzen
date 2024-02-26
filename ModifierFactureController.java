package controllers;

import Entites.Etat_Facture;
import Entites.Facture;
import Service.ServiceFacture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ModifierFactureController {

    @FXML
    private TextField txt_Description;

    @FXML
    private ChoiceBox<Etat_Facture> choice_Etat_Facture;


    @FXML
    private TextField txt_Id_Facture;

    @FXML
    private TextField txt_Id_Utilisateur;

    @FXML
    private TextField txt_Montant_Facture;

    private final ServiceFacture ser = new ServiceFacture();

    private ObservableList<Facture> observableList;

    public void setObservableList(ObservableList<Facture> observableList) {
        this.observableList = observableList;
    }
    public void  initialize(){
        ObservableList<Etat_Facture> choices = FXCollections.observableArrayList(Etat_Facture.values());
        choice_Etat_Facture.setItems(choices);
    }

    @FXML
    void afficher_Facture(ActionEvent event) {
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
    @FXML
    void modifierFacture(ActionEvent event) throws SQLException {
        Facture factureModifie = creerFactureModifie();
        if (factureModifie != null) {
            ser.update(factureModifie);
            observableList.setAll(ser.readAll());
            Stage stage = (Stage) txt_Id_Utilisateur.getScene().getWindow();
            stage.close();
        }
    }
    public void initDonneesFacture(Facture facture) {
        txt_Id_Facture.setText(String.valueOf(facture.getId_Facture()));
        txt_Id_Utilisateur.setText(String.valueOf(facture.getId_Utilisateur()));
        txt_Montant_Facture.setText(String.valueOf(facture.getMontant_Facture()));
        choice_Etat_Facture.setValue(Etat_Facture.valueOf(facture.getEtat_Facture()));
        txt_Description.setText(facture.getDescription());
    }
    private Facture creerFactureModifie() {

        LocalDate currentDate =LocalDate.now();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = Date.valueOf(currentDate);

        int Id_Facture = Integer.parseInt(txt_Id_Facture.getText());
        int Id_Utilisateur = Integer.parseInt(txt_Id_Utilisateur.getText());
        Date Date_Facture = sqlDate;
        float Montant_Facture = Float.parseFloat(txt_Montant_Facture.getText());
        String Etat_Facture = String.valueOf(choice_Etat_Facture.getValue());
        String Description = txt_Description.getText();
        if (txt_Id_Utilisateur.getText().isEmpty() || txt_Montant_Facture.getText().isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        // Créez l'objet Facture modifié
        Facture factureModifie = new Facture(Id_Facture,Id_Utilisateur,Date_Facture, Montant_Facture, Etat_Facture, Description);
        return factureModifie;
    }


}
