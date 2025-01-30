package iStore;

import User.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ManageArticles extends JFrame {
    Utilisateur utilisateur;
    private DefaultListModel<String> articleListModel;
    private JList<String> articleList;
    private JComboBox<String> shopComboBox;
    private JButton addArticleButton, removeArticleButton, updateStockButton, returnButton;
    private Connection conn;

    public ManageArticles(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("Gestion des articles");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // Initialisation des composants UI EN PREMIER
        shopComboBox = new JComboBox<>();
        articleListModel = new DefaultListModel<>();
        articleList = new JList<>(articleListModel);
        addArticleButton = new JButton("Ajouter un article");
        removeArticleButton = new JButton("Supprimer un article");
        updateStockButton = new JButton("Modifier le stock");
        returnButton = new JButton("Retour");

        // Configuration de l'UI
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Sélectionner un magasin :"));
        topPanel.add(shopComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addArticleButton);
        buttonPanel.add(removeArticleButton);
        buttonPanel.add(updateStockButton);
        buttonPanel.add(returnButton);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(articleList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Connexion BD et chargement initial
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/istore", "root", "");
            loadShops();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de connexion : " + e.getMessage());
        }

        // Gestion des événements
        shopComboBox.addActionListener(e -> loadArticles());

        addArticleButton.addActionListener(e -> addArticle());
        removeArticleButton.addActionListener(e -> removeArticle());
        updateStockButton.addActionListener(e -> updateStock());

        returnButton.addActionListener(e -> {
            dispose();
            new iStore(utilisateur).setVisible(true);
        });
    }

    private void loadShops() {
        shopComboBox.removeAllItems();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name FROM store")) {

            while (rs.next()) {
                shopComboBox.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement : " + e.getMessage());
        }
    }

    private void loadArticles() {
        articleListModel.clear();
        String shopName = (String) shopComboBox.getSelectedItem();
        if (shopName == null) return;

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT i.name, i.storage, i.price " +
                        "FROM items i " +
                        "JOIN inventory inv ON i.inventory_id = inv.id " +
                        "WHERE inv.store_id = (SELECT id FROM store WHERE name = ?)")) {

            stmt.setString(1, shopName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                articleListModel.addElement(String.format(
                        "Nom: %s | Stock: %d | Prix: %.2f€",
                        rs.getString("name"),
                        rs.getInt("storage"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement : " + e.getMessage());
        }
    }

    private void addArticle() {
        String shopName = (String) shopComboBox.getSelectedItem();
        if (shopName == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un magasin !");
            return;
        }

        try {
            // Récupérer l'ID du store
            int storeId;
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id FROM store WHERE name = ?")) {
                stmt.setString(1, shopName);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                storeId = rs.getInt("id");
            }

            // Saisie des données
            String articleName = JOptionPane.showInputDialog("Nom de l'article :");
            int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock :"));
            double price = Double.parseDouble(JOptionPane.showInputDialog("Prix :"));

            // Insertion
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO items (name, storage, price, inventory_id) " +
                            "VALUES (?, ?, ?, (SELECT id FROM inventory WHERE store_id = ?))")) {

                stmt.setString(1, articleName);
                stmt.setInt(2, stock);
                stmt.setDouble(3, price);
                stmt.setInt(4, storeId);
                stmt.executeUpdate();

                loadArticles();
                JOptionPane.showMessageDialog(this, "✅ Article ajouté !");
            }
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    private void removeArticle() {
        String selectedArticle = articleList.getSelectedValue();
        if (selectedArticle == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un article !");
            return;
        }

        String articleName = selectedArticle.split(" \\| ")[0].replace("Nom: ", "");
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM items WHERE name = ?")) {

            stmt.setString(1, articleName);
            stmt.executeUpdate();

            loadArticles();
            JOptionPane.showMessageDialog(this, "✅ Article supprimé !");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

    private void updateStock() {
        String selectedArticle = articleList.getSelectedValue();
        if (selectedArticle == null) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un article !");
            return;
        }

        String articleName = selectedArticle.split(" \\| ")[0].replace("Nom: ", "");
        int newStock = Integer.parseInt(JOptionPane.showInputDialog("Nouveau stock :"));

        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE items SET storage = ? WHERE name = ?")) {

            stmt.setInt(1, newStock);
            stmt.setString(2, articleName);
            stmt.executeUpdate();

            loadArticles();
            JOptionPane.showMessageDialog(this, "✅ Stock mis à jour !");
        } catch (SQLException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }

}