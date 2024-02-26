package Service;

import Entites.Chauffeur;
import Entites.MoyenTransport;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceChauffeur  implements IService<Chauffeur>{
    private Connection con= DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceChauffeur()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }

    }
    public void ajouter(Chauffeur chauffeur) throws SQLException {

        String req="INSERT INTO `Chauffeur` ( `id_chauffeur`, `retard`,`description` ,`id_transport`) VALUES ( '"+chauffeur.getId_chauffeur()+"', '"+chauffeur.getRetard()+"', '"+chauffeur.getDescription()+"','"+chauffeur.getId_transport()+"');";
        ste.executeUpdate(req);
    }
    public void ajouterPST(Chauffeur chauffeur) throws SQLException {

        String req="INSERT INTO `Chauffeur` (`id_chauffeur`, `retard` ,`description` ,`id_transport` ) VALUES ( ?,?,?,?);";
        PreparedStatement pre=con.prepareStatement(req);

        pre.setInt(1,chauffeur.getId_chauffeur());
        pre.setString(2,chauffeur.getRetard());
        pre.setString(3,chauffeur.getDescription());
        pre.setInt(4,chauffeur.getId_transport());

        pre.executeUpdate();
    }
    @Override
    public void delete(Chauffeur chauffeur) throws SQLException {
       /* String req = "DELETE FROM `MoyenTransport` WHERE id_transport='" +  moyenTransport.getId_transport() + "';";
       PreparedStatement pre=con.prepareStatement(req);
        int rowsDeleted = pre.executeUpdate(req);
         return rowsDeleted > 0;*/
        String req = "DELETE from Chauffeur where id_chauffeur = " + chauffeur.getId_chauffeur() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("Chauffeur supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void update(Chauffeur chauffeur) throws SQLException {
        // String req = "UPDATE MoyenTransport SET type_transport`='" + moyenTransport.getType_transport() + "', lieu`='" + moyenTransport.getLieu() + "',  destination`='" + moyenTransport.getDestination() + "', heure_depart`='" + moyenTransport.getHeure_depart()  + "',frequence`=`'"+moyenTransport.getFrequence() +"'WHERE id_transport= " + moyenTransport.getId_transport() + "';";
       /* int rowsUpdated = ste.executeUpdate(req);
        return rowsUpdated > 0;*/
        String req = "UPDATE Chauffeur set retard = '" + chauffeur.getRetard() + "', description = '" + chauffeur.getDescription() + "', id_transport = '" + chauffeur.getId_transport()   + "' where id_chauffeur = " + chauffeur.getId_chauffeur() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("Chauffeur modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public List<Chauffeur> readAll() throws SQLException {

        List<Chauffeur> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from chauffeur");
        while (res.next()) {

            int id_chauffeur = res.getInt(1);
            String retard = res.getString("retard");
            String description = res.getString("description");

            int id_transport = res.getInt(4);
            Chauffeur p1=new Chauffeur(id_chauffeur,retard,description,id_transport);
            list.add(p1);
        }

        return list;
    }
    @Override
    public Chauffeur findById(int id_chauffeur) throws SQLException {
        return null;
    }

}
