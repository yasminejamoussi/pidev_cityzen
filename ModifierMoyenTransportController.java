package controllers;
import Entites.MoyenTransport;
import Service.ServiceMoyenTransport;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierMoyenTransportController {
    @FXML
    private TextField txtdestination;

    @FXML
    private TextField txtfrequence;

    @FXML
    private TextField txtheure;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtlieu;

    @FXML
    private TextField txttype;

    @FXML
    void modiermoyen(ActionEvent event) throws SQLException {
        MoyenTransport moyenTransportModifie = creerMoyenModifie();
        if (moyenTransportModifie != null) {
            ser.update(moyenTransportModifie);
            observableList.setAll(ser.readAll());
            Stage stage = (Stage) txttype.getScene().getWindow();
            stage.close();
        }

    }
    @FXML
    void affichermoyen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMoyenTransport.fxml"));
            Parent root = loader.load();
            AfficherMoyenTransportController dc = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
           // ((Stage) spinnerNbrPlace.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ServiceMoyenTransport ser = new ServiceMoyenTransport();
    //private int id_transport;
    private ObservableList<MoyenTransport> observableList;

    private MoyenTransport creerMoyenModifie() {
        int id_transport = Integer.parseInt(txtid.getText());
        String type_transport = txttype.getText();
        String lieu = txtlieu.getText();
        String destination = txtdestination.getText();
        String heure_depart = txtheure.getText();
        int frequence = Integer.parseInt(txtfrequence.getText());
        if (type_transport.isEmpty() || lieu.isEmpty() || destination.isEmpty() || heure_depart.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return null;
        }

        // Créez l'objet Parking modifié
        MoyenTransport moyenTransportModifie = new MoyenTransport(id_transport, type_transport, lieu, destination, heure_depart,frequence);
        return moyenTransportModifie;
    }


    public void initDonneesMoyen(MoyenTransport moyenTransport) {
        txtid.setText(String.valueOf(moyenTransport.getId_transport()));
        txttype.setText(moyenTransport.getType_transport());
        txtlieu.setText(moyenTransport.getLieu());
        txtdestination.setText(moyenTransport.getDestination());
        txtheure.setText(moyenTransport.getHeure_depart());
        txtfrequence.setText(String.valueOf(moyenTransport.getFrequence()));
    }

    public void setObservableList(ObservableList<MoyenTransport> observableList) {
        this.observableList = observableList;
    }

}

