/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.main;

/**
 * Classe de l'objet evaluation permettant l'insertion, l'ajout de note
 * la modification de modalité et l'affichage d'une note
 * @author alexandre.brouzes
 */
public class Evaluation implements OutilsEvaluation{
    
    /**  
     * Nom de la matière de l'évaluation
     */
    private String ressource = "";
    /**  
     * Note de l'évaluation 
     */
    private Note note;
    /**  
     * type de contrôle entre devoir sur table, Qcm, tp noté
     */
    private String type = "";
    
    /**
     * Coefficient de la note de l'évaluation (ne peut pas être négatif
     */
    private double coefficient = 0.0;
    
    /**
     * Date de l'évaluation sous forme de chaîne de caractère car 
     * approximation de la date possible
     */
    private String date = "";
    
    // constructor Evaluation
    public Evaluation(String ressourceEvaluation, Note noteEvaluation, String typeEvaluation,double coefficientEvaluation
                                           ,String dateEvaluation ){
        this.ressource = ressourceEvaluation;
        this.note = null;
        this.type = typeEvaluation;
        this.coefficient = coefficientEvaluation;
        this.date = dateEvaluation;
    }
    /**
     * Ajoute une note à l'intance de l'évaluation
     * @return true si l'ajout a bien été effectué, false sinon
     */
    public boolean ajouterNote(Note noteAAjouter){ 
        if (note == null){
            note = noteAAjouter;
            return true;
        }
        return false;
    }
    
    
    /**
     * Affiche l'l'instance de l'évaluation au format 
     * libelle type date coefficient note
     * @return la chaîne de caractère contenant la description de l'évaluation
     */
    
    public String toSring(){
        if (this.note == null){
            return ressource + " " + type + " " + date 
                            + coefficient + " note non renseignée";
        }
        return ressource + " " + type + " " + date 
                            + coefficient + note;   
    }
    /**
     * Modifie tous les attributs d'un objet évaluation
     * @return true si la modification à été effectué, false sinon
     */
    private boolean modifierModalite(String nouveauLibelle,Note nouvelleNote,
                                  String nouveauType, double nouveauCoefficient,
                                  String nouvelleDate){
        ressource = nouveauLibelle;
        note = nouvelleNote;
        type = nouveauType;
        coefficient = nouveauCoefficient;
        date = nouvelleDate;
        
        if (ressource == nouveauLibelle && note == nouvelleNote
            && type == nouveauType    
            && coefficient == nouveauCoefficient
            && date == nouvelleDate){
            return true;
        }
        return false;  
    }    
    public double getNote(){
        return note.getNote();
    }
    public double getCoefficient(){
        return coefficient;
    }
}
