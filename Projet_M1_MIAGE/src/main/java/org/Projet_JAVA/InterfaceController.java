package org.Projet_JAVA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.Projet_JAVA.base.Binome;
import org.Projet_JAVA.base.Etudiant;
import org.Projet_JAVA.base.Note;
import org.Projet_JAVA.base.Projet;

public class InterfaceController  {

    private Connection connection;

    public InterfaceController(Connection connection) {
        this.connection = connection;
    }

    //definition une methode pour recuperer un etutiant par son id 
    public Etudiant getEtudiantById(int idEtudiant) throws SQLException {
        // Utiliser une requête SQL SELECT pour obtenir l'étudiant par son ID
        String selectQuery = "SELECT * FROM etudiant WHERE Id_etudiant = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, idEtudiant);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                // Utiliser le constructeur de la classe Etudiant pour créer une instance avec les informations
                return new Etudiant(
                    resultSet.getInt("Id_etudiant"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getInt("formation_id")
                );
            }
        }
        // Retourner null si l'étudiant n'est pas trouvé
        return null;
    }

     //definition une methode pour recuperer un projet par son id
     
     public Projet getProjetById(int idProjet) throws SQLException {
        // Utiliser une requête SQL SELECT pour obtenir le projet par son ID
        String selectQuery = "SELECT * FROM projet WHERE Id_projet = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, idProjet);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Utiliser le constructeur de la classe Projet pour créer une instance avec les informations
                return new Projet(
                        resultSet.getInt("Id_projet"),
                        resultSet.getString("nom_matiere"),
                        resultSet.getString("sujet"),
                        resultSet.getDate("date_remise")
                );
            }
        }
        // Retourner null si le projet n'est pas trouvé
        return null;
    }

    public Note getNoteByIdBinome(int idBinome) throws SQLException {
        String selectQuery = "SELECT * FROM note WHERE binome_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, idBinome);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Récupérer les valeurs de la base de données
                double noterapport = resultSet.getDouble("noterapport");
                double note_soutenance1 = resultSet.getDouble("note_soutenance1");
                double note_soutenance2 = resultSet.getDouble("note_soutenance2");

                // Utiliser le constructeur de Note pour créer une instance avec les informations
                return new Note(idBinome, getBinomeById(idBinome).getProjetId(), getBinomeById(idBinome).getEtudiant1(), getBinomeById(idBinome).getEtudiant2(), noterapport, note_soutenance1, note_soutenance2);
            }
        }
        // Retourner null si la note n'est pas trouvée
        return null;
    }



       // Méthode pour obtenir les binômes filtrés en fonction du texte de recherche
    public List<Binome> getFilteredBinomes(String searchText) {
        List<Binome> filteredBinomes = new ArrayList<>();

        // Utiliser une requête SQL SELECT pour obtenir les informations nécessaires sur les binômes
        String selectQuery = "SELECT * FROM binome " +
                             "INNER JOIN etudiant e1 ON binome.id_etudiant_1 = e1.Id_etudiant " +
                             "INNER JOIN etudiant e2 ON binome.id_etudiant_2 = e2.Id_etudiant " +
                             "WHERE e1.nom LIKE ? OR e1.prenom LIKE ? OR e2.nom LIKE ? OR e2.prenom LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            // Paramètres de recherche pour le nom ou prénom de l'étudiant
            String searchParameter = "%" + searchText + "%";
            preparedStatement.setString(1, searchParameter);
            preparedStatement.setString(2, searchParameter);
            preparedStatement.setString(3, searchParameter);
            preparedStatement.setString(4, searchParameter);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idBinome = resultSet.getInt("Id_binome");
                int idProjet = resultSet.getInt("projet_id");
                int idEtudiant1 = resultSet.getInt("id_etudiant_1");
                int idEtudiant2 = resultSet.getInt("id_etudiant_2");

                // Utiliser un getter pour obtenir les informations de l'étudiant 1
                Etudiant etudiant1 = getEtudiantById(idEtudiant1);

                // Utiliser un getter pour obtenir les informations de l'étudiant 2
                Etudiant etudiant2 = getEtudiantById(idEtudiant2);

                // Utilisation du nouveau constructeur
                Binome binome = new Binome(idBinome, idProjet, etudiant1, etudiant2);

                filteredBinomes.add(binome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredBinomes;
    }

    //methode pour recupperer le binome par son ID
    public Binome getBinomeById(int idBinome) throws SQLException {
        String selectQuery = "SELECT * FROM binome WHERE Id_binome = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, idBinome);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Récupérer les valeurs de la base de données
                int projetId = resultSet.getInt("projet_id");
                int idEtudiant1 = resultSet.getInt("id_etudiant_1");
                int idEtudiant2 = resultSet.getInt("id_etudiant_2");

                // Utiliser les méthodes getEtudiantById pour obtenir les instances d'étudiants
                Etudiant etudiant1 = getEtudiantById(idEtudiant1);
                Etudiant etudiant2 = getEtudiantById(idEtudiant2);

                // Utiliser le constructeur de Binome pour créer une instance avec les informations
                return new Binome(idBinome, projetId, etudiant1, etudiant2);
            }
        }
        // Retourner null si le binôme n'est pas trouvé
        return null;
    }




 

}
