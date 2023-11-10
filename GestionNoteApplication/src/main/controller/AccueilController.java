/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IHM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robin.britelle
 */
public class AccueilController implements Initializable {
    

    @FXML
    private AnchorPane contenuPage;
    
    Stage fenetreSuivante = null;
    
    Parent fxml;


    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fxml = FXMLLoader.load(getClass().getResource("accueil.fxml"));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @FXML
    void mesNotesActionBtn(ActionEvent event) {
        try {
            changerPage("page2.fxml");
        } catch (IOException ex) {
            
        }
    }

    
    public void changerPage(String page) throws IOException {
        fxml = FXMLLoader.load(getClass().getResource("accueil.fxml"));
        contenuPage.getChildren().removeAll();
        contenuPage.getChildren().setAll(fxml);
    }

    
    
}
