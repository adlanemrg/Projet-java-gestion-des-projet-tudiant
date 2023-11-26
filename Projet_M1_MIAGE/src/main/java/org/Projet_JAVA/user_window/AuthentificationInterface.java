package org.Projet_JAVA.user_window;

import org.Projet_JAVA.Connexion.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AuthentificationInterface extends Application {

    private Stage primaryStage;
    private Connection con;  // Déclaration de la connexion en tant que membre de la classe

    @Override
    public void start(Stage primaryStage) {
        
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Authentification");

        // Elements de l'interface
        Label labelUtilisateur = new Label("Nom d'utilisateur:");
        TextField champUtilisateur = new TextField();

        Label labelMotDePasse = new Label("Mot de passe:");
        PasswordField champMotDePasse = new PasswordField();

        Button boutonConnexion = new Button("Se connecter");

        // Gestionnaire d'événements pour le bouton de connexion
        boutonConnexion.setOnAction(e -> {
            String utilisateur = champUtilisateur.getText();
            String motDePasse = champMotDePasse.getText();

            // Ajoutez ici la logique d'authentification
            boolean authentificationReussie = authentifier(utilisateur, motDePasse);

            if (authentificationReussie) {
                System.out.println("Authentification réussie !");

                // Ouvrir la nouvelle fenêtre après l'authentification
                MenuPrincipal menuPrincipal = new MenuPrincipal(con, primaryStage);
                menuPrincipal.ouvrirMenuPrincipal();
                
            } else {
                System.out.println("Authentification échouée. Veuillez vérifier vos identifiants.");
            }
        });

        // Mise en page
        VBox layout = new VBox(10);
        layout.getChildren().addAll(labelUtilisateur, champUtilisateur, labelMotDePasse, champMotDePasse, boutonConnexion);

        // Affichage de la scène
        Scene scene = new Scene(layout, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour l'authentification
    private boolean authentifier(String utilisateur, String motDePasse) {
        try {
            con = DatabaseConnection.connect();  // Initialiser la connexion
            if (con != null) {
                String selectQuery = "SELECT * FROM enseignant WHERE username = ? AND pwd = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {
                    preparedStatement.setString(1, utilisateur);
                    preparedStatement.setString(2, motDePasse);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    // Si le resultSet a des lignes, alors l'authentification est réussie
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // // Méthode pour ouvrir nouvelle fenêtre 
    // private void ouvrirFenetrePrincipale() {
    //     FenetrePrincipale fenetrePrincipale = new FenetrePrincipale();
    //     Scene scene = new Scene(fenetrePrincipale.getContenu(con), 700, 700);
    //     primaryStage.setScene(scene);
    // }




    public static void main(String[] args) {
        // Afficher l'interface
        launch(AuthentificationInterface.class, args);
    }
}
