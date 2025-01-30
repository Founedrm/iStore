package iStore;

import Authentification.Conn;
import User.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;

public class Compte extends JFrame {
    private String newPseudo;
    private Utilisateur utilisateur;
    public Compte(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("iStore compte");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        addLeftPanel();
        addRightPanel();
        setVisible(true);
    }

    private void addLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 200, 700);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(null);

        JLabel logo = new JLabel("iStore");
        logo.setFont(new Font("Arial", Font.PLAIN, 28));
        logo.setBounds(20, 20, 160, 30);
        leftPanel.add(logo);

        JButton homeBouton = new JButton("Dashboard");
        homeBouton.setBackground(new Color(0xe0aaff));
        homeBouton.setForeground(Color.BLACK);
        homeBouton.setBorder(BorderFactory.createEmptyBorder());
        homeBouton.setBounds(20, 100, 160, 40);
        leftPanel.add(homeBouton);

        JButton userBouton = new JButton("Utilisateurs");
        userBouton.setBackground(new Color(0xe0aaff));
        userBouton.setForeground(Color.BLACK);
        userBouton.setBorder(BorderFactory.createEmptyBorder());
        userBouton.setBounds(20, 160, 160, 40);
        leftPanel.add(userBouton);

        add(leftPanel);
    }

    private void addRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(200, 0, 1000, 700);
        rightPanel.setLayout(null);

        JButton compte = new JButton("Compte");
        compte.setBounds(840, 15, 120, 40);
        compte.setBackground(new Color(0x52b788));
        compte.setForeground(Color.white);
        compte.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.add(compte);

        JLabel bienvenue = new JLabel("Bonjour à toi" + " " + utilisateur.getPseudo());
        bienvenue.setFont(new Font("Arial", Font.BOLD, 38));
        bienvenue.setForeground(Color.BLACK);
        bienvenue.setBounds(25, 100, 600, 40);
        rightPanel.add(bienvenue);

        JButton pseudoChanger = new JButton("Changer de pseudo");
        pseudoChanger.setBackground(new Color(0xa2d2ff));
        pseudoChanger.setForeground(Color.BLACK);
        pseudoChanger.setBorder(BorderFactory.createEmptyBorder());
        pseudoChanger.setBounds(25, 170, 160, 40);
        rightPanel.add(pseudoChanger);

        JButton emailChanger = new JButton("Changer d'email");
        emailChanger.setBackground(new Color(0xa2d2ff));
        emailChanger.setForeground(Color.BLACK);
        emailChanger.setBorder(BorderFactory.createEmptyBorder());
        emailChanger.setBounds(200, 170, 160, 40);
        rightPanel.add(emailChanger);

        JButton passwordChanger = new JButton("Changer de mot de passe");
        passwordChanger.setBackground(new Color(0xa2d2ff));
        passwordChanger.setForeground(Color.BLACK);
        passwordChanger.setBorder(BorderFactory.createEmptyBorder());
        passwordChanger.setBounds(375, 170, 160, 40);
        rightPanel.add(passwordChanger);

        pseudoChanger.addActionListener(e -> {
            pseudoChang(newPseudo, utilisateur.getEmail());
        });

        add(rightPanel);

    }

    public boolean pseudoChang(String newPseudo, String email) {
        String query = "UPDATE utilisateur SET pseudo = ? WHERE email = ?";

        try (PreparedStatement stmt = Conn.getConn().prepareStatement(query)) {
            stmt.setString(1, newPseudo);
            stmt.setString(2, email);

            int rowsUpdate = stmt.executeUpdate();
            if (rowsUpdate > 0) {
                utilisateur.setPseudo(newPseudo);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
