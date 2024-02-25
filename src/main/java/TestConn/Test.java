package TestConn;
import java.sql.*;
public class Test {

    private  static String url = "jdbc:mysql://localhost:3306/pr";
    private static String user="root";
    private  static String pwd="";
    private static Connection con;


    private static Statement ste;

    public static void main(String[] args) {
      try {
          con = DriverManager.getConnection(url, user, pwd);
          System.out.println("connexion établie");
      }catch (SQLException e)
      {
          System.out.println(e);
      }
      try {
          ste=con.createStatement();
      }catch (SQLException e)
      {
          System.out.println(e);
      }

      String req="INSERT INTO `personne` ( `nom_reclamation`, `prenom_reclamation`, `email_reclamation`,`content_reclamation`) VALUES ( 'bouabid', 'farouk', 'bouabidfarouk@gmail.com','zhfuazgefuçzgfupazegfapuz');";

      try {
          ste.executeUpdate(req);
          System.out.println("élément insérer");
      }catch (SQLException e)
      {
          System.out.println(e);
      }

      try {
          ResultSet res=ste.executeQuery("select * from reclamation");

          while (res.next()){

              int id = res.getInt(1);
              String nom = res.getString(2);
              String prenom = res.getString(3);
              String email = res.getString(4);
              String content = res.getString(5);
              System.out.println("id :"+id+" nom :"+nom+" prenom :"+prenom+" email :"+email+" reclamation :"+content);
          }
      }catch (SQLException e)
      {
          System.out.println(e);
      }



    }
}
