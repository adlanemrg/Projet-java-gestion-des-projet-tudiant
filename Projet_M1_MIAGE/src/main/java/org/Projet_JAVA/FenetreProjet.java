package org.Projet_JAVA;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.Projet_JAVA.base.Projet;

public class FenetreProjet extends Application{

    private Connection connection;
    private Stage primaryStage;

    //test
    public FenetreProjet(Connection connection) {
        this.connection = connection;
        
    }

    public FenetreProjet(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;
    }


    // //redefiniction de start 
    // @Override
    // public void start(Stage primaryStage) {
    //     primaryStage.setTitle("Fenêtre Projet");

    //     // Contenu de la fenêtre
    //     VBox layout = new VBox(10);
    //     // ... Ajoutez le contenu de votre fenêtre ici ...

    //     // Affichage de la scène
    //     Scene scene = new Scene(layout, 700, 700);
    //     primaryStage.setScene(scene);
    //     primaryStage.show();
    // }


     //redefiniction de start 
    @Override
    public void start(Stage primaryStage) {

        Platform.runLater(() -> {
            primaryStage.setTitle("Liste des Projets");

            // Créer une TableView pour afficher les projets
            TableView<Projet> projetTableView = new TableView<>();

            // Créer les colonnes de la TableView
            TableColumn<Projet, Integer> idCol = new TableColumn<>("ID Projet");
            idCol.setCellValueFactory(new PropertyValueFactory<>("idProjet"));

            TableColumn<Projet, String> nomMatiereCol = new TableColumn<>("Nom Matière");
            nomMatiereCol.setCellValueFactory(new PropertyValueFactory<>("nomMatiere"));

            TableColumn<Projet, String> sujetCol = new TableColumn<>("Sujet");
            sujetCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));


            TableColumn<Projet, String> dateRemisePrevueCol = new TableColumn<>("date de remise");
            dateRemisePrevueCol.setCellValueFactory(new PropertyValueFactory<>("dateRemisePrevue"));


           
            // Ajouter les colonnes à la TableView
            projetTableView.getColumns().addAll(idCol, nomMatiereCol, sujetCol,dateRemisePrevueCol);

            // Récupérer les projets depuis la base de données
            chargerProjets(projetTableView);

            // Gestionnaire d'événements pour la sélection d'un projet
            projetTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Afficher la liste des binômes pour le projet sélectionné
                    afficherBinomes(newValue.getIdProjet());
                }
            });

            // Mise en page
            VBox layout = new VBox(10);
            layout.getChildren().addAll(projetTableView);

            // Affichage de la scène
            Scene scene = new Scene(layout, 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    private void chargerProjets(TableView<Projet> projetTableView) {
        // Utiliser une requête SQL SELECT pour obtenir les informations nécessaires
        String selectQuery = "SELECT * FROM projet";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                int idProjet = resultSet.getInt("Id_projet");
                String nomMatiere = resultSet.getString("nom_matiere");
                String sujet = resultSet.getString("sujet");
                Date dateRemisePrevue = resultSet.getDate("date_remise");
    
                // Utilisation du nouveau constructeur
                Projet projet = new Projet(idProjet, nomMatiere, sujet, dateRemisePrevue);
    
                projetTableView.getItems().add(projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void afficherBinomes(int idProjet) {
        // Écrire la logique pour afficher la liste des binômes pour le projet sélectionné
        // Utiliser une requête SQL SELECT pour obtenir les informations nécessaires
        // Afficher les résultats dans une nouvelle fenêtre ou boîte de dialogue
    }
}
