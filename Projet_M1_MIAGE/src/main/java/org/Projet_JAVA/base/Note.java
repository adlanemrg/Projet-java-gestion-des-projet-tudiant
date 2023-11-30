package org.Projet_JAVA.base;

import java.util.Date;

public class Note {

        private int idNote;
        private Binome binome; // Référence à un binôme
        private double noterapport;
        private double note_soutenance1;
        private double note_soutenance2;
        private Date date_remise_effective;
    
        // Constructeur
        public Note(int idNote, Binome binome, double noterapport, double note_soutenance1, double note_soutenance2, Date date_remise_effective) {
            this.idNote = idNote;
            this.binome = binome;
            this.noterapport = noterapport;
            this.note_soutenance1 = note_soutenance1;
            this.note_soutenance2 = note_soutenance2;
            this.date_remise_effective = date_remise_effective;
        }

        // Constructeur
        public Note( Binome binome, double noterapport, double note_soutenance1, double note_soutenance2, Date date_remise_effective) {
            this.binome = binome;
            this.noterapport = noterapport;
            this.note_soutenance1 = note_soutenance1;
            this.note_soutenance2 = note_soutenance2;
            this.date_remise_effective = date_remise_effective;
        }
    

    // public Note(int id, int projetId, Etudiant etudiant1, Etudiant etudiant2, double noterapport, double note_soutenance1, double note_soutenance2 ) {
    //     super(id, projetId, etudiant1, etudiant2);
    //     this.noterapport = noterapport;
    //     this.note_soutenance1 = note_soutenance1;
    //     this.note_soutenance2 = note_soutenance2;
    // }

    public Binome getBinome() {
        return binome;
    }

    public void setBinome(Binome b) {
        this.binome = b;
    }
 

    public double getNoterapport() {
        return noterapport;
    }

    public void setNoterapport(double noterapport) {
        this.noterapport = noterapport;
    }

    public double getNote_soutenance1() {
        return note_soutenance1;
    }

    public void setNote_soutenance1(double note_soutenance1) {
        this.note_soutenance1 = note_soutenance1;
    }

    public double getNote_soutenance2() {
        return note_soutenance2;
    }

    public void setNote_soutenance2(double note_soutenance2) {
        this.note_soutenance2 = note_soutenance2;
    }

     public Date getDate_remise_effective() {
        return date_remise_effective;
    }

    public void setDate_remise_effective(Date dre) {
        this.date_remise_effective = dre;
    }
    
}
