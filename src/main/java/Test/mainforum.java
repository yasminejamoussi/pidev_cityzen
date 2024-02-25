package Test;

import Entites.Forum;
import Service.ServiceForum;

import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

class Mainforum {

    public static void mainforum(String[] args) {

        ServiceForum fer = new ServiceForum() {

        };

        // Adding a reservation
        try {
            Forum f11 = new Forum(1,1, "zeuzoei", "fzeoiffi", 23);
            fer.ajouter(f11);
            System.out.println("forum ajoutée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la réservation : " + e.getMessage());
        }

        // Displaying reservations after addition
        try {
            List<Forum> reservations = fer.readAll();
            System.out.println("Affichage après l'ajout de la réservation :");
            reservations.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }
/*
        // Deleting a reservation
        try {
            Forum reservationToDelete = fer.findById(6);
            fer.delete(reservationToDelete);
            System.out.println("Reservation supprimée avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la réservation : " + e.getMessage());
        }

        // Displaying reservations after deletion
        try {
            List<Forum> reservations = fer.readAll();
            System.out.println("Affichage après la suppression de la réservation :");
            reservations.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }

        // Updating a reservation
        Forum reservationToUpdate = new Forum(5, 1, 1, Time.valueOf("00:00:00"), Time.valueOf("16:00:00"));
        reservationService.update(reservationToUpdate);

        // Displaying reservations after the update
        try {
            List<Reservation> reservations = reservationService.readAll();
            System.out.println("Affichage après la modification de la réservation :");
            reservations.forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e);
        }
        */
    }
}