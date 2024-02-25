package Entites;

import java.util.Objects;

public class Reclamation {
    private int id;
    private  String nom;
    private  String prenom;
    private  String email;
    private  String reclamation;

    public Reclamation(int id, String nom, String prenom, String email, String reclamation) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.reclamation = reclamation;
    }

    public Reclamation(String nom, String prenom, String email, String reclamation) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.reclamation = reclamation;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public  String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", reclamation='" + reclamation + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reclamation that = (Reclamation) obj;
        return Objects.equals(id, that.id); // Use a unique identifier for comparison
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Use a unique identifier for hashing
    }


}
