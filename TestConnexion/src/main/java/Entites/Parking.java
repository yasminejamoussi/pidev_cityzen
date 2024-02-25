package Entites;

public class Parking {
    private int idpark;
    private String nompark;
    private String adressepark;
    private int nbrplace;
    private String statuspark;

    public Parking(int idpark, String nompark, String adressepark, int nbrplace, String statuspark) {
        this.idpark = idpark;
        this.nompark = nompark;
        this.adressepark = adressepark;
        this.nbrplace = nbrplace;
        this.statuspark = statuspark;
    }

    public Parking(String nompark, String adressepark, int nbrplace, String statuspark) {
        this.nompark = nompark;
        this.adressepark = adressepark;
        this.nbrplace = nbrplace;
        this.statuspark = statuspark;
    }

    public int getIdpark() {
        return idpark;
    }

    public void setIdpark(int idpark) {
        this.idpark = idpark;
    }

    public String getNompark() {
        return nompark;
    }

    public void setNompark(String nompark) {
        this.nompark = nompark;
    }

    public String getAdressepark() {
        return adressepark;
    }

    public void setAdressepark(String adressepark) {
        this.adressepark = adressepark;
    }

    public int getNbrplace() {
        return nbrplace;
    }

    public void setNbrplace(int nbrplace) {
        this.nbrplace = nbrplace;
    }

    public String getStatuspark() {
        return statuspark;
    }

    public void setStatuspark(String statuspark) {
        this.statuspark = statuspark;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "idpark=" + idpark +
                ", nompark='" + nompark + '\'' +
                ", adressepark='" + adressepark + '\'' +
                ", nbrplace=" + nbrplace +
                ", statuspark='" + statuspark + '\'' +
                '}';
    }


}
