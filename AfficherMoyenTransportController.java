package controllers;
import Entites.MoyenTransport;
import Service.ServiceMoyenTransport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherMoyenTransportController {
    @FXML
    private Label lbtype;
    @FXML
    private TableColumn<MoyenTransport, String> coldestination;

    @FXML
    private TableColumn<MoyenTransport, Integer> colfrequence;

    @FXML
    private TableColumn<MoyenTransport, String> colheure;

    @FXML
    private TableColumn<MoyenTransport, String> collieu;

    @FXML
    private TableColumn<MoyenTransport, String> coltype;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private ComboBox<String> combofiltrer;

    @FXML
    private TableView<MoyenTransport> tableview;

    public Label getLbtype() {
        return lbtype;

    }

    public void setLbtype(String lbtype) {
        this.lbtype.setText(lbtype);
    }

    private final ServiceMoyenTransport ser=new ServiceMoyenTransport();
    @FXML
    void initialize()
    {
        try {
            List<MoyenTransport> list=ser.readAll();
            ObservableList<MoyenTransport> obers= FXCollections.observableList(list);
            FilteredList<MoyenTransport> filteredList = new FilteredList<>(obers);
            tableview.setItems(filteredList);
            colid.setCellValueFactory(new PropertyValueFactory<>("id_transport"));
            coltype.setCellValueFactory(new PropertyValueFactory<>("type_transport"));
            collieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            coldestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            colheure.setCellValueFactory(new PropertyValueFactory<>("heure_depart"));
            colfrequence.setCellValueFactory(new PropertyValueFactory<>("frequence"));

            combofiltrer.valueProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(moyenTransport -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Afficher tous les éléments si rien n'est sélectionné
                    }
                    String selectedType = newValue.toLowerCase();
                    return moyenTransport.getType_transport().toLowerCase().equals(selectedType);
                });
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void SupprimerMoyenTransport(javafx.event.ActionEvent actionEvent) throws SQLException {
      // Logique pour supprimer le parking sélectionné
      MoyenTransport moyenTransportSelectionne = tableview.getSelectionModel().getSelectedItem();
      if (moyenTransportSelectionne != null) {
          // Appel de votre service ou méthode pour supprimer le parking
          ser.delete(moyenTransportSelectionne);
          // Rafraîchissez la table après la suppression
          initialize();
      } else {
          // Aucun parking sélectionné, affichez un message d'erreur ou avertissement
          System.out.println("Aucun Moyen sélectionné.");
      }
    }

    private final ServiceMoyenTransport serr = new ServiceMoyenTransport();


   /* private void searchParking(String query) {
        List<MoyenTransport> resultatsRecherche = ser.search(query);
        ObservableList<MoyenTransport> obers = FXCollections.observableList(resultatsRecherche);
        tableview.setItems(obers);
    }*/

    @FXML
    void modifiermoyentransport(javafx.event.ActionEvent event) throws SQLException  {
        MoyenTransport moyenTransportSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (moyenTransportSelectionne != null) {
            ouvrirInterfaceModification(moyenTransportSelectionne);

        } else {
            System.out.println("Aucun Moyen sélectionné.");
        }
    }



    private void ouvrirInterfaceModification(MoyenTransport moyenTransport) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMoyenTransport.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierMoyenTransportController modifierController = loader.getController();
            modifierController.setObservableList(tableview.getItems());
            modifierController.initDonneesMoyen(moyenTransport);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void reservation(javafx.event.ActionEvent event) {
        MoyenTransport moyenTransportSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (moyenTransportSelectionne != null) {
            ouvrirInterfaceReservation(moyenTransportSelectionne);
        } else {
            System.out.println("Aucun Moyen sélectionné.");
        }
    }

    private void ouvrirInterfaceReservation(MoyenTransport moyenTransport) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
        Parent root;
        try {
            root = loader.load();
            AjouterReservationController ajouterController = loader.getController();
            ajouterController.initDonneesMoyen(moyenTransport);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  /*  @FXML
    void reservation(javafx.event.ActionEvent event) {
        MoyenTransport moyenTransportSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (moyenTransportSelectionne != null) {
            ouvrirInterfaceReservation(moyenTransportSelectionne);

        } else {
            System.out.println("Aucun Moyen sélectionné.");
        }

    }*/
    /*private void ouvrirInterfaceReservation(MoyenTransport moyenTransport) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
        Parent root;
        try {
            root = loader.load();
            AjouterReservationController ajouterController = loader.getController();
            ajouterController.setObservableList(tableview.getItems());
            ajouterController.initDonneesMoyen(moyenTransport);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public MoyenTransport getParkingSelectionne() {
        return tableview.getSelectionModel().getSelectedItem();
    }

}
