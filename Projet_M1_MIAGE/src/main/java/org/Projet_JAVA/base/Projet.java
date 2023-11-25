package org.Projet_JAVA.base;

import java.util.Date;

public class Projet {
    private int idProjet;
    private String nomMatiere;
    private String sujet;
    private Date dateRemisePrevue;

    // Constructeur
    public Projet(int idProjet, String nomMatiere, String sujet, Date dateRemisePrevue) {
        this.idProjet = idProjet;
        this.nomMatiere = nomMatiere;
        this.sujet = sujet;
        this.dateRemisePrevue = dateRemisePrevue;
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

 

    // il faut que j'ajoute des methodes CRUD
}
