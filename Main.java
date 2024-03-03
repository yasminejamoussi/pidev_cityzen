package Test;

import Entites.Chauffeur;
import Entites.MoyenTransport;
import Entites.Reservation;
import Service.ServiceChauffeur;
import Service.ServiceMoyenTransport;
import Service.ServiceReservation;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.List;
import java.sql.Date;
public class Main {

    public static void main(String[] args) {

        ServiceMoyenTransport ser = new ServiceMoyenTransport();

       MoyenTransport p1 = new MoyenTransport(1, "car", "tunis", "ariana", "21:15", 30);

       /*try {
            ser.ajouterPST(p1);
        } catch (SQLException e) {
            System.out.println(e);
        }*/

        MoyenTransport p2 = new MoyenTransport(5, "Metro", "centre ville", "Ariana", "18:30", 20);
        /*try {
            ser.ajouterPST(p2);
        } catch (SQLException e) {
            System.out.println(e);
        }*/

        List<MoyenTransport> l1 = null;
        try {
            l1 = ser.readAll();
        } catch (SQLException e) {
            System.out.println(e);
        }
        System.out.println("Affichage");
        l1.forEach(System.out::println);

       // ser.delete(new MoyenTransport(1,"metro" ,"tunis", "ariana", "18:30",30));
        try {
            ser.delete(p2);
        } catch (SQLException e) {
            System.out.println(e);
        }
        //ps.modifier(new Cours(2, "test", "1111111111","111111111111","1111111","test1",4,"hello"));
        /*try {
            ser.update(p2);
        } catch (SQLException e) {
            System.out.println(e);
        }*/
        p1 = new MoyenTransport(1,"testtt","ttes","ahah","13:00",40);

      try{
        // Appelez la méthode update.
        ser.update(p1);} catch(SQLException e){
        System.out.println(e);}

        ServiceChauffeur serr = new ServiceChauffeur();

        Chauffeur p3 = new Chauffeur(1, "30 min", "haaaaaaaaa", 9);
        Chauffeur p4= new Chauffeur(2, "30 min", "ajjajaa", 10);
        try {
            serr.ajouterPST(p4);
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            serr.delete(p3);
        } catch (SQLException e) {
            System.out.println(e);
        }
        //p4 = new Chauffeur(2, "10 min", "jjjjjjjjaaa", 10);
        try{
            // Appelez la méthode update.
            serr.update(p4);} catch(SQLException e){
            System.out.println(e);}



        ServiceReservation serrr = new ServiceReservation();


        Reservation p5 = new Reservation(Date.valueOf("2024-02-01"), "18:00", "1DT", 7,5);
        //Chauffeur p4= new Chauffeur(2, "30 min", "ajjajaa",   10);
        try {
            serrr.ajouterPST(p5);
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            serrr.delete(p5);
        } catch (SQLException e) {
            System.out.println(e);
        }
         Reservation p8 = new Reservation(Date.valueOf("2024-02-01"), "20:00", "3DT", 1,5);
        try{
            // Appelez la méthode update.
            serrr.update(p8);} catch(SQLException e){
            System.out.println(e);}


    }

}


