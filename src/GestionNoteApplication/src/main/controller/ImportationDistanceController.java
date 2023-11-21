package GestionNoteApplication.src.main.controller;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.modele.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.modele.Server;
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
     * Initializes the controller class.
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
   
    
    @FXML
    void importationDistanceButton(ActionEvent event) {
        
        if(ressourceToggle.isSelected() || nationalToggle.isSelected()) {
            connexion.setVisible(true);
            annulerButton.setVisible(true);
             t1 = new Thread(new Runnable() {
            @Override
           public void run() {
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
                //System.out.println(fichier.size());
                boolean reussi = false;
                while(!Thread.interrupted() && !reussi) {
                    if(Server.connexion()) {
                        boolean correct = true;
                        for(int i = 0; i < fichier.size() && correct; i++ ) {
                            System.out.println(fichier.get(i));
                            reussi = Server.receiveCSVFile(fichier.get(i));
                            correct = reussi;
                        }
                    }
                    
                    
                }
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
                try {
                    Server.reponse(message);
                } catch (IOException ex) {
                    System.out.println("La réponse n'a pas pu être envoyé");
                }
                Server.closeClient();
                annulerAttenteAction(null);
            }
            });
            t1.start();
        }
    }

    @FXML
    void annulerAttenteAction(ActionEvent event) {
        connexion.setVisible(false);
        annulerButton.setVisible(false);
        Server.closeServer();
        t1.interrupt();
    }
}
