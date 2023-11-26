package org.Projet_JAVA.user_window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.Projet_JAVA.InterfaceController;
import org.Projet_JAVA.base.Binome;
import org.Projet_JAVA.base.Etudiant;
import org.Projet_JAVA.base.Projet;

public class FenetreBinome extends Application {

    private Connection connection;
    private Stage primaryStage;
    private InterfaceController interfaceController;

    public FenetreBinome(Connection connection) {
        this.connection = connection;
    }

    // Constructeur avec l'instance de InterfaceController
      public FenetreBinome(Connection connection, Stage primaryStage, InterfaceController interfaceController) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        this.interfaceController = new InterfaceController(connection); // Instanciation de l'InterfaceController
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Liste des Binômes");

    
        // Créer une TableView pour afficher les binômes
        TableView<Binome> binomeTableView = new TableView<>();

        // Créer les colonnes de la TableView pour les binômes
        TableColumn<Binome, Integer> idBinomeCol = new TableColumn<>("ID Binôme");
        idBinomeCol.setCellValueFactory(new PropertyValueFactory<>("id"));


        // Créer une colonne pour afficher le sujet du projet
        TableColumn<Binome, String> sujetProjetCol = new TableColumn<>("Sujet du Projet");
        sujetProjetCol.setCellValueFactory(cellData -> {
            Binome binome = cellData.getValue();

            // Utiliser la méthode getProjetById pour obtenir les informations du projet
            try {
                Projet projet = interfaceController.getProjetById(binome.getProjetId());
                return new SimpleStringProperty(projet.getSujet());
            } catch (SQLException e) {
                e.printStackTrace();
                return new SimpleStringProperty("Erreur de récupération du sujet");
            }
        });

        // Créer une colonne pour afficher le sujet du projet
        TableColumn<Binome, String> matiereProjetCol = new TableColumn<>("UE");
        matiereProjetCol.setCellValueFactory(cellData -> {
            Binome binome = cellData.getValue();

            // Utiliser la méthode getProjetById pour obtenir les informations du projet
            try {
                Projet projet = interfaceController.getProjetById(binome.getProjetId());
                return new SimpleStringProperty(projet.getNomMatiere());
            } catch (SQLException e) {
                e.printStackTrace();
                return new SimpleStringProperty("Erreur de récupération de la matiere");
            }
        });

        TableColumn<Binome, String> etudiant1Col = new TableColumn<>("Étudiant 1");
        etudiant1Col.setCellValueFactory(cellData -> {
            Binome binome = cellData.getValue();
            return new SimpleStringProperty(binome.getEtudiant1().getNom() + " " + binome.getEtudiant1().getPrenom());
        });

        TableColumn<Binome, String> etudiant2Col = new TableColumn<>("Étudiant 2");
        etudiant2Col.setCellValueFactory(cellData -> {
            Binome binome = cellData.getValue();
            return new SimpleStringProperty(binome.getEtudiant2().getNom() + " " + binome.getEtudiant2().getPrenom());
        });


        // Ajouter les colonnes à la TableView des binômes
        binomeTableView.getColumns().addAll(idBinomeCol, etudiant1Col, etudiant2Col, matiereProjetCol,sujetProjetCol);

        // Créer un TextField pour la recherche en temps réel
        TextField searchTextField1 = new TextField();
        searchTextField1.setPromptText("Rechercher par étudiant...");
        searchTextField1.setStyle("-fx-background-color: yellow;");


        // Ajouter un listener pour détecter les changements dans le TextField
        searchTextField1.textProperty().addListener((observable, oldValue, newValue) -> {
        // Mettre à jour la TableView en fonction du texte de recherche
        filterBinomes(binomeTableView, newValue);
    });

        // Récupérer les binômes depuis la base de données
        chargerBinomes(binomeTableView);

        // Mise en page
        VBox layout = new VBox(10);
        layout.getChildren().addAll(searchTextField1,binomeTableView);

        // Affichage de la scène
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void chargerBinomes(TableView<Binome> binomeTableView) {
        binomeTableView.getItems().clear(); // Efface les éléments existants dans la TableView
    
        // Utiliser une requête SQL JOIN pour obtenir les informations nécessaires sur les binômes avec le nom du projet
        String selectQuery = "SELECT b.Id_binome, b.projet_id, b.id_etudiant_1, b.id_etudiant_2, p.sujet " +
                "FROM binome b " +
                "JOIN projet p ON b.projet_id = p.Id_projet";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                int idBinome = resultSet.getInt("Id_binome");
                int projetId = resultSet.getInt("projet_id");
    
                // Utilisation du constructeur de Binome avec récupération des étudiants et du nom du projet
                Binome binome = new Binome(idBinome, projetId, interfaceController.getEtudiantById(resultSet.getInt("id_etudiant_1")), interfaceController.getEtudiantById(resultSet.getInt("id_etudiant_2")));
    
                binomeTableView.getItems().add(binome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


private void filterBinomes(TableView<Binome> binomeTableView, String searchText) {
    // Effacer la TableView
    binomeTableView.getItems().clear();

    // Utiliser une requête SQL JOIN pour obtenir les informations nécessaires sur les binômes avec le nom du projet
    String selectQuery = "SELECT b.Id_binome, b.projet_id, b.id_etudiant_1, b.id_etudiant_2, p.sujet " +
            "FROM binome b " +
            "JOIN projet p ON b.projet_id = p.Id_projet";
    
    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
        ResultSet resultSet = preparedStatement.executeQuery();
    
        while (resultSet.next()) {
            int idBinome = resultSet.getInt("Id_binome");
            int projetId = resultSet.getInt("projet_id");

            // Utilisation du constructeur de Binome avec récupération des étudiants et du nom du projet
            Binome binome = new Binome(idBinome, projetId, interfaceController.getEtudiantById(resultSet.getInt("id_etudiant_1")), interfaceController.getEtudiantById(resultSet.getInt("id_etudiant_2")));

            // Vérifier si le texte de recherche est vide ou s'il est contenu dans le nom complet de l'étudiant1 ou de l'étudiant2
            if (searchText.trim().isEmpty() ||
                    binome.getEtudiant1().getNomComplet().toLowerCase().contains(searchText.toLowerCase()) ||
                    binome.getEtudiant2().getNomComplet().toLowerCase().contains(searchText.toLowerCase())) {
                binomeTableView.getItems().add(binome);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    
}
