package controllers;

import Entites.Facture;
import Entites.Type_Facture;
import Service.ServiceFacture;
import Service.ServiceType_Facture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherType_FactureController {

    @FXML
    private TableColumn<Type_Facture, Integer> column_Id_Facture;

    @FXML
    private TableColumn<Type_Facture, String> column_Type_Facture;

    @FXML
    private TableView<Type_Facture> tableview;

    public Label getLbl_Id_Facture() {
        return lbl_Id_Facture;
    }

    public void setLbl_Id_Facture(int lbl_Id_Facture) {
        this.lbl_Id_Facture.setText(String.valueOf(lbl_Id_Facture));
    }

    @FXML
    private Label lbl_Id_Facture;
    private final ServiceType_Facture ser=new ServiceType_Facture();
    @FXML
    void  initialize()
    {

        try {
            List<Type_Facture> list=ser.readAll();
            ObservableList<Type_Facture> obers= FXCollections.observableList(list);
            tableview.setItems(obers);
            column_Id_Facture.setCellValueFactory(new PropertyValueFactory<>("Id_Facture"));
            column_Type_Facture.setCellValueFactory(new PropertyValueFactory<>("Type_Facture"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void ouvrirInterfaceModification(Type_Facture type_facture) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierType_Facture.fxml"));
        Parent root;
        try {
            root = loader.load();
            ModifierType_FactureController modifierController = loader.getController();
            modifierController.setObservableList(tableview.getItems());
            modifierController.initDonneesType_Facture(type_facture);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierType_Facture(ActionEvent event) {
        Type_Facture type_factureSelectionne = tableview.getSelectionModel().getSelectedItem();
        if (type_factureSelectionne != null) {
            ouvrirInterfaceModification(type_factureSelectionne);

        } else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Erreur");
            alert1.setContentText("Aucun type facture séléctionné");
            alert1.showAndWait();
        }
    }

    @FXML
    void supprimerType_Facture(ActionEvent event) {
        try {
            Type_Facture type_factureSelectionne = tableview.getSelectionModel().getSelectedItem();
            if (type_factureSelectionne != null) {
                ser.delete(type_factureSelectionne);
                initialize();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Erreur");
                alert1.setContentText("Aucune facture séléctionné");
                alert1.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
