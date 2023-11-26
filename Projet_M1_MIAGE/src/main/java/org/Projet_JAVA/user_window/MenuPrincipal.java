package org.Projet_JAVA.user_window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;

import org.Projet_JAVA.InterfaceController;
import org.Projet_JAVA.base.Projet;

public class MenuPrincipal extends Application {

    private Connection connection;
    private Stage primaryStage;
    private InterfaceController interfaceController ; 
    private TableView<Projet> projetTableView;

    

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
        Button formationButton = new Button("FORMATION");

        // Gestionnaire d'événements pour le bouton PROJET
        projetButton.setOnAction(event -> {
            // FenetreProjet fenetreProjet = new FenetreProjet(connection);
            FenetreProjet fenetreProjet = new FenetreProjet( connection, primaryStage, interfaceController );
            fenetreProjet.start(new Stage());
            // fenetreProjet.afficherFenetre();
        });

        // Gestionnaire d'événements pour le bouton ETUDIANTS
        etudiantsButton.setOnAction(event -> {
            // Ajoutez ici le code pour la fenêtre ETUDIANTS
            FenetreBinome fenetrebinome = new FenetreBinome(connection, primaryStage, interfaceController);
            fenetrebinome.start(new Stage());        
        });

        // // Gestionnaire d'événements pour le bouton UE
        //     // Ajoutez ici le code pour la fenêtre UE
        //      formationButton.setOnAction(event -> {
        //     FenetrePrincipale fenetrePrincipale = new  FenetrePrincipale( connection,  primaryStage,  interfaceController);
        //     fenetrePrincipale.start(new Stage());
        //     // fenetreProjet.afficherFenetre();
        // });

        // Mise en page
        VBox layout = new VBox(10);
        layout.getChildren().addAll(projetButton, etudiantsButton, formationButton);

        // Affichage de la scène
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void ouvrirMenuPrincipal() {
        start(primaryStage);
    }
}
