/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Classe de l'objet evaluation permettant l'insertion, l'ajout de note
 * la modification de modalité et l'affichage d'une note
 * @author alexandre.brouzes
 */
public class Evaluation implements Serializable{
    
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
    public Evaluation(String ressourceEvaluation, Note noteEvaluation
                      ,String typeEvaluation,double coefficientEvaluation
                      ,String dateEvaluation )throws EvaluationException{
        if(!isCoefficient(coefficientEvaluation)){
            throw new EvaluationException("le coefficient doit être >0 & <= 100");  
        }
        if(ressourceEvaluation.equals("")|| ressourceEvaluation == null
           || typeEvaluation.equals("")|| typeEvaluation == null){
            throw new EvaluationException("les champs de ressource ou du type de l'évaluation"
                    + "sont incomplet");
        }
        this.ressource = ressourceEvaluation;
        this.note = noteEvaluation;
        this.type = typeEvaluation;
        this.coefficient = coefficientEvaluation;
        this.date = dateEvaluation;
    }
    public Evaluation(String type,String dateEvaluation
                      ,double coefficientEvaluation
                      )throws EvaluationException{
        if(!isCoefficient(coefficientEvaluation)){
            throw new EvaluationException("le coefficient doit être >0 & <= 100");  
        }
        this.type = type;
        this.coefficient = coefficientEvaluation;
        this.date = dateEvaluation;
        this.note = null;
    }
    /**
     * Ajoute une note à l'intance de l'évaluation
     * @return true si l'ajout a bien été effectué(la nouvelle note inséré 
     * n'écrase pas une ancienne note), false sinon
     */
    public boolean ajouterNote(Note noteAAjouter){
        if(note == null){
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
    
    public String toString(){
        if (note == null){
            return ressource + " " + type + " " + date 
                             + coefficient + " note non renseignée";
        }
        DecimalFormat df = new DecimalFormat("#.##"); //définition d'un format XX.XX 
        String noteArrondi = df.format(note.getNote());
    
        noteArrondi.replace('.', ','); // remplace le '.' par ','
        return ressource + " " + type + " " + date 
                         + coefficient + noteArrondi;   
    }
    /**
     * Modifie tous les attributs d'un objet évaluation
     * @return true si la modification à été effectué, false sinon
     */
    public boolean modifierModalite(String nouveauLibelle,Note nouvelleNote,
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
    //getter note
    public double getNote(){
        return note.getNote();
    }
    //getter coefficient
    public double getCoefficient(){
        return coefficient;
    }
    /**
     * vérification de la cohérence d'un cohéficient (>0 <100)
     * @return true si le coefficient est correct, false sinon
     */
    public static boolean isCoefficient(double coefficientATester){
        return coefficientATester <= 100 && coefficientATester > 0;
    }

    public String getType() {
       return this.type;
    }

    public String getDate() {
        return this.date;
    }
}
