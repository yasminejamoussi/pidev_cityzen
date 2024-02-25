package controllers;

import Entites.Citoyen;
import Entites.Utilisateur;
import Service.ServiceUtilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SinscrireCitoyenController {
    ServiceUtilisateur UserService = new ServiceUtilisateur();
    ServiceUtilisateur su = new ServiceUtilisateur();
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

    public void Login(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Boolean VerifUserChamps() {
        boolean verif = false;
        String style = " -fx-border-color: red;";
        if (this.txtcin.getText().trim().equals("")) {
            this.txtcin.setStyle(style);
            verif = true;
        }

        if (this.txtnom.getText().trim().equals("")) {
            this.txtnom.setStyle(style);
            verif = true;
        }

        if (this.txtprenom.getText().trim().equals("")) {
            this.txtprenom.setStyle(style);
            verif = true;
        }
        if (this.txttele.getText().trim().equals("")) {
            this.txttele.setStyle(style);
            verif = true;
        }

        if (this.txtage.getText().trim().equals("")) {
            this.txtage.setStyle(style);
            verif = true;
        }

        if (this.txtemail.getText().trim().equals("")) {
            this.txtemail.setStyle(style);
            verif = true;
        }
        if (this.txtmdp.getText().trim().equals("") ) {
            this.txtmdp.setStyle(style);
            verif = true;
        }
        if (this.txtsexe.getText().trim().equals("")) {
            this.txtsexe.setStyle(style);
            verif = true;
        }

        if (!verif) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Verifier les champs.");
            al.setHeaderText((String)null);
            al.show();
            return false;
        }}

    public Boolean CheckLogin() throws SQLException {
        Boolean verif = true;
        List<Utilisateur> list_user = this.UserService.readAll();

        for(int i = 0; i < list_user.size(); ++i) {
            if (((Utilisateur)list_user.get(i)).getMail_Util().equals(this.txtemail.getText())) {
                verif = false;
            }
        }

        if (!verif) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Cet utilisateur existe déja!");
            al.setHeaderText((String)null);
            al.show();
        }

        return verif;
    }
    //Input Control: Validate CIN
    public boolean validateNumberCin() {
        Pattern p = Pattern.compile("[0-9]+\\.[0-9]+|[0-9]+");
        Matcher m = p.matcher(this.txtcin.getText());
        if (m.find() && m.group().equals(this.txtcin.getText()) && this.txtcin.getText().length() == 8) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("CIN doit contenir 8 chiffres.");
            al.setHeaderText((String)null);
            txtcin.setText("");
            al.show();
            return false;
        }
    }
    //Input Control: Validate email
    public boolean ValidateEmail() {
        Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(this.txtemail.getText());
        if (m.find() && m.group().equals(this.txtemail.getText())) {
            return true;
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("Email incorrecte");
            al.setHeaderText((String)null);
            txtemail.setText("");
            al.show();
            return false;
        }
    }
    private boolean restrictSpacesInUserName() {
        String trimmedValue = txtnom.getText().trim();
        if (!txtnom.getText().equals(trimmedValue)) {
            txtnom.setText(trimmedValue);
        }

        if (trimmedValue.contains(" ")) {
            showAlert("Error", "Pas d'espace");
            txtnom.setText("");
            return false;
        }
        return true;
    }

    private boolean allowOnlyLettersInField(TextField textField, String fieldName) {
        String trimmedValue = textField.getText().trim();
        if (!textField.getText().equals(trimmedValue)) {
            textField.setText(trimmedValue);
        }

        if (!trimmedValue.matches("^[a-zA-Z]*$")) {
            showAlert("Error", "Seules les lettres sont autorisées dans le " + fieldName + " Nom.");
            textField.setText("");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String password = txtmdp.getText();
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!?*-/]).{8,}$")) {
            showAlert("Error", "Le mot de passe doit comporter au moins 8 caractères et contenir des lettres, des chiffres, des majuscules et des minuscules, ainsi qu'au moins un caractère spécial.");
            txtmdp.setText("");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void sinscrire(javafx.event.ActionEvent actionEvent) throws SQLException{
            Citoyen user = new Citoyen();
            if (this.CheckLogin() && this.VerifUserChamps() && this.validateNumberCin() && this.ValidateEmail() &&
                    this.restrictSpacesInUserName() && this.allowOnlyLettersInField(txtnom, "Nom") &&
                    this.allowOnlyLettersInField(txtprenom, "Prenom") && this.validatePassword()

            ) {

                user.setMDP_Util(this.su.encrypt(this.txtmdp.getText()));
                user.setMail_Util(this.txtemail.getText());
                user.setNom(this.txtnom.getText());
                user.setPrenom(this.txtprenom.getText());
                user.setCin(Integer.parseInt(this.txtcin.getText()));
                user.setSexe(this.txtsexe.getText());
                user.setAge(Integer.parseInt(this.txtage.getText()));
                user.setNum(Integer.parseInt(this.txttele.getText()));

                this.UserService.ajouter(user);
                Alert resAlert = new Alert(Alert.AlertType.INFORMATION);
                resAlert.setHeaderText((String) null);
                resAlert.setContentText("Compte créé avec succès");
                resAlert.showAndWait();
                // this.Login(event);

            }
        }



}
