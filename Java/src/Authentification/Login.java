package Authentification;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
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
        JTextField[] fields = {new JTextField(), new JPasswordField()};

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
        Utilitaires.styleButton(loginButton, new Color(0x2196F3)); // Utilisation de ScreenUtils
        panel.add(loginButton);
    }
}
