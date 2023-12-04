/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.controller;

import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.package1.GestionNote;
import GestionNoteApplication.src.main.java.package1.Server;
import GestionNoteApplication.src.main.java.package1.Stockage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
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
    private Label textePresentation;
   
    @FXML
    private AnchorPane contenuPage;
   
   
    Parent fxml;
    private ArrayList<Node> sauvegardeAccueil;
    
    @FXML
    private Label TextTitre;
   
    static boolean mesNoteCourant;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mesNoteCourant = false;
       
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
            try {
                GestionNote.recupererDonnees();
                userLabel.setText(Stockage.getInstance().getUserName());
                textePresentation.setText("Bienvenu " + userLabel.getText() + " sur votre application de Gestion de Note");
            } catch (IOException ex) {
               
            } catch (ClassNotFoundException ex) {
               
            }
       sauvegardeAccueil = new ArrayList();
       sauvegardeAccueil.addAll(contenuPage.getChildren());
    }
   
    @FXML
    void AccueilActionBouton() {
       
        if(mesNoteCourant){
            
            Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. voulez vous vraiment abandonner vos évaluations non sauvegardées ?","");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                TextTitre.setText("Application Gestion Notes");
                if(t1 != null && t1.isAlive()) {
                    Server.closeServer();
                    t1.interrupt();
                }
                contenuPage.getChildren().setAll(sauvegardeAccueil);
                mesNoteCourant = false;
            }         
        } else {
            TextTitre.setText("Application Gestion Notes");
            if(t1 != null && t1.isAlive()) {
                    Server.closeServer();
                    t1.interrupt();
                }
            contenuPage.getChildren().setAll(sauvegardeAccueil);
        }
    }
   
    @FXML
    void mesNotesActionBtn() {
        try {
            
            if(!mesNoteCourant){
                TextTitre.setText("Mes Notes");
                changerPage("notes.fxml");
            }
        } catch (IOException ex) {

        }
    }
     @FXML
    void AjouterEvaluationActionButton() {
        try {
            if(mesNoteCourant){
                Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. voulez vous vraiment abandonner vos évaluations non sauvegardées ?","");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    TextTitre.setText("Vos Moyennes");
                    changerPage("calculerMoyenne.fxml");
                    mesNoteCourant = false;
                }
            } else {
                
                changerPage("calculerMoyenne.fxml");
                TextTitre.setText("Vos Moyennes");
            }
        } catch (IOException ex) {
           
        }
    }

   

   

    @FXML
    void ParametreActionButton() {
         try {
            //NotesController nC = new NotesController();
            //nC.modification();
            if(mesNoteCourant){
                Optional<ButtonType> result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. voulez vous vraiment abandonner vos évaluations non sauvegardées ?","");
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    TextTitre.setText("Parametres");
                    changerPage("Parametres.fxml");
                    mesNoteCourant = false;
                }
            } else {
                TextTitre.setText("Parametres");
                changerPage("Parametres.fxml");
            }
        } catch (IOException ex) {
           
        }
    }

    @FXML
    void QuitterActionButton() {
        Optional<ButtonType> result;
        //NotesController nC = new NotesController();
        //nC.modification();
        if(mesNoteCourant){
            result = NotificationController.popUpChoix("Des modifications sur la page des évaluation non pas été sauvegardé. voulez vous vraiment abandonner vos évaluations non sauvegardées et quitter l'application ?","");
            mesNoteCourant = false;
        } else {
            result = NotificationController.popUpChoix("","Souhaitez vous quitter l'application ?");          
        }
        if (result.isPresent() && result.get() == ButtonType.OK) {
                if(t1 != null && t1.isAlive()) {
                    Server.closeServer();
                    t1.interrupt();
                }
                //fenetreActive.hide();
                GestionNote.enregistrerDonnees();
                System.exit(0);          
        }
    }


   
    public void changerPage(String page) throws IOException {
        if(t1 != null && t1.isAlive()) {
            Server.closeServer();
            t1.interrupt();
        }
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/"+page);
        System.out.println(file.exists());
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
        Stockage.getInstance().setUserName(userLabel.getText());
        textePresentation.setText("Bienvenu " + userLabel.getText() + " sur votre application de Gestion de Note");
    }
    
    
    @FXML
    void NoticeClick() {
         try {
             NoticeController.y = 0;
            TextTitre.setText("Notice");
            changerPage("Notice.fxml");
        } catch (IOException ex) {
            
        }
    }
}

