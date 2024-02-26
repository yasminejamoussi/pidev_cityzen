package Entites;

//import java.util.Date;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Reservation {
    int id_reservation;
    Date date_reservation;
    String heure_reservation;
    String prix;
    int id_transport;
    int id_user;

    public Reservation(int id_reservation, Date date_reservation, String heure_reservation, String prix, int id_transport, int id_user) {
        this.id_reservation = id_reservation;
       /* try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.date_reservation = dateFormat.parse(dateRe);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //this.id_reservation = id_reservation;
        this.date_reservation = date_reservation;
        this.heure_reservation = heure_reservation;
        this.prix = prix;
        this.id_transport = id_transport;
        this.id_user = id_user;
    }
    public Reservation( Date date_reservation, String heure_reservation, String prix, int id_transport, int id_user) {


        this.date_reservation = date_reservation;
        this.heure_reservation = heure_reservation;
        this.prix = prix;
        this.id_transport = id_transport;
        this.id_user = id_user;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public String getHeure_reservation() {
        return heure_reservation;
    }

    public String getPrix() {
        return prix;
    }

    public int getId_transport() {
        return id_transport;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public void setHeure_reservation(String heure_reservation) {
        this.heure_reservation = heure_reservation;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setId_transport(int id_transport) {
        this.id_transport = id_transport;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", date_reservation=" + date_reservation +
                ", heure_reservation='" + heure_reservation + '\'' +
                ", prix='" + prix + '\'' +
                ", id_transport=" + id_transport +
                ", id_user=" + id_user +
                '}';
    }
}
