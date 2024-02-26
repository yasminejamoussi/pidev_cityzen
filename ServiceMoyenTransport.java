package Service;

import Entites.MoyenTransport;
import Entites.Personne;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceMoyenTransport implements IService<MoyenTransport>{
    private Connection con= DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceMoyenTransport()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }

    }
    public void ajouter(MoyenTransport moyenTransport) throws SQLException {

        String req="INSERT INTO `MoyenTransport` ( `id_transport`, `type_transport`, `lieu` ,`destination` ,`heure_depart`,`frequence`) VALUES ( '"+moyenTransport.getType_transport()+"', '"+moyenTransport.getLieu()+"', '"+moyenTransport.getDestination()+"','"+moyenTransport.getHeure_depart()+"','"+moyenTransport.getFrequence()+"');";
        ste.executeUpdate(req);
    }
    public void ajouterPST(MoyenTransport moyenTransport) throws SQLException {

        String req="INSERT INTO `MoyenTransport` (`type_transport`, `lieu` ,`destination` ,`heure_depart`,`frequence` ) VALUES ( ?,?,?,?,?);";
        PreparedStatement pre=con.prepareStatement(req);

        pre.setString(1,moyenTransport.getType_transport());
        pre.setString(2,moyenTransport.getLieu());
        pre.setString(3,moyenTransport.getDestination());
        pre.setString(4,moyenTransport.getHeure_depart());
        pre.setInt(5,moyenTransport.getFrequence());

        pre.executeUpdate();
    }

    @Override
    public void delete(MoyenTransport moyenTransport) throws SQLException {
       /* String req = "DELETE FROM `MoyenTransport` WHERE id_transport='" +  moyenTransport.getId_transport() + "';";
       PreparedStatement pre=con.prepareStatement(req);
        int rowsDeleted = pre.executeUpdate(req);
         return rowsDeleted > 0;*/
        String req = "DELETE from MoyenTransport where id_transport = " + moyenTransport.getId_transport() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("Moyen supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(MoyenTransport moyenTransport) throws SQLException {
       // String req = "UPDATE MoyenTransport SET type_transport`='" + moyenTransport.getType_transport() + "', lieu`='" + moyenTransport.getLieu() + "',  destination`='" + moyenTransport.getDestination() + "', heure_depart`='" + moyenTransport.getHeure_depart()  + "',frequence`=`'"+moyenTransport.getFrequence() +"'WHERE id_transport= " + moyenTransport.getId_transport() + "';";
       /* int rowsUpdated = ste.executeUpdate(req);
        return rowsUpdated > 0;*/
        String req = "UPDATE MoyenTransport set type_transport = '" + moyenTransport.getType_transport() + "', lieu = '" + moyenTransport.getLieu() + "', destination = '" + moyenTransport.getDestination() + "', heure_depart = '" + moyenTransport.getHeure_depart() + "', frequence = '" + moyenTransport.getFrequence()  + "' where id_transport = " + moyenTransport.getId_transport() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("Moyen modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public MoyenTransport findById(int id_transport) throws SQLException {
        return null;
    }

    @Override
    public List<MoyenTransport> readAll() throws SQLException {

        List<MoyenTransport> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from moyentransport");
        while (res.next()) {

            int id_transport = res.getInt(1);
            String type_transport = res.getString("type_transport");
            String lieu = res.getString("lieu");
            String destination = res.getString("destination");
            String heure_depart= res.getString("heure_depart");
            int frequence = res.getInt(6);
            MoyenTransport p1=new MoyenTransport(id_transport,type_transport,lieu,destination,heure_depart,frequence);
            list.add(p1);
        }

        return list;
    }
}
