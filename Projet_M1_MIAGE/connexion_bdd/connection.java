package ProjetM1Miage.connexion_bdd.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Informations de connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/java_project";
        String utilisateur = "root";
        String motDePasse = "";

        // Établir la connexion
        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);

            // Votre code ici pour interagir avec la base de données

            // Fermer la connexion lorsque vous avez terminé
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
