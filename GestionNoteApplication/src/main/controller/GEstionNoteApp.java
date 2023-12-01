/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author enzo.cluzel
 */
public class GEstionNoteApp extends Application {
    
    public static Thread t1;
    private static Stage secondaryStage;
   
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
                primaryStage.setResizable(false);
                System.out.println();
                File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
                 System.out.println(file.getAbsolutePath());
                 System.out.println("salut4");
                
                Parent fxml;
                fxml = FXMLLoader.load(getClass().getResource("../../ressources/fxml/accueil.fxml"));
                System.out.println("salut3");
                Scene sceneActive = new Scene(fxml);
                
                secondaryStage = primaryStage;

               primaryStage.setScene(sceneActive);
               primaryStage.show();
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
    
    
    public static Stage getPrimaryStage() {
        return secondaryStage;
    }
     
}
    
