package Entites;
public class Abonnement {
     private int ID_Abo;
    private int ID_Util;
    private String Type_Abo;
    private String Periode_Abo;
    private String Statut_Abo;

    public Abonnement(int ID_Abo, int ID_Util, String type_Abo, String periode_Abo, String statut_Abo) {
        this.ID_Abo = ID_Abo;
        this.ID_Util = ID_Util;
        Type_Abo = type_Abo;
        Periode_Abo = periode_Abo;
        Statut_Abo = statut_Abo;
    }

    public Abonnement(int ID_Util, String type_Abo, String periode_Abo, String statut_Abo) {
        this.ID_Util = ID_Util;
        Type_Abo = type_Abo;
        Periode_Abo = periode_Abo;
        Statut_Abo = statut_Abo;
    }

    public int getID_Abo() {
        return ID_Abo;
    }

    public void setID_Abo(int ID_Abo) {
        this.ID_Abo = ID_Abo;
    }

    public int getID_Util() {
        return ID_Util;
    }

    public void setID_Util(int ID_Util) {
        this.ID_Util = ID_Util;
    }

    public String getType_Abo() {
        return Type_Abo;
    }

    public void setType_Abo(String type_Abo) {
        Type_Abo = type_Abo;
    }

    public String getPeriode_Abo() {
        return Periode_Abo;
    }

    public void setPeriode_Abo(String periode_Abo) {
        Periode_Abo = periode_Abo;
    }

    public String getStatut_Abo() {
        return Statut_Abo;
    }

    public void setStatut_Abo(String statut_Abo) {
        Statut_Abo = statut_Abo;
    }

    @Override
    public String toString() {
        return "Abonnement{" +
                "ID_Abo=" + ID_Abo +
                ", ID_Util=" + ID_Util +
                ", Type_Abo='" + Type_Abo + '\'' +
                ", Periode_Abo='" + Periode_Abo + '\'' +
                ", Statut_Abo='" + Statut_Abo + '\'' +
                '}';
    }
}