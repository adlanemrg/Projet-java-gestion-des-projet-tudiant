package org.Projet_JAVA.user_window;
import org.Projet_JAVA.InterfaceController;
import org.Projet_JAVA.base.Binome;
import org.Projet_JAVA.base.Etudiant;
import org.Projet_JAVA.base.Projet;




import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


public class FenetreProjet extends Application {

    // public class FenetreProjet extends Application {

    private Connection connection;
    private Stage primaryStage;
    private InterfaceController interfaceController;
    // private TableView<Projet> projetTableView;


    //test
    public FenetreProjet(Connection connection) {
        this.connection = connection;
        
    }

    public FenetreProjet(Connection connection, Stage primaryStage) {
        this.connection = connection;
        this.primaryStage = primaryStage;
    }

      // Constructeur avec l'instance de InterfaceController
      public FenetreProjet(Connection connection, Stage primaryStage, InterfaceController interfaceController ) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        // this.projetTableView = projetTableView;
        this.interfaceController = new InterfaceController(connection); // Instanciation de l'InterfaceController
    }
   


     //redefiniction de start 
    @Override
    public void start(Stage primaryStage) {

        Platform.runLater(() -> {
            primaryStage.setTitle("Liste des Projets");

            
            // Créer une TableView pour afficher les projets
            TableView<Projet> projetTableView = new TableView<>();


            // Créer une ComboBox pour les noms de matières
            ComboBox<String> matiereComboBox = new ComboBox<>();
            matiereComboBox.setPromptText("Sélectionner une matière");

            // Ajouter un bouton "Voir tous les projets"
            Button voirTousLesProjetsButton = new Button("Voir tous les projets");
            voirTousLesProjetsButton.setOnAction(event -> {
            // Appeler la méthode pour afficher tous les projets (vous devez implémenter cette méthode)
            afficherTousLesProjets(projetTableView);
            });

            

             // Créer le bouton "Ajouter"
            Button ajouterButton = new Button("Ajouter");
            ajouterButton.setOnAction(event -> ouvrirBoiteDialogueAjoutProjet(projetTableView));



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
            projetTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);


            // Récupérer les projets depuis la base de données
            chargerProjets(projetTableView);

            // Gestionnaire d'événements pour la sélection d'un projet
            projetTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Afficher la liste des binômes pour le projet sélectionné
                    try {
                        afficherBinomes(newValue.getIdProjet());
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });

            // Récupérer la liste des noms de matières depuis la base de données
            chargerNomsMatieres(matiereComboBox);

            // Ajouter un listener pour détecter les changements dans la ComboBox
            matiereComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Mettre à jour la TableView en fonction de la matière sélectionnée
            chargerProjetsEnFonctionDeLaMatiere(projetTableView, newValue);
            });

            // Mise en page
            VBox layout = new VBox(10);
            layout.getChildren().addAll(matiereComboBox,voirTousLesProjetsButton,ajouterButton,projetTableView);

            // Affichage de la scène
            Scene scene = new Scene(layout, 900, 600);
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

    //redefinition de la methode afficherBinome

   private void afficherBinomes(int idProjet) throws SQLException{
    // Créer une nouvelle TableView pour les binômes
    TableView<Binome> binomeTableView = new TableView<>();

    // Créer les colonnes de la TableView pour les binômes
    TableColumn<Binome, Integer> idBinomeCol = new TableColumn<>("ID Binôme");
    idBinomeCol.setCellValueFactory(new PropertyValueFactory<>("id"));

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

    // creation de la colonne NOTE FINALE qui affiche la note du binome dans le projet (la note n'est pas stocké mais juste Calculé)
    TableColumn<Binome, Integer> noteFinaleCol = new TableColumn<>("Note finale");
    noteFinaleCol.setCellValueFactory(new PropertyValueFactory<>("id"));

    // Ajouter les colonnes à la TableView des binômes
    binomeTableView.getColumns().addAll(idBinomeCol, etudiant1Col, etudiant2Col);

    // Récupérer les binômes depuis la base de données pour le projet sélectionné
    chargerBinomes(binomeTableView, idProjet);

    // Créer la mise en page pour afficher la TableView des binômes
    VBox layout = new VBox(10);

    // Ajouter un en-tête pour la matière du projet
    Label matiereLabel = new Label("Matière du Projet : " + interfaceController.getProjetById(idProjet).getNomMatiere());
    layout.getChildren().add(matiereLabel);

    // Ajouter un en-tête pour le sujet du projet
    Label sujetLabel = new Label("Sujet du Projet : " + interfaceController.getProjetById(idProjet).getSujet());
    layout.getChildren().add(sujetLabel);

    layout.getChildren().addAll(binomeTableView);


    // Afficher la scène avec les binômes
    Scene scene = new Scene(layout, 500, 300);
    Stage binomeStage = new Stage();
    binomeStage.setTitle("Binômes du Projet : " + idProjet);
    binomeStage.setScene(scene);
    binomeStage.show();
}



    //redefinition de la methode chargerbinome 

    private void chargerBinomes(TableView<Binome> binomeTableView, int idProjet) {
        binomeTableView.getItems().clear(); // Efface les éléments existants dans la TableView
    
        // Utiliser une requête SQL SELECT pour obtenir les informations nécessaires sur les binômes
        String selectQuery = "SELECT * FROM binome WHERE projet_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, idProjet);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                int idBinome = resultSet.getInt("Id_binome");
                int idEtudiant1 = resultSet.getInt("id_etudiant_1");
                int idEtudiant2 = resultSet.getInt("id_etudiant_2");
    
                // Utiliser un getter pour obtenir les informations de l'étudiant 1
                Etudiant etudiant1 = interfaceController.getEtudiantById(idEtudiant1);
                String nomEtudiant1 = etudiant1.getNom();
                String prenomEtudiant1 = etudiant1.getPrenom();
    
                // Utiliser un getter pour obtenir les informations de l'étudiant 2
                Etudiant etudiant2 = interfaceController.getEtudiantById(idEtudiant2);
                String nomEtudiant2 = etudiant2.getNom();
                String prenomEtudiant2 = etudiant2.getPrenom();
    
                // Utilisation du nouveau constructeur
                Binome binome = new Binome(idBinome,idProjet, etudiant1, etudiant2);
    
                binomeTableView.getItems().add(binome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chargerNomsMatieres(ComboBox<String> matiereComboBox) {
        // Utiliser une requête SQL DISTINCT pour obtenir les noms uniques de matières
        String selectQuery = "SELECT DISTINCT nom_matiere FROM projet";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nomMatiere = resultSet.getString("nom_matiere");
                matiereComboBox.getItems().add(nomMatiere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void chargerProjetsEnFonctionDeLaMatiere(TableView<Projet> projetTableView, String nomMatiere) {
        projetTableView.getItems().clear(); // Effacer les éléments existants dans la TableView

        // Utiliser une requête SQL SELECT pour obtenir les informations nécessaires
        // Ajouter une condition WHERE pour filtrer par la matière sélectionnée
        String selectQuery = "SELECT * FROM projet";
        if (nomMatiere != null && !nomMatiere.isEmpty()) {
            selectQuery += " WHERE nom_matiere = ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            if (nomMatiere != null && !nomMatiere.isEmpty()) {
                preparedStatement.setString(1, nomMatiere);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Utilisation du nouveau constructeur
                Projet projet = new Projet(
                        resultSet.getInt("Id_projet"),
                        resultSet.getString("nom_matiere"),
                        resultSet.getString("sujet"),
                        resultSet.getDate("date_remise")
                );

                projetTableView.getItems().add(projet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //ouvrir boite de dialogue 

     private void ouvrirBoiteDialogueAjoutProjet(TableView<Projet> projetTableView) {
        // Créer une boîte de dialogue modale pour l'ajout de projets
        Dialog<Projet> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un Projet");

        // Créer des champs pour les informations du projet (à remplacer par vos propres champs)
        TextField nomMatiereField = new TextField();
        TextField sujetField = new TextField();
        DatePicker dateRemisePicker = new DatePicker();
        dateRemisePicker.setEditable(false);  //  modif du champ de date non modifiable


        dateRemisePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
    

        // Ajouter les champs à la boîte de dialogue
        GridPane grid = new GridPane();
        grid.add(new Label("Nom de la Matière:"), 0, 0);
        grid.add(nomMatiereField, 1, 0);
        grid.add(new Label("Sujet:"), 0, 1);
        grid.add(sujetField, 1, 1);
        grid.add(new Label("Date de Remise prévue:"), 0, 2);
        grid.add(dateRemisePicker, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Ajouter les boutons OK et Annuler
        ButtonType boutonOK = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType boutonAnnuler = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(boutonOK, boutonAnnuler);

        // Attendre la réponse de la boîte de dialogue
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == boutonOK) {
                // Récupérer les valeurs des champs et créer un nouveau projet
                String nomMatiere = nomMatiereField.getText();
                String sujet = sujetField.getText();
                LocalDate dateRemisePrevue = dateRemisePicker.getValue();

                // Vérifier si les champs sont vides
                if (nomMatiere.isEmpty() || sujet.isEmpty() || dateRemisePrevue == null) {
                    afficherAlerte("Veuillez remplir tous les champs.");
                    return null;
                }
                 // Vérifier si la date est ultérieure à la date actuelle
                if (dateRemisePrevue.isBefore(LocalDate.now())) {
                afficherAlerte("La date de remise doit être antérieure ou égale à la date actuelle.");
                return null;
            }

                // if(dateRemisePrevue != null)
                java.util.Date dateRemisePrevueUtilDate = java.sql.Date.valueOf(dateRemisePrevue);
                // Créer un nouveau projet
                Projet nouveauProjet = new Projet(0, nomMatiere, sujet, dateRemisePrevueUtilDate);
                
                // Ajouter le nouveau projet à la base de données
                nouveauProjet.ajouterProjet(nouveauProjet, connection );

                // Mettre à jour la TableView
                  // Effacer la TableView
                projetTableView.getItems().clear();
                chargerProjets(projetTableView);

                return nouveauProjet;
            }
            return null;
        });

        // Afficher la boîte de dialogue et attendre la réponse
        Optional<Projet> result = dialog.showAndWait();
    }

     private void afficherAlerte(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour afficher tous les projets
    private void afficherTousLesProjets(TableView<Projet> projetTableView) {
    // Effacer la TableView
    projetTableView.getItems().clear();

    // Récupérer tous les projets depuis la base de données
    chargerProjets(projetTableView);
    }

    

 
}
