package Authentification;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {
    public HomeScreen() {
        setTitle("IStore");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel logoLabel = new JLabel("IStore");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 48));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createAccountButton = new JButton("Créer un compte");
        JButton loginButton = new JButton("Se connecter");

        styleButton(createAccountButton, new Color(0x4CAF50));
        styleButton(loginButton, new Color(0x2196F3));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(0xF1F1F1));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createAccountButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalGlue());

        createAccountButton.addActionListener(e -> {
            new SignIn(this).setVisible(true);
            setVisible(false);
        });

        loginButton.addActionListener(e -> {
            new Login(this).setVisible(true);
            setVisible(false);
        });

        add(mainPanel);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
