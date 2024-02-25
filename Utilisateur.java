package Entites;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

sealed abstract public class Utilisateur permits Admin,Citoyen {
    private int ID_Util;

    private String Mail_Util;
    private String MDP_Util;

    public Utilisateur(int ID_Util, String mail_Util, String MDP_Util) {
        this.ID_Util = ID_Util;
        Mail_Util = mail_Util;
        this.MDP_Util = MDP_Util;
    }

    public Utilisateur(String mail_Util, String MDP_Util) {
        Mail_Util = mail_Util;
        this.MDP_Util = MDP_Util;
    }

    protected Utilisateur() {
    }

    public int getID_Util() {
        return ID_Util;
    }

    public void setID_Util(int ID_Util) {
        this.ID_Util = ID_Util;
    }

    public String getMail_Util() {
        return Mail_Util;
    }

    public void setMail_Util(String mail_Util) {
        Mail_Util = mail_Util;
    }

    public String getMDP_Util() {
        return MDP_Util;
    }

    public void setMDP_Util(String MDP_Util) {
        this.MDP_Util = MDP_Util;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilisateur that)) return false;
        return getID_Util() == that.getID_Util() && Objects.equals(getMail_Util(), that.getMail_Util()) && Objects.equals(getMDP_Util(), that.getMDP_Util());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID_Util(), getMail_Util(), getMDP_Util());
    }
}
