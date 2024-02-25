package controllers;

import Entites.Utilisateur;
import Entites.Admin;
import Service.ServiceUtilisateur;
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

public class ModifierAdmin {

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtid;

    @FXML
    private PasswordField txtmdp;

    private final ServiceUtilisateur ser = new ServiceUtilisateur();
    public void ModifierAdmin(ActionEvent actionEvent) throws SQLException {
        Admin adminModifie = creerAdminModifie();
        if (adminModifie != null) {
            ser.update(adminModifie);
            Stage currentStage = (Stage) txtemail.getScene().getWindow();
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
    private Admin creerAdminModifie() {
        int idUtil = Integer.parseInt(txtid.getText());
        String email = txtemail.getText();
        String mdp = txtmdp.getText();

        if (email.isEmpty() || mdp.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        Admin adminModifie = new Admin(idUtil, email, mdp);
        return adminModifie;
    }

    public void initDonneesAdmin(Admin admin) {
        txtid.setText(String.valueOf(admin.getID_Util()));
        txtemail.setText(admin.getMail_Util());
        txtmdp.setText(admin.getMDP_Util());
    }
}
