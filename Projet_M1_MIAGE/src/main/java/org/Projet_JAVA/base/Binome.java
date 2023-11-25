package org.Projet_JAVA.base;

public class Binome {
    private int id;
    private int projetId;  // L'identifiant du projet associé à ce binôme

    // Constructeur
    public Binome(int id, int projetId) {
        this.id = id;
        this.projetId = projetId;
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

    // il faut que j'ajoute des methodes CRUD
}
