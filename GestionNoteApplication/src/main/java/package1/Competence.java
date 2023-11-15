/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Classe de l'objet competence permettant de calculer la moyenne et d'afficher une compétence
 * ainsi que l'ajout ou la supression d'une ressource
 * @author alexandre.brouzes
 */
public class Competence {

    public String libelle;
    public String identifiant;
    private Note note;
    public ArrayList<Ressource> ressources;

    /**
     * constructeur de l'objet competence
     * @param libelle
     */
    public Competence(String id) {
        this.identifiant = id;
        this.note = null;
        ressources = new ArrayList<>();
    }

    /**
     * calcule la moyenne des ressources liées à cette compétence
     * @return moyenne double contenant la moyenne de la ressource
     */
    public Note calculMoyenneCompetence() throws NoteException{
        double totalCoef = 0.0;
        double calculMoyenne = 0.0;
        
        if(ressources.size() ==0){
            return note;
        }
        
        for(int index = 0 ; index < ressources.size(); index++){
            calculMoyenne += ressources.get(index).calculMoyenne().getNote() 
                             * ressources.get(index).getCoefficient();               
            totalCoef += ressources.get(index).getCoefficient();
        }
        
        DecimalFormat df = new DecimalFormat("#.##"); //définition d'un format XX.XX 
        String noteArrondi = df.format(calculMoyenne/totalCoef);
        note.setNote(Double.parseDouble(noteArrondi.replace(',', '.')));

        return note ; // calcul la moyenne d'une ressource
       
    }

    /**
     * Affiche l'instance de l'competence
     * @return la chaîne de caractère contenant la description de l'évaluation
     */
    public String competenceToString() {

        DecimalFormat df = new DecimalFormat("#.##"); //définition d'un format XX.XX 
        String noteArrondi = df.format(note);
        noteArrondi.replace('.', ','); // remplace le '.' par ','
        String ensembleRessource = "";
        for (Ressource ressourceAAfficher : ressources){
            ensembleRessource += ressourceAAfficher.toString();
        }
        if (note == null){
            return libelle +" note non renseignée " + ensembleRessource;
        }
        return libelle +" "+ noteArrondi + ensembleRessource;
    }

    /**
     * ajoute une ressources à la compétence
     * @param ressourceAAjouter ressource à ajouter
     * @return true si l'ajout a pu être effectué, false sinon
     */
    public boolean ajouterRessource(Ressource resourceAAjouter) {
        if(ressources.contains(resourceAAjouter) || resourceAAjouter == null){
            return false;
        }
        ressources.add(resourceAAjouter);
        return true;
    }

    /**
     * supprimer la ressources d'une compétence
     * @param ressourceASupprimer ressource à supprimer
     * @return true si la suppression a pu être effectué, false sinon
     */
    public boolean supprimerRessource(Ressource ressourceASupprimer) {
        if(!ressources.contains(ressourceASupprimer)){
            return false;
        }
        ressources.remove(ressourceASupprimer);
        if(ressources.contains(ressourceASupprimer)){
            return false;
        }
        return true;
    }
    
    public ArrayList<Ressource> getRessources(){
        return  ressources;
    }
}
