package Service;
import Entites.Parking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.DataSource;
public class ServiceParking  implements IService<Parking> {
    private Connection con = DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceParking() {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }


    }

    @Override
    public void ajouter(Parking parking) throws SQLException {

        String req = "INSERT INTO `parking` ( `nompark`, `adressepark`, `nbrplace`, `statuspark`) VALUES ( '" + parking.getNompark() + "', '" + parking.getAdressepark() + "', '" + parking.getNbrplace() + "', '" + parking.getStatuspark() + "');";
        ste.executeUpdate(req);
    }

    @Override
    public void update(Parking p) {
        String req = "UPDATE parking SET nompark = ?, adressepark = ?, nbrplace = ?, statuspark = ? WHERE idpark = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setString(1, p.getNompark());
            preparedStatement.setString(2, p.getAdressepark());
            preparedStatement.setInt(3, p.getNbrplace());
            preparedStatement.setString(4, p.getStatuspark());
            preparedStatement.setInt(5, p.getIdpark());

            preparedStatement.executeUpdate();
            System.out.println("Parking modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du parking : " + e.getMessage());
        }
    }



    @Override
    public void delete(Parking parking) {
        String req = "DELETE from parking where idpark = " + parking.getIdpark() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Parking findById(int id) throws SQLException {
        String req = "SELECT * FROM parking WHERE idpark = " + id + ";";
        ResultSet res = ste.executeQuery(req);
        if (res.next()) {
            int idpark = res.getInt(1);
            String nompark = res.getString("nompark");
            String adressepark = res.getString(3);
            int nbrplace = res.getInt("nbrplace");
            String statuspark = res.getString(5);
            return new Parking(idpark, nompark, adressepark, nbrplace, statuspark);
        }
        return null;
    }

    @Override
    public List<Parking> readAll() throws SQLException {

        List<Parking> list = new ArrayList<>();
        ResultSet res = ste.executeQuery("select * from parking");
        while (res.next()) {

            int idpark = res.getInt(1);
            String nompark = res.getString("nompark");
            String adressepark = res.getString(3);
            int nbrplace = res.getInt("nbrplace");
            String statuspark = res.getString(5);
            Parking p1 = new Parking(idpark, nompark, adressepark, nbrplace, statuspark);
            list.add(p1);
        }

        return list;
    }

    public List<Parking> search(String query) {
        query = query.toLowerCase();
        List<Parking> resultatsRecherche = new ArrayList<>();

        try {
            String req = "SELECT * FROM parking WHERE LOWER(nompark) LIKE '%" + query + "%' OR LOWER(adressepark) LIKE '%" + query + "%' OR LOWER(nbrplace) LIKE '%" + query + "%' OR LOWER(statuspark) LIKE '%" + query + "%';";
            ResultSet res = ste.executeQuery(req);

            while (res.next()) {
                int idpark = res.getInt(1);
                String nompark = res.getString("nompark");
                String adressepark = res.getString(3);
                int nbrplace = res.getInt("nbrplace");
                String statuspark = res.getString(5);
                Parking parking = new Parking(idpark, nompark, adressepark, nbrplace, statuspark);
                resultatsRecherche.add(parking);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultatsRecherche;
    }
    public List<Integer> getListeIdPark() throws SQLException {
        List<Integer> idParkList = new ArrayList<>();

        try {
            String req = "SELECT idpark FROM parking";
            ResultSet res = ste.executeQuery(req);

            while (res.next()) {
                int idpark = res.getInt("idpark");
                idParkList.add(idpark);
            }
        } catch (SQLException e) {
            // Gérer l'exception correctement
            e.printStackTrace();
        }

        return idParkList;
    }
    public List<Parking> getAvailableParkings() {
        List<Parking> availableParkings = new ArrayList<>();
        try {
            String query = "SELECT * FROM parking WHERE statuspark = 'Ouvert' AND nbrplace > 0";
            ResultSet resultSet = ste.executeQuery(query);

            while (resultSet.next()) {
                int idpark = resultSet.getInt("idpark");
                String nompark = resultSet.getString("nompark");
                String adressepark = resultSet.getString("adressepark");
                int nbrplace = resultSet.getInt("nbrplace");
                String statuspark = resultSet.getString("statuspark");

                Parking parking = new Parking(idpark, nompark, adressepark, nbrplace, statuspark);
                availableParkings.add(parking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableParkings;
    }
}
