/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.main;

import java.util.ArrayList;

/**
 * class destiner à créer une ressource
 * @author ahmed.bribach
 */
public class Ressource {
    private String intitule;
    private double coeficient;
    private String identifiant;
    private Note note;
    private ArrayList<Evaluation> evaluations;
    
    /**
     * constructeur d'une ressources
     * @param intitule
     * @param coeficient
     * @param id
     * @param identifiant
     * @throws NoteException 
     */
    public Ressource(String intitule, double coeficient , String id,String identifiant)throws NoteException{
        this.note = new Note(0);
        this.intitule = intitule;
        this.identifiant = identifiant;
        this.coeficient = coeficient;
        evaluations = new ArrayList<>();
    }
    
    /**
     * calcul la moyenne de la ressources
     * à l'aides des evaluations de cette ressources
     * @return laMoyenne de cette ressource
     */
    private double calculMoyenne(){
        double totalCoef = 0.0;
        double calculMoyenne=0.0;
        for(int index = 0 ; index < evaluations.size(); index++){
            calculMoyenne +=evaluations.get(index).getNote()*evaluations.get(index).getCoefficient();
            totalCoef += evaluations.get(index).getCoefficient();
        }
        
        return calculMoyenne/totalCoef; // calcul la moyenne d'une ressource
    }
    
    /**
     * 
     * @return toutes les info de la ressource
     */
    private String ressourceToString(){
        return ""; // bouchon
    }
    
    /**
     * ajoute une evaluation à la ressource
     * @param evaluationAAjouter 
     */
    private boolean ajouterEvaluation(Evaluation evaluationAAjouter){
        if(evaluations.contains(evaluationAAjouter)){
            return false;
        }
        evaluations.add(evaluationAAjouter);
        return true;
    }
    
    /**
     * supprimer une évaluation à la ressources
     * @param evaluationASupprimer 
     */
    private boolean supprimerEvaluation(Evaluation evaluationASupprimer){
        if(!evaluations.contains(evaluationASupprimer)){
            return false;
        }
        evaluations.remove(evaluationASupprimer);
        if(evaluations.contains(evaluationASupprimer)){
            return false;
        }
        return true;
    }

}
