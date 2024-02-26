package Entites;

public class MoyenTransport {
    private int id_transport;
    private String type_transport;
    private String lieu;
    private String destination;
    private String heure_depart;
    private int frequence;


    public MoyenTransport(int id_transport, String type_transport, String lieu, String destination, String heure_depart, int frequence) {
        this.id_transport = id_transport;
        this.type_transport = type_transport;
        this.lieu = lieu;
        this.destination = destination;
        this.heure_depart = heure_depart;
        this.frequence = frequence;
    }
    public MoyenTransport( String type_transport, String lieu, String destination, String heure_depart, int frequence) {

        this.type_transport = type_transport;
        this.lieu = lieu;
        this.destination = destination;
        this.heure_depart = heure_depart;
        this.frequence = frequence;
    }

    public int getId_transport() {
        return id_transport;
    }

    public String getType_transport() {
        return type_transport;
    }

    public String getLieu() {
        return lieu;
    }

    public String getDestination() {
        return destination;
    }

    public String getHeure_depart() {
        return heure_depart;
    }

    public int getFrequence() {
        return frequence;
    }

    public void setId_transport(int id_transport) {
        this.id_transport = id_transport;
    }

    public void setType_transport(String type_transport) {
        this.type_transport = type_transport;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setHeure_depart(String heure_depart) {
        this.heure_depart = heure_depart;
    }

    public void setFrequence(int frequence) {
        this.frequence = frequence;
    }
}
