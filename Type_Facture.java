package Entites;

import java.util.Date;

public class Type_Facture {

    private int Id_Type_Facture;
    private int Id_Facture;
    private String Type_Facture;

    public Type_Facture(int id_Type_Facture, int id_Facture, String type_Facture) {
        Id_Type_Facture = id_Type_Facture;
        Id_Facture = id_Facture;
        Type_Facture = type_Facture;
    }

    public Type_Facture(int id_Facture, String type_Facture) {
        Id_Facture = id_Facture;
        Type_Facture = type_Facture;
    }

    public int getId_Type_Facture() {
        return Id_Type_Facture;
    }

    public void setId_Type_Facture(int id_Type_Facture) {
        Id_Type_Facture = id_Type_Facture;
    }

    public int getId_Facture() {
        return Id_Facture;
    }

    public void setId_Facture(int id_Facture) {
        Id_Facture = id_Facture;
    }

    public String getType_Facture() {
        return Type_Facture;
    }

    public void setType_Facture(String type_Facture) {
        Type_Facture = type_Facture;
    }

    @Override
    public String toString() {
        return "Type_Facture{" +
                "Id_Type_Facture=" + Id_Type_Facture +
                ", Id_Facture=" + Id_Facture +
                ", Type_Facture='" + Type_Facture + '\'' +
                '}';
    }

}
