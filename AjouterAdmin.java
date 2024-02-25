package controllers;

import Entites.Admin;
import Entites.Utilisateur;
import Service.ServiceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AjouterAdmin {

    @FXML
    private TextField txtemail;

    @FXML
    private PasswordField txtmdp;

    private final ServiceUtilisateur userService = new ServiceUtilisateur();

    @FXML
    public void sinscrire(ActionEvent actionEvent) throws SQLException {
        Admin user = new Admin();
        if (checkLogin() && verifUserChamps() && validateEmail() && validatePassword()) {
            user.setMDP_Util(this.userService.encrypt(this.txtmdp.getText()));
            user.setMail_Util(this.txtemail.getText());

            this.userService.ajouter(user);
            showAlert("Information", "Account created successfully");
            Login(actionEvent);
        }
    }

    private Boolean verifUserChamps() {
        boolean verif = false;
        String style = " -fx-border-color: red;";
        if (this.txtemail.getText().trim().isEmpty()) {
            this.txtemail.setStyle(style);
            verif = true;
        }
        if (this.txtmdp.getText().trim().isEmpty()) {
            this.txtmdp.setStyle(style);
            verif = true;
        }
        if (!verif) {
            return true;
        } else {
            showAlert("Error", "Please fill in all required fields.");
            return false;
        }
    }

    private Boolean checkLogin() throws SQLException {
        List<Utilisateur> listUser = this.userService.readAll();
        for (Utilisateur user : listUser) {
            if (user.getMail_Util().equals(this.txtemail.getText())) {
                showAlert("Error", "User already exists!");
                return false;
            }
        }
        return true;
    }

    private boolean validateEmail() {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher matcher = pattern.matcher(this.txtemail.getText());
        if (matcher.matches()) {
            return true;
        } else {
            showAlert("Error", "Incorrect email format.");
            txtemail.setText("");
            return false;
        }
    }

    private boolean validatePassword() {
        String password = txtmdp.getText();
        if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!?*-/]).{8,}$")) {
            showAlert("Error", "Password must be at least 8 characters and contain letters, numbers, uppercase, lowercase, and at least one special character.");
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

    public void Login(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current stage (window)
            Stage currentStage = (Stage) txtemail.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
