package org.Projet_JAVA.Connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connect() {
        String url = "jdbc:mysql://localhost:3307/projet_java";
        String user = "root";
        String password = "";

        try {
            // Tentative de connexion à la base de données
            Connection connection = DriverManager.getConnection(url, user, password);
            
            // Affichage si la connexion réussit
            System.out.println("Connexion à la base de données réussie carré");
            
            return connection;
        } catch (SQLException e) {
            // Gestion des exceptions SQLException
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            
            // Vous pouvez également imprimer la trace de la pile pour obtenir des détails supplémentaires
            e.printStackTrace();
            
            // Renvoie null en cas d'échec de la connexion
            return null;
        }
    }
}
