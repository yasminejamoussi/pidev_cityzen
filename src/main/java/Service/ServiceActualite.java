package Service;

import Entites.Actualite;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;


public class ServiceActualite implements IService<Actualite>{
    private Connection con= DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceActualite()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }


    }
   @Override
    public void ajouter(Actualite actualite) throws SQLException {

        String req="INSERT INTO `actualite` ( `titre`, `contenu`, `dateA`, `categorie`, `auteur`) VALUES ( '"+actualite.getTitre()+"', '"+actualite.getContenu()+"','"+actualite.getDateA()+"', '"+actualite.getCategorie()+"','"+actualite.getAuteur()+"');";
        ste.executeUpdate(req);
    }
    public  void ajouterCommentaire(Actualite actualite) throws SQLException {}


   @Override
   public void delete(Actualite actualite) {
       String req = "DELETE from actualite where id_actualite = " + actualite.getId() + ";";
       try {
           Statement st = con.createStatement();
           st.executeUpdate(req);
           System.out.println("supprmiée !");
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }
   }


    @Override
    public void update(Actualite a) {
        String req = "UPDATE actualite set titre = '" + a.getTitre() + "', contenu = '" + a.getContenu() + "', dateA = '" + a.getDateA() + "', categorie  = '" + a.getCategorie() +"', auteur  = '" + a.getAuteur() +  "' where id_actualite = " + a.getId() + ";";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(req);
            System.out.println("modifiée !");
        } catch (SQLException ev) {
            System.out.println(ev.getMessage());
        }
    }

    @Override
    public Actualite findById(int id) throws SQLException {
        String req = "SELECT * FROM actualite WHERE id_actualite = " + id + ";";
        ResultSet res = ste.executeQuery(req);
        if (res.next()) {
            int id_actualite = res.getInt(1);
            String titre = res.getString("titre");
            String contenu = res.getString("contenu");
            Date dateA = res.getDate("dateA");
            String categorie = res.getString("categorie");
            String auteur = res.getString("auteur");
            return new Actualite(id_actualite, titre, contenu, dateA, categorie, auteur);
        }
        return null;
    }
    @Override
    public List<Actualite> readAll() throws SQLException {

        List<Actualite> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from actualite");
        while (res.next()) {

            int id_actualite = res.getInt(1);
            String titre = res.getString("titre");
            String contenu = res.getString(3);
            Date dateA = res.getDate(4);
            String categorie = res.getString(5);
            String auteur = res.getString(6);
            Actualite a1 =new Actualite (id_actualite,titre,contenu,dateA,categorie,auteur);
            list.add(a1);
        }

        return list;
    }
    @Override
    public List<Actualite> readOnly() throws SQLException {
        List<Actualite> list3=new ArrayList<>();
        ResultSet res=ste.executeQuery("SELECT actualite.id_actualite, commentaire.contenuC \" +\n" +
                "                           \"FROM actualite \" +\n" +
                "                           \"INNER JOIN commentaire ON actualite.id_actualite = commentaire.id_actualite");
        while (res.next()) {

            int id_actualite = res.getInt(1);
            String titre = res.getString(2);
            String contenu = res.getString(3);
            Date dateA = res.getDate(4);
            String categorie = res.getString(5);
            String auteur = res.getString(6);
            Actualite p1=new Actualite(id_actualite,titre,contenu,dateA,categorie,auteur);
            list3.add(p1);
        }

        return list3;
    }

}
