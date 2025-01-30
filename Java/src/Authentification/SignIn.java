package Authentification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import User.Utilisateur;
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

        JPanel mainPanel = Utilitaires.createFormPanel("Créer un compte");
        addFormFields(mainPanel);
        Utilitaires.addBackButton(parent, mainPanel);

        setLayout(new GridBagLayout());
        add(mainPanel, new GridBagConstraints());
    }

    private void addFormFields(JPanel panel) {
        String[] labels = {"Email:", "Pseudo:", "Mot de passe:"};
        email = new JTextField();
        password = new JPasswordField();
        pseudo = new JTextField();

        JTextField[] fields = {email,pseudo,password};

        for(int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            Utilitaires.styleFormComponent(label, fields[i]);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(label);

            fields[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(fields[i]);
        }

        panel.add(Box.createVerticalStrut(15));
        JButton submitButton = new JButton("Créer un compte");
        Utilitaires.styleButton(submitButton, new Color(0x4CAF50));

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (email.getText().isEmpty() || pseudo.getText().isEmpty() || password.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(SignIn.this, "Tous les champs sont obligatoires !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Conn conn = new Conn();
                boolean isSignIn = conn.singIn(email.getText(), pseudo.getText(), new String(password.getPassword()));
                if (isSignIn) {
                    Utilisateur utilisateur = new Utilisateur(email.getText());
                    iStore iStore = new iStore(utilisateur);
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