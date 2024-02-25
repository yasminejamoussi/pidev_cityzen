package Entites;

final public class Citoyen extends Utilisateur{
    private String nom;
    private String prenom;
    private int cin;
    private int age;
    private int num;
    private String sexe;

    public Citoyen() {

    }

    public Citoyen(int ID_Util, String mail_Util, String MDP_Util, String nom, String prenom, int cin, int age, int num, String sexe) {
        super(ID_Util, mail_Util, MDP_Util);
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.age = age;
        this.num = num;
        this.sexe = sexe;
    }

    public Citoyen(String mail_Util, String MDP_Util, String nom, String prenom, int cin, int age, int num, String sexe) {
        super(mail_Util, MDP_Util);
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.age = age;
        this.num = num;
        this.sexe = sexe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Citoyen{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", cin=" + cin +
                ", age=" + age +
                ", sexe='" + sexe + '\'' +
                '}';
    }
}
