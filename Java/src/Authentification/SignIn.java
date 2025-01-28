package Authentification;

import javax.swing.*;
import java.awt.*;

public class SignIn extends JFrame {
    public SignIn(JFrame parent) {
        setTitle("Création de compte");
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Utilisation de ScreenUtils pour créer le panel
        JPanel mainPanel = Utilitaires.createFormPanel("Créer un compte");
        addFormFields(mainPanel);
        Utilitaires.addBackButton(parent, mainPanel); // Utilisation de ScreenUtils

        setLayout(new GridBagLayout()); // Centrer tout contenu ajouté
        add(mainPanel, new GridBagConstraints());
    }

    private void addFormFields(JPanel panel) {
        // Champs de formulaire
        String[] labels = {"Email:", "Pseudo:", "Mot de passe:"};
        JTextField[] fields = new JTextField[3];

        for(int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            fields[i] = (i == 2) ? new JPasswordField() : new JTextField();
            Utilitaires.styleFormComponent(label, fields[i]); // Utilisation de ScreenUtils
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);

            fields[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(fields[i]);
        }

        // Bouton de soumission
        panel.add(Box.createVerticalStrut(15));
        JButton submitButton = new JButton("Créer un compte");
        Utilitaires.styleButton(submitButton, new Color(0x4CAF50)); // Utilisation de ScreenUtils
        panel.add(submitButton);
    }
}