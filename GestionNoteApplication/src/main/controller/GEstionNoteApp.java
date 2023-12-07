
package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.package1.GestionNote;
import GestionNoteApplication.src.main.java.package1.Server;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Classe principale de l'application. Elle lance l'interface graphique de l'application de gestion de notes.
 * @author Enzo.Cluzel, Robin.Britelle
 */
public class GEstionNoteApp extends Application {
    
    // Thread pour la gestion du serveur permettant la communiction client/server
    public static Thread t1;
    
    // recupéres la fenêtre principale de l'application
    private static Stage secondaryStage;
   
    Stage fenetreSuivante = null;
    
    
    /**
     * Méthode principale pour lancer l'application.
     * @param args 
     */
    public static void main(String[] args) {
        // Récupération du chemin absolu vers les fichiers FXML de l'application
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
        System.out.println(file.getAbsolutePath());
        
        // Lancement de l'application JavaFX
        launch(args);
    }
    
    
    /**
     * Méthode de démarrage de l'interface graphique de l'application.
     * @param primaryStage La fenêtre principale de l'application
     * @throws Exception Une exception en cas de problème lors du chargement de l'interface
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Configuration de la fenêtre principale
            primaryStage.setResizable(false);
            File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
            System.out.println(file.getAbsolutePath());
            System.out.println("salut4");
            
            // Chargement du fichier FXML de la page d'accueil
            Parent fxml;
            fxml = FXMLLoader.load(getClass().getResource("../../ressources/fxml/accueil.fxml"));
            System.out.println("salut3");
            Scene sceneActive = new Scene(fxml);
            
            secondaryStage = primaryStage;
            
            // Affichage de la fenêtre principale avec la page d'accueil chargée
            primaryStage.setScene(sceneActive);
            primaryStage.show();
            
            // Gestion de la fermeture de la fenêtre
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent evt) {
                    // Arrêt du serveur si actif et sauvegarde des données
                    if (t1 != null && t1.isAlive()) {
                        Server.closeServer();
                        t1.interrupt();
                    }
                    GestionNote.enregistrerDonnees();
                    primaryStage.hide();
                }
            });
        } catch (IOException ex) {
            // Gestion d'une exception d'entrée/sortie
            Logger.getLogger(GEstionNoteApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retourne la fenêtre principale de l'application, pour pouvoir l'utiliser dans n'importe quel class
     * @return La fenêtre principale de l'application
     */
    public static Stage getPrimaryStage() {
        return secondaryStage;
    }
     
}
    
