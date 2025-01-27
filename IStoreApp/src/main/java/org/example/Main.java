package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("IStoreApp");

        // Créer le logo "IStore" avec une belle police et style
        Label logoLabel = new Label("IStore");
        logoLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-font-family: 'Arial';");

        // Créer les boutons pour se connecter ou créer un compte
        Button createAccountButton = new Button("Créer un compte");
        Button loginButton = new Button("Se connecter");

        // Personnalisation des boutons avec des couleurs douces et épurées
        createAccountButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px;");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 10px 20px;");

        // Mise en page du menu d'accueil
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f1f1f1; -fx-padding: 40 20 20 20;");

        root.getChildren().addAll(logoLabel, createAccountButton, loginButton);

        // Créer un StackPane pour gérer les transitions
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(root);

        // Naviguer vers le formulaire de création de compte
        createAccountButton.setOnAction(e -> showCreateAccountScreen(stackPane));

        // Naviguer vers le formulaire de connexion
        loginButton.setOnAction(e -> showLoginScreen(stackPane));

        Scene scene = new Scene(stackPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showCreateAccountScreen(StackPane stackPane) {
        CreateAccountScreen createAccountScreen = new CreateAccountScreen(stackPane);
        createAccountScreen.show();
    }

    private void showLoginScreen(StackPane stackPane) {
        LoginScreen loginScreen = new LoginScreen(stackPane);
        loginScreen.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class CreateAccountScreen {
    private StackPane stackPane;

    public CreateAccountScreen(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void show() {
        VBox createAccountLayout = new VBox(20);
        createAccountLayout.setAlignment(Pos.CENTER);
        createAccountLayout.setStyle("-fx-background-color: #f1f1f1; -fx-padding: 20;");

        // Créer le logo "IStore" avec une belle police et style
        Label logoLabel = new Label("IStore");
        logoLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-font-family: 'Arial';");

        // Label et champs de saisie
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label pseudoLabel = new Label("Pseudo:");
        TextField pseudoField = new TextField();
        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        // ComboBox pour choisir le rôle
        Label roleLabel = new Label("Choisissez votre rôle:");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Utilisateur", "Administrateur");
        roleComboBox.setValue("Utilisateur");  // Par défaut, "Utilisateur"

        // Fixer la largeur des champs de texte pour empêcher leur agrandissement
        emailField.setPrefWidth(250);
        pseudoField.setPrefWidth(250);
        passwordField.setPrefWidth(250);
        roleComboBox.setPrefWidth(250);

        // Fixer les propriétés des champs pour les rendre non redimensionnables
        emailField.setMaxWidth(250);
        pseudoField.setMaxWidth(250);
        passwordField.setMaxWidth(250);
        roleComboBox.setMaxWidth(250);

        // Bouton de soumission
        Button submitButton = new Button("Créer un compte");
        Label messageLabel = new Label();

        // Style du bouton
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px;");

        // Bouton retour
        Button backButton = new Button("Retour");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10px 20px;");

        // Gestion du bouton retour
        backButton.setOnAction(e -> showHomeScreen());

        // Ajouter les éléments au formulaire
        createAccountLayout.getChildren().addAll(logoLabel, emailLabel, emailField, pseudoLabel, pseudoField, passwordLabel, passwordField, roleLabel, roleComboBox, submitButton, messageLabel, backButton);

        // Gérer l'événement du clic sur le bouton
        submitButton.setOnAction(e -> {
            String email = emailField.getText();
            String pseudo = pseudoField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();

            // Validation de la saisie
            if (email.isEmpty() || pseudo.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Erreur : Tous les champs doivent être remplis.");
                return;
            }

            // Logique de création de compte
            messageLabel.setText("Compte créé avec succès ! Rôle : " + role);
        });

        // Remplacer le contenu de la scène actuelle
        stackPane.getChildren().clear();
        stackPane.getChildren().add(createAccountLayout);
    }

    private void showHomeScreen() {
        // Ne rien changer et juste revenir à l'écran principal (accueil)
        Main main = new Main();
        main.start((Stage) stackPane.getScene().getWindow());  // Retour à l'écran principal
    }
}

class LoginScreen {
    private StackPane stackPane;

    public LoginScreen(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    public void show() {
        VBox loginLayout = new VBox(20);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setStyle("-fx-background-color: #f1f1f1; -fx-padding: 20;");

        // Créer le logo "IStore" avec une belle police et style
        Label logoLabel = new Label("IStore");
        logoLabel.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #333; -fx-font-family: 'Arial';");

        // Label et champs de saisie
        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        // Fixer la largeur des champs de texte pour empêcher leur agrandissement
        emailField.setPrefWidth(250);
        passwordField.setPrefWidth(250);

        // Fixer les propriétés des champs pour les rendre non redimensionnables
        emailField.setMaxWidth(250);
        passwordField.setMaxWidth(250);

        // Bouton de connexion
        Button loginButton = new Button("Se connecter");
        Label messageLabel = new Label();

        // Style du bouton
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 10px 20px;");

        // Bouton retour
        Button backButton = new Button("Retour");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10px 20px;");

        // Gestion du bouton retour
        backButton.setOnAction(e -> showHomeScreen());

        // Ajouter les éléments au formulaire
        loginLayout.getChildren().addAll(logoLabel, emailLabel, emailField, passwordLabel, passwordField, loginButton, messageLabel, backButton);

        // Gérer l'événement du clic sur le bouton de connexion
        loginButton.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            // Vérification de la connexion (simulation ici)
            if (email.equals("user1@example.com") && password.equals("password123")) {
                messageLabel.setText("Connexion réussie !");
            } else {
                messageLabel.setText("Erreur : Email ou mot de passe incorrect.");
            }
        });

        // Remplacer le contenu de la scène actuelle
        stackPane.getChildren().clear();
        stackPane.getChildren().add(loginLayout);
    }

    private void showHomeScreen() {
        // Ne rien changer et juste revenir à l'écran principal (accueil)
        Main main = new Main();
        main.start((Stage) stackPane.getScene().getWindow());  // Retour à l'écran principal
    }
}
