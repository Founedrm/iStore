package Authentification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import iStore.iStore;

public class SignIn extends JFrame {
    protected JTextField email;
    protected JPasswordField password;
    protected JTextField pseudo;

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
        email = new JTextField();
        password = new JPasswordField();
        pseudo = new JTextField();

        JTextField[] fields = {email,pseudo,password};

        for(int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
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

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (email.getText().isEmpty() || pseudo.getText().isEmpty() || password.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(SignIn.this, "Tous les champs sont obligatoires !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return; // Bloque l'exécution
                }
                Conn conn = new Conn();
                boolean isSignIn = conn.singIn(email.getText(), pseudo.getText(), new String(password.getPassword()));
                if (isSignIn) {
                    iStore iStore = new iStore();
                    dispose();
                    System.out.println("Utilisateur inscript");
                } else {
                    System.out.println("Inscription non possible");
                }
            }
        });

        panel.add(submitButton);
    }
}