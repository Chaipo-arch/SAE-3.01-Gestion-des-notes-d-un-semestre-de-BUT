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
   

    /**
     * Communiquer avec un ordinateur pour lui envoyé les documents coché
     * les documents à envoyé sont crée puis une tentative de connexion s'effectue selon le nombre de fichier à envoyé
     * Une fois envoyé l'ordinateur attend une réponse sur l'envoie de la part de l'autre ordinateur
     * @author Robin Britelle, Enzo Cluzel, Ahmed Bribach
     */
    @FXML
    public void CommuniquerAction() throws IOException{
        
        
        
        boolean ipValider = false;
        
        boolean checkValider = false;

        String serverIP = adresseIPText.getText(); // Adresse IP du serveur
        
        
        try {
            // Utilisation de la classe IPAddressValidator pour valider l'adresse IP
            boolean isValidIP = Client.validateIP(serverIP);

            if (isValidIP) {
                
                System.out.println("L'adresse IP est valide : " + serverIP);
                ipValider = true;
            } else {
                // Geres le cas d'une IP non valide
                System.out.println("L'adresse IP n'est pas valide : " + serverIP);
                NotificationController.popUpMessage("Erreur format IP invalide","");
                System.out.println("Une Erreur s'est produite lors de la validation de l'adresse IP");
                ipValider = false;
            }
            
            
        } catch (Exception e) {
            NotificationController.popUpMessage("Erreur format IP invalide","");
            System.out.println("Une exception s'est produite lors de la validation de l'adresse IP : " + e.getMessage());
            ipValider = false;
        }
        
        if (ipValider == true) {
            try {
                String filePath = "";
                ArrayList<String> fichiers = new ArrayList();
                if(checkNational.isSelected()) {
                    ParametrageNationalPrototype.createCsv();
                    filePath = "NationalExporte.csv";
                    fichiers.add(filePath);
                    checkValider = true;
                }
                if(checkRessource.isSelected()) {
                    ParametrageRessourcePrototype.createCsv();
                    filePath = "RessourceExporte.csv";
                    fichiers.add(filePath);
                    checkValider = true;
                }
                
                
                //Definit le port Utiliser lors de la communication en reseau entre
                //le client et le server
                int port = 51263;
                
                
                Client client = new Client();
                System.out.println(serverIP);
                
                if(!checkNational.isSelected() && !checkRessource.isSelected()){
                     NotificationController.popUpMessage("Veuillez sélectionner un fichier à envoyer.","");
                }
                
                // Vérifie si les conditions pour l'envoi de fichiers sont valides
                if (checkValider == true ) {
                    
                     // Initialise la connexion avec le serveur
                    client.connection(serverIP, port);
                    boolean ok;
                    
                    
                    // Envoie un code spécifique pour établir une communication sécurisée avec le serveur
                    ok = client.sendA(Cryptage.codeAliceEtBob());
                            if(ok){
                                System.out.println("Envoie initial du chiffrement");
                                
                                
                                // Reçoit une réponse du serveur et crée une clé de chiffrement en fonction de la réponse
                                int b = Integer.parseInt(client.recevoirReponse());
                                Cryptage.creationClefBobOuAlice(b);

                                System.out.println("la clé est : "+Cryptage.cle);

                                }
                       
                        if (Client.checkServer){
                            // Envoie les fichiers sélectionnés au serveur
                            if(fichiers.size() != 0) { 
                                for(int i = 0; i < fichiers.size();i++) {
                                    
                                    // Établit une connexion et envoie chaque fichier au serveur
                                    client.connection(serverIP, port);
                                    client.sendCSVFileToServer(fichiers.get(i));
                                    
                                    // Vérifie si d'autres fichiers restent à envoyer et ferme la connexion
                                    if(fichiers.size() != i+1) {
                                        System.out.println("ok");
                                        client.closeConnection();
                                    }
                                }
                                try {
                                    notifEnvoi.setText(client.recevoirReponse());
                                } catch (IOException ex) {
                                }
                            }
                        }
                    
                }
            } catch (NumberFormatException e) {
            }
        }

    }
}