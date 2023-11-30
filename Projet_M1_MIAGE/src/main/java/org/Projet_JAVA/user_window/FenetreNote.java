package org.Projet_JAVA.user_window;
import org.Projet_JAVA.InterfaceController;
import org.Projet_JAVA.base.Binome;
import org.Projet_JAVA.base.Etudiant;
import org.Projet_JAVA.base.Note;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FenetreNote extends Application {

    private Connection connection;
    private InterfaceController interfaceController;
    private Stage primaryStage;


    public FenetreNote(Connection connection, Stage primaryStage, InterfaceController interfaceController ) {
        this.connection = connection;
        this.primaryStage = primaryStage;
        this.interfaceController = new InterfaceController(connection); // Instanciation de l'InterfaceController
    }

    @Override
    public void start(Stage primaryStage) {
        TableView<Note> noteTableView = new TableView<>();

    //     // Créer les colonnes de la TableView
    //     TableColumn<Note, Integer> id_binome_Col = new TableColumn<>("ID Binome");
    //     id_binome_Col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdBinome()).asObject());


    //     TableColumn<Note, String> etudiant1Col = new TableColumn<>("Étudiant 1");
    //     etudiant1Col.setCellValueFactory(cellData -> {
    //         Binome binome = cellData.getValue();
    //         return new SimpleStringProperty(binome.getEtudiant1().getNom() + " " + binome.getEtudiant1().getPrenom());
    //     });

    //    // Créer la colonne pour la date de remise effective
    //     TableColumn<Note, Date> dateRemiseEffectiveCol = new TableColumn<>("Date de Remise Effective");
    //     dateRemiseEffectiveCol.setCellValueFactory(cellData -> {
    //         Date dateRemiseEffective = cellData.getValue().getDate_remise_effective();
    //         return new SimpleObjectProperty<>(dateRemiseEffective);
    //     });

    // Créer la colonne pour la note du rapport
    TableColumn<Note, Double> noteRapportCol = new TableColumn<>("Note Rapport");
    noteRapportCol.setCellValueFactory(new PropertyValueFactory<>("noterapport"));

    // Créer la colonne pour la note de la soutenance 1
    TableColumn<Note, Double> noteSoutenance1Col = new TableColumn<>("Note Soutenance 1");
    noteSoutenance1Col.setCellValueFactory(new PropertyValueFactory<>("note_soutenance1"));

    // Créer la colonne pour la note de la soutenance 2
    TableColumn<Note, Double> noteSoutenance2Col = new TableColumn<>("Note Soutenance 2");
    noteSoutenance2Col.setCellValueFactory(new PropertyValueFactory<>("note_soutenance2"));

    // Créer la colonne pour la date de remise effective
    TableColumn<Note, Date> dateRemiseEffectiveCol = new TableColumn<>("Date de Remise Effective");
    dateRemiseEffectiveCol.setCellValueFactory(new PropertyValueFactory<>("date_remise_effective"));

    // Créer la colonne pour le nom et prénom de l'étudiant 1
    TableColumn<Note, String> etudiant1NomPrenomCol = new TableColumn<>("Étudiant 1");
    etudiant1NomPrenomCol.setCellValueFactory(cellData -> {
        
        // Etudiant etudiant1 = cellData.getValue().getEtudiant1();
        Etudiant etudiant1 = cellData.getValue().getBinome().getEtudiant1();
        return new SimpleStringProperty(etudiant1.getNom() + " " + etudiant1.getPrenom());
    });
 // Créer la colonne pour le nom et prénom de l'étudiant 1
    TableColumn<Note, String> etudiant2NomPrenomCol = new TableColumn<>("Étudiant 2");
    etudiant2NomPrenomCol.setCellValueFactory(cellData -> {
        
        // Etudiant etudiant1 = cellData.getValue().getEtudiant1();
        Etudiant etudiant2 = cellData.getValue().getBinome().getEtudiant2();
        return new SimpleStringProperty(etudiant2.getNom() + " " + etudiant2.getPrenom());
    });

        // Ajouter les colonnes à la TableView

        noteTableView.getColumns().addAll(etudiant1NomPrenomCol,etudiant2NomPrenomCol,noteRapportCol, noteSoutenance1Col,noteSoutenance2Col,dateRemiseEffectiveCol);


        // Charger les données depuis la base de données
        chargerDonnees(noteTableView, interfaceController);

        // Mise en page
        Scene scene = new Scene(noteTableView, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Table des Notes");
        primaryStage.show();
    }

    private void chargerDonnees(TableView<Note> noteTableView , InterfaceController i) {
        // Utiliser une requête SQL SELECT pour obtenir les informations nécessaires
        String selectQuery = "SELECT * FROM note";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<Note> noteList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int binomeId = resultSet.getInt("binome_id");
                int noteRapport = resultSet.getInt("note_rapport");
                int noteSoutenance1 = resultSet.getInt("note_soutenance1");
                int noteSoutenance2 = resultSet.getInt("note_soutenance2");
                Date dateRemiseEffective = resultSet.getDate("date_remise_effective");

                // Utilisation du constructeur de la classe Note
                // Note note = new Note(binomeId, noteRapport, noteSoutenance1, noteSoutenance2, dateRemiseEffective);
                Note note = new Note(i.getBinomeById(binomeId), noteRapport, noteSoutenance1,noteSoutenance2 , dateRemiseEffective);

                noteList.add(note);
            }

            // Ajouter les données à la TableView
            noteTableView.setItems(noteList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
