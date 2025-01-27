package org.example;

public class User {
    private String email;
    private String pseudo;
    private String password;  // Le mot de passe sera stocké sous forme hachée
    private String role;      // Le rôle de l'utilisateur (ex. "admin", "user")

    // Constructeur
    public User(String email, String pseudo, String password, String role) {
        this.email = email;
        this.pseudo = pseudo;
        this.password = password;
        this.role = role;
    }

    // Getters et setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
