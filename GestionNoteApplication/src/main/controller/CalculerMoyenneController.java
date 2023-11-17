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
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author ahmed.bribach
 */
public class CalculerMoyenneController implements Initializable {

    @FXML
    public GridPane tableau;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Stockage stockage = Stockage.getInstance();
        
        for(int i=0 ; i< stockage.competences.size();i++){
           try{
                System.out.println(stockage.competences.get(i).calculMoyenneCompetence().getNote());
           }catch(Exception e){
              e.printStackTrace();
           }
            try{
                
                tableau.add(new Label(stockage.competences.get(i).identifiant + ""),0,0);
                tableau.add( new Label(stockage.competences.get(i).calculMoyenneCompetence().getNote() + ""),1,0);
            }catch(Exception e){
                
            }
            
           /* for(int indice = 0 ; i < stockage.competences.get(i).getRessources().size();indice++){
                tableau.addRow(0, new Label(stockage.competences.get(i).getRessources().get(indice).identifiant));
                try{
                    tableau.addRow(1, new Label(stockage.competences.get(i).getRessources().get(indice).calculMoyenne().getNote() + ""));
                }catch(Exception e){

                }
            }*/
        }
        
    }    
    
}
