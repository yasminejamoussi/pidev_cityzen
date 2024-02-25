package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


public class MainFxml extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Afficher une boîte de dialogue demandant à l'utilisateur de choisir une interface
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choix de l'action");
        alert.setHeaderText("Veuillez choisir une fonctionnalité");
        alert.setContentText("Que souhaitez-vous faire ?");

        ButtonType buttonTypeOne = new ButtonType("Gérer Actualités");
        ButtonType buttonTypeTwo = new ButtonType("Gérer Commentaires");

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

        // Attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeOne) {
                // Charger et afficher la première interface
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterActualite.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    primaryStage.setTitle("Gérer Actualités");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (buttonType == buttonTypeTwo) {
                // Charger et afficher la deuxième interface
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentaire.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setTitle("Gérer Commentaires");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}


