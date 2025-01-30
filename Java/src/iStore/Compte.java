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

        JButton homeBouton = new JButton("Accueil");
        homeBouton.setBackground(new Color(0xe0aaff));
        homeBouton.setForeground(Color.BLACK);
        homeBouton.setBorder(BorderFactory.createEmptyBorder());
        homeBouton.setBounds(20, 100, 160, 40);
        leftPanel.add(homeBouton);

        homeBouton.addActionListener(e -> {
            dispose();
            iStore accueil = new iStore(utilisateur);
        });

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

        pseudoChanger.addActionListener(e -> {
            JTextField pseudoField = new JTextField();
            pseudoField.setBounds(25, 250, 360, 30);

            JButton send = new JButton("Envoyer");
            send.setBounds(25, 300, 100, 40);
            send.setBackground(new Color(0xc1121f));
            send.setForeground(Color.white);
            send.setBorder(BorderFactory.createEmptyBorder());

            rightPanel.add(pseudoField);
            rightPanel.add(send);

            send.addActionListener(ev -> {
                String newPseudo = pseudoField.getText();
                if (!newPseudo.isEmpty()) {
                    boolean success = pseudoChang(newPseudo, utilisateur.getID());
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Pseudo mis à jour avec succès");
                        utilisateur.setPseudo(newPseudo);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du pseudo", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            rightPanel.add(pseudoField);
            rightPanel.add(send);
            rightPanel.revalidate();
            rightPanel.repaint();
        });

        emailChanger.addActionListener(e -> {
            JTextField emailField = new JTextField();
            emailField.setBounds(25, 250, 360, 30);

            JButton sendE = new JButton("Envoyer");
            sendE.setBounds(25, 300, 100, 40);
            sendE.setBackground(new Color(0xc1121f));
            sendE.setForeground(Color.white);
            sendE.setBorder(BorderFactory.createEmptyBorder());

            sendE.addActionListener(ev -> {
                String newEmail = emailField.getText();
                if (!newEmail.isEmpty()) {
                    boolean success = emailChang(newEmail, utilisateur.getID());
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Email mis à jour avec succès");
                        utilisateur.setEmail(newEmail);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour de l'email", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            rightPanel.add(emailField);
            rightPanel.add(sendE);
            rightPanel.revalidate();
            rightPanel.repaint();

        });

        JButton supprimer = new JButton("Supprimer le compte");
        supprimer.setBackground(new Color(0xf26419));
        supprimer.setForeground(Color.white);
        supprimer.setBorder(BorderFactory.createEmptyBorder());
        supprimer.setBounds(375, 170, 160, 40);
        rightPanel.add(supprimer);

        supprimer.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer votre compte ?", "Confirmer", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = supprimerCompte(utilisateur.getID());
                if (success) {
                    JOptionPane.showMessageDialog(null, "Compte supprimé avec succès.");
                    System.exit(0); // Ferme la fenêtre
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du compte", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        admin(rightPanel);
        add(rightPanel);

    }

    public boolean pseudoChang(String newPseudo, int userID) {
        String query = "UPDATE utilisateur SET pseudo = ? WHERE id = ?";

        try (PreparedStatement stmt = Conn.getConn().prepareStatement(query)) {
            stmt.setString(1, newPseudo);
            stmt.setInt(2, userID);

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

    public boolean emailChang(String newEmail, int userID) {
        String query = "UPDATE utilisateur SET email = ? WHERE id = ?";

        try (PreparedStatement stmt = Conn.getConn().prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, userID);

            int rowsUpdate = stmt.executeUpdate();
            if (rowsUpdate > 0) {
                utilisateur.setEmail(newEmail);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean supprimerCompte(int userID) {
        String query = "DELETE FROM utilisateur WHERE id = ?";
        try (PreparedStatement stmt = Conn.getConn().prepareStatement(query)) {
            stmt.setInt(1, userID);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                return true; // Suppression réussie
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Erreur lors de la suppression
    }

    public void admin(JPanel panel) {
        if (utilisateur.getRole().equals("ADMIN")) {
            JLabel listeblanche = new JLabel("Ajouter un email dans la liste blanches");
            listeblanche.setFont(new Font("Arial", Font.PLAIN, 20));
            listeblanche.setForeground(Color.black);
            listeblanche.setBounds(25, 400, 600, 40);
            panel.add(listeblanche);

            JTextField whitelistField = new JTextField();
            whitelistField.setBounds(25, 455, 360, 30);

            JButton envoyerWhite = new JButton("Envoyer");
            envoyerWhite.setBounds(25, 500, 100, 40);
            envoyerWhite.setBackground(new Color(0xc1121f));
            envoyerWhite.setForeground(Color.white);
            envoyerWhite.setBorder(BorderFactory.createEmptyBorder());

            panel.add(whitelistField);
            panel.add(envoyerWhite);

            envoyerWhite.addActionListener(e -> {
                String newEmail = whitelistField.getText();
                if (!newEmail.isEmpty()) {
                    boolean success = Conn.addWhitelisted(newEmail);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Email ajotuer à la liste blanche");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour de la liste blanche", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }
}
