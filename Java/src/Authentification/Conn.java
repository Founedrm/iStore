package Authentification;

import javax.swing.*;
import java.sql.*;

public class Conn {
    private static Connection conn;
    public Conn() {
        String url = "jdbc:mysql://localhost:3306/istore";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect to database");
            System.exit(0);
        }
    }

    public static boolean Connect(String email, String password) {
        String query = "SELECT * FROM utilisateur WHERE email = ? AND password = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean singIn(String email, String pseudo, String password) {
        if (!whitelisted(email)) {
            JOptionPane.showMessageDialog(null, "Vous n'êtes pas sur liste blanche, Sorry !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (isEmailExist(email)) {
            JOptionPane.showMessageDialog(null, "Cet email est déjà inscrit, Sorry !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int rowCount = tableRow();
        if (rowCount < 0) {
            System.err.println("Erreur lors du comptage des utilisateurs");
            return false;
        }

        String query = (rowCount == 0) ? "INSERT INTO utilisateur (email, pseudo, password, role) VALUES (?, ?, ?, 'ADMIN')" : "INSERT INTO utilisateur (email, pseudo, password, role) VALUES (?, ?, ?, 'PEOPLE')";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, pseudo);
            stmt.setString(3, password);

            int row = stmt.executeUpdate();

            return row > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean whitelisted(String email) {
        String query = "SELECT * FROM whitelisted WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isEmailExist(String email) {
        String query = "SELECT * FROM utilisateur WHERE email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int tableRow(){
        String query = "SELECT COUNT(*) AS total FROM utilisateur";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
