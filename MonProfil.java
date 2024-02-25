package controllers;

import Entites.Citoyen;
import Entites.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MonProfil {


    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;

    // Initialise les champs avec les informations de l'utilisateur connecté
    public void initialize() {
        // Utilisez la méthode statique pour accéder à l'utilisateur connecté
        Citoyen utilisateurConnecte = LoginController.getUtilisateurConnecte();

        // Vérifiez si l'utilisateur est connecté avant d'initialiser les champs
        if (utilisateurConnecte != null) {
            txtnom.setText(utilisateurConnecte.getNom());
            txtprenom.setText(utilisateurConnecte.getPrenom());
        }
    }
}
