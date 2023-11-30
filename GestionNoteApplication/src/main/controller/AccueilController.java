/*
 * AccueilController.java
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
 * Controller de la premiere page de l'application 
 * Cette classe permet de faire la liaison entre toutes les autres pages de l'application
 * Ainsi que de gérer l'arret de l'aplication
 * Si le thread de l'application est activé lors d'une action sur cette page 
 * il doit être arrété sauf pour le changement du nom d'utilisateur 
 * @author enzo.cluzel
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

    @FXML
    private Label textePresentation;
    
    /** Affiche la nouvelle page appelé */
    @FXML
    private AnchorPane contenuPage;

    /** Appeler les pages fxml  */
    Parent fxml;

    /** sauvegarde de la page accueil pour remettre le contenu de accueil */
    private ArrayList<Node> sauvegardeAccueil;

    /**
     * Recupere les donnes de l'application, mets le nom de l'utilisateur
     * Sauvegarde le contenu de l'accueil pour la garder en mémoire en cas de retour à l'accueil 
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
            try {
                GestionNote.recupererDonnees();
                userLabel.setText(Stockage.getInstance().getUserName());
                textePresentation.setText("Bienvenu " + userLabel.getText() + " sur votre application de Gestion de Note");
            } catch (IOException|ClassNotFoundException ex) {
                System.out.println("L'application n'a pas pu récupérer les données de la derniere connexion");
            }
       sauvegardeAccueil = new ArrayList();
       sauvegardeAccueil.addAll(contenuPage.getChildren());
    }
    
    /**
     * Arrete le thread si activé
     * change le contenu de l'anchor pane a celle sauvegardé 
     */
    @FXML
    void AccueilActionBouton() {
        if(t1 != null && t1.isAlive()) {
            Server.closeServer();
            t1.interrupt();
        }
       contenuPage.getChildren().setAll(sauvegardeAccueil);
    }
    
    /**
     * Donne la page correspondant aux button a changerPage
     */
     @FXML
    void mesNotesActionBtn() {
        try {
            changerPage("notes.fxml");
        } catch (IOException ex) {
            
        }
    }
    
    /**
     * Donne la page correspondant aux button a changerPage
     */
     @FXML
    void AjouterEvaluationActionButton() {
        try {
            changerPage("calculerMoyenne.fxml");
        } catch (IOException ex) {
            
        }
    }

    

    
    /**
     * Donne la page correspondant aux button a changerPage
     */
    @FXML
    void ParametreActionButton() {
         try {
            changerPage("Parametres.fxml");
        } catch (IOException ex) {
            
        }
    }

    /**
     * Arrete l'application et sauvegarde les donnees
     * Arrete le thread si activé
     */
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


    /**
     * Change la page selon le button appuyé sur le menu
     * Arrete le thread si activé
     * L'anchor pane de la page est modifié pour prendre celle du fxml demandé
     * @param page, la page demandée
     * @throws IOException si la page demandée est cherché mais n'est pas trouvé
     */
    public void changerPage(String page) throws IOException {
        if(t1 != null && t1.isAlive()) {
            Server.closeServer();
            t1.interrupt();
        }
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/"+page);
        if(file.exists()) {
            String changementPage = "../../ressources/fxml/"+page;
            System.out.println(file.getAbsolutePath());
            fxml = FXMLLoader.load(getClass().getResource(changementPage));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        }
    }
    
    /**
     * Detecte la souris dans le text field de User 
     * Permet de changer le nom de l'utilisateur
     */
    @FXML
    void UserMouseEnter() {
        UserTextField.setText(userLabel.getText());
        UserTextField.setVisible(true);
        userLabel.setVisible(false);
        UserTextField.setEditable(true);
    }

     /**
     * Detecte la sortie de la souris dans le text field de User 
     * Sauvegarde les changements effectués + enleve la possibilité de modifier le nom du User
     */
    @FXML
    void UserMouseExit() {
        UserTextField.setVisible(false);
        userLabel.setText(UserTextField.getText());
        userLabel.setVisible(true);
        Stockage.getInstance().setUserName(userLabel.getText());
        textePresentation.setText("Bienvenu " + userLabel.getText() + " sur votre application de Gestion de Note");
    }
}