package Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entites.Abonnement;
import Utils.DataSource;

public class ServiceAbonnement implements IService<Abonnement> {
    private Connection con=DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceAbonnement()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    @Override
    public void ajouter(Abonnement abonnement) throws SQLException {
        String req = "INSERT INTO abonnement (ID_Util,Type_Abo,Periode_Abo,Statut_Abo) VALUES ('"
                + abonnement.getID_Util() + "', '" + abonnement.getType_Abo() + "', '"
                + abonnement.getPeriode_Abo() + "', '" + abonnement.getStatut_Abo() + "');";
        ste.executeUpdate(req);
    }
    @Override
    public void update(Abonnement r) {
        String req = "UPDATE abonnement set ID_Util = '" + r.getID_Util() + "', Type_Abo = '"
                + r.getType_Abo() + "', Periode_Abo = '" + r.getPeriode_Abo() + "', Statut_Abo = '" + r.getStatut_Abo()
                + "' where ID_Abo = " + r.getID_Abo() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("modifiée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }

    @Override
    public void delete(Abonnement abonnement) {
        String req = "DELETE from abonnement where ID_Abo = " + abonnement.getID_Abo() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Abonnement findById(int id) throws SQLException {
        String req = "SELECT * FROM abonnement WHERE ID_Abo = " + id + ";";
        ResultSet res = ste.executeQuery(req);
        if (res.next()) {
            int ID_Abo = res.getInt(1);
            int ID_Util = res.getInt(2);
            String Type_Abo = res.getString(3);
            String Periode_Abo = res.getString(4);
            String Statut_Abo = res.getString(5);
            return new Abonnement(ID_Abo, ID_Util, Type_Abo, Periode_Abo, Statut_Abo);
        }
        return null;
    }

    @Override
    public List<Abonnement> readAll() throws SQLException {
        List<Abonnement> list = new ArrayList<>();
        ResultSet res = ste.executeQuery("SELECT * FROM abonnement");
        while (res.next()) {
            int ID_Abo = res.getInt(1);
            int ID_Util = res.getInt(2);
            String Type_Abo = res.getString(3);
            String Periode_Abo = res.getString(4);
            String Statut_Abo = res.getString(5);
            Abonnement r1 = new Abonnement(ID_Abo, ID_Util, Type_Abo, Periode_Abo, Statut_Abo);
            list.add(r1);
        }
        return list;
    }

}