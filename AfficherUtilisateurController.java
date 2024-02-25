package controllers;

import Entites.Admin;
import Entites.Citoyen;
import Entites.Utilisateur;
import Service.ServiceUtilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class AfficherUtilisateurController {

    @FXML
    private TableColumn<Citoyen, Integer> colage;

    @FXML
    private TableColumn<Citoyen, Integer> colcin;

    @FXML
    private TableColumn<Utilisateur, Integer> colid;

    @FXML
    private TableColumn<Utilisateur, String> colmail;

    @FXML
    private TableColumn<Utilisateur, String> colmdp;

    @FXML
    private TableColumn<Citoyen, String> colnom;

    @FXML
    private TableColumn<Citoyen, Integer> colnum;

    @FXML
    private TableColumn<Citoyen, String> colprenom;

    @FXML
    private TableColumn<Citoyen, String> colsexe;

    @FXML
    private TableColumn<Admin, Integer> colidadmin;

    @FXML
    private TableColumn<Admin, String> colmailadmin;

    @FXML
    private TableColumn<Admin, String> colmdpadmin;

    @FXML
    private TableView<Utilisateur> tableviewadmin;

    @FXML
    private TableView<Utilisateur> tableviewcitoyen;

    private final ServiceUtilisateur ser = new ServiceUtilisateur();

    @FXML
    void initialize() {
        try {
            List<Utilisateur> allUsers = ser.readAll();
            ObservableList<Utilisateur> allUsersData = FXCollections.observableArrayList(allUsers);

            FilteredList<Utilisateur> adminFilteredList = new FilteredList<>(allUsersData, user -> user instanceof Admin);
            FilteredList<Utilisateur> citoyenFilteredList = new FilteredList<>(allUsersData, user -> user instanceof Citoyen);

            SortedList<Utilisateur> sortedAdminList = new SortedList<>(adminFilteredList, Comparator.comparing(Utilisateur::getMail_Util));
            SortedList<Utilisateur> sortedCitoyenList = new SortedList<>(citoyenFilteredList, Comparator.comparing(Utilisateur::getMail_Util));

            tableviewadmin.setItems(sortedAdminList);
            tableviewcitoyen.setItems(sortedCitoyenList);

            configureAdminTable();
            configureCitoyenTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configureAdminTable() {
        colidadmin.setCellValueFactory(new PropertyValueFactory<>("ID_Util"));
        colmailadmin.setCellValueFactory(new PropertyValueFactory<>("Mail_Util"));
        colmdpadmin.setCellValueFactory(new PropertyValueFactory<>("MDP_Util"));
        // Ajoutez d'autres colonnes spécifiques à Admin si nécessaire
    }

    private void configureCitoyenTable() {
        colid.setCellValueFactory(new PropertyValueFactory<>("ID_Util"));
        colmail.setCellValueFactory(new PropertyValueFactory<>("Mail_Util"));
        colmdp.setCellValueFactory(new PropertyValueFactory<>("MDP_Util"));
        colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colnum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colage.setCellValueFactory(new PropertyValueFactory<>("age"));
        colcin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        colsexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        // Ajoutez d'autres colonnes spécifiques à Citoyen si nécessaire
    }

    public void supprimerCitoyen(ActionEvent actionEvent) throws SQLException {
        Utilisateur utilisateurSelectionne = tableviewcitoyen.getSelectionModel().getSelectedItem();
        if (utilisateurSelectionne != null) {
            ser.delete(utilisateurSelectionne);

            initialize();
        } else {
            System.out.println("Aucun citoyen sélectionné.");
        }
    }

    public void supprimerAdmin(ActionEvent actionEvent) throws SQLException {
        Utilisateur utilisateurSelectionne = tableviewadmin.getSelectionModel().getSelectedItem();
        if (utilisateurSelectionne != null) {
            ser.delete(utilisateurSelectionne);
            initialize();
        } else {
            System.out.println("Aucun admin sélectionné.");
        }
    }

    public void ModifierAdmin(ActionEvent actionEvent) {
        Admin adminSelectionne = (Admin) tableviewadmin.getSelectionModel().getSelectedItem();
        if (adminSelectionne != null) {
            fermerInterfaceActuelle(actionEvent);
            ouvrirInterfaceModification(adminSelectionne);
        } else {
            System.out.println("Aucun parking sélectionné.");
        }
    }

    public void ModifierCitoyen(ActionEvent actionEvent) {
        Citoyen parkingSelectionne = (Citoyen) tableviewcitoyen.getSelectionModel().getSelectedItem();
        if (parkingSelectionne != null) {
            fermerInterfaceActuelle(actionEvent);
            ouvrirInterfaceModificationCitoyen(parkingSelectionne);
        } else {
            System.out.println("Aucun parking sélectionné.");
        }
    }

    private void ouvrirInterfaceModificationCitoyen(Citoyen citoyen) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCitoyen.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierCitoyen modifierController = loader.getController();
            modifierController.initDonneesCitoyen(citoyen);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ouvrirInterfaceModification(Admin admin) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAdmin.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierAdmin modifierController = loader.getController();
            modifierController.initDonneesAdmin(admin);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fermerInterfaceActuelle(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void AjouterAdmin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void AfficherUtilisateur(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUsers.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
