package Entites;

public class Forum {
    private int id;
    private int id_util;
    private String sujet_forum;
    private String message_forum;
    private int id_reclamation;

    public Forum(String text) {
    }

    public Forum(int id, String message_forum) {
        this.message_forum = message_forum;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_util() {
        return id_util;
    }

    public void setId_util(int id_util) {
        this.id_util = id_util;
    }

    public String getSujet_forum() {
        return sujet_forum;
    }

    public void setSujet_forum(String sujet_forum) {
        this.sujet_forum = sujet_forum;
    }

    public String getMessage_forum() {
        return message_forum;
    }

    public void setMessage_forum(String message_forum) {
        this.message_forum = message_forum;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    @Override
    public String toString() {
        return "Forum{" +
                "id=" + id +
                ", id_util=" + id_util +
                ", sujet_forum='" + sujet_forum + '\'' +
                ", message_forum='" + message_forum + '\'' +
                ", id_reclamation='" + id_reclamation + '\'' +
                '}';
    }

    public Forum(int id_util, String sujet_forum, String message_forum, int id_reclamation) {
        this.id_util = id_util;
        this.sujet_forum = sujet_forum;
        this.message_forum = message_forum;
        this.id_reclamation = id_reclamation;
    }

    public Forum(int id, int id_util, String sujet_forum, String message_forum, int id_reclamation) {
        this.id = id;
        this.id_util = id_util;
        this.sujet_forum = sujet_forum;
        this.message_forum = message_forum;
        this.id_reclamation = id_reclamation;
    }
}
