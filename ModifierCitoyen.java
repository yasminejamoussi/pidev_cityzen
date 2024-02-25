package controllers;

import Entites.Citoyen;
import Entites.Utilisateur;
import Service.ServiceUtilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierCitoyen {
    @FXML
    private TextField txtid;
    @FXML
    private TextField txtage;

    @FXML
    private TextField txtcin;

    @FXML
    private TextField txtemail;

    @FXML
    private PasswordField txtmdp;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;

    @FXML
    private TextField txtsexe;

    @FXML
    private TextField txttele;
    private final ServiceUtilisateur ser = new ServiceUtilisateur();

    private Citoyen creerCitoyenModifie() {
        int idUtil = Integer.parseInt(txtid.getText());
        String nom = txtnom.getText();
        String prenom = txtprenom.getText();
        String email = txtemail.getText();
        String mdp = txtmdp.getText();
        int age = Integer.parseInt(txtage.getText());
        int num = Integer.parseInt(txttele.getText());
        int cin = Integer.parseInt(txtcin.getText());
        String sexe = txtsexe.getText();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty() || sexe.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        Citoyen citoyenModifie = new Citoyen(idUtil, email, mdp, nom, prenom, cin, age, num, sexe);
        return citoyenModifie;
    }

    public void initDonneesCitoyen(Citoyen citoyen) {
        txtid.setText(String.valueOf(citoyen.getID_Util()));
        txtnom.setText(citoyen.getNom());
        txtprenom.setText(citoyen.getPrenom());
        txtcin.setText(String.valueOf(citoyen.getCin()));
        txtsexe.setText(citoyen.getSexe());
        txttele.setText(String.valueOf(citoyen.getNum()));
        txtemail.setText(citoyen.getMail_Util());
        txtmdp.setText(citoyen.getMDP_Util());
        txtage.setText(String.valueOf(citoyen.getAge()));
    }

    public void ModifierCitoyen(ActionEvent actionEvent) throws SQLException {
        Citoyen citoyenModifie = creerCitoyenModifie();
        if (citoyenModifie != null) {
            ser.update(citoyenModifie);
            Stage currentStage = (Stage) txtnom.getScene().getWindow();
            currentStage.close();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUtilisateur.fxml"));
                Parent root = loader.load();

                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
