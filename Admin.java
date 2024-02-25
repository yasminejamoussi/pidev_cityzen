package Entites;

final public class Admin extends Utilisateur{
    public Admin(int ID_Util, String mail_Util, String MDP_Util) {
        super(ID_Util, mail_Util, MDP_Util);
    }

    public Admin(String mail_Util, String MDP_Util) {
        super(mail_Util, MDP_Util);
    }

    public Admin() {

    }

    @Override
    public String toString() {
        return "Admin{" +
                "ID_Util=" + getID_Util() +
                ", mail_Util='" + getMail_Util() + '\'' +
                ", MDP_Util='" + getMDP_Util() + '\'' +
                '}';
    }
}
