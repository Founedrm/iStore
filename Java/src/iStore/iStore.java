package iStore;

import User.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class iStore extends JFrame {
    private Utilisateur utilisateur;
    private JPanel rightPanel;
    private JPanel leftPanel;
    public iStore(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("iStore");
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
        leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 200, 700);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(null);

        JLabel logo = new JLabel("iStore");
        logo.setFont(new Font("Arial", Font.PLAIN, 28));
        logo.setBounds(20, 20, 160, 30);
        leftPanel.add(logo);

        JButton homeBouton = new JButton("Accueil");
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

        userBouton.addActionListener(e -> {
            dispose();
            new ManageUsers(utilisateur);
        });

        JButton stockBouton = new JButton("Gérer les stocks");
        stockBouton.setBackground(new Color(0xe0aaff));
        stockBouton.setForeground(Color.BLACK);
        stockBouton.setBorder(BorderFactory.createEmptyBorder());
        stockBouton.setBounds(20, 220, 160, 40);

        if (utilisateur.getRole().equals("ADMIN")) {
            leftPanel.add(stockBouton);
        }

        stockBouton.addActionListener(e -> {
            dispose();  // Fermer la fenêtre actuelle
            ManageArticles manageArticlesPage = new ManageArticles(utilisateur);  // Créer l'instance de ManageArticles
            manageArticlesPage.setVisible(true);  // Assurez-vous que la page s'affiche
        });


        add(leftPanel);
    }

    private void addRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setBounds(200, 0, 1000, 700);
        rightPanel.setLayout(null);

        JButton compte = new JButton("Compte");
        compte.setBounds(840, 15, 120, 40);
        compte.setBackground(new Color(0x52b788));
        compte.setForeground(Color.white);
        compte.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.add(compte);

        compte.addActionListener(e -> {
            dispose();

            // Créer une nouvelle instance de Compte et la rendre visible
            Compte comptePage = new Compte(utilisateur);
            comptePage.setVisible(true);
        });

        add(rightPanel);


    }


}
