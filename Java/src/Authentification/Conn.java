package Authentification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
