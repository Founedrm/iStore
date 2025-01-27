package Authentification;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login() {
        // info de base
        this.setTitle("Connexion");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(350, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // panel
        JPanel panel = new JPanel();
        this.setContentPane(panel);
        panel.setLayout(null);

        // Swing
        JLabel label = new JLabel("Connexion");
        label.setBounds(120, 50, 100, 30);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label);

        this.setVisible(true);




    }
}
