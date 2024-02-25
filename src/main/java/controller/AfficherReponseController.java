package controller;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;
import Entites.Forum;
import Entites.Reclamation;
import Service.ServiceForum;
import Service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherReponseController {
    @FXML
    private TableColumn<Forum, Integer> colid;

    @FXML
    private TableColumn<Forum,String> colreponse;
    public Forum reclamationselect;

    public Forum getReclamationselect() {
        return reclamationselect;
    }

    @FXML
    private TableColumn<Forum,String> colsujet;

    @FXML
    private TextField txtreponse;
   // @FXML
    //private TableColumn<Reclamation, String> colid;
    @FXML
    private TableView<Forum> tableviewf;
    private TableView<Reclamation> tableview;
    @FXML
    private TextField txtnid;
    private final ServiceForum fer=new ServiceForum();
    private final ServiceReclamation ser=new ServiceReclamation();
    private Reclamation selectedReclamation;
    private Forum selectedForum;
    public void setSelectedReclamation(Reclamation selectedReclamation) {
        this.selectedReclamation = selectedReclamation;
        initialize();
    }
    public void setSelectedForum(Forum selectedForum) {
        this.selectedForum = selectedForum;
        initialize();
    }
    @FXML

    void initialize() {
        try {
           /* List<Forum> list=fer.readAll();

            ObservableList<Forum> obse= FXCollections.observableList(list);
            tableviewf.setItems(obse);
            colreponse.setCellValueFactory(new PropertyValueFactory<>("message_forum"));



        } catch (SQLException e) {

        }


            */
            if (selectedReclamation != null) {
                // Assuming you have a method in ServiceForum to retrieve responses based on selected Reclamation
                List<Forum> list = fer.readResponsesForSelectedReclamation(selectedReclamation);

                ObservableList<Forum> obse = FXCollections.observableList(list);
                tableviewf.setItems(obse);
                colreponse.setCellValueFactory(new PropertyValueFactory<>("message_forum"));
                colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            } else {
                System.out.println("Aucune Réclamation sélectionnée.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly
        }
    }

    public void ajouterreponse(ActionEvent actionEvent) {
       Forum reclamationselect = tableviewf.getSelectionModel().getSelectedItem();
        ServiceForum fer=new ServiceForum();
        Forum rec=new Forum(reclamationselect.getId_util(),reclamationselect.getSujet_forum(),txtreponse.getText(),reclamationselect.getId_reclamation());
        try {
            fer.ajouter(rec);
            initialize();
        } catch (SQLException e) {}

    }


    @FXML
    void supprimerreponse(ActionEvent event) throws SQLException {
        Forum reclamationselect = tableviewf.getSelectionModel().getSelectedItem();
        if (reclamationselect != null) {

            fer.delete(reclamationselect);
            initialize();
        } else {
            System.out.println("Aucun Moyen sélectionné.");
        }
    }

    /*
    @FXML
    void handleModifierClick(ActionEvent event) {

        if (selectedForum != null) {
            // Get the modified message from the TextField
            String modifiedMessage = txtreponse.getText();

            // Update the Forum message
            selectedForum.setMessage_forum(modifiedMessage);

            try {
                // Assuming 'forumService' is an instance of your ServiceForum class
                fer.update(selectedForum); // Call your update method here

                // Clear the selected Forum and the TextField
                selectedForum = null;
                txtreponse.clear();
            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
            // For this example, let's print the updated message
            System.out.println("Updated Message: " + selectedForum.getMessage_forum());

            // Clear the selected Forum and the TextField
            selectedForum = null;
            txtreponse.clear();
        }
    }
*/

    @FXML
    public void handleTableClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) { // Double-click
            Forum selectedForum = tableviewf.getSelectionModel().getSelectedItem();
            if (selectedForum != null) {
                // Set the Forum message to the TextField for editing
               txtreponse.setText(selectedForum.getMessage_forum());

                //txtnid.setText(String.valueOf(selectedForum.getId()));

            }
        }
    }
    public void modifier(ActionEvent actionEvent) {
        Forum selectedForum = tableviewf.getSelectionModel().getSelectedItem();

        if (selectedForum != null) {
            // Show a TextInputDialog to get the modified message
            TextInputDialog dialog = new TextInputDialog(selectedForum.getMessage_forum());
            dialog.setTitle("Modifier Réponse");
            dialog.setHeaderText("Entrez le nouveau message pour la réponse :");
            dialog.setContentText("Nouveau Message:");

            // Get the result of the dialog
            Optional<String> result = dialog.showAndWait();

            // If the user entered a new message, update the Forum
            result.ifPresent(modifiedMessage -> {
                selectedForum.setMessage_forum(modifiedMessage);

                try {
                    // Update the Forum in the database
                    fer.update(selectedForum);
                    // Refresh the table view after the update
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            });
        } else {
            System.out.println("Aucune Réponse sélectionnée.");
        }
    }

    public Forum getForumSelectionne() {
        return tableviewf.getSelectionModel().getSelectedItem();
    }
    public void setTableView(TableView<Reclamation> tableview) {
        this.tableview = tableview;
    }

}
