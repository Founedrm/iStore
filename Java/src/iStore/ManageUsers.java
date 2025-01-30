package iStore;

import User.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ManageUsers extends JFrame {
    Utilisateur utilisateur;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public ManageUsers(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;

        setTitle("Gestion des Utilisateurs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        add(mainPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(50, 50, 50));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JButton backButton = new JButton("← Retour");
        styleButton(backButton, new Color(30, 144, 255), Color.WHITE);
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Gestion des Utilisateurs", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel accountLabel = new JLabel("Bienvenue, " + utilisateur.getPseudo(), JLabel.RIGHT);
        accountLabel.setForeground(Color.WHITE);
        topPanel.add(accountLabel, BorderLayout.EAST);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(2, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(leftPanel, BorderLayout.WEST);

        JButton addUserButton = new JButton("Ajouter un Utilisateur");
        styleButton(addUserButton, new Color(34, 177, 76), Color.WHITE);
        JButton deleteUserButton = new JButton("Supprimer un Utilisateur");
        styleButton(deleteUserButton, new Color(200, 50, 50), Color.WHITE);

        if (utilisateur.getRole().equals("ADMIN")) {
            leftPanel.add(addUserButton);
            leftPanel.add(deleteUserButton);
        }

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        JLabel displayTitle = new JLabel("Liste des Utilisateurs", JLabel.CENTER);
        displayTitle.setFont(new Font("Arial", Font.BOLD, 18));
        rightPanel.add(displayTitle, BorderLayout.NORTH);

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JScrollPane scrollPane = new JScrollPane(userList);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        backButton.addActionListener(e -> {
            this.dispose();
            new iStore(utilisateur);
        });

        addUserButton.addActionListener(e -> addUser());
        deleteUserButton.addActionListener(e -> deleteUser());

        loadUsersFromDatabase();
        setVisible(true);
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void addUser() {
        String email = JOptionPane.showInputDialog("Email :");
        if (email == null || email.trim().isEmpty()) return;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/istore", "root", "")) {

            PreparedStatement checkWhitelist = conn.prepareStatement(
                    "SELECT id FROM whitelisted WHERE email = ?"
            );
            checkWhitelist.setString(1, email);
            ResultSet whitelistResult = checkWhitelist.executeQuery();

            if (!whitelistResult.next()) {
                JOptionPane.showMessageDialog(this,
                        "❌ Création impossible :\nCet email n'est pas autorisé (non whitelisté).",
                        "Erreur Whitelist",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreparedStatement checkExistingUser = conn.prepareStatement(
                    "SELECT id FROM utilisateur WHERE email = ?"
            );
            checkExistingUser.setString(1, email);
            ResultSet userResult = checkExistingUser.executeQuery();

            if (userResult.next()) {
                JOptionPane.showMessageDialog(this,
                        "❌ Création impossible :\nCet email est déjà utilisé par un autre compte.",
                        "Email Existant",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String pseudo = JOptionPane.showInputDialog("Pseudo :");
            String password = JOptionPane.showInputDialog("Mot de passe :");

            if (pseudo == null || password == null || pseudo.trim().isEmpty() || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Les champs ne peuvent pas être vides !");
                return;
            }

            PreparedStatement createUser = conn.prepareStatement(
                    "INSERT INTO utilisateur (email, pseudo, password, role) VALUES (?, ?, ?, 'PEOPLE')"
            );
            createUser.setString(1, email);
            createUser.setString(2, pseudo);
            createUser.setString(3, password);
            createUser.executeUpdate();

            // Rafraîchir la liste + feedback
            userListModel.addElement(pseudo + " (" + email + ")");
            JOptionPane.showMessageDialog(this,
                    "✅ Utilisateur créé avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            String errorMessage = "Erreur inattendue : ";
            if (e.getMessage().contains("Duplicate entry")) {
                errorMessage = "Le pseudo est déjà utilisé par un autre utilisateur !";
            }
            JOptionPane.showMessageDialog(this, errorMessage + e.getMessage());
        }
    }

    private void deleteUser() {
        String selectedUser = userList.getSelectedValue();
        if (selectedUser == null) return;

        String pseudo = selectedUser.split(" ")[0];
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/istore", "root", "");
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM utilisateur WHERE pseudo = ?")) {

            stmt.setString(1, pseudo);
            stmt.executeUpdate();

            userListModel.removeElement(selectedUser);
            JOptionPane.showMessageDialog(this, "Utilisateur supprimé !");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage());
        }
    }

    private void loadUsersFromDatabase() {
        userListModel.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/istore", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT pseudo, email FROM utilisateur")) {

            while (rs.next()) {
                userListModel.addElement(rs.getString("pseudo") + " (" + rs.getString("email") + ")");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement: " + e.getMessage());
        }
    }

}
