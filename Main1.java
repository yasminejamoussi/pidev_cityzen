package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main1 extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterMoyenTrannsport.fxml"));
        // FXMLLoader loaderOne=new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
        Parent root=loader.load();
        //Parent rootOne=loaderOne.load();
        Scene scene =new Scene(root);
        //  Scene sceneOne =new Scene(rootOne);
        stage.setTitle("Gérer MoyenTransport");
        // stage.setTitle("Gérer Reservation");
        stage.setScene(scene);
        //stage.setScene(sceneOne);
        stage.show();

    }
    public static void main(String[] args){
        launch(args);
    }
}

