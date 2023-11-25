package org.Projet_JAVA.base;

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

    // il faut que j'ajoute des methodes CRUD
}
