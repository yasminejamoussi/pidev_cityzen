package Service;
import Entites.Parking;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entites.Reservation;
import Utils.DataSource;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.scene.control.Alert;

public class ServiceReservation implements IService<Reservation> {
    private Connection con = DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceReservation() {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        String req = "INSERT INTO `reservation` (`idutilisateur`, `idpark`, `heuredebut`, `heurefin`) VALUES (?, ?, ?, ?)";

        try (PreparedStatement st = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, reservation.getIdutilisateur());
            st.setInt(2, reservation.getParking().getIdpark());
            st.setInt(3, reservation.getHeuredebut());
            st.setInt(4, reservation.getHeurefin());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                // Récupérer la clé générée automatiquement
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idres = generatedKeys.getInt(1);
                        reservation.setIdres(idres);
                    }
                }
            }
        }
    }


    @Override
    public void update(Reservation r) {
        try {
            if (r.getParking() != null) {
                String req = "UPDATE reservation SET idutilisateur = ?, idpark = ?, heuredebut = ?, heurefin = ? WHERE idres = ?";

                PreparedStatement st = con.prepareStatement(req);
                st.setInt(1, r.getIdutilisateur());
                st.setInt(2, r.getParking().getIdpark());
                st.setInt(3, r.getHeuredebut());
                st.setInt(4, r.getHeurefin());
                st.setInt(5, r.getIdres());

                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Modifiée !");
                } else {
                    System.out.println("Aucune ligne modifiée.");
                }
            } else {
                System.out.println("Le parking dans la réservation est null.");
            }
        } catch (SQLException ev) {
            System.out.println("Erreur lors de la mise à jour : " + ev.getMessage());
        }
    }


    @Override
    public void delete(Reservation reservation) {
        String req = "DELETE from reservation where idres = " + reservation.getIdres() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Reservation findById(int id) throws SQLException {
        String req = "SELECT * FROM reservation WHERE idres = " + id + ";";
        ResultSet res = ste.executeQuery(req);
        if (res.next()) {
            int idres = res.getInt(1);
            int idutilisateur = res.getInt(2);
            int idpark = res.getInt(3);
            Integer heuredebut = res.getInt(4);
            Integer heurefin = res.getInt(5);
            Parking parking = new Parking(
                    res.getInt("idpark"),
                    res.getString("nompark"),
                    res.getString("adressepark"),
                    res.getInt("nbrplace"),
                    res.getString("statuspark")
            );
            parking.setIdpark(idpark);
            return new Reservation(idres, idutilisateur, parking, heuredebut, heurefin);
        }
        return null;
    }

    @Override
    public List<Reservation> readAll() throws SQLException {
        List<Reservation> list = new ArrayList<>();
        ResultSet res = ste.executeQuery("SELECT reservation.*, parking.* FROM reservation " +
                "JOIN parking ON reservation.idpark = parking.idpark");
        while (res.next()) {
            int idres = res.getInt("idres");
            int idutilisateur = res.getInt("idutilisateur");
            int idpark = res.getInt("idpark");
            Integer heuredebut = res.getInt("heuredebut");
            Integer heurefin = res.getInt("heurefin");

            Parking parking = new Parking(
                    res.getInt("idpark"),
                    res.getString("nompark"),
                    res.getString("adressepark"),
                    res.getInt("nbrplace"),
                    res.getString("statuspark")
            );

            Reservation r1 = new Reservation(idres, idutilisateur, parking, heuredebut, heurefin);
            list.add(r1);
        }
        return list;
    }

    public List<Reservation> search(String query) {
        query = query.toLowerCase();
        List<Reservation> resultatsRecherche = new ArrayList<>();

        try {
            String req = "SELECT * FROM reservation WHERE LOWER(idutilisateur) LIKE '%" + query + "%' OR LOWER(idpark) LIKE '%" + query + "%' OR LOWER(heuredebut) LIKE '%" + query + "%' OR LOWER(heurefin) LIKE '%" + query + "%';";
            ResultSet res = ste.executeQuery(req);

            while (res.next()) {
                int idutilisateur = res.getInt(1);
                int idpark = res.getInt(2);
                Integer heuredebut = res.getInt(4);
                Integer heurefin = res.getInt(5);
                Parking parking = new Parking(
                        res.getInt("idpark"),
                        res.getString("nompark"),
                        res.getString("adressepark"),
                        res.getInt("nbrplace"),
                        res.getString("statuspark")
                );
                parking.setIdpark(idpark);
                Reservation reservation = new Reservation(idutilisateur, parking, heuredebut, heurefin);
                resultatsRecherche.add(reservation);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return resultatsRecherche;
    }

}
