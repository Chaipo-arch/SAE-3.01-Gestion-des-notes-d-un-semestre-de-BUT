package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.modele.Client;
import GestionNoteApplication.src.main.java.modele.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/*import GestionNoteApplication.src.main.java.modele.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;*/

public class CommuniquerController {

   @FXML
    private TextField adresseIPText;

    @FXML
    private RadioButton toggleNational;

    @FXML
    private Button btnComm;

    @FXML
    private RadioButton toggleRessource;

    @FXML
    private TextField portID;

    @FXML
    private ToggleGroup choix;
    
    @FXML
    private CheckBox checkNational;
    
    @FXML
    private CheckBox checkRessource;
    
    @FXML
    private CheckBox checkTout;

    private Client client = new Client(); // Instanciation de la classe Client
   

    @FXML
    public void CommuniquerAction() {
        
        boolean ipValider = false;
        //String adresseIP = adresseIPText.getText();
        //int port = Integer.parseInt(portText.getText()); // Convertir le port en entier
        //portID.setText("1234");
        String serverIP = adresseIPText.getText(); // Adresse IP du serveur
        
        
        try {
            // Utilisation de la classe IPAddressValidator pour valider l'adresse IP
            boolean isValidIP = Client.validateIP(serverIP);

            if (isValidIP) {
                // L'adresse IP est valide, effectuez votre logique ici
                System.out.println("L'adresse IP est valide : " + serverIP);
                ipValider = true;
                // ... Autres actions à effectuer avec l'adresse IP valide
            } else {
                // L'adresse IP n'est pas valide, gérer l'erreur
                System.out.println("L'adresse IP n'est pas valide : " + serverIP);
                NotificationController.popUpMessage("Erreur format IP invalide","");
                System.out.println("Une exception s'est produite lors de la validation de l'adresse IP : ");
                ipValider = false;
                // ... Traitement en cas d'adresse IP invalide
            }
        } catch (Exception e) {
            // Une exception s'est produite lors de la validation de l'adresse IP
            NotificationController.popUpMessage("Erreur format IP invalide","");
            System.out.println("Une exception s'est produite lors de la validation de l'adresse IP : " + e.getMessage());
            // Gérer l'exception ici
            ipValider = false;
        }
        

        
        
        
        
        if (ipValider == true) {
            try {
                String filePath = "";
                ArrayList<String> fichiers = new ArrayList();
                if(checkNational.isSelected()) {
                    System.out.println("Envoie des Parametres national");
                    ParametrageNationalPrototype.createCsv();
                    filePath = "NationalExporte.csv";
                    fichiers.add(filePath);
                }
                if(checkRessource.isSelected()) {
                    System.out.println("Envoie des Parametres Ressource");
                    ParametrageRessourcePrototype.createCsv();
                    filePath = "RessourceExporte.csv";
                    fichiers.add(filePath);
                } 
                //ParametrageNationalPrototype.createCsv();

                System.out.println(portID.getText());
                int port = Integer.parseInt(portID.getText());
                System.out.println(port);
                System.out.println("salut debug1");
                System.out.println(serverIP);

                // Tentative de connexion au serveur et envoi de fichiers
                Client.connection(serverIP, port);

                // Envoi de fichiers au serveur
                for (int i = 0; i < fichiers.size(); i++) {
                    client.sendCSVFileToServer(fichiers.get(i));
                }

                // Réception de la réponse du serveur
                try {
                    Client.recevoirReponse();
                } catch (IOException ex) {
                    System.out.println("Erreur lors de la réception de la réponse du serveur : " + ex.getMessage());
                    // Autres actions en cas d'erreur de réception de réponse
                }
            } catch (NumberFormatException e) {
                System.out.println("Le port doit être un nombre valide.");
                // Actions en cas d'erreur de conversion du port en nombre
            } catch (Exception e) {
                System.out.println("Une erreur s'est produite : " + e.getMessage());
                // Actions en cas d'autres exceptions génériques
            }

            System.out.println("Fin du traitement dans le contrôleur");
            // Suite du code après la tentative de connexion et l'envoi de fichiers
        }
        
    }
}
