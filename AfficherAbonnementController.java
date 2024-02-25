package controllers;

import Entites.Abonnement;
import Entites.Utilisateur;
import Service.ServiceAbonnement;
import Service.ServiceUtilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class AfficherAbonnementController {

    @FXML
    private TableColumn<Abonnement, Integer> colidabo;

    @FXML
    private TableColumn<Abonnement, Integer> colidutil;

    @FXML
    private TableColumn<Abonnement, String> colperiode;

    @FXML
    private TableColumn<Abonnement, String> colstatut;

    @FXML
    private TableColumn<Abonnement, String> coltype;

    @FXML
    private TableView<Abonnement> tableview;
    private final ServiceAbonnement ser=new ServiceAbonnement();

    @FXML
    void  initialize() {

        try {
            List<Abonnement> list = ser.readAll();
            ObservableList<Abonnement> obers = FXCollections.observableList(list);
            tableview.setItems(obers);
            colidabo.setCellValueFactory(new PropertyValueFactory<>("ID_Abo"));
            colidutil.setCellValueFactory(new PropertyValueFactory<>("ID_Util"));
            coltype.setCellValueFactory(new PropertyValueFactory<>("Type_Abo"));
            colperiode.setCellValueFactory(new PropertyValueFactory<>("Periode_Abo"));
            colstatut.setCellValueFactory(new PropertyValueFactory<>("Statut_Abo"));



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void supprimerAbonnement(ActionEvent event) {
        Abonnement abonnementSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (abonnementSelectionne != null) {
            ser.delete(abonnementSelectionne);

            initialize();
        } else {

            System.out.println("Aucun abonnement sélectionné.");
        }
    }
}
