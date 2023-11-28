/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.tests;


import GestionNoteApplication.src.main.java.package1.Stockage;
import GestionNoteApplication.src.main.java.package1.Ressource;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author enzo.cluzel
 */
public class TestStockage{
    
    static Stockage stockage;
    static ArrayList<Competence> listeCompe = new ArrayList<>();
    static ArrayList<Ressource> listeRessour = new ArrayList<>();
    static ArrayList<Evaluation> listeEvaluation = new ArrayList<>();
    
    static Competence [] tabCompe ;
    static Ressource [] tabRessource ;
    static Evaluation [] tabEval ;
    
    public static void jeuxDeDonnees()  {
        try {
            stockage = Stockage.getInstance();
            
            tabCompe = new Competence[2];
            tabCompe[0] = new Competence("U2.1","c1");
            tabCompe[1] = new Competence("U2.2","c2");
            tabRessource = new Ressource[2];
            tabRessource[0] = new Ressource("Sae", "id", "Programmation Général", 10);
            tabRessource[1] = new Ressource("Portefolio", "id", "Mathématique", 20);
            tabEval = new Evaluation[1];
            tabEval[0] = new Evaluation("QCM","25/01/2023",1);
            Competence competence = new Competence("U2.1","c3");
            listeCompe.add(competence);
        } catch (EvaluationException|NoteException ex) {
            System.out.println("Une evaluation ou une note est incorrecte");
        }
    }
    public static void testAdd(){
        int nombreErreur = 0;
        System.out.println("## TestStockageAdd ## \n ###########################\n ");
        
        for (Competence competence: tabCompe) {
            listeCompe.add(competence);
            System.out.print("cas competence :" + competence.libelle);
            if(stockage.addCompetences(listeCompe)){
                System.out.println(": cas valide");
            }else{
                System.out.println(": cas invalide");
                nombreErreur++;
            }
        }
        for (Competence competence: tabCompe) {
            listeCompe.add(competence);
            System.out.print("cas competence :" + competence.libelle);
            if(stockage.addCompetences(listeCompe)){
                System.out.println(": cas invalide");
                nombreErreur++;
            }else{
                System.out.println(": cas valide");
                
            }
        }
        listeCompe.get(0).getRessources().clear();
        stockage.ressources.clear();
        for (Ressource ressource : tabRessource) {
            System.out.print("cas ressource :" + ressource.getLibelle());
            listeRessour.add(ressource);
            listeCompe.get(0).ajouterRessource(ressource);
            stockage.addRessources(listeRessour, listeCompe.get(0));
            
            if(stockage.ressources.containsAll(listeRessour)){
                System.out.println(": cas valide");
            }else{
                System.out.print(stockage.ressources);
                System.out.println(": cas invalide");
                nombreErreur++;
            }
        }
        listeCompe.get(0).getRessources().clear();
         stockage.ressources.clear();
         for (Ressource ressource : tabRessource) {
            System.out.print("cas ressource :" + ressource.getLibelle());
            listeRessour.add(ressource);
            listeCompe.get(0).ajouterRessource(ressource);
           
            stockage.addRessources(listeRessour, listeCompe.get(0));
            if(stockage.ressources.containsAll(listeRessour)){
                System.out.println(": cas invalide");
                nombreErreur++;
            }else{
                System.out.println(": cas valide");
                
            }
        }
        System.out.println("le nombre d'erreur est de : "+nombreErreur);
    }
    
    public static void testSuppression(){
        System.out.println("## TestStockageSuppression ## \n ###########################\n ");
        if(stockage.recherche("U2.1") !=null) {
            stockage.supprimerDonnees();
        }
        if(stockage.recherche("U2.1") !=null) {
            System.out.print("Données non supprimé");
            System.out.println(": cas invalide");
        } else {
            System.out.println("Fonctionnel");
        }
        
    }
    
    public static void testRecherche() {
        int nombreErreur = 0;
        System.out.println("## TestStockageRecherche ## \n ###########################\n ");
        if(stockage.recherche("U2.1") !=null) {
            System.out.println("cas valide");
        } else {
            System.out.println(": cas invalide");
                nombreErreur++;
        }
        if(stockage.recherche("U2.") == null) {
            System.out.println("cas valide");
        } else {
            System.out.println(": cas invalide");
                nombreErreur++;
        }
        System.out.println("le nombre d'erreur est de : "+nombreErreur);
    }
    public static void main(String[] args){
        jeuxDeDonnees();
        testAdd();
         //System.out.println(stockage.ressources.get(1).libelle);
        testRecherche();
        testSuppression();
    }
    


}
