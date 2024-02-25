package Test;

import Entites.Forum;
import Entites.Reclamation;
import Service.ServiceReclamation;
import Service.ServiceForum;
import Service.ServiceReclamation;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        ServiceReclamation ser = new ServiceReclamation() {


        };
        ServiceForum fer = new ServiceForum() {

        };
        ////////////////////////////AJOUTER RECLAMATION////////////////////////////////////////

        Reclamation p1 = new Reclamation(34,"bouabid", "farouk", "farouk@gamai.com", "fzhzifhzeuiofhuiohdfaz");
        Reclamation p2 = new Reclamation("aloui", "khalil", "khalil@gmail.com", "zeifuzeiàfyhzeiçà_fçhyzeàçf");
        Reclamation p3 = new Reclamation(23,"hi", "hello", "khalil@gmail.com", "zeifuzeiàfyhzeiçàfçhyzeàçf");
           /* try {
            ser.ajouterPST(p2);
        }catch (SQLException e)
        {
            System.out.println(e);
        }
*/
        ////////////////////////////AJOUTER FORUM////////////////////////////////////////
        Forum p6 = new Forum(1,1,"balas", "balas",22);
        try {
            Forum f11 = new Forum(1,1, "zeuzoei", "fzeoiffi", 23);
            fer.ajouter(p6);
            System.out.println("forum ajoutée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réservation : " + e.getMessage());
        }


        ////////////////////////////AFFICHAGE RECLAMATION////////////////////////////////////////
        List<Reclamation> l1 = null;
            try {
                l1 = ser.readAll();
            } catch (SQLException e) {
                System.out.println(e);
            }

            System.out.println("Affichage");
            l1.forEach(System.out::println);


        ////////////////////////////AFFICHAGE FORUM////////////////////////////////////////
        List<Forum> l2 = null;
        try {
            l2 = fer.readAll();
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println("Affichage forum");
        l2.forEach(System.out::println);

        List<Forum> l3 = null;
        try {
            l3 = fer.readOnly();
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println("Affichage forum");
        l3.forEach(System.out::println);


        /////////////////////////DELETE FORUM//////////////////////////////////////
        //fer.delete(p6);



        /////////////////////////DELETE RECLAMATION//////////////////////////////////////
        //ser.delete(p1);




        ////////////////////////UPDATE//////////////////////////////////////
        p3 = new Reclamation(23,"bouabid", "fafou", "farouk@gamai.com", "ugugiugiugigui");
        ser.update(p3);
        ////////////////////////////////////////////////////////////////////
        }
    }
