/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.main;

import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * class destiner à créer une ressource
 * @author ahmed.bribach
 */
public class Ressource {
    private String intitule;
    private double coefficient;
    private String identifiant;
    private Note note;
    private ArrayList<Evaluation> evaluations;
    
    /**
     * constructeur d'une ressources
     * @param intitule
     * @param coefficient
     * @param id
     * @param identifiant
     * @throws NoteException 
     */
    public Ressource(String intitule, double coefficient , String id,String identifiant)throws NoteException{
        if(coefficient<=0 || intitule.isEmpty() || id.isEmpty() || identifiant.isEmpty()){
            throw new IllegalArgumentException();
        }
        this.note = new Note(-1);
        this.intitule = intitule;
        this.identifiant = identifiant;
        this.coefficient = coefficient;
        evaluations = new ArrayList<>();
        
        
    }
    
    /**
     * calcul la moyenne de la ressources
     * à l'aides des evaluations de cette ressources
     * @return laMoyenne de cette ressource
     */
    public double calculMoyenne(){
        double totalCoef = 0.0;
        double calculMoyenne=0.0;
        for(int index = 0 ; index < evaluations.size(); index++){
            calculMoyenne +=evaluations.get(index).getNote()*evaluations.get(index).getCoefficient();
            totalCoef += evaluations.get(index).getCoefficient();
        }
        
        return note = new Note(calculMoyenne/totalCoef); // calcul la moyenne d'une ressource
    }
    
    /**
     * Affichage d'une ressource
     * @return une chaîne de caractère contennant l'affichage d'une ressource
     */
    private String ressourceToString(){

        String affichageEvaluation = "";
        DecimalFormat df = new DecimalFormat("#.##"); //définition d'un format XX.XX 
        String noteArrondi = df.format(note);
        noteArrondi.replace('.', ','); // remplace le '.' par ','

        for (evaluationAAfficher : evaluations){
            affichageEvaluation += evaluationAAfficher.toString();
        }
        if (note.getNote() == -1){
            return identifiant + " " + intitule + " " + coefficient + " note non renseigné " 
                   + affichageEvaluation;     
        }
        return identifiant + " " + intitule + " " + coefficient + " " + noteArrondi
                   + affichageEvaluation;
    }
    
    /**
     * ajoute une evaluation à la ressource
     * @param evaluationAAjouter 
     * @return true si l'évaluation a été correctement ajouté, false sinon
     */
    public boolean ajouterEvaluation(Evaluation evaluationAAjouter){
        if(evaluations.contains(evaluationAAjouter)){
            return false;
        }
        evaluations.add(evaluationAAjouter);
        return true;
    }
    
    /**
     * supprimer une évaluation à la ressources
     * @param evaluationASupprimer 
     * @return true si l'évaluation a été correctement supprimé, false sinon
     */
    public boolean supprimerEvaluation(Evaluation evaluationASupprimer){
        if(!evaluations.contains(evaluationASupprimer)){
            return false;
        }
        evaluations.remove(evaluationASupprimer);
        if(evaluations.contains(evaluationASupprimer)){
            return false;
        }
        return true;
    }

    /**
     * renvoi le coefficient de la ressource
     * @return coefficient  
     */
    public double getCoefficient(){
        return coefficient
    }
}