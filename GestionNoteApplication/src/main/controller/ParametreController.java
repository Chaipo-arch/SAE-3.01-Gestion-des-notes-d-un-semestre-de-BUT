package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.package1.GestionNote;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.package1.Stockage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;


/**
 * Contrôleur pour la gestion de la page de paramètres dans l'application de gestion de notes.
 * Initialise et gère les éléments de la page de paramètres.
 * @author Enzo Cluzel
 */
public class ParametreController implements Initializable {
    
    Parent fxml;
    @FXML
    private AnchorPane contenuPage;

     /**
     * Initialise le contrôleur de la classe 
     * @param url URL utilisée pour initialiser le contrôleur.
     * @param rb ResourceBundle utilisé pour localiser l'objet racine.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         System.out.println("bon controller");
    }
    
     /**
     * Action effectuée lors du clic sur le bouton "communiquer",
     * permet de changer la page courantes sur communiquer.fxml
     * @throws IOException Si une erreur d'entrée/sortie survient lors du changement de page.
     */
    @FXML
    void communiquerActionButton() throws IOException {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
        System.out.println(file.getAbsolutePath());
         System.out.println("bon controller");
        changerPage("communiquer.fxml");
    }
    
    
     /**
     * Action effectuée lors du clic sur le bouton pour réinitialiser les données
     * Affiche une notification a l'utilisateur pour la comfirmation de la supression
     * et supprime ou non les données en conséquence
     */
    @FXML
    void reinitialiserActionButton() {
        //NotificationController.popUpMessage("Reinitialisation données","");
        NotificationController NotificationController = new NotificationController();
        
        //NotificationController.popUpMessage("salut", "salut");
        Optional<ButtonType> result = NotificationController.popUpChoix("Reinitialisation données ?","");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Reini");
            Stockage.getInstance().supprimerDonnees();
            NotificationController.showNotification("Vos données ont bien était réinitialiser");
        }
        ParametrageNationalPrototype.flag = true;
    }

    /**
     * Action effectuée lors du clic sur le bouton "importer"
     * et renvoie a la page ImporterParametres.fxml
     * @throws IOException Si une erreur d'entrée/sortie survient lors du changement de page.
     */
    @FXML
    void importerActionButton() throws IOException {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
        System.out.println(file.getAbsolutePath());
        changerPage("ImporterParametres.fxml");
    }
    
    
    
    /**
     * Methodes permettant de chnger la page courante de l'utilisateur, la destination est 
     * passer en parametre
     * @param page Nom de la page à charger.
     * @throws IOException Si une erreur survient lors du chargement de la page.
     */
     public void changerPage(String page) throws IOException {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/"+page);
        System.out.println(file.getAbsolutePath());
        if(file.exists()) {
             String changementPage = "../../ressources/fxml/"+page;
            fxml = FXMLLoader.load(getClass().getResource(changementPage));
            contenuPage.getChildren().removeAll();
            contenuPage.getChildren().setAll(fxml);
        }
    }

}