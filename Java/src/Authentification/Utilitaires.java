package Authentification;

import javax.swing.*;
import java.awt.*;

public class Utilitaires {
    public static JPanel createFormPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0xDEDBD2));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(50));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(30));

        return panel;
    }

    public static void styleFormComponent(JLabel label, JComponent component) {
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        component.setPreferredSize(new Dimension(250, 30));
        component.setMaximumSize(new Dimension(250, 30));
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public static void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 35));
        button.setMaximumSize(new Dimension(200, 35));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public static void addBackButton(JFrame parent, JPanel panel) {
        JButton backButton = new JButton("Retour");
        styleButton(backButton, new Color(0xF44336));
        backButton.addActionListener(e -> {
            parent.setVisible(true);
            ((JFrame) SwingUtilities.getWindowAncestor(panel)).dispose();
        });
        panel.add(Box.createVerticalStrut(20));
        panel.add(backButton);
    }
}