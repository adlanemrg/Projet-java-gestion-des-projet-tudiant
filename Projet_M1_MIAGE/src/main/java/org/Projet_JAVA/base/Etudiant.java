package org.Projet_JAVA.base;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Etudiant {
    
    private int id;
    private String nom;
    private String prenom;
    private int formationId;  // L'identifiant de la formation à laquelle l'étudiant appartient

    // Constructeur
    public Etudiant(int id, String nom, String prenom, int formationId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.formationId = formationId;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getFormationId() {
        return formationId;
    }

    public void setFormationId(int formationId) {
        this.formationId = formationId;
    }

     // Méthode pour obtenir le nom complet de l'étudiant
     public String getNomComplet() {
        return this.nom + " " + this.prenom;
    }


    // redefinition de la methode toString pour l'affichage des etudiants dans une liste pour l'ajout des binomes 
    @Override
    public String toString() {
        // Retourne la représentation textuelle souhaitée (nom, prénom, formation, etc.)
        return nom + " " + prenom + " - " + formationId; 
    }

    

}
