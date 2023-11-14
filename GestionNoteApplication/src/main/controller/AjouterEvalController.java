package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.modele.Ressource;
import GestionNoteApplication.src.main.java.modele.Stockage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AjouterEvalController implements Initializable{

    @FXML
    private TextField dateText;

    @FXML
    private ComboBox<String> comboBoxR;

    @FXML
    private TextField CoeffText;

    @FXML
    private TextField TypeEvalText;

    @FXML
    private AnchorPane ajoutReussiNotif;

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO faire fonctionner sans commentaire
        if(Stockage.getInstance().ressources.size() != 0) {
            
        
        Stockage stock = Stockage.getInstance();
        ObservableList<String> items =  FXCollections.observableArrayList();
        for(Ressource r : stock.ressources) {
            items.add(r.identifiant);
            
            //comboBox.setAccessibleText(r.identifiant);
            
            //comboBox.setValue(r.identifiant);
        }
        comboBoxR.setItems(items);
        comboBoxR.getSelectionModel().selectFirst();
        
        }
        //comboBox.setVisibleRowCount(5);
        
        //System.out.println(comboBox.getAccessibleText());
       
       
    }
    @FXML
    void ajouterEvalAction(ActionEvent event) {
        
    }

}
