package GestionNoteApplication.src.main.controller;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import static GestionNoteApplication.src.main.controller.GEstionNoteApp.t1;
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
    private RadioButton nationalToggle;

    @FXML
    private ToggleGroup choix;
    @FXML
    private Button annulerButton;

    @FXML
    private Label connexion;

    @FXML
    private Label adresseIP;

    @FXML
    private RadioButton ressourceToggle;

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
            AccueilController a = new AccueilController();
            
            connexion.setVisible(true);
            annulerButton.setVisible(true);
             t1 = new Thread(new Runnable() {
            @Override
            public void run() {
              
                
                while(!Thread.interrupted()) {
                    i++;
                    System.out.println(i);
                }

            }
            });
            
           
            t1.start();
            
             System.out.println(t1.getName());
             
            
      
     
            System.out.println("eza");
        }
       
    }

    @FXML
    void annulerAttenteAction(ActionEvent event) {
        connexion.setVisible(false);
        annulerButton.setVisible(false);
        t1.interrupt();
        
        
    }

    

}
