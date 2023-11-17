/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.controller;

import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.modele.GestionNote;
import GestionNoteApplication.src.main.java.modele.Server;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robin.britelle
 */
public class AccueilController implements Initializable {
    
        @FXML
    private TextField UserTextField;

    @FXML
    private Label userLabel;

   @FXML
    private AnchorPane idaccueil;

    @FXML
    private Label BtnAccueil;
    private Stage fenetreActive;

    public void setFenetre(Stage fenetre) {
        fenetreActive = fenetre;
    }

    
    
    @FXML
    private AnchorPane contenuPage;
    
    
    Parent fxml;
    private ArrayList<Node> sauvegardeAccueil;

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
                 System.out.println(file.getAbsolutePath());
            try {
                GestionNote.recupererDonnees();
            } catch (IOException ex) {
                
            } catch (ClassNotFoundException ex) {
               
            }
       sauvegardeAccueil = new ArrayList();
       sauvegardeAccueil.addAll(contenuPage.getChildren());
    }
    
    @FXML
    void AccueilActionBouton() {
        if(t1 != null && t1.isAlive()) {
            Server.closeServer();
            t1.interrupt();
        }
       contenuPage.getChildren().setAll(sauvegardeAccueil);
    }
    
     @FXML
    void mesNotesActionBtn() {
        try {
            changerPage("notes.fxml");
        } catch (IOException ex) {
            
        }
    }
     @FXML
    void AjouterEvaluationActionButton() {
        try {
            changerPage("ajouterEvaluations.fxml");
        } catch (IOException ex) {
            
        }
    }

    

    

    @FXML
    void ParametreActionButton() {
         try {
            changerPage("Parametres.fxml");
        } catch (IOException ex) {
            
        }
    }

    @FXML
    void QuitterActionButton() {
        if(t1 != null && t1.isAlive()) {
            Server.closeServer();
            t1.interrupt();
        }
        //fenetreActive.hide();
        GestionNote.enregistrerDonnees();
        System.exit(0);
    }


    
    public void changerPage(String page) throws IOException {
        if(t1 != null && t1.isAlive()) {
            Server.closeServer();
            t1.interrupt();
        }
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/"+page);
        System.out.println(file.getAbsolutePath());
        if(file.exists()) {
            String changementPage = "../../ressources/fxml/"+page;
            System.out.println(file.getAbsolutePath());
            fxml = FXMLLoader.load(getClass().getResource(changementPage));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        }
    }
    
    @FXML
    void UserMouseEnter() {
        UserTextField.setText(userLabel.getText());
        UserTextField.setVisible(true);
        userLabel.setVisible(false);
        UserTextField.setEditable(true);
    }

    @FXML
    void UserMouseExit() {
        UserTextField.setVisible(false);
        userLabel.setText(UserTextField.getText());
        userLabel.setVisible(true);

    }

    
    
}
