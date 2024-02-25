package controllers;

import Entites.Actualite;
import Entites.Commentaire;
import Service.ServiceActualite;
import Service.ServiceCommentaire;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ModifierCommentaireController {
    @FXML
    private TextArea txtcontenuComModif;

    @FXML
    private TextField txtidComModif;
    private final ServiceCommentaire ser = new ServiceCommentaire();
    private ObservableList<Commentaire> observableList;

    private Actualite actualite; // Déclarer une variable pour stocker l'actualité associée

    public void setActualite(Actualite actualite) {
        this.actualite = actualite;
    }

    @FXML
    void ModifierCommentairem(ActionEvent event) throws SQLException {
        Commentaire commentaireModifie = creerCommentaireModifie();
        if (commentaireModifie != null) {
            ser.update(commentaireModifie);
            // Mettre à jour uniquement l'affichage des commentaires pour cette actualité
            observableList.setAll(ser.readCommentairesForSelectedActualite(actualite));
            Stage stage = (Stage) txtcontenuComModif.getScene().getWindow();
            stage.close();
        }
    }
    private Commentaire creerCommentaireModifie() {
        int id_com = Integer.parseInt(txtidComModif.getText());
        String contenuC = txtcontenuComModif.getText();
        if (contenuC.isEmpty() ) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        Commentaire commentaireModifie = new Commentaire(id_com,contenuC);
        return commentaireModifie;
    }
    public void initDonneesCommentaire(Commentaire commentaire) {
        txtidComModif.setText(String.valueOf(commentaire.getId_com()));
        txtcontenuComModif.setText(commentaire.getContenuC());

    }
    public void setObservableList(ObservableList<Commentaire> observableList) {
        this.observableList = observableList;
    }
}
