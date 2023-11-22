/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.modele.GestionNote;
import GestionNoteApplication.src.main.java.modele.Server;
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
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**aaa
 *
 * @author enzo.cluzel
 */
public class GEstionNoteApp extends Application {
    
    public static Thread t1;
    private static Stage primaryStage;
    static public double windowHeight;
    static public double windowWidth;
    
    static public NotificationController notificationController;
    
   
    Stage fenetreSuivante = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
        System.out.println(file.getAbsolutePath());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
                //NotificationController notificationController = new NotificationController(primaryStage);
                //GEstionNoteApp.primaryStage = primaryStage; // Stocker la référence à la fenêtre principale
    
                System.out.println();
                File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
                 System.out.println(file.getAbsolutePath());
                 System.out.println("salut4");
                
                Parent fxml;
                fxml = FXMLLoader.load(getClass().getResource("../../ressources/fxml/accueil.fxml"));
                System.out.println("salut3");
                Scene sceneActive = new Scene(fxml);

               primaryStage.setScene(sceneActive);
               primaryStage.show();
               System.out.println(primaryStage.getWidth());
               
               
               //notificationController = new NotificationController(primaryStage);
               
               //double windowHeight = primaryStage.getHeight();
               //double windowWidth = primaryStage.getWidth();
               
               primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
               public void handle(WindowEvent evt){
                   if(t1 != null && t1.isAlive()) {
                        Server.closeServer();
                        t1.interrupt();
                    }
                   GestionNote.enregistrerDonnees();
                    primaryStage.hide();
                   }
               });

        } catch (IOException ex) {
            
            Logger.getLogger(GEstionNoteApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //todo a supp
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
     public static double getwindowHeight() {
        return windowHeight;
    }
     
      public static double getwindowWidth() {
        return windowWidth;
    }
      
    public static NotificationController getNotificationController() {
       return notificationController;
    }

     
}
    
