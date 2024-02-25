package controller;

import Entites.Reclamation;
import Service.ServiceReclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterReclamationController {

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtmessage;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtprenom;
    ServiceReclamation ser=new ServiceReclamation();
    @FXML
    void ajouterReclamation(ActionEvent event) {
        ServiceReclamation ser=new ServiceReclamation();
        String nom = txtnom.getText();
        String prenom = txtprenom.getText();
        String email = txtemail.getText();
        String message = txtmessage.getText();
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        if (email.matches(emailRegex)) {
        Reclamation rec=new Reclamation(txtnom.getText(),txtprenom.getText(),txtemail.getText(),txtmessage.getText());
        try {
            ser.ajouter(rec);
            txtnom.clear();
            txtprenom.clear();
            txtemail.clear();
            txtmessage.clear();
        } catch (SQLException e) {}

        } else {
            // Invalid email, show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid email address.");
            alert.showAndWait();
        }
    }

    @FXML
    private Button btnafficher;
    @FXML
    void afficherReclamation(ActionEvent event) throws IOException {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
                Parent root = loader.load();
                AfficherReclamationController dc = loader.getController();
                dc.setLbname(txtnom.getText());
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                ((Node) (event.getSource())).getScene().getWindow().hide();

                // Fermer la fenêtre actuelle si nécessaire
                // ((Stage) txtnbrplace.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace(); // Gérer l'exception correctement
            }
        }

}
