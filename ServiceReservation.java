package Service;
import Entites.MoyenTransport;
import Entites.Reservation;
import Utils.DataSource;

import java.sql.*;

import java.util.ArrayList;
import java.sql.Date;
//import java.util.Date;
import java.util.List;
public class ServiceReservation implements IService <Reservation> {

        private Connection con= DataSource.getInstance().getCon();

        private Statement ste;

        public ServiceReservation()
        {
            try {
                ste= con.createStatement();
            }catch (SQLException e)
            {
                System.out.println(e);
            }

        }
        public void ajouter(Reservation reservation) throws SQLException {

            String req="INSERT INTO `reservationtransport` ( `id_resevation`, `date_reservation`, `heure_reservation` ,`prix` ,`id_user`,`id_transport`) VALUES ( '"+reservation.getDate_reservation()+"', '"+reservation.getHeure_reservation()+"', '"+reservation.getPrix()+"','"+reservation.getId_user()+"','"+reservation.getId_transport()+"');";
            ste.executeUpdate(req);
        }
        /*
        public void ajouterPST(Reservation reservation) throws SQLException {

            String req="INSERT INTO `reservationtransport` (`date_reservation`, `heure_reservation` ,`prix` ,`id_user`,`id_transport` ) VALUES ( ?,?,?,?,?);";
            PreparedStatement pre=con.prepareStatement(req);

            pre.setDate(1,reservation.getDate_reservation());
            pre.setString(2,reservation.getHeure_reservation());
            pre.setString(3,reservation.getPrix());
            pre.setInt(4,reservation.getId_user());
            pre.setInt(5,reservation.getId_transport());

            pre.executeUpdate();
        }*/
        public void ajouterPST(Reservation reservation) throws SQLException {
            // Vérifier si l'id_user existe
            String reqUser = "SELECT ID_Util FROM utilisateur WHERE ID_Util = ?";
            PreparedStatement preUser = con.prepareStatement(reqUser);
            preUser.setInt(1, reservation.getId_user());
            ResultSet resultSetUser = preUser.executeQuery();
            if (!resultSetUser.next()) {
                throw new SQLException("L'id_user n'existe pas dans la table user.");
            }

            // Vérifier si l'id_transport existe
            String reqTransport = "SELECT id_transport FROM moyentransport WHERE id_transport = ?";
            PreparedStatement preTransport = con.prepareStatement(reqTransport);
            preTransport.setInt(1, reservation.getId_transport());
            ResultSet resultSetTransport = preTransport.executeQuery();
            if (!resultSetTransport.next()) {
                throw new SQLException("L'id_transport n'existe pas dans la table transport.");
            }

            // Insertion de la réservation
            String reqInsert = "INSERT INTO reservationtransport (date_reservation, heure_reservation, prix, id_user, id_transport) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preInsert = con.prepareStatement(reqInsert);
            preInsert.setDate(1, reservation.getDate_reservation());
            preInsert.setString(2, reservation.getHeure_reservation());
            preInsert.setString(3, reservation.getPrix());
            preInsert.setInt(4, reservation.getId_user());
            preInsert.setInt(5, reservation.getId_transport());
            preInsert.executeUpdate();
        }

    @Override
    public void delete(Reservation reservation) throws SQLException {
       /* String req = "DELETE FROM `MoyenTransport` WHERE id_transport='" +  moyenTransport.getId_transport() + "';";
       PreparedStatement pre=con.prepareStatement(req);
        int rowsDeleted = pre.executeUpdate(req);
         return rowsDeleted > 0;*/
        String req = "DELETE from reservationtransport where id_reservation = " + reservation.getId_reservation() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("Reservation supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Reservation reservation) throws SQLException {
        // String req = "UPDATE MoyenTransport SET type_transport`='" + moyenTransport.getType_transport() + "', lieu`='" + moyenTransport.getLieu() + "',  destination`='" + moyenTransport.getDestination() + "', heure_depart`='" + moyenTransport.getHeure_depart()  + "',frequence`=`'"+moyenTransport.getFrequence() +"'WHERE id_transport= " + moyenTransport.getId_transport() + "';";
       /* int rowsUpdated = ste.executeUpdate(req);
        return rowsUpdated > 0;*/
        String req = "UPDATE reservationtransport set date_reservation = '" + reservation.getDate_reservation() + "', heure_reservation = '" + reservation.getHeure_reservation() + "', prix = '" + reservation.getPrix() + "', id_user = '" + reservation.getId_user() + "', id_transport = '" + reservation.getId_transport()  + "' where id_reservation = " + reservation.getId_reservation() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("Reservation modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Reservation findById(int id_reservation) throws SQLException {
        return null;
    }

    @Override
    public List<Reservation> readAll() throws SQLException {

        List<Reservation> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from reservationtransport");
        while (res.next()) {

            int id_reservation = res.getInt(1);
            Date date_reservation = res.getDate("date_reservation");
            String heure_reservation = res.getString("heure_reservation");
            String prix = res.getString("prix");
            int id_user= res.getInt(5);
            int id_transport = res.getInt(6);
            Reservation p1=new Reservation(id_reservation,date_reservation,heure_reservation,prix,id_user,id_transport);
            list.add(p1);
        }

        return list;
    }
}
