package GestionNoteApplication.src.main.controller;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.package1.Cryptage;
import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.Server;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ImportationDistanceController implements Initializable{

    @FXML 
    private CheckBox nationalToggle;
    
    @FXML
    private Button annulerButton;

    @FXML
    private Label connexion;

    @FXML
    private Label adresseIP;

    @FXML
    private  CheckBox ressourceToggle;

    /**
     * Affiche l'addresse ip de l'ordinateur 
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            
        }
       adresseIP.setText("Votre adresse ip : " + ip.getHostAddress());
    }
    private int i = 0;
   
    /**
     * Cree une fonction que le thread va utiliser, puis lance le thread et affiche les labels de connexion
     * Le thread ajoute des fichiers qui vont être envoyé selon ce qui est coché, puis le serveur est crée 
     * et connecte un autre ordinateur selon le nombre de fichiers voulu, et recoit les fichers demandés
     * une fois reçus, les fichiers sont lues et crée les instances selon leur contenu
     * une reponse correspondante aux fichiers lue et envoyé a l'ordinateur qui a communiquer
     * @param event click sur le button
     */
    @FXML
    void importationDistanceButton(ActionEvent event) {
        if(ressourceToggle.isSelected() || nationalToggle.isSelected()) {
            connexion.setVisible(true);
            annulerButton.setVisible(true);
            t1 = new Thread(new Runnable() {
            @Override
           public void run() {
                try {
                    ArrayList<String> fichier = new ArrayList();
                    if(nationalToggle.isSelected()) {
                        System.out.println("ok1");
                        fichier.add("NationalExporte.csv");
                    }
                    if(ressourceToggle.isSelected()) {
                        System.out.println("ok2");
                        fichier.add("RessourceExporte.csv");
                    }
                    Server.createServer();
                    String codeBob = Cryptage.codeBob(2);
                    //System.out.println(fichier.size());
                    boolean reussi = false;
                    while(!Thread.interrupted() && !reussi) {
                        Server.connexion();
                        boolean ok;
                        ok =Server.cle();
                        
                        Server.reponse(codeBob);
                        if(ok){
                            
                            System.out.println("sa marche");
                            
                        }
                        Server.closeClient();
                        boolean correct = true;
                    
                        for(int i = 0; i < fichier.size() && correct; i++ ) {
                            correct = Server.connexion();
                            if(correct) {
                                System.out.println(fichier.get(i));
                                reussi = Server.receiveCSVFile(fichier.get(i));
                                if(fichier.size() != i+1) {
                                    Server.closeClient();
                                    
                                } else if(reussi){
                                    System.out.println("Fichier " + fichier.get(i) + " reçu");
                                } else {
                                    System.out.println("Fichiers non reçu");
                                }
                            }
                        }
                        if(!correct) {
                            
                            System.out.println("Probleme de connexion");
                            Server.closeClient();
                        }
                    }
                    System.out.println("Envoie en cours");
                    String message = "CSV reçu";
                    try {
                        if(nationalToggle.isSelected()) {
                            ParametrageNationalPrototype pNP = new ParametrageNationalPrototype(new File("NationalExporte.csv"));
                            pNP.parse();
                        }
                        if(ressourceToggle.isSelected()) {
                            ParametrageRessourcePrototype pRP = new ParametrageRessourcePrototype(new File("RessourceExporte.csv"));
                            pRP.parse();
                        }
                        
                    } catch (IOException|MauvaisFormatFichierException|EvaluationException|NoteException ex) {
                        System.out.println(ex.getMessage());
                        message = ex.getMessage();
                    }
                    //new file().delete();
                    System.out.println(message);
                    Server.reponse(message);
                    Server.closeClient();
                    annulerAttenteAction(null);
                } catch (IOException ex) {
                    
                }
            }
            });
            t1.start();
        }
       
    }

    /**
     * Arrete le thread, et le serveur 
     * @param event 
     */
    @FXML
    void annulerAttenteAction(ActionEvent event) {
        connexion.setVisible(false);
        annulerButton.setVisible(false);
        Server.closeServer();
        t1.interrupt();
        
        
        
    }

    

}
