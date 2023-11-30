/*
 * CalculerMoyenneController.java  
 */
package GestionNoteApplication.src.main.controller;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Stockage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.ScrollPane;
/**
 * 
 * @author ahmed.bribach
 */
public class CalculerMoyenneController implements Initializable {

    @FXML
    private GridPane tableau;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Stockage stockage = Stockage.getInstance();
        tableau.getRowConstraints().get(0).setMinHeight(40.0);
        for (int i = 0; i < stockage.competences.size(); i++) {
            try {
                tableau.add(new Label(stockage.competences.get(i).identifiant + ""), 0, tableau.getRowConstraints().size());
                double noteValeur;
                
                if(stockage.competences.get(i).calculMoyenneCompetence().getNote() == -1){
                    noteValeur = 0;
                }else {
                    noteValeur = stockage.competences.get(i).calculMoyenneCompetence().getNote();
                }
                tableau.add(new Label(noteValeur + ""), 1, tableau.getRowConstraints().size());
                System.out.println(tableau.getRowConstraints().size());
                RowConstraints row = new RowConstraints();
                row.setMinHeight(40.0);
                tableau.getRowConstraints().add(row);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            for (int indice = 0; indice < stockage.competences.get(i).getRessources().size(); indice++) {

                try {
                    tableau.add(new Label("Ressource : " + stockage.competences.get(i).getRessources().get(indice).getIdentifiant()), 0, tableau.getRowConstraints().size());
                    if (stockage.competences.get(i).getRessources().get(indice).calculMoyenne().getNote() < 0) {
                        tableau.add(new Label(""), 1, tableau.getRowConstraints().size());
                    } else {
                        tableau.add(new Label(stockage.competences.get(i).getRessources().get(indice).calculMoyenne().getNote() + ""), 1, tableau.getRowConstraints().size());
                    }

                    System.out.println(tableau.getRowConstraints().size());
                    RowConstraints row = new RowConstraints();
                    row.setMinHeight(40.0);
                    tableau.getRowConstraints().add(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        tableau.setLayoutY(0);
        tableau.getRowConstraints().size();
        double totalHeight = tableau.getRowConstraints().stream()
                .mapToDouble(rowConstraints -> rowConstraints.getMinHeight()).sum();
        
        tableau.setPrefHeight(totalHeight);
    }
}