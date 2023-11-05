/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.main;
import java.text.DecimalFormat;

/**
 * Classe de l'objet competence permettant de calculer la moyenne et d'afficher une compétence
 * ainsi que l'ajout ou la supression d'une ressource
 * @author alexandre.brouzes
 */
public class Competence {

    private String libelle;
    private Note note;

    /**
     * constructeur de l'objet competence
     * @param libelle
     */
    public Competence(String libelle) {
        this.libelle = libelle;
        this.note = null;
        ressources = new Arralist<>();
    }

    /**
     * calcule la moyenne des ressources liées à cette compétence
     * @return moyenne double contenant la moyenne de la ressource
     */
    public double calculMoyenneCompetence() {
        double totalCoef = 0.0;
        double calculMoyenne = 0.0;
        for(int index = 0 ; index < ressources.size(); index++){
            calculMoyenne += ressources.get(index).getNote() * ressources.get(index).getCoefficient();
            totalCoef += ressources.get(index).getCoefficient();
        }
        
        return note = calculMoyenne/totalCoef; // calcul la moyenne d'une competence
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
        for (ressourceAAfficher : ressources){
            ensembleRessource += ressourceAAfficher.ressourceToString;
        }
        if (note == null){
            return libelle +" note non renseignée " + ensembleRessource;
        }
        return libelle +" "+ note + ensembleRessource;
    }

    /**
     * ajoute une ressources à la compétence
     * @param ressourceAAjouter ressource à ajouter
     * @return true si l'ajout a pu être effectué, false sinon
     */
    public boolean ajouterRessource(Ressource resourceAAjouter) {
        if(ressources.contains(resourceAAjouter || resourceAAjouter == null)){
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
}
