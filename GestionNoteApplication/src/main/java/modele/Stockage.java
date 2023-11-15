/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.modele;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Ressource;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Stockage < singleton > permettant de stocker les competences, ressources, 
 * evaluations lors de l'importation de paramétrage ou de l'initialisation des 
 * données afin de faciliter l'accés de ces instances lors de l'utilisation de 
 * l'application
 * @author enzo.cluzel
 */
public class Stockage implements Serializable {
    public ArrayList<Competence> competences ;
   /** TODO comment field role (attribute, association) */
    public ArrayList<Ressource> ressources;
    public  ArrayList<Evaluation> evaluations;
    private static Stockage instance = new Stockage();
    
    private Stockage() {
        competences = new ArrayList<>();
        ressources = new ArrayList<>();
        evaluations = new ArrayList<>();
    }
    
    /**
     * Permet d'obtenir l'instance crée au début de l'application 
     * @return l'instance de stockage
     */
    public static Stockage getInstance() {
        return instance;
    }
    
    /**
     * Sauvegarde de nouvelles instances de l'objet competences dans la liste associé
     * @param aAjouter , les instances à ajouter dans la liste
     * @return a
     */
    public boolean addCompetences(ArrayList<Competence> aAjouter) {
         if(competences.containsAll(aAjouter)) {
            return false;
        }
        ArrayList<Competence> aSupprimer = new ArrayList<>() ;
        for(Competence competence : aAjouter) {
            for(Competence c: competences) {
                if (competence.identifiant.equals(c.identifiant)) {
                    aSupprimer.add(competence);
                } 
            }
             
        }
        aAjouter.removeAll(aSupprimer);
        return competences.addAll(aAjouter);
    
    }
    
    /**
     * Sauvegarde de nouvelles instances de l'objet ressource dans la liste associé
     * @param aAjouter , les instances à ajouter dans la liste
     * @return l'array list aAjouter mais avec les ressources qui sont déjà dans le Stockage
     */
    public ArrayList<Ressource> addRessources(ArrayList<Ressource> aAjouter) {
        if(ressources.containsAll(aAjouter)) {
            return null;
        }
       
        ArrayList<Ressource> save = new ArrayList<>() ;
        ArrayList<Ressource> aSupprimer = new ArrayList<>() ;
        //for(Competence comps: competences) {
            for (Ressource ressou: ressources) {
                for(Ressource r: aAjouter)
                if(r.identifiant.equals(ressou.identifiant)) {
                    save.add(ressou);
                    aSupprimer.add(r);
                }
                 
            }
        //}
        aAjouter.removeAll(aSupprimer);
        
        ressources.addAll(aAjouter);
       aAjouter.addAll(save);
        return aAjouter;
    }
    
   
    /**
     * Sauvegarde de nouvelles instances de l'objet évaluation dans la liste associé
     * @param aAjouter , les instances à ajouter dans la liste
     * @return barnabe
     */
    public boolean addEvaluations(ArrayList<Evaluation> aAjouter) {
        if(evaluations.containsAll(aAjouter)) {
            return false;
        }
         ArrayList<Evaluation> save = new ArrayList<>() ;
        for(Evaluation eval : aAjouter) {
            if (evaluations.contains(eval)) {
                save.add(eval);
            } 
        }
        aAjouter.removeAll(save);
        return evaluations.addAll(aAjouter);
    }
    
    
    /**
     * Supprime les données dans stockage en enlevant les éléments dans les listes
     */
    public void supprimerDonnees() {
        competences.clear();
        ressources.clear();
        evaluations.clear();
    }
    
    
    /**
     * Trouve la competence, ou ressource selon le libele de ce qui est cherché
     * @param identifiant , le libelle de ce qui est cherche
     * @return null si l'objet cherché n'est pas trouvé sinon l'instance du libellé
     */
    public Object recherche(String identifiant) {
        Object cherche = null;
        char c = identifiant.charAt(0);
        if(c != 'R' && c != 'U' && c!= 'P' && c != 'S') {
            return null;
        } 
        if(c == 'U') {
            for (Competence competence : competences) {
                if (competence.identifiant.equals(identifiant)) {
                    cherche = competence;
                }
            }
        } else {
            for (Ressource ressource :ressources) {
                if (ressource.identifiant.equals(identifiant)) {
                    cherche = ressource;
                }
            }
        }
        return cherche;
                
        
    }
            
    
}