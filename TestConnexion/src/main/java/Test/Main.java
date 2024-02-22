package Test;

import Entites.Reservation;
import Service.ServiceReservation;
import Entites.Parking;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ServiceReservation reservationService = new ServiceReservation();

        // Adding a reservation
       /* try {
            Parking parking = new Parking(1, "Nom du parking", "Adresse du parking", 50, "Ouvert");
            Reservation reservationTest = new Reservation( 1, parking, 02, 12);
            reservationService.ajouter(reservationTest);
            System.out.println("Reservation ajoutée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réservation : " + e.getMessage());
        }

        // Displaying reservations after addition
        try {
            List<Reservation> reservations = reservationService.readAll();
            System.out.println("Affichage après l'ajout de la réservation :");
            reservations.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }*/

        // Deleting a reservation
      /*  try {
            Reservation reservationToDelete = reservationService.findById(6);
            reservationService.delete(reservationToDelete);
            System.out.println("Reservation supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la réservation : " + e.getMessage());
        }

        // Displaying reservations after deletion
        try {
            List<Reservation> reservations = reservationService.readAll();
            System.out.println("Affichage après la suppression de la réservation :");
            reservations.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }

       */
        Parking parkingmodif = new Parking(1, "Nom du parking", "Adresse du parking", 50, "Ouvert");
        Reservation reservationToUpdate = new Reservation(22, 1, parkingmodif, 11, 6);
        reservationService.update(reservationToUpdate);

        // Displaying reservations after the update
        try {
            List<Reservation> reservations = reservationService.readAll();
            System.out.println("Affichage après la modification de la réservation :");
            reservations.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

/*package Test;

import Entites.Parking;
import Service.ServiceParking;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ServiceParking parkingService = new ServiceParking();

        // Adding a parking
       try {
            Parking parkingTest = new Parking("Test Parking", "Test Address", 50, "Open");
            parkingService.ajouter(parkingTest);
            System.out.println("Parking ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du parking : " + e.getMessage());
        }

        // Displaying parkings after addition
        try {
            List<Parking> parkings = parkingService.readAll();
            System.out.println("Affichage après l'ajout du parking :");
            parkings.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Deleting a parking
        try {
            Parking parkingToDelete = parkingService.findById(28); // Assuming there is a parking with id = 1
            parkingService.delete(parkingToDelete);
            System.out.println("Parking supprimé avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du parking : " + e.getMessage());
        }

        // Displaying parkings after deletion
        try {
            List<Parking> parkings = parkingService.readAll();
            System.out.println("Affichage après la suppression du parking :");
            parkings.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Updating a parking
        Parking parkingToUpdate = new Parking(2, "Updated Parking", "Updated Address", 75, "Closed");
        parkingService.update(parkingToUpdate);

        // Displaying parkings after the update
        try {
            List<Parking> parkings = parkingService.readAll();
            System.out.println("Affichage après la modification du parking :");
            parkings.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}*/

