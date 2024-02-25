package Test;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

public class MainFxml extends Application {

    @Override
    public void start(Stage stage) throws Exception {

       FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterParking.fxml"));
      // FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherReservationUtilisateur.fxml"));
      //  FXMLLoader loader=new FXMLLoader(getClass().getResource("/MenuFront.fxml"));
        Parent root=loader.load();

        Scene scene=new Scene(root);

        scene.getStylesheets().add(getClass().getResource("/Style/stylesheet.css").toExternalForm());
       // stage.setTitle("Gérer Parkings");
        stage.setTitle("Gérer Réservations");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
