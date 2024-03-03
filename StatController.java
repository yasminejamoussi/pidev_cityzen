package controllers;

import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.sql.*;

public class StatController {
    private Connection connection;

    private Connection con= DataSource.getInstance().getCon();

   // private Statement ste;

    @FXML
    private PieChart pieChart;

    @FXML
    void afficherStatistique(ActionEvent event) {

    }

    private ObservableList<PieChart.Data> contc() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        try {
            // Utilisez un Statement pour exécuter la requête SQL
            try (Statement ste = con.createStatement();
                 ResultSet resultSet = ste.executeQuery("SELECT date_reservation, heure_reservation, COUNT(*) FROM reservationtransport GROUP BY date_reservation, heure_reservation")) {

                // Parcours des résultats et ajout des données au PieChart
                while (resultSet.next()) {
                    String heure_reservation = resultSet.getString("heure_reservation");
                    Date date_reservation = resultSet.getDate("date_reservation");
                    int nombreEvenements = resultSet.getInt(3); // Vous pouvez également utiliser le nom de la colonne "COUNT(*)"

                    PieChart.Data slice = new PieChart.Data(date_reservation + " - date_reservation " + heure_reservation, nombreEvenements);
                    pieChartData.add(slice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }
    @FXML
    void initialize() {
        ObservableList<PieChart.Data> pieChartData = contc();
        pieChart.setData(pieChartData);
    }
}

