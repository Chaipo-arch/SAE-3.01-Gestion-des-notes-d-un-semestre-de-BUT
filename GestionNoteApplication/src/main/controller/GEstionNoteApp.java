/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author enzo.cluzel
 */
public class GEstionNoteApp extends Application {
    
   
    Stage fenetreSuivante = null;
    Parent fxml;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
                Parent fxml = FXMLLoader.load(getClass().getResource("accueil.fxml"));
                Scene sceneActive = new Scene(fxml);

               primaryStage.setScene(sceneActive);
               primaryStage.show();
               

        } catch (IOException ex) {
                Logger.getLogger(GEstionNoteApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
}
    
