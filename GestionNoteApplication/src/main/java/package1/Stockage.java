/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;

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
    
    private static final long serialVersionUID = 1L;
    public ArrayList<Competence> competences ;
    
   /** TODO comment field role (attribute, association) */
    public ArrayList<Ressource> ressources;
    
    public int semestreActuelle;
    public String parcoursActuelle;
    public  ArrayList<Evaluation> evaluations;
    
    
    private String userName;
    
   
    private static Stockage instance = new Stockage();
    
    private Stockage() {
        competences = new ArrayList<>();
        ressources = new ArrayList<>();
        evaluations = new ArrayList<>();
        userName = "user";
        parcoursActuelle = "0";
        semestreActuelle = 0;
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
    public ArrayList<Ressource> addRessources(ArrayList<Ressource> aAjouter, Competence c) {
        if(ressources.containsAll(aAjouter)) {
            return null;
        }

        ArrayList<Ressource> save = new ArrayList<>() ;
        ArrayList<Ressource> aSupprimer = new ArrayList<>() ;
        System.out.println("ok");
            for (Ressource ressou: c.getRessources()) {
                for(Ressource r: aAjouter){
                    if(r.getIdentifiant().equals(ressou.getIdentifiant())) {
                        System.out.println("non");
                        save.add(ressou);
                        aSupprimer.add(r);
                    }
                 
                }
            }
        
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
        ArrayList<Evaluation> save = new ArrayList<>() ;
        for(Evaluation eval : aAjouter) {
            if(contientDeja(eval)) {
                save.add(eval);
            } 
        }
        aAjouter.removeAll(save);
        return evaluations.addAll(aAjouter);
    }
    public boolean contientDeja(Evaluation e) {
    	for (Evaluation evaluationAComparer : evaluations) {
    		if(e.compareEvaluation(evaluationAComparer)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * Supprime les données dans stockage en enlevant les éléments dans les listes
     */
    public void supprimerDonnees() {
        competences.clear();
        ressources.clear();
        evaluations.clear();
        parcoursActuelle = "0";
        semestreActuelle = 0;
    }
    
    
    /**
     * Trouve la competence, ou ressource selon le libele de ce qui est cherché
     * @param identifiant , le libelle de ce qui est cherche
     * @return null si l'objet cherché n'est pas trouvé sinon l'instance du libellé
     */
    public ArrayList<Object> recherche(String identifiant) {
        Object cherche = null;
        char c = identifiant.charAt(0);
        ArrayList<Object> liste = new ArrayList();
        if(c != 'R' && c != 'U' && c!= 'P' && c != 'S') {
            return null;
        } 
        if(c == 'U') {
            for (Competence competence : competences) {
                if (competence.identifiant.equals(identifiant)) {
                    liste.add(competence);
                    cherche = competence;
                }
            }
        } else {
            for (Ressource ressource :ressources) {
                if (ressource.getIdentifiant().equals(identifiant)) {
                    liste.add(ressource);
                    cherche = ressource;
                }
            }
        }
        return liste;       
    }
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String name) {
        this.userName = name;
    }
    
    public int getSemestre() {
        return semestreActuelle;
    }
    
    public void setSemestre(int semestre) {
        this.semestreActuelle = semestre;
    }
    
    public String getParcour() {
        return parcoursActuelle;
    }
    
    public void setParcour(String parcour) {
        this.parcoursActuelle = parcour;
    }
    
    public ArrayList<String> getRessourcesId(){
        ArrayList<String> identifiantDejaApparue = new ArrayList<>();
        for(Competence c : competences){   
           for(Ressource r : c.getRessources()){
                if(!identifiantDejaApparue.contains(r.getIdentifiant())){
                    identifiantDejaApparue.add(r.getIdentifiant());
                }
            }
        }
        return identifiantDejaApparue;
    }
    
}