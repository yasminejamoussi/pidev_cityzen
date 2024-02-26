package Entites;

import java.sql.*;
import java.sql.Date;

public class Facture {
    private int Id_Facture;
    private int Id_Utilisateur;
    private Date Date_Facture;
    private float Montant_Facture;
    private String Etat_Facture;
    private String Description;

    public Facture(int Id_Facture, int Id_Utilisateur, Date Date_Facture, float Montant_Facture, String Etat_Facture, String Description) {
        this.Id_Facture = Id_Facture;
        this.Id_Utilisateur = Id_Utilisateur;
        this.Date_Facture = Date_Facture;
        this.Montant_Facture = Montant_Facture;
        this.Etat_Facture = Etat_Facture;
        this.Description = Description;
    }

    public Facture(int Id_Utilisateur, Date Date_Facture, float Montant_Facture, String Etat_Facture, String Description) {
        this.Id_Utilisateur = Id_Utilisateur;
        this.Date_Facture = Date_Facture;
        this.Montant_Facture = Montant_Facture;
        this.Etat_Facture = Etat_Facture;
        this.Description = Description;
    }

    public int getId_Facture() {
        return Id_Facture;
    }

    public void setId_Facture(int id_Facture) {
        Id_Facture = id_Facture;
    }

    public int getId_Utilisateur() {
        return Id_Utilisateur;
    }

    public void setId_Utilisateur(int id_Utilisateur) {
        Id_Utilisateur = id_Utilisateur;
    }

    public Date getDate_Facture() {
        return Date_Facture;
    }

    public void setDate_Facture(Date date_Facture) {
        Date_Facture = date_Facture;
    }

    public float getMontant_Facture() {
        return Montant_Facture;
    }

    public void setMontant_Facture(float montant_Facture) {
        Montant_Facture = montant_Facture;
    }

    public String getEtat_Facture() {
        return Etat_Facture;
    }

    public void setEtat_Facture(String etat_Facture) {
        Etat_Facture = etat_Facture;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "Id Facture=" + Id_Facture +
                ", Id Utilisateur='" + Id_Utilisateur + '\'' +
                ", Date Facture='" + Date_Facture + '\'' +
                ", Montant Facture='" + Montant_Facture + '\'' +
                ", Etat Facture='" + Etat_Facture + '\'' +
                ", Description=" + Description +
                '}';
    }
}



