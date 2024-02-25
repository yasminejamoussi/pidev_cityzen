package controllers;

import Entites.Actualite;
import Service.ServiceActualite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AfficherActualiteController {
    @FXML
    private Label lbtitre;
    @FXML
    private TableColumn<Actualite, String> colauteur;

    @FXML
    private TableColumn<Actualite, String> colcategorie;

    @FXML
    private TableColumn<Actualite, String> colcontenu;

    @FXML
    private TableColumn<Actualite, LocalDate> coldate;

    @FXML
    private TableColumn<Actualite, String> coltitre;
    @FXML
    private TableView<Actualite> tableview;
    @FXML
    private TableColumn<Actualite, Integer> colid;
    private final ServiceActualite ser=new ServiceActualite();
    public Label getLbtitre() {
        return lbtitre;
    }

    public void setLbtitre(String lbtitre) {
        this.lbtitre.setText(lbtitre);
    }
    @FXML
    void  initialize()
    {
        try {
            // Récupérer les actualités depuis la base de données
            List<Actualite> list = ser.readAll();
            // Créer des propriétés de type PropertyValueFactory pour les colonnes du TableView
           PropertyValueFactory<Actualite, Integer> id_actualite = new PropertyValueFactory<>("id_actualite");
            PropertyValueFactory<Actualite, String> titre = new PropertyValueFactory<>("titre");
            PropertyValueFactory<Actualite, String> contenu = new PropertyValueFactory<>("contenu");
            PropertyValueFactory<Actualite, LocalDate> dateA = new PropertyValueFactory<>("dateA");
            PropertyValueFactory<Actualite, String> categorie = new PropertyValueFactory<>("categorie");
            PropertyValueFactory<Actualite, String> auteur= new PropertyValueFactory<>("auteur");

            // Associer les PropertyValueFactory aux colonnes du TableView
            colid.setCellValueFactory(id_actualite);
            coltitre.setCellValueFactory(titre);
            colcontenu.setCellValueFactory(contenu);
            coldate.setCellValueFactory(dateA); // Utiliser un PropertyValueFactory pour un LocalDate
            colcategorie.setCellValueFactory(categorie);
            colauteur.setCellValueFactory(auteur);

            // Charger les données dans le TableView
            ObservableList<Actualite> obers = FXCollections.observableList(list);
            tableview.setItems(obers);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    public void supprimerActualite(javafx.event.ActionEvent actionEvent) {
        // Logique pour supprimer le parking sélectionné
        Actualite actualiteSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (actualiteSelectionne != null) {
            // Appel de votre service ou méthode pour supprimer le parking
            ser.delete(actualiteSelectionne);
            // Rafraîchissez la table après la suppression
            initialize();
        } else {
            // Aucun parking sélectionné, affichez un message d'erreur ou avertissement
            System.out.println("Aucune actualitée sélectionné.");
        }
    }


  @FXML
    public void updateActualite(javafx.event.ActionEvent actionEvent) {
      Actualite actualiteSelectionne = tableview.getSelectionModel().getSelectedItem();
      if (actualiteSelectionne != null) {
          ouvrirInterfaceModification(actualiteSelectionne);

      } else {
          System.out.println("Aucune Actualitée sélectionné.");
      }
    }
    /*
    private boolean showModifierDialog(Actualite actualite) {
        TextInputDialog dialog = new TextInputDialog(actualite.getTitre());
        dialog.setTitle("Modifier Actualité");
        dialog.setHeaderText(null);
        dialog.setContentText("Nouveau nom de l'actualité:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            actualite.setTitre(result.get());
            ser.update(actualite);
            new Alert(Alert.AlertType.INFORMATION, "Modification réussie.").showAndWait();
            return true;
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Modification annulée.").showAndWait();
            return false;
        }
    }
    private void showAlert(String s, Alert.AlertType alertType) {
        
    }*/

    private void ouvrirInterfaceModification(Actualite actualite) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierActualite.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierActualiteController modifierController = loader.getController();
            modifierController.setObservableList(tableview.getItems());
            modifierController.initDonneesActualite(actualite);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Actualite getActualiteSelectionne() {
        return tableview.getSelectionModel().getSelectedItem();
    }

}



