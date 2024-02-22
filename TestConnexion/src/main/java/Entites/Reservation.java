package Entites;

import java.sql.Time;

public class Reservation {
    private int idres;
    private int idutilisateur;
    private Parking parking;

    public Reservation(int idutilisateur, Parking parking, Integer heuredebut) {
        this.idutilisateur = idutilisateur;
        this.parking = parking;
        this.heuredebut = heuredebut;
    }

    private Integer heuredebut;
    private Integer heurefin;

    public Reservation(int idres, int idutilisateur, Parking parking, Integer heuredebut, Integer heurefin) {
        this.idres = idres;
        this.idutilisateur = idutilisateur;
        this.parking = parking;
        this.heuredebut = heuredebut;
        this.heurefin = heurefin;
    }

    public Reservation(int idutilisateur, Parking parking, Integer heuredebut, Integer heurefin) {
        this.idutilisateur = idutilisateur;
        this.parking = parking;
        this.heuredebut = heuredebut;
        this.heurefin = heurefin;
    }

    public int getIdres() {
        return idres;
    }

    public void setIdres(int idres) {
        this.idres = idres;
    }

    public int getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(int idutilisateur) {
        this.idutilisateur = idutilisateur;
    }



    public Integer getHeuredebut() {
        return heuredebut;
    }

    public void setHeuredebut(Integer heuredebut) {
        this.heuredebut = heuredebut;
    }

    public Integer getHeurefin() {
        return heurefin;
    }

    public void setHeurefin(Integer heurefin) {
        this.heurefin = heurefin;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idres=" + idres +
                ", idutilisateur=" + idutilisateur +
                ", idparking=" + parking.getIdpark() +
                ", heuredebut=" + heuredebut +
                ", heurefin=" + heurefin +
                '}';
    }
}

