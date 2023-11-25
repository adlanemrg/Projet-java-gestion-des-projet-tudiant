package org.Projet_JAVA.base;

public class Formation {
    private int idFormation;
    private String nom;
    private String promotion;

    // Constructeur
    public Formation(int idFormation, String nom, String promotion) {
        this.idFormation = idFormation;
        this.nom = nom;
        this.promotion = promotion;
    }

    // Getters et setters
    public int getIdFormation() {
        return idFormation;
    }
    public void setIdFormation(int idFormation) {
        this.idFormation = idFormation;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    // il faut que j'ajoute des methodes CRUD
}
