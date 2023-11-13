/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
       sauvegardeAccueil = new ArrayList();
       sauvegardeAccueil.addAll(contenuPage.getChildren());
    }
    
    @FXML
    void AccueilActionBouton() {
        
       contenuPage.getChildren().setAll(sauvegardeAccueil);
    }
    
     @FXML
    void mesNotesActionBtn() {
        try {
            changerPage("../../ressources/fxml/notes.fxml");
        } catch (IOException ex) {
            
        }
    }
     @FXML
    void AjouterEvaluationActionButton() {
        try {
            changerPage("../../ressources/fxml/ajouterEvaluations.fxml");
        } catch (IOException ex) {
            
        }
    }

    

    

    @FXML
    void ParametreActionButton() {
         try {
            changerPage("../../ressources/fxml/Parametres.fxml");
        } catch (IOException ex) {
            
        }
    }

    @FXML
    void QuitterActionButton() {
        //fenetreActive.hide();
        System.exit(0);
    }


    
    public void changerPage(String page) throws IOException {
        File file = new File("src/GestionNoteApplication.src/"+page);
        
        if(file.exists()) {
            System.out.println(file.getAbsolutePath());
            fxml = FXMLLoader.load(getClass().getResource(page));
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
