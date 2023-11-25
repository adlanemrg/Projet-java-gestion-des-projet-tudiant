package org.Projet_JAVA;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;

public class MenuPrincipal extends Application {

    private Connection connection;
    private Stage primaryStage;

    public MenuPrincipal(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Principal");

        // Créer des boutons pour chaque fonctionnalité
        Button projetButton = new Button("PROJET");
        Button etudiantsButton = new Button("ETUDIANTS");
        Button ueButton = new Button("UE");

        // Gestionnaire d'événements pour le bouton PROJET
        projetButton.setOnAction(event -> {
            FenetreProjet fenetreProjet = new FenetreProjet(connection);
            fenetreProjet.start(new Stage());
            // fenetreProjet.afficherFenetre();
        });

        // Gestionnaire d'événements pour le bouton ETUDIANTS
        etudiantsButton.setOnAction(event -> {
            // Ajoutez ici le code pour la fenêtre ETUDIANTS
            System.out.println("Afficher la fenêtre ETUDIANTS");
        });

        // Gestionnaire d'événements pour le bouton UE
        ueButton.setOnAction(event -> {
            // Ajoutez ici le code pour la fenêtre UE
            System.out.println("Afficher la fenêtre UE");
        });

        // Mise en page
        VBox layout = new VBox(10);
        layout.getChildren().addAll(projetButton, etudiantsButton, ueButton);

        // Affichage de la scène
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void ouvrirMenuPrincipal() {
        start(primaryStage);
    }
}
