package GestionNoteApplication.src.main.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.package1.Ressource;
import GestionNoteApplication.src.main.java.modele.Stockage;

public class NotesController implements Initializable{

    
    @FXML
    private Button ValiderChangementButton;

    @FXML
    private Label NomRessource;

    @FXML
    private Label Type;

    @FXML
    private GridPane GridPaneR;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private Label IdentifiantR;
    
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
        comboBox.setItems(items);
        comboBox.getSelectionModel().selectFirst();
        NomRessource.setText(stock.ressources.get(0).libelle);
        Type.setText(stock.ressources.get(0).type);
        IdentifiantR.setText(stock.ressources.get(0).identifiant);
        }
        //comboBox.setVisibleRowCount(5);
        
        //System.out.println(comboBox.getAccessibleText());
       
       
    }
    @FXML
    void ValiderAction(ActionEvent event) {
        Stockage stock = Stockage.getInstance();
          for(Ressource r : stock.ressources) {
              if(r.identifiant.equals(comboBox.getValue())) {
                   NomRessource.setText(r.libelle);
                   IdentifiantR.setText(r.identifiant);
                   Type.setText(r.type);
              }
          }
    }

}
