package Authentification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import iStore.iStore;

public class Login extends JFrame {
    protected JTextField emailField;
    protected JPasswordField passwordField;

    public Login (JFrame parent) {
        setTitle("Connexion");
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Utilisation de Utilitaires pour créer le panel
        JPanel mainPanel = Utilitaires.createFormPanel("Connexion");
        addFormFields(mainPanel);
        Utilitaires.addBackButton(parent, mainPanel);

        setLayout(new GridBagLayout()); // Centrer tout contenu ajouté
        add(mainPanel, new GridBagConstraints());
    }

    private void addFormFields(JPanel panel) {
        String[] labels = {"Email:", "Mot de passe:"};
        emailField = new JTextField();
        passwordField = new JPasswordField();

        JTextField[] fields = {emailField, passwordField};

        for(int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            Utilitaires.styleFormComponent(label, fields[i]); // Utilisation de Utilitaires
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);

            fields[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(fields[i]);
        }

        panel.add(Box.createVerticalStrut(15));  // Ajoute un espace de 10 pixels en hauteur
        JButton loginButton = new JButton("Se connecter");
        Utilitaires.styleButton(loginButton, new Color(0x2196F3)); // Utilisation de Utilitaires

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Conn conn = new Conn();
                boolean isAuthenticated = conn.Connect(emailField.getText(), new String(passwordField.getPassword()));
                if (isAuthenticated) {
                    iStore iStore = new iStore();
                    dispose();
                    System.out.println("Utilisateur authentifié !");
                } else {
                    System.out.println("Échec de l'authentification.");
                }
            }
        });

        panel.add(loginButton);

    }
}
