package org.Projet_JAVA.base;


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


    // Constructeur
    public Binome(int id, int projetId, Etudiant etudiant1, Etudiant etudiant2) {
        this.id = id;
        this.projetId = projetId;
        this.etudiant1 = etudiant1;
        this.etudiant2 = etudiant2;
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


  

}
