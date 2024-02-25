package Service;

import Entites.Forum;
import Entites.Reclamation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.DataSource;
public class ServiceReclamation implements IService<Reclamation>{

    private Connection con=DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceReclamation()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }


    }
    @Override
    public  void ajouter(Reclamation reclamation) throws SQLException {

        String req="INSERT INTO `reclamation` ( `nom_reclamation`, `prenom_reclamation`, `email_reclamation`,`content_reclamation`) VALUES ( '"+reclamation.getNom()+"', '"+reclamation.getPrenom()+"', '"+reclamation.getEmail()+"', '"+reclamation.getReclamation()+"');";
        ste.executeUpdate(req);
    }
    public  void ajouterReponse(Reclamation reclamation) throws SQLException {}
    public void ajouterPST(Reclamation reclamation) throws SQLException {

        String req="INSERT INTO `reclamation` ( `nom_reclamation`, `prenom_reclamation`, `email_reclamation`,`content_reclamation`) VALUES ( ?,?,?,?);";
        PreparedStatement pre=con.prepareStatement(req);

        pre.setString(1,reclamation.getNom());
        pre.setString(2,reclamation.getPrenom());
        pre.setString(3,reclamation.getEmail());
        pre.setString(4,reclamation.getReclamation());

        pre.executeUpdate();
    }



    @Override
    public void update(Reclamation reclamation) {
        String req = "UPDATE reclamation set nom_reclamation = '" + reclamation.getNom() + "', prenom_reclamation = '" + reclamation.getPrenom() + "', email_reclamation = '" + reclamation.getEmail() + "', content_reclamation = '" + reclamation.getReclamation() +"' WHERE id_reclamation='" + reclamation.getId() + "';";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("evvvvv modifiée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }

    @Override
    public void delete(Reclamation reclamation) {
        String req = "DELETE from reclamation where id_reclamation = " + reclamation.getId() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("evvvvvv supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Reclamation findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Reclamation> readAll() throws SQLException {

        List<Reclamation> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from reclamation");
        while (res.next()) {

            int id = res.getInt(1);
            String nom = res.getString(2);
            String prenom = res.getString(3);
            String email = res.getString(4);
            String content = res.getString(5);
            Reclamation p1=new Reclamation(id,nom,prenom,email,content);
            list.add(p1);
        }

        return list;
    }

    @Override
    public List<Reclamation> readOnly() throws SQLException {
        List<Reclamation> list3=new ArrayList<>();
        ResultSet res=ste.executeQuery("SELECT reclamation.id_reclamation, forum.message_forum \" +\n" +
                "                           \"FROM reclamation \" +\n" +
                "                           \"INNER JOIN forum ON reclamation.id_reclamation = forum.id_reclamation");
        while (res.next()) {

            int id = res.getInt(1);
            String nom = res.getString(2);
            String prenom = res.getString(3);
            String email = res.getString(4);
            String reclamation = res.getString(5);
            Reclamation p1=new Reclamation(id,nom,prenom,email,reclamation);
            list3.add(p1);
        }

        return list3;
    }
}
