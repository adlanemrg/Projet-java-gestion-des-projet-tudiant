package org.Projet_JAVA.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import org.Projet_JAVA.InterfaceController;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Projet {
    private int idProjet;
    private String nomMatiere;
    private String sujet;
    private Date dateRemisePrevue;

    private SimpleBooleanProperty selected;

    // Constructeur
    public Projet(int idProjet, String nomMatiere, String sujet, Date dateRemisePrevue ) {
        this.idProjet = idProjet;
        this.nomMatiere = nomMatiere;
        this.sujet = sujet;
        this.dateRemisePrevue = dateRemisePrevue;

    }


    // Constructeur
    public Projet(int idProjet, String nomMatiere, String sujet, Date dateRemisePrevue, SimpleBooleanProperty selected ) {
        this.idProjet = idProjet;
        this.nomMatiere = nomMatiere;
        this.sujet = sujet;
        this.dateRemisePrevue = dateRemisePrevue;
        this.selected = new SimpleBooleanProperty(false);

    }
   

    // Getters et setters
    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Date getDateRemisePrevue() {
        return dateRemisePrevue;
    }

    public void setDateRemisePrevue(Date dateRemisePrevue) {
        this.dateRemisePrevue = dateRemisePrevue;
    }

    //redefinition de la methode toString
    @Override
    public String toString() {
        // Retourne la représentation textuelle souhaitée (nom, prénom, formation, etc.)
        return nomMatiere + " -" + sujet ; 
    }

    //methodes pour le selection d'un projet afin de le supprimer 

        public boolean isSelected() {
        return selected.get();
    }

    public SimpleBooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

 
    // code de toutes les methodes CRUD 

   public void ajouterProjet(Projet projet ,Connection connection  ) {
    // Connection connection ;
        // Utiliser une requête SQL INSERT pour ajouter le nouveau projet à la base de données
        String insertQuery = "INSERT INTO projet (nom_matiere, sujet, date_remise) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, projet.getNomMatiere());
            preparedStatement.setString(2, projet.getSujet());
            preparedStatement.setDate(3, new java.sql.Date(projet.getDateRemisePrevue().getTime())); // Convertir java.util.Date en java.sql.Date

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


public void supprimerProjet(Projet projet ,Connection connection , InterfaceController inte ) {

    // Supprimez le projet lui-même
        String deleteProjetQuery = "DELETE FROM Projet WHERE Id_projet = ?";
        try (PreparedStatement deleteProjetStatement = connection.prepareStatement(deleteProjetQuery)) {
            deleteProjetStatement.setInt(1, projet.getIdProjet());
            deleteProjetStatement.executeUpdate();
        }catch (SQLException e) {
            inte.afficherAlerte("Erreur lors de la suppression du projet.");
             e.printStackTrace();
        }
        

    }


    


}
