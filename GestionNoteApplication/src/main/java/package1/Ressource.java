
package GestionNoteApplication.src.main.java.package1;

import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.NoteException;
import java.io.Serializable;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * class destiner à créer une ressource
 * @author ahmed.bribach
 */
public class Ressource implements Serializable{
    private String type;
    private String libelle;
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
    public Ressource(String type, String id, String intitule, double coeff) throws NoteException{
        this.note = new Note(-1);
        this.identifiant = id;
        this.libelle = intitule;
        this.type = type;
        this.coefficient = coeff;
        evaluations = new ArrayList<>();
        
        
    }


    /**
     * calcul la moyenne de la ressources
     * à l'aides des evaluations de cette ressources
     * @return laMoyenne de cette ressource
     */
    public Note calculMoyenne() throws NoteException{
        double totalCoef = 0.0;
        double calculMoyenne=0.0;
        if(evaluations.size()==0){
            return note;
        }
        else{
            for(int index = 0 ; index < evaluations.size(); index++){
                calculMoyenne +=evaluations.get(index).getNote()*evaluations.get(index).getCoefficient();
                totalCoef += evaluations.get(index).getCoefficient();
            }
            DecimalFormat df = new DecimalFormat("#.##"); //définition d'un format XX.XX 
            String noteArrondi = df.format(calculMoyenne/totalCoef);
            note.setNote(Double.parseDouble(noteArrondi.replace(',', '.')));

            return note ; // calcul la moyenne d'une ressource
        }
        
        
    }
    
    /**
     * Affichage d'une ressource
     * @return une chaîne de caractère contennant l'affichage d'une ressource
     * 
     */
    
    public String toString(){
        String affichageEvaluation = "";
        DecimalFormat df = new DecimalFormat("#.##"); //définition d'un format XX.XX 
        String noteArrondi = df.format(note.getNote());
        noteArrondi.replace('.', ','); // remplace le '.' par ','

        for (int i=0 ; i<evaluations.size();i++){
            affichageEvaluation += evaluations.get(i).toString() + " | ";
        }
        if (note.getNote() == -1){
            return identifiant + " " + libelle + " " + coefficient + " note non renseigné " 
                   + affichageEvaluation;     
        }
        return identifiant + " " + libelle + " " + coefficient + " " + noteArrondi
                   + affichageEvaluation;
        }
    
    /**
     * ajoute une evaluation à la ressource
     * @param evaluationAAjouter 
     * @return true si l'évaluation a été correctement ajouté, false sinon
     */
    public boolean ajouterEvaluation(Evaluation evaluationAAjouter){
        if(contientDeja(evaluationAAjouter)){
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
        return coefficient;
    }


    /**
     * renvoi le type de la ressource
     * @return coefficient  
     */
    public String getType(){
        return type;
    }

    public String getIdentifiant(){
        return identifiant;
    }

    public String getLibelle(){
        return libelle;
    }

    public ArrayList<Evaluation> getEvaluation(){
        return evaluations;
    }
    public double getNote(){
        return note.getNote();
    }

    /**
    * Vérifie si les informations fournies pour une évaluation sont valides.
    * 
    * @param intitule     L'intitulé de l'évaluation.
    * @param coefficient  Le coefficient de l'évaluation.
    * @param id           L'ID de l'évaluation.
    * @param identifiant  L'identifiant de l'évaluation.
    * @return             Retourne true si les informations sont valides, sinon false.
    */
    public static boolean isValide(String intitule, double coefficient, String id, String identifiant) {
        if (coefficient <= 0 || intitule.trim().isEmpty() || id.trim().isEmpty() || identifiant.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    /**
    * Vérifie si une évaluation est déjà présente dans une liste d'évaluations.
    * 
    * @param e L'évaluation à comparer avec les autres évaluations dans la liste.
    * @return  Retourne true si l'évaluation est déjà présente, sinon false.
    */
    public boolean contientDeja(Evaluation e) {
        for (Evaluation evaluationAComparer : evaluations) {
            if (e.compareEvaluation(evaluationAComparer)) {
                return true;
            }
        }
        return false;
    }
    
}
