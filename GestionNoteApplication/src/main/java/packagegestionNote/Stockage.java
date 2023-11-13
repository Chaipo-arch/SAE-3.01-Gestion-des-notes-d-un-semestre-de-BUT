/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.main;

import java.util.ArrayList;

/**
 * Stockage < singleton > permettant de stocker les competences, ressources, 
 * evaluations lors de l'importation de paramétrage ou de l'initialisation des 
 * données afin de faciliter l'accés de ces instances lors de l'utilisation de 
 * l'application
 * @author enzo.cluzel
 */
public class Stockage {
    ArrayList<Competence> competences ;
   public ArrayList<Ressource> ressources;
    ArrayList<Evaluation> evaluations;
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
     */
    public boolean addCompetences(ArrayList<Competence> aAjouter) {
         if(competences.containsAll(aAjouter)) {
            return false;
        }
        ArrayList<Competence> save = new ArrayList<>() ;
        for(Competence competence : aAjouter) {
            
            if (competences.contains(competence)) {
                save.add(competence);
            } 
        }
        aAjouter.removeAll(save);
        return competences.addAll(aAjouter);
    
    }
    
    /**
     * Sauvegarde de nouvelles instances de l'objet ressource dans la liste associé
     * @param aAjouter , les instances à ajouter dans la liste
     */
    public boolean addRessources(ArrayList<Ressource> aAjouter) {
        if(ressources.containsAll(aAjouter)) {
            return false;
        }
        ArrayList<Ressource> save = new ArrayList<>() ;
        for(Ressource ressource : aAjouter) {
            if (ressources.contains(ressource)) {
                save.add(ressource);
            } 
        }
        aAjouter.removeAll(save);
        return ressources.addAll(aAjouter);
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
     * @param libelle , le libelle de ce qui est cherche
     * @return null si l'objet cherché n'est pas trouvé sinon l'instance du libellé
     */
    public Object recherche(String libelle) {
        Object cherche = null;
        char c = libelle.charAt(0);
        if(c != 'R' && c != 'U' ) {
            return null;
        } 
        if(c == 'R') {
            for (Ressource ressource : ressources) {
                if (ressource.libelle.equals(libelle)) {
                    cherche = ressource;
                }
            }
        } else {
            for (Competence competence : competences) {
                if (competence.libelle.equals(libelle)) {
                    cherche = competence;
                }
            }
        }
        return cherche;
                
        
    }
            
    
}
