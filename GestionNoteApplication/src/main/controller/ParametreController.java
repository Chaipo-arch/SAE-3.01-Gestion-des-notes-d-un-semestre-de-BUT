package GestionNoteApplication.src.main.controller;

import static GestionNoteApplication.src.main.controller.GEstionNoteApp.notificationController;
import GestionNoteApplication.src.main.java.modele.GestionNote;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.modele.Stockage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;




public class ParametreController implements Initializable {
    
    Parent fxml;
    @FXML
    private AnchorPane contenuPage;
    
    //NotificationController notificationController = GEstionNoteApp.getNotificationController();
    

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         System.out.println("bon controller");
    }
    @FXML
    void communiquerActionButton() throws IOException {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
        System.out.println(file.getAbsolutePath());
         System.out.println("bon controller");
        changerPage("communiquer.fxml");
    }

    @FXML
    void reinitialiserActionButton() {
        
        //NotificationController notificationWindow = new NotificationController();
        NotificationController NotificationController = new NotificationController();
        Optional<ButtonType> result = NotificationController.popUp("Reinitialisation données","Réinitialiser vos données ?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Reini");
            Stockage.getInstance().supprimerDonnees();
            NotificationController.showNotification("Vos données ont bien était réinitialiser");
        }
        

        
        
        
        //NotificationController notificationController = GEstionNoteApp.getNotificationController();
        //notificationController.showNotification("Nique ta mereeeee!");
        // Lorsque l'événement se produit, affichez une notification
        //notificationWindow.showNotification("Nouvelle notification !");
        


//NotificationController.showNotification(message);
        System.out.println("test");
        ParametrageNationalPrototype.flag = true;
    }

    @FXML
    void importerActionButton() throws IOException {
        File file = new File("src/GestionNoteApplication/src/ressources/fxml/");
        System.out.println(file.getAbsolutePath());
        changerPage("ImporterParametres.fxml");
    }
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
