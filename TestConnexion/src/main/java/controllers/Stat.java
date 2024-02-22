package controllers;

import Entites.Parking;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Stat implements Initializable {
    @FXML
    private BarChart<String, Integer> Chart;

    @FXML
    private CategoryAxis s_axis;

    @FXML
    private NumberAxis y_axis;

    private final ObservableList<Parking> data1 = FXCollections.observableArrayList();

    private Connection con;
    private Statement ste;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        try {
            con = DataSource.getInstance().getCon();
            ste = con.createStatement();

            // Requête SQL pour obtenir le nombre de réservations pour chaque adresse
            String query = "SELECT p.adressepark, COUNT(r.idres) as reservation_count " +
                    "FROM parking p " +
                    "LEFT JOIN reservation r ON p.idpark = r.idpark " +
                    "GROUP BY p.adressepark";

            ResultSet rs = ste.executeQuery(query);

            while (rs.next()) {
                // Ajouter les données à la série
                series.getData().add(new XYChart.Data(rs.getString("adressepark"), rs.getInt("reservation_count")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Chart.getData().addAll(series);
    }

    // Méthode pour obtenir le nombre de réservations pour un parking donné
    private int getReservationCountForParking(int idpark) {
        int count = 0;

        try {
            ResultSet rs1 = ste.executeQuery("SELECT COUNT(*) as count FROM reservation WHERE idpark = " + idpark);
            if (rs1.next()) {
                count = rs1.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
