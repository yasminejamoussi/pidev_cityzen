package Service;
import Entites.Forum;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entites.Reclamation;
import Utils.DataSource;

public class ServiceForum implements IService<Forum> {

    private Connection con = DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceForum() {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void ajouter(Forum forum) throws SQLException {

        //String req = "INSERT INTO `forum` ( `message_forum`) VALUES (  '" + forum.getMessage_forum() + "');";
         String req = "INSERT INTO `forum` ( `id_util`, `sujet_forum`, `message_forum`,`id_reclamation`) VALUES ( '" + forum.getId_util() + "', '" + forum.getSujet_forum() + "', '" + forum.getMessage_forum() + "', '" + forum.getId_reclamation() + "');";
        ste.executeUpdate(req);
    }

    public void ajouterReponse(Forum forum) throws SQLException {

        String req = "INSERT INTO forum ( `message_forum`) VALUES ( '" + forum.getMessage_forum() + "');";
        ste.executeUpdate(req);
    }
    @Override
    public void delete(Forum forum) throws SQLException {
        String req = "DELETE from forum where id_forum = " + forum.getId() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("forum supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Forum forum) throws SQLException {
        String req = "UPDATE forum SET message_forum = ? WHERE id_forum = ?";
        try (PreparedStatement st = con.prepareStatement(req)) {
            st.setString(1, forum.getMessage_forum());
            st.setInt(2, forum.getId());

            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Forum modifié !");
            } else {
                System.out.println("Aucun reponse modifié.");
            }
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }

    @Override
    public Forum findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Forum> readAll() throws SQLException {
        List<Forum> list1=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from forum ");
        while (res.next()) {

            int id = res.getInt(1);
            int id_util = res.getInt(2);
            String sujet = res.getString(3);
            String message = res.getString(4);
            int id_reclamation = res.getInt(5);
            Forum p1=new Forum(id,id_util,sujet,message,id_reclamation);
            list1.add(p1);
        }

        return list1;
    }
    Reclamation getReclamationselect;
    public List<Forum> readOnly() throws SQLException {
        List<Forum> list2=new ArrayList<>();

        ResultSet res=ste.executeQuery("SELECT message_forum from forum WHERE id_reclamation ='" + getReclamationselect +"';");
        while (res.next()) {

            int id = res.getInt(1);
            int id_util = res.getInt(2);
            String sujet = res.getString(3);
            String message = res.getString(4);
            int id_reclamation = res.getInt(5);
            Forum p1=new Forum(id,id_util,sujet,message,id_reclamation);
            list2.add(p1);
        }

        return list2;
    }
    public List<Forum> readResponsesForSelectedReclamation(Reclamation selectedReclamation) throws SQLException {
        List<Forum> responseList = new ArrayList<>();

        // Assuming you have a database connection (conn) and a Statement (ste) in your ServiceForum class
        String query = "SELECT * FROM forum WHERE id_reclamation = " + selectedReclamation.getId();
        ResultSet resultSet = ste.executeQuery(query);

        while (resultSet.next()) {
            int id = resultSet.getInt("id_forum");
            int idUtil = resultSet.getInt("id_util");
            String sujetForum = resultSet.getString("sujet_forum");
            String messageForum = resultSet.getString("message_forum");
            int idReclamation = resultSet.getInt("id_reclamation");

            Forum response = new Forum(id, idUtil, sujetForum, messageForum, idReclamation);
            responseList.add(response);
        }

        return responseList;
    }

}