package iStore;

import Authentification.Conn;
import User.Utilisateur;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class iStore extends JFrame {
    private Utilisateur utilisateur;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private DefaultListModel<String> storeListModel;
    private JList<String> storeList;
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
            dispose();
            ManageArticles manageArticlesPage = new ManageArticles(utilisateur);
            manageArticlesPage.setVisible(true);
        });

        JButton addStoreButton = new JButton("Ajouter Magasin");
        addStoreButton.setBackground(new Color(0xe0aaff));
        addStoreButton.setForeground(Color.BLACK);
        addStoreButton.setBorder(BorderFactory.createEmptyBorder());
        addStoreButton.setBounds(20, 280, 160, 40);

        JButton removeStoreButton = new JButton("Supprimer Magasin");
        removeStoreButton.setBackground(new Color(0xe0aaff));
        removeStoreButton.setForeground(Color.BLACK);
        removeStoreButton.setBorder(BorderFactory.createEmptyBorder());
        removeStoreButton.setBounds(20, 340, 160, 40);

        if(utilisateur.getRole().equals("ADMIN")) {
            leftPanel.add(addStoreButton);
            leftPanel.add(removeStoreButton);
        }

        addStoreButton.addActionListener(e -> addStore());
        removeStoreButton.addActionListener(e -> removeStore());

        add(leftPanel);
    }

    private void addRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setBounds(200, 0, 1000, 700);
        rightPanel.setLayout(new BorderLayout());


        JPanel comptePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton compte = new JButton("Compte");
        compte.setBackground(new Color(0x52b788));
        compte.setForeground(Color.WHITE);
        compte.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        comptePanel.add(compte);
        rightPanel.add(comptePanel, BorderLayout.NORTH);

        JPanel storePanel = new JPanel(new BorderLayout());
        storePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        storeListModel = new DefaultListModel<>();
        storeList = new JList<>(storeListModel);
        storeList.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(storeList);
        storePanel.add(scrollPane, BorderLayout.CENTER);

        rightPanel.add(storePanel, BorderLayout.CENTER);

        loadStoresFromDatabase();

        compte.addActionListener(e -> {
            dispose();
            new Compte(utilisateur).setVisible(true);
        });

        add(rightPanel);
    }
    private void loadStoresFromDatabase() {
        storeListModel.clear();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/istore", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name FROM store")) {

            while(rs.next()) {
                storeListModel.addElement("ID: " + rs.getInt("id") + " | Nom: " + rs.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement: " + e.getMessage());
        }
    }

    private void addStore() {
        String storeName = JOptionPane.showInputDialog(this, "Nom du magasin:");
        if(storeName != null && !storeName.trim().isEmpty()) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/istore", "root", "")) {
                // Insertion dans store
                PreparedStatement stmtStore = conn.prepareStatement(
                        "INSERT INTO store (name) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                stmtStore.setString(1, storeName);
                stmtStore.executeUpdate();

                // Création de l'inventory
                ResultSet generatedKeys = stmtStore.getGeneratedKeys();
                if(generatedKeys.next()) {
                    int storeId = generatedKeys.getInt(1);

                    PreparedStatement stmtInventory = conn.prepareStatement(
                            "INSERT INTO inventory (store_id) VALUES (?)"
                    );
                    stmtInventory.setInt(1, storeId);
                    stmtInventory.executeUpdate();

                    storeListModel.addElement("ID: " + storeId + " | Nom: " + storeName);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erreur BD: " + e.getMessage());
            }
        }
    }

    private void removeStore() {
        String selectedStore = storeList.getSelectedValue();

        if (selectedStore == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "⚠️ Veuillez sélectionner un magasin à supprimer",
                    "Avertissement",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "❓ Voulez-vous vraiment supprimer ce magasin et TOUS ses stocks associés ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/istore", "root", "")) {
            conn.setAutoCommit(false);

            int storeId = Integer.parseInt(
                    selectedStore.split(" \\| ")[0].replace("ID: ", "").trim()
            );

            try (PreparedStatement deleteItems = conn.prepareStatement(
                    "DELETE items FROM items " +
                            "INNER JOIN inventory ON items.inventory_id = inventory.id " +
                            "WHERE inventory.store_id = ?")) {

                deleteItems.setInt(1, storeId);
                deleteItems.executeUpdate();
            }

            try (PreparedStatement deleteInventory = conn.prepareStatement(
                    "DELETE FROM inventory WHERE store_id = ?")) {

                deleteInventory.setInt(1, storeId);
                deleteInventory.executeUpdate();
            }

            try (PreparedStatement deleteStore = conn.prepareStatement(
                    "DELETE FROM store WHERE id = ?")) {

                deleteStore.setInt(1, storeId);
                int rowsAffected = deleteStore.executeUpdate();

                if (rowsAffected == 0) {
                    conn.rollback();
                    throw new SQLException("Aucun magasin trouvé avec cet ID");
                }
            }

            conn.commit();

            storeListModel.removeElement(selectedStore);
            JOptionPane.showMessageDialog(
                    this,
                    "✅ Magasin et stocks associés supprimés avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException | NumberFormatException e) {
            try {
                if (Conn.getConn() != null) Conn.getConn().rollback();
            } catch (SQLException ex) {
                e.addSuppressed(ex);
            }

            String errorMessage = "❌ Erreur lors de la suppression :\n";
            if (e instanceof NumberFormatException) {
                errorMessage += "Format d'ID invalide";
            } else {
                errorMessage += e.getMessage();
            }

            JOptionPane.showMessageDialog(
                    this,
                    errorMessage,
                    "Erreur Critique",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            try {
                if (Conn.getConn() != null) Conn.getConn().setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }


}
