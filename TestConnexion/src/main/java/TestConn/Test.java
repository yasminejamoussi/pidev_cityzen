package TestConn;
import java.sql.*;
public class Test {

    private  static String url = "jdbc:mysql://localhost:3306/pidev";
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

      String req="INSERT INTO `parking`  ( `nompark`, `adressepark`, `nbrplace`, `statuspark`) VALUES ( 'parking1', 'ariana', '20','ouvert');";

      try {
          ste.executeUpdate(req);
          System.out.println("élément insérer");
      }catch (SQLException e)
      {
          System.out.println(e);
      }

      try {
          ResultSet res= ste.executeQuery("select * from parking");

          while (res.next()){

              int idpark=res.getInt(1);
              String nompark=res.getString("nompark");
              String adressepark=res.getString(3);
              int nbrplace=res.getInt("nbrplace");
              String statuspark=res.getString(5);

              System.out.println("id :"+idpark+" nom :"+nompark+" adresse :"+adressepark+" nbr place :"+nbrplace+" status :"+statuspark);
          }
      }catch (SQLException e)
      {
          System.out.println(e);
      }



    }
}
