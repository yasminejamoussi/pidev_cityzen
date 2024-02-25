package Test;
import javafx.application.Application;
import javafx.stage.Stage;
import  javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
public class testy extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Ajouter");
        stage.setScene(scene);
        stage.show();

    }

    public static void testy(String[] args) {
        launch(args);
    }
}