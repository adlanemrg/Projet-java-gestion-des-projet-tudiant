package org.Projet_JAVA.base;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.Projet_JAVA.InterfaceController;
import org.Projet_JAVA.interface_all.*;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class Binome {

// public class Binome extends Etudiant implements Crud  {

    private int id;
    private int projetId;  // L'identifiant du projet associé à ce binôme
    private Etudiant etudiant1;
    private Etudiant etudiant2;
    

    //juste un test pour fusionnet note et binome 
    private double noterapport;
    private double note_soutenance1;
    private double note_soutenance2;   
    private Date date_remise_effective;


    // Constructeur
    public Binome(int id, int projetId, Etudiant etudiant1, Etudiant etudiant2) {
        this.id = id;
        this.projetId = projetId;
        this.etudiant1 = etudiant1;
        this.etudiant2 = etudiant2;
    }

       // Constructeur pour le test 
    public Binome(int id, int projetId, Etudiant etudiant1, Etudiant etudiant2 ,double noterapport, double note_soutenance1, double note_soutenance2,Date date_remise_effective ) {
        this.id = id;
        this.projetId = projetId;
        this.etudiant1 = etudiant1;
        this.etudiant2 = etudiant2;
        this.noterapport = noterapport;
        this.note_soutenance1 = note_soutenance1;
        this.note_soutenance2 = note_soutenance2;
        this.date_remise_effective = date_remise_effective;
    
    }


    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjetId() {
        return projetId;
    }

    public void setProjetId(int projetId) {
        this.projetId = projetId;
    }

    public Etudiant getEtudiant1() {
        return etudiant1;
    }
    
    public void setEtudiant1(Etudiant e1) {
        this.etudiant1 = e1;
    }

      public Etudiant getEtudiant2() {
        return etudiant2;
    }
    
    public void setEtudiant2(Etudiant e2) {
        this.etudiant2 = e2;
    }

    //ajout des methodes CRUD 

    public void ajouterBinome(Binome binome, Connection connection ,InterfaceController in ) {

        if (binomeExisteDeja(binome, connection, in)) {
            // Afficher une alerte indiquant que le binôme existe déjà
            in.afficherAlerte("Le binôme existe déjà.");
            return;
        }
    
    // Utiliser une requête SQL INSERT pour ajouter le nouveau binôme à la base de données
    String insertQuery = "INSERT INTO binome (projet_id, id_etudiant_1, id_etudiant_2) VALUES (?, ?, ?)";
    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
        preparedStatement.setInt(1, binome.getProjetId());
        preparedStatement.setInt(2, binome.getEtudiant1().getId());
        preparedStatement.setInt(3, binome.getEtudiant2().getId());

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        // Gérez l'exception (affichez une alerte, etc.)
    }
}

// Méthode pour vérifier si le binôme existe déjà

//CE N'EST PAS LA METHODE LA PLUS OPTIMAL , CA AURAIT ETE MIEUX DE METTRE UNE LISTE D'ETUDIANT DANS BINOME , POUR FACILITER LA RECHERCHE DES BINOME 
private boolean binomeExisteDeja(Binome binome, Connection connection, InterfaceController in) {
    String selectQuery = "SELECT * FROM binome WHERE (projet_id = ? AND id_etudiant_1 = ? AND id_etudiant_2 = ?) OR (projet_id = ? AND id_etudiant_1 = ? AND id_etudiant_2 = ?)";
    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
        preparedStatement.setInt(1, binome.getProjetId());
        preparedStatement.setInt(2, binome.getEtudiant1().getId());
        preparedStatement.setInt(3, binome.getEtudiant2().getId());
        preparedStatement.setInt(4, binome.getProjetId());
        preparedStatement.setInt(5, binome.getEtudiant2().getId());
        preparedStatement.setInt(6, binome.getEtudiant1().getId());

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next(); // Retourne vrai si le binôme existe déjà
    } catch (SQLException e) {
        e.printStackTrace();
        in.afficherAlerte("Erreur BINOME");
        return false; // Par défaut, retourne faux en cas d'erreur
    }
}



  

}
