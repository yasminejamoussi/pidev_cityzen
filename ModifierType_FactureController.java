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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierType_FactureController {


    @FXML
    private TextField txt_Id_Type_Facture;

    @FXML
    private TextField txt_Id_Facture;

    @FXML
    private ChoiceBox<Type_F> choice_Type_Facture;
    private ObservableList<Type_Facture> observableList;
    public void setObservableList(ObservableList<Type_Facture> observableList) {
        this.observableList = observableList;
    }
    private final ServiceType_Facture ser = new ServiceType_Facture();

    public void initialize() {
        ObservableList<Type_F> choices = FXCollections.observableArrayList(Type_F.values());
        choice_Type_Facture.setItems(choices);
    }
    @FXML
    void afficherType_Facture(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherType_Facture.fxml"));
            Parent root = loader.load();
            AfficherFactureController dc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle si nécessaire
            ((Stage) txt_Id_Facture.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception correctement
        }
    }

    @FXML
    void modifierType_Facture(ActionEvent event) throws SQLException {
        Type_Facture type_factureModifie = creerType_FactureModifie();
        if (type_factureModifie != null) {
            ser.update(type_factureModifie);
            observableList.setAll(ser.readAll());
            Stage stage = (Stage) txt_Id_Facture.getScene().getWindow();
            stage.close();
        }
    }
    public void initDonneesType_Facture(Type_Facture type_facture) {
        txt_Id_Type_Facture.setText(String.valueOf(type_facture.getId_Type_Facture()));
        txt_Id_Facture.setText(String.valueOf(type_facture.getId_Facture()));
        choice_Type_Facture.setValue(Type_F.valueOf(type_facture.getType_Facture()));
    }
    private Type_Facture creerType_FactureModifie() {

        int Id_Type_Facture = Integer.parseInt(txt_Id_Type_Facture.getText());
        int Id_Facture = Integer.parseInt(txt_Id_Facture.getText());
        String Type_Facture = String.valueOf(choice_Type_Facture.getValue());
        if (txt_Id_Facture.getText().isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        // Créez l'objet Type_Facture modifié
        Type_Facture type_factureModifie = new Type_Facture(Id_Type_Facture,Id_Facture,Type_Facture);
        return type_factureModifie;
    }
}
