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

/**aaa
 *
 * @author enzo.cluzel
 */
public class GEstionNoteApp extends Application {
    
    public static Thread t1;
   
    Stage fenetreSuivante = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
                File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
                Parent fxml;
                fxml = FXMLLoader.load(getClass().getResource("../../ressources/fxml/accueil.fxml"));
                Scene sceneActive = new Scene(fxml);
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
     
}
    
