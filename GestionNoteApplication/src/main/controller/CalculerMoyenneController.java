/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.modele.Stockage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * FXML Controller class
 *
 * @author ahmed.bribach
 */
public class CalculerMoyenneController implements Initializable {

    @FXML
    private GridPane tableau;
    
    @FXML
    private ScrollPane scroll;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Stockage stockage = Stockage.getInstance();
        
      
        
        for(int i=0 ; i< stockage.competences.size();i++){
            try{
             
                //tableau.add(new Label(stockage.competences.get(i).identifiant + ""),0,0);
                tableau.add(new Label(stockage.competences.get(i).identifiant + ""),0,tableau.getRowConstraints().size());
                
                tableau.add(new Label(stockage.competences.get(i).calculMoyenneCompetence().getNote() + ""),1,tableau.getRowConstraints().size());
                System.out.println(tableau.getRowConstraints().size());
                RowConstraints row = new RowConstraints();
                row.setMinHeight(40.0);
                tableau.getRowConstraints().add(row);
            }catch(Exception e){
                
                System.out.println(e.getMessage());
            }
            
            for(int indice = 0 ; indice < stockage.competences.get(i).getRessources().size();indice++){
                
                try{
                    tableau.add(new Label("UE :" +stockage.competences.get(i).identifiant + "\nRessource" + stockage.competences.get(i).getRessources().get(indice).identifiant),0,tableau.getRowConstraints().size());
                    if(stockage.competences.get(i).getRessources().get(indice).calculMoyenne().getNote()<0){
                         tableau.add(new Label(""),1,tableau.getRowConstraints().size());
                    }else{
                         tableau.add(new Label(stockage.competences.get(i).getRessources().get(indice).calculMoyenne().getNote() + ""),1,tableau.getRowConstraints().size());
                    }
                    
                    System.out.println(tableau.getRowConstraints().size());
                    RowConstraints row = new RowConstraints();
                    row.setMinHeight(30.0);
                    tableau.getRowConstraints().add(row);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
        }
        /*ScrollPane scroll = new ScrollPane();
        scroll.setContent(tableau);
        scroll.autosize();
       
        scroll.setVisible(true);
        */
        tableau.heightProperty().add(5000);
        scroll.setContent(tableau);
        
    }    
    
}
