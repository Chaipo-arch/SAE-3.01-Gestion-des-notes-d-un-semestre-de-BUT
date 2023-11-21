package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.modele.Client;
import GestionNoteApplication.src.main.java.modele.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    private Label notifEnvoi;
     
    @FXML
    private CheckBox checkTout;

    private Client client = new Client(); // Instanciation de la classe Client
   

    @FXML
    public void CommuniquerAction() {
        notifEnvoi.setVisible(false);
        String serverIP = adresseIPText.getText(); // Adresse IP du serveur
        boolean BoucleTout = false;
        try {
            String filePath = "";
            ArrayList<String> fichiers = new ArrayList();
            if(checkNational.isSelected()) {
                //System.out.println("Envoie des Parametres national");
                ParametrageNationalPrototype.createCsv();
                filePath = "NationalExporte.csv";
                fichiers.add(filePath);
            }
            if(checkRessource.isSelected()) {
                //System.out.println("Envoie des Parametres Ressource");
                ParametrageRessourcePrototype.createCsv();
                filePath = "RessourceExporte.csv";
                fichiers.add(filePath);
            }             
            System.out.println(portID.getText());
            int port = Integer.parseInt(portID.getText());
            System.out.println(port);
            Client client = new Client();
            System.out.println(serverIP);
            if(fichiers.size() != 0) { 
                client.connection(serverIP, port);
                for(int i = 0; i < fichiers.size();i++) {
                    client.sendCSVFileToServer(fichiers.get(i));
                }
                try {
                    notifEnvoi.setVisible(true);
                    notifEnvoi.setText(client.recevoirReponse());
                } catch (IOException ex) {
                    notifEnvoi.setVisible(true);
                    notifEnvoi.setText("Pas de reponse reçu");
                    
                }
            }
        } catch (NumberFormatException e) {
            notifEnvoi.setVisible(true);
            notifEnvoi.setText("Le port doit être un nombre valide.");
            
            //e.printStackTrace();
        }
        

        //client.sendSerializedFileToServer(serverIP, filePath, port); // Appeler la méthode du client
    }
}