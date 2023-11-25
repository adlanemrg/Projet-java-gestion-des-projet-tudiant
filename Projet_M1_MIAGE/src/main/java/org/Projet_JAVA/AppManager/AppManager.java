// PARLER 
// DE
// SINGLETON

package org.Projet_JAVA.AppManager;

import java.sql.Connection;

import javafx.stage.Stage;

public class AppManager {
    private static Connection connection;
    private static Stage primaryStage;

    public static void setConnection(Connection connection) {
        AppManager.connection = connection;
    }
 
    public static Connection getConnection() {
        return connection;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        AppManager.primaryStage = primaryStage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
