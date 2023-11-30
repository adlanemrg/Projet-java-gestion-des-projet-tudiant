package org.Projet_JAVA.user_window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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




        // creation  un TextField pour la recherche en temps réel
        TextField searchTextField1 = new TextField();
        searchTextField1.setPromptText("Rechercher par étudiant...");
        searchTextField1.setStyle("-fx-background-color: yellow;");

        // creation d'un  bouton pour l'ajp
        Button ajouterBinomeButton = new Button("Ajouter un Binôme");
        ajouterBinomeButton.setOnAction(event -> ouvrirBoiteDialogueAjoutBinome(binomeTableView));



        // Ajouter un listener pour détecter les changements dans le TextField
        searchTextField1.textProperty().addListener((observable, oldValue, newValue) -> {
        // Mettre à jour la TableView en fonction du texte de recherche
        filterBinomes(binomeTableView, newValue);
    });

        // Récupérer les binômes depuis la base de données
        chargerBinomes(binomeTableView);

        // Mise en page
        VBox layout = new VBox(10);
        layout.getChildren().addAll(searchTextField1,ajouterBinomeButton,binomeTableView);

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


    // boite de dialogue pour l'ajout des binome 
    public void ouvrirBoiteDialogueAjoutBinome(TableView<Binome> btv) {
    // Créer une boîte de dialogue modale pour l'ajout de binômes
    Dialog<Binome> dialog = new Dialog<>();
    dialog.setTitle("Ajouter un Binôme");

    // Ajoutez des listes déroulantes pour les étudiants
    ComboBox<Etudiant> etudiant1ComboBox = new ComboBox<>();
    ComboBox<Etudiant> etudiant2ComboBox = new ComboBox<>();
    ComboBox<Projet> projetComboBox = new ComboBox<>();


    // Remplissez les listes déroulantes avec tous les étudiants
    chargerTousLesEtudiants(etudiant1ComboBox,etudiant2ComboBox);
    chargerTousLesProjets(projetComboBox);

    // Ajoutez les listes déroulantes à la boîte de dialogue
    GridPane grid = new GridPane();
    grid.add(new Label("Étudiant 1:"), 0, 0);
    grid.add(etudiant1ComboBox, 1, 0);
    grid.add(new Label("Étudiant 2:"), 0, 1);
    grid.add(etudiant2ComboBox, 1, 1);
    grid.add(new Label("Projet:"), 0, 2);
    grid.add(projetComboBox, 1, 2);

    dialog.getDialogPane().setContent(grid);

    // Ajoutez les boutons OK et Annuler
    ButtonType boutonOK = new ButtonType("OK", ButtonData.OK_DONE);
    ButtonType boutonAnnuler = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
    dialog.getDialogPane().getButtonTypes().addAll(boutonOK, boutonAnnuler);


    // Attendre la réponse de la boîte de dialogue
     dialog.setResultConverter(dialogButton -> {
        if (dialogButton == boutonOK) {

            Etudiant etudiant1 = etudiant1ComboBox.getValue();
            Etudiant etudiant2 = etudiant2ComboBox.getValue();      
            Projet projet = projetComboBox.getValue();

            // Vérifier si les champs sont vides
            if (etudiant1 == null|| etudiant2 == null|| projet == null) {
                interfaceController.afficherAlerte("Veuillez remplir tous les champs.");
                return null;
            }
            Binome binome = new Binome(0, projet.getIdProjet(), etudiant1, etudiant2) ;
            // Ajoutez le nouveau binôme à la base de données ou effectuez d'autres actions nécessaires
            binome.ajouterBinome(binome,connection,interfaceController);
              // Mettre à jour la TableView
            // Effacer la TableView
            btv.getItems().clear();
            chargerBinomes(btv);
            return binome; // Retourner le binôme créé

        }
        return null;
    });

        // Attendre la réponse de la boîte de dialogue
    Optional<Binome> result = dialog.showAndWait();

    }


private void chargerTousLesEtudiants(ComboBox<Etudiant> comboBox1, ComboBox<Etudiant> comboBox2) {
    // Utilisez une requête SQL SELECT pour obtenir tous les étudiants depuis la base de données
    String selectQuery = "SELECT * FROM Etudiant";

    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int idEtudiant = resultSet.getInt("Id_etudiant");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            int formationId = resultSet.getInt("formation_id");


            // Créez un objet Etudiant à partir des résultats de la base de données
            Etudiant etudiant = new Etudiant(idEtudiant, nom, prenom,formationId);

            // Ajoutez l'étudiant à la liste déroulante du premier ComboBox
            comboBox1.getItems().add(etudiant);
        }

        // Copiez les étudiants du premier ComboBox dans le second ComboBox
        comboBox2.getItems().addAll(comboBox1.getItems());

        // Ajoutez un gestionnaire d'événements pour gérer la sélection dans le premier ComboBox
        comboBox1.setOnAction(event -> {
            // Supprimez l'étudiant sélectionné du deuxième ComboBox
            Etudiant etudiantSelectionne = comboBox1.getValue();
            comboBox2.getItems().remove(etudiantSelectionne);
        });
    } catch (SQLException e) {
        e.printStackTrace();
        // Gérez l'exception (affichez une alerte, etc.)
    }
}



// Méthode pour charger tous les projets dans la ComboBox
private void chargerTousLesProjets(ComboBox<Projet> prj) {
    // Utilisez une requête SQL SELECT pour obtenir tous les projets depuis la base de données
    String selectQuery = "SELECT * FROM Projet";

    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int idProjet = resultSet.getInt("Id_projet");
            String nomMatiere = resultSet.getString("nom_matiere");
            String sujet = resultSet.getString("sujet");
            Date drp = resultSet.getDate("date_remise"); 

            // Créez un objet Projet à partir des résultats de la base de données
            Projet projet = new Projet(idProjet, nomMatiere, sujet ,drp);

            // Ajoutez le projet à la liste déroulante des projets
            prj.getItems().add(projet);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Gérez l'exception (affichez une alerte, etc.)
    }
}



    
}
