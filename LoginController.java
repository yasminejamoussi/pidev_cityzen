package controllers;

import Entites.Admin;
import Entites.Citoyen;
import Entites.Utilisateur;
import Service.ServiceUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtmdp;
    ServiceUtilisateur UserService = new ServiceUtilisateur();
    ServiceUtilisateur su = new ServiceUtilisateur();
    private static Citoyen utilisateurConnecte; // Utilisez Citoyen au lieu de Utilisateur

    public static Citoyen getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    @FXML
    void Login(ActionEvent event) throws SQLException {
        Alert al;
        String encPass = su.encrypt(txtmdp.getText());
        Utilisateur user = su.find(txtemail.getText(), encPass);
        if (user == null) {
            al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Alert");
            al.setContentText("invalid login or mot de passe");
            al.setHeaderText((String) null);
            al.show();
            return;
        }
            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUtilisateur.fxml"));
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (user instanceof Citoyen) {
                Citoyen citoyen = (Citoyen) user;
                utilisateurConnecte = (Citoyen) user;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profil.fxml"));
                    Parent root = loader.load();

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Autre type d'utilisateur ou null");
            }
        }
    }


