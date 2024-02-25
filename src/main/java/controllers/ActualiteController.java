package controllers;

import Entites.Actualite;
import Entites.Commentaire;
import Service.ServiceActualite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.time.Instant;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class ActualiteController {

    @FXML
    private TableColumn<Actualite, Integer> colidAct1;
    @FXML
    private TextField txtauteurA;
    @FXML
    private ComboBox<String> txtcategA;
    @FXML
    private TextArea txtcontenuAct;
    @FXML
    private DatePicker txtdateAct;

    @FXML
    private TextField txttitreA;
    @FXML
    private TableColumn<Actualite, String> colauteurA;

    @FXML
    private TableColumn<Actualite, String> colcategA;

    @FXML
    private TableColumn<Actualite, String> colcontenuAct;

    @FXML
    private TableColumn<Actualite, LocalDate> coldateAct;

    @FXML
    private TableColumn<Actualite, String> coltitreA;

    @FXML
    private TableView<Actualite> tableviewAct;
    @FXML
    private TableColumn<Commentaire, String> colcontenuC;
    @FXML
    private TableColumn<Commentaire, LocalDate> coldateC;
    public Actualite selectedActualite;
    @FXML
    private TableColumn<Commentaire, Integer> colidCom1;
    @FXML
    private Label lbcontenu;
    @FXML
    private TableView<Commentaire> tableviewc;
    ObservableList<Actualite> listeActualites = FXCollections.observableArrayList();
    private final ServiceActualite ser = new ServiceActualite();
    public void setTableViewAct(TableView<Actualite> tableviewAct) {
        this.tableviewAct = tableviewAct;
    }

    @FXML
    public void initialize() {
        // Créer une nouvelle liste observable vide
        listeActualites = FXCollections.observableArrayList();

        // Ajouter des éléments au ComboBox
        ObservableList<String> categories = FXCollections.observableArrayList("Mobilité et transport", "Environnement et durabilité", "Urbanisme", "Événements culturels et sociaux");
        txtcategA.setItems(categories);

        // Lier la liste observable à la TableView
        tableviewAct.setItems(listeActualites);

        // Assuming the property name in the Actualite class is "id"
        PropertyValueFactory<Actualite, Integer> idColumn = new PropertyValueFactory<>("id");
        // Set the cell value factory for the TableColumn
        colidAct1.setCellValueFactory(idColumn);
        coltitreA.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colcontenuAct.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        coldateAct.setCellValueFactory(new PropertyValueFactory<>("dateA"));
        colcategA.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        colauteurA.setCellValueFactory(new PropertyValueFactory<>("auteur"));

        // Charger les données de la base de données dans votre liste observable au démarrage
        try {
            // Clear the list before adding new items to avoid duplicates
            listeActualites.clear();
            listeActualites.addAll(ser.readAll());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        // Set up row factory to handle mouse clicks
        tableviewAct.setRowFactory(tv -> {
            TableRow<Actualite> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    Actualite selectedActualite = row.getItem();
                    // Maintenant vous pouvez utiliser selectedActualite ici
                    handleRowClick(selectedActualite);
                }
            });
            return row;
        });
    }

    @FXML
    private void handleRowClick(Actualite selectedActualite) {

        if (selectedActualite != null) {
            setSelectedActualite(selectedActualite);
            openAfficherCommentaireController(selectedActualite);
        } else {
            System.out.println("Aucune Actualitée sélectionnée.");
        }

    }

    private void setSelectedActualite(Actualite selectedActualite) {
        this.selectedActualite = selectedActualite;
    }

    private void openAfficherCommentaireController(Actualite selectedActualite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommentaire.fxml"));
            Parent root = loader.load();
            AfficherCommentaireController controller = loader.getController();

            // Pass the selected Actualite to the AfficherCommentaireController
            controller.setSelectedActualite(selectedActualite);

            // Set the TableView in AfficherCommentaireController
            controller.setTableViewAct(tableviewAct);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly
        }
    }

    @FXML
    void AjouterActualitee(ActionEvent event) {
        try {
            String titre = txttitreA.getText();
            String contenu = txtcontenuAct.getText();
            LocalDate localDate = txtdateAct.getValue();
            Date dateA = java.sql.Date.valueOf(localDate);
            String categorie = txtcategA.getValue();
            String auteur = txtauteurA.getText();

            String titreRegex =  "^[A-Za-z\\s]*$"; // Expression régulière pour le titre
            String auteurRegex = "^[A-Za-z\\s]*$";   // Expression régulière pour l'auteur

            if (titre.matches(titreRegex) && auteur.matches(auteurRegex)) {
                Actualite a1 = new Actualite(titre, contenu, dateA, categorie, auteur);
                try {
                    ser.ajouter(a1);
                    listeActualites.clear();
                    listeActualites.addAll(ser.readAll());

                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setTitle("Confirmation");
                    alert1.setContentText("Actualité ajoutée avec succès");
                    alert1.showAndWait();

                    txttitreA.clear();
                    txtcontenuAct.clear();
                    txtdateAct.setValue(null);
                    txtcategA.getSelectionModel().clearSelection();
                    txtauteurA.clear();

                } catch (SQLException | NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Entrée invalide");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez fournir des informations valides pour tous les champs.");
                alert.showAndWait();
            }
        } finally {
            // Traitez les opérations finales ici si nécessaire
        }
    }

    @FXML
    void SupprimerActualitee(ActionEvent event) {
// Logique pour supprimer le parking sélectionné
        Actualite actualiteSelectionne = tableviewAct.getSelectionModel().getSelectedItem();
        if (actualiteSelectionne != null) {
            // Appel de votre service ou méthode pour supprimer le parking
            ser.delete(actualiteSelectionne);
            // Afficher une confirmation à l'utilisateur
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Suppression");
            alert1.setContentText("Actualité supprimée avec succès");
            alert1.showAndWait();
            // Rafraîchissez la table après la suppression
            initialize();
        } else {
            // Aucun parking sélectionné, affichez un message d'erreur ou avertissement
            System.out.println("Aucune actualitée sélectionné.");
        }
    }
    @FXML
    void ModifierActualitee(ActionEvent event) {
        Actualite actualiteSelectionne = tableviewAct.getSelectionModel().getSelectedItem();
        if (actualiteSelectionne != null) {
            ouvrirInterfaceModification(actualiteSelectionne);

        } else {
            System.out.println("Aucune Actualitée sélectionné.");
        }
    }
    private void ouvrirInterfaceModification(Actualite actualite) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierActualite.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierActualiteController modifierController = loader.getController();
            modifierController.setObservableList(tableviewAct.getItems());
            modifierController.initDonneesActualite(actualite);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Actualite getActualiteSelectionne() {
        return tableviewAct.getSelectionModel().getSelectedItem();
    }


    public Actualite actualiteselect;

    public Actualite getActualiteselect() {
        return actualiteselect;
    }
}

