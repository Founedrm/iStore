package User;

import Authentification.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Utilisateur {
    private int ID;
    private String email;
    private String pseudo;
    private String role;

    public Utilisateur(String email) {
        this.ID = ID;
        this.email = email;
        this.pseudo = pseudo;
        this.role = role;
        setInfo();
    }

    private void setInfo(){
        String query = "SELECT * FROM utilisateur WHERE email = ?";
        try {
            Connection conn = Conn.getConn();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, this.email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                this.ID = rs.getInt("id");
                this.pseudo = rs.getString("pseudo");
                this.role = rs.getString("role");
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }
    public String getEmail() {
        return email;
    }
    public String getPseudo() {
        System.out.println(pseudo);
        return pseudo;
    }
    public String getRole() {
        return role;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
