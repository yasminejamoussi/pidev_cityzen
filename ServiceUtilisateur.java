package Service;

import Entites.Admin;
import Entites.Citoyen;
import Entites.Utilisateur;

import java.sql.*;
import java.util.*;

import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.Nullable;

public  class ServiceUtilisateur implements IService<Utilisateur>{

    private Connection con=DataSource.getInstance().getCon();

    private Statement ste;

    public ServiceUtilisateur()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }
    }
    @Override
    public void ajouter(Utilisateur utilisateur) throws SQLException {
        if (utilisateur instanceof Admin) {
            Admin admin = (Admin) utilisateur;
            String query = "INSERT INTO utilisateur (Mail_Util, MDP_Util, role) VALUES(?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, admin.getMail_Util());
                stmt.setString(2, admin.getMDP_Util());
                stmt.setString(3, "admin");
                stmt.executeUpdate();
            }
        } else if (utilisateur instanceof Citoyen) {
            Citoyen citoyen = (Citoyen) utilisateur;
            String query = "INSERT INTO utilisateur (`CIN`,`Nom_util`, `Prenom_Util`,`Num_Util`, `Age_Util`,`Mail_Util`, `MDP_Util`, `Sexe_Util`,role) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, citoyen.getCin());
                stmt.setString(2, citoyen.getNom());
                stmt.setString(3, citoyen.getPrenom());
                stmt.setInt(4, citoyen.getNum());
                stmt.setInt(5, citoyen.getAge());
                stmt.setString(6, citoyen.getMail_Util());
                stmt.setString(7, citoyen.getMDP_Util());
                stmt.setString(8, citoyen.getSexe());
                stmt.setString(9, "citoyen");
                stmt.executeUpdate();
            }
        }
    }
    @Override
    public void delete(final Utilisateur utilisateur) throws SQLException {

        delete(utilisateur.getID_Util());
    }
    public void delete(int id) throws SQLException {
        final String query = "DELETE FROM utilisateur WHERE ID_Util = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        }
    @Override
    public void update(Utilisateur utilisateur) throws SQLException {
        if (utilisateur instanceof Admin) {
            Admin admin = (Admin) utilisateur;
            String query = " UPDATE  utilisateur SET  Mail_Util= ?, MDP_Util= ? where ID_Util =?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, admin.getMail_Util());
                stmt.setString(2, admin.getMDP_Util());
                stmt.setInt(3, utilisateur.getID_Util());
                stmt.executeUpdate();
                System.out.printf("admin %d updated\n", utilisateur.getID_Util());
            }
        } else if (utilisateur instanceof Citoyen) {
            Citoyen citoyen = (Citoyen) utilisateur;
            String query = "UPDATE  utilisateur SET CIN = ?,Nom_util= ?, Prenom_Util= ?, Num_Util= ?, Age_Util= ?, Mail_Util= ?, MDP_Util= ?,Sexe_Util= ? where ID_Util= ?";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setInt(1, citoyen.getCin());
                stmt.setString(2, citoyen.getNom());
                stmt.setString(3, citoyen.getPrenom());
                stmt.setInt(4, citoyen.getNum());
                stmt.setInt(5, citoyen.getAge());
                stmt.setString(6, citoyen.getMail_Util());
                stmt.setString(7, citoyen.getMDP_Util());
                stmt.setString(8, citoyen.getSexe());
                stmt.setInt(9, citoyen.getID_Util());
                stmt.executeUpdate();

            }
        }
    }

    @Override
    public Utilisateur findById(int id) throws SQLException {
        String req = "select * from utilisateur where ID_Util = ?";
        try (PreparedStatement stmt = con.prepareStatement(req)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.first()) {
                    final String password = rs.getString("MDP_Util");
                    final String email = rs.getString("Mail_Util");
                    final String role = rs.getString("role");

                    return switch (role) {
                        case "admin" -> new Admin(id, password, email);

                        case "citoyen" -> new Citoyen(id, password, email, rs.getString("Nom_util"),
                                rs.getString("Prenom_Util"), rs.getInt("CIN"), rs.getInt("Age_Util"),
                                rs.getInt("Num_Util"), rs.getString("Sexe_Util"));

                        default -> throw new IllegalArgumentException("Invalid user role: " + role);
                    };

                }

            }
            return null;
        }
    }
    public @Nullable Utilisateur find(String username, String password) throws SQLException {
        String req = "select * from utilisateur where Mail_Util = ? AND MDP_Util = ? ";
        try (PreparedStatement stmt = con.prepareStatement(req, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.first()) {
                    final int id = rs.getInt("ID_Util");
                    final String email = rs.getString("Mail_Util");
                    final String type = rs.getString("role");

                    return switch (type) {
                        case "admin" -> new Admin(id, password, email);
                        case "citoyen" -> new Citoyen(id, password, email, rs.getString("Nom_util"),
                                rs.getString("Prenom_Util"), rs.getInt("CIN"), rs.getInt("Age_Util"),
                                rs.getInt("Num_Util"), rs.getString("Sexe_Util"));
                        default -> throw new IllegalArgumentException("Invalid user type: " + type);
                    };
                }
            }
        }
        return null;
    }

    @Override
    public List<Utilisateur> readAll() throws SQLException {
        final String query = "SELECT * FROM utilisateur";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            final List<Utilisateur> users = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("ID_Util");
                final String email = rs.getString("Mail_Util");
                final String password = rs.getString("MDP_Util");
                final String role = rs.getString("role");

                final Utilisateur utilisateur = switch (role) {
                    case "admin" -> new Admin(id, email, password);
                    case "citoyen" -> new Citoyen(id, email, password, rs.getString("Nom_Util"),
                            rs.getString("Prenom_Util"), rs.getInt("CIN"),
                            rs.getInt("Age_Util"), rs.getInt("Num_Util"), rs.getString("Sexe_Util"));
                    default -> throw new IllegalArgumentException("Invalid user type: " + role);
                };


                users.add(utilisateur);
            }

            return users;
        }
    }
    public String encrypt(String password) {

        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public String decrypt(String password) {
        return new String(Base64.getMimeDecoder().decode(password));
    }

    public List<Utilisateur> rechercherParNom(String nom) throws SQLException {
        List<Utilisateur> resultats = new ArrayList<>();

        final String query = "SELECT * FROM utilisateur WHERE Mail_Util LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, "%" + nom + "%");  // Utilisation de LIKE pour rechercher partiellement

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID_Util");
                    final String email = rs.getString("Mail_Util");
                    final String password = rs.getString("MDP_Util");
                    final String role = rs.getString("role");

                    final Utilisateur utilisateur = switch (role) {
                        case "admin" -> new Admin(id, email, password);
                        case "citoyen" -> new Citoyen(id, email, password, rs.getString("Nom_Util"),
                                rs.getString("Prenom_Util"), rs.getInt("CIN"),
                                rs.getInt("Age_Util"), rs.getInt("Num_Util"), rs.getString("Sexe_Util"));
                        default -> throw new IllegalArgumentException("Invalid user type: " + role);
                    };

                    resultats.add(utilisateur);
                }
            }
        }

        return resultats;
    }

    public List<Utilisateur> diplayListsortedbymail() throws SQLException {
        List<Utilisateur> sortedUsers = this.readAll();
        Collections.sort(sortedUsers, Comparator.comparing(Utilisateur::getMail_Util));
        return sortedUsers;
    }

}