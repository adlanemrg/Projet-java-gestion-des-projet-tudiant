package org.Projet_JAVA;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.Projet_JAVA.base.Formation;

public class FenetrePrincipale {


    
    //methode 1
   public VBox getContenu(Connection connection) {
        VBox layout = new VBox(10);

        // Créer une TableView pour afficher les formations
        TableView<Formation> tableView = new TableView<>();
        tableView.setPrefSize(600, 400);

        // Créer les colonnes de la TableView
        TableColumn<Formation, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("idFormation"));

        TableColumn<Formation, String> nomCol = new TableColumn<>("Nom");
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Formation, String> promotionCol = new TableColumn<>("Promotion");
        promotionCol.setCellValueFactory(new PropertyValueFactory<>("promotion"));

        // Ajouter les colonnes à la TableView
        tableView.getColumns().addAll(idCol, nomCol, promotionCol);

        // Récupérer les données de la table Formation et les ajouter à la TableView
        afficherTableFormations(connection, tableView);

        // Créer un champ de texte pour saisir le nom de la formation à rechercher
        TextField champRecherche = new TextField();
        champRecherche.setPromptText("Nom de la formation");

      
        champRecherche.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            rechercherFormation(connection, tableView, newValue);
        });
        

        // Ajouter le champ de texte à une mise en page HBox
        HBox rechercheLayout = new HBox(10);
        rechercheLayout.getChildren().addAll(champRecherche);

        // Ajouter la TableView et la mise en page de recherche à la mise en page principale
        layout.getChildren().addAll(new Label("Bienvenue dans la fenêtre principale!"), tableView, rechercheLayout);

        return layout;
    }



    //methode 2
    private void rechercherFormation(Connection connection, TableView<Formation> tableView, String nomFormation) {
        try {
            String selectQuery = "SELECT * FROM Formation WHERE nom_formation LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, "%" + nomFormation + "%");

                ResultSet resultSet = preparedStatement.executeQuery();

                // Créer une liste observable pour stocker les données de la TableView
                ObservableList<Formation> formationsList = FXCollections.observableArrayList();

                // Ajouter chaque formation à la liste
                while (resultSet.next()) {
                    int idFormation = resultSet.getInt("id_formation");
                    String nomFormationResult = resultSet.getString("nom_formation");
                    String promotion = resultSet.getString("promotion");

                    formationsList.add(new Formation(idFormation, nomFormationResult, promotion));
                }

                // Ajouter la liste à la TableView
                tableView.setItems(formationsList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //methode 3
    private void afficherTableFormations(Connection connection, TableView<Formation> tableView) {
        try {
            String selectQuery = "SELECT * FROM Formation";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                ResultSet resultSet = preparedStatement.executeQuery();

                // Créer une liste observable pour stocker les données de la TableView
                ObservableList<Formation> formationsList = FXCollections.observableArrayList();

                // Ajouter chaque formation à la liste
                while (resultSet.next()) {
                    int idFormation = resultSet.getInt("id_formation");
                    String nomFormation = resultSet.getString("nom_formation");
                    String promotion = resultSet.getString("promotion");

                    formationsList.add(new Formation(idFormation, nomFormation, promotion));
                }

                // Ajouter la liste à la TableView
                tableView.setItems(formationsList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
