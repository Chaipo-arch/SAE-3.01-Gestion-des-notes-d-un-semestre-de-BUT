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
 * Contrôleur FXML pour la page de calcul de moyenne.
 * Gère les fonctionnalités de calcul de moyenne pour les compétences et les ressources.
 * Implémente Initializable pour l'initialisation du contrôleur.
 * @author ahmed Bribach
 */
public class CalculerMoyenneController implements Initializable {

    private boolean AffichageNotificationErreur = false;
    
    @FXML
    private GridPane tableau;

    /**
     * Initialise la classe du contrôleur.
     * Charge et affiche les moyennes des compétences et des ressources.
     * Affiche une notification si des notes sont manquantes pour calculer les moyennes.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Stockage stockage = Stockage.getInstance();
        
        tableau.getRowConstraints().get(0).setMinHeight(40.0);
        
        // Affichage des moyennes des compétences
        for (int i = 0; i < stockage.competences.size(); i++) {
            try {
                // Ajout des identifiants des compétences
                tableau.add(new Label(stockage.competences.get(i).identifiant + ""), 0, tableau.getRowConstraints().size());
                String noteValeur;
                
                // Vérification des moyennes et gestion d'erreurs s'il en existe
                if(stockage.competences.get(i).calculMoyenneCompetence().getNote() == -1){
                    noteValeur = "";
                    AffichageNotificationErreur = true;
                } else {
                    noteValeur = stockage.competences.get(i).calculMoyenneCompetence().getNote()+"";
                }
                tableau.add(new Label(noteValeur + ""), 1, tableau.getRowConstraints().size());
                
                // Ajout de contraintes de hauteur à la grille
                RowConstraints row = new RowConstraints();
                row.setMinHeight(40.0);
                tableau.getRowConstraints().add(row);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Affichage des moyennes des ressources pour chaque compétence
            for (int indice = 0; indice < stockage.competences.get(i).getRessources().size(); indice++) {
                try {
                    // Ajout des identifiants des ressources
                    tableau.add(new Label("Ressource : " + stockage.competences.get(i).getRessources().get(indice).getIdentifiant()), 0, tableau.getRowConstraints().size());
                    
                    // Vérification des moyennes et gestion d'erreurs s'il en existe
                    if (stockage.competences.get(i).getRessources().get(indice).calculMoyenne().getNote() < 0) {
                        tableau.add(new Label(""), 1, tableau.getRowConstraints().size());
                    } else {
                        tableau.add(new Label(stockage.competences.get(i).getRessources().get(indice).calculMoyenne().getNote() + ""), 1, tableau.getRowConstraints().size());
                    }

                    // Ajout de contraintes de hauteur à la grille
                    RowConstraints row = new RowConstraints();
                    row.setMinHeight(40.0);
                    tableau.getRowConstraints().add(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        // Affichage d'une notification si des notes sont manquantes
        if (AffichageNotificationErreur==true){
            NotificationController.popUpMessage("Attention, des notes sont manquantes. Toutes les moyennes de vos compétences ne pourront pas être calculées","");
        }
        
        // Calcul de la hauteur totale de la grille pour l'affichage correct
        tableau.setLayoutY(0);
        tableau.getRowConstraints().size();
        double totalHeight = tableau.getRowConstraints().stream()
                .mapToDouble(rowConstraints -> rowConstraints.getMinHeight()).sum();
        
        tableau.setPrefHeight(totalHeight);
    }
}
