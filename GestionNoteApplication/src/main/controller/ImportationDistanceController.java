package GestionNoteApplication.src.main.controller;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
import GestionNoteApplication.src.main.java.modele.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.modele.Server;
import GestionNoteApplication.src.main.java.modele.Stockage;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;
import java.io.File;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ImportationDistanceController implements Initializable{


    @FXML
    private ToggleGroup choix;
    @FXML
    private Button annulerButton;

    @FXML
    private Label connexion;

    @FXML
    private Label adresseIP;



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
        
        
            
            connexion.setVisible(true);
            annulerButton.setVisible(true);
             t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Server.createServer();
                boolean reussi = false;
                while(!Thread.interrupted() && !reussi) {
                    reussi = Server.receiveCSVFile();
                    i++;
                    System.out.println(i);
                }
                annulerAttenteAction(null);
                //System.out.println(Stockage.getInstance());
                try {
                    ParametrageRessourcePrototype pRP = new ParametrageRessourcePrototype(new File("RessourceExporte.csv"));
                    pRP.parse();
                    System.out.println(Stockage.getInstance());
                    
                    
                            
                            /*try {
                            
                            ParametrageNationalPrototype pNP = new ParametrageNationalPrototype(new File("NationalExporte.csv"));
                            pNP.parse();
                            
                            
                            } catch (IOException ex) {
                            Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (MauvaisFormatFichierException ex) {
                            Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (EvaluationException ex) {
                            Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (NoteException ex) {
                            Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            */
                } catch (IOException ex) {
                    Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MauvaisFormatFichierException ex) {
                    Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EvaluationException ex) {
                    Logger.getLogger(ImportationDistanceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
            });
            

            t1.start();
            
             System.out.println(t1.getName());
             
            
      
     
            System.out.println("eza");
        
       
    }

    @FXML
    void annulerAttenteAction(ActionEvent event) {
        connexion.setVisible(false);
        annulerButton.setVisible(false);
        Server.closeServer();
        t1.interrupt();
        
        
    }

    

}
