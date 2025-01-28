package Authentification;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {
    public HomeScreen() {
        setTitle("IStore"); // Titre de la fenêtre
        setSize(800, 600); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermer l'application quand on ferme la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        setResizable(false);

        // Création du logo
        JLabel logoLabel = new JLabel("IStore");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 48)); // Style du texte
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le texte

        // Boutons
        JButton createAccountButton = new JButton("Créer un compte");
        JButton loginButton = new JButton("Se connecter");

        // Style des boutons
        styleButton(createAccountButton, new Color(0x4CAF50)); // Vert
        styleButton(loginButton, new Color(0x2196F3)); // Bleu

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Disposition verticale
        mainPanel.setBackground(new Color(0xF1F1F1)); // Couleur de fond
        mainPanel.add(Box.createVerticalGlue()); // Espace flexible en haut
        mainPanel.add(logoLabel); // Ajouter le logo
        mainPanel.add(Box.createVerticalStrut(15)); // Espace entre les éléments
        mainPanel.add(createAccountButton); // Ajouter le bouton "Créer un compte"
        mainPanel.add(Box.createVerticalStrut(10)); // Espace entre les éléments
        mainPanel.add(loginButton); // Ajouter le bouton "Se connecter"
        mainPanel.add(Box.createVerticalGlue()); // Espace flexible en bas

        // Gestion des clics
        createAccountButton.addActionListener(e -> {
            new SignIn(this).setVisible(true); // Ouvrir l'écran d'inscription
            setVisible(false); // Masquer l'écran d'accueil
        });

        loginButton.addActionListener(e -> {
            new Login(this).setVisible(true); // Ouvrir l'écran de connexion
            setVisible(false); // Masquer l'écran d'accueil
        });

        add(mainPanel); // Ajouter le panneau principal à la fenêtre
    }

    // Méthode pour styliser les boutons
    private void styleButton(JButton button, Color color) {
        button.setBackground(color); // Couleur de fond
        button.setForeground(Color.WHITE); // Couleur du texte
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Police du texte
        button.setPreferredSize(new Dimension(200, 40)); // Taille du bouton
        button.setMaximumSize(new Dimension(200, 40)); // Taille maximale
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le bouton
    }
}
