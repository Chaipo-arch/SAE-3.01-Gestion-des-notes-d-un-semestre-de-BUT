/*
 * CommuniquerController.java
 */
package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.package1.Client;
import GestionNoteApplication.src.main.java.package1.Cryptage;
import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
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


/**
 * Controlleur permettant de gérer l'exportation des fichiers à un autre ordinateur
 * L'ordinateur joue le client
 *@author enzo.cluzel, ahmed.bribach, robin.britelle
 */
public class CommuniquerController {

   @FXML
    private TextField adresseIPText;

    @FXML
    private Button btnComm;

    @FXML
    private TextField portID;
    
    @FXML
    private CheckBox checkNational;
    
    @FXML
    private CheckBox checkRessource;
    
    @FXML
    private Label notifEnvoi;

    private Client client = new Client(); 
   
    /**
     * Communique avec un ordinateur pour lui envoyé les documents coché
     * les documents à envoyé sont crée puis une tentative de connexion s'effectue selon le nombre de fichier à envoyé
     * Une fois envoyé l'ordinateur attend une réponse sur l'envoie de la part de l'autre ordinateur
     */
    @FXML
    public void CommuniquerAction() throws IOException{
        // TODO
        // Gerer le cas ou un fichiers de trop à était coché par rapport aux serveurs
        // Cela peut causer un crash ou une erreur
        
        notifEnvoi.setVisible(false);
        String serverIP = adresseIPText.getText(); // Adresse IP du serveur
        boolean BoucleTout = false;
        try {
            String filePath = "";
            // fichiers a envoyé selon ce qui est coché
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
            int port = Integer.parseInt(portID.getText());
            Client client = new Client();
            System.out.println(serverIP);
            client.connection(serverIP, port);
            boolean ok;
            // Envoie de la clé
            ok = client.sendA(Cryptage.codeAlice(2));
            if(ok){
                System.out.println("sa marche");
                       
                System.out.println("1000");
                String s = client.recevoirReponse();
                Cryptage.creationClefAlice(s);
                        
                System.out.println("la clé est : "+Cryptage.cle);
                        
            }
            //envoie Fichier(s)
               if(fichiers.size() != 0) { 
                // plusieurs connexion si plusieurs fichiers à envoyé 
                // on ne peut pas envoyé  plusieurs fichiers avec une connexion CHANGER SI SOLUTION
                for(int i = 0; i < fichiers.size();i++) {
                    client.connection(serverIP, port);
                
                    client.sendCSVFileToServer(fichiers.get(i));
                    if(fichiers.size() != i+1) {
                        System.out.println("ok");
                        client.closeConnection();
                    }
                }
                notifEnvoi.setVisible(true);
                try {
                    // recupére la reponse de l'autre ordinateur
                    notifEnvoi.setText(client.recevoirReponse());
                } catch (IOException ex) {
                    Logger.getLogger(CommuniquerController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NumberFormatException e) {
            notifEnvoi.setVisible(true);
            notifEnvoi.setText("Le port doit être un nombre valide.");
        }
    }
}
