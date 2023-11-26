package org.Projet_JAVA.base;

public class Note extends Binome {
    private double noterapport;
    private double note_soutenance1;
    private double note_soutenance2;

    public Note(int id, int projetId, Etudiant etudiant1, Etudiant etudiant2, double noterapport, double note_soutenance1, double note_soutenance2) {
        super(id, projetId, etudiant1, etudiant2);
        this.noterapport = noterapport;
        this.note_soutenance1 = note_soutenance1;
        this.note_soutenance2 = note_soutenance2;
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
}
