/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.test;


import GestionNoteApplication.src.main.java.package1.Stockage;
import GestionNoteApplication.src.main.java.package1.Ressource;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.Competence;
import java.util.ArrayList;


/**
 *
 * @author enzo.cluzel
 */
public class TestStockage{
    
    static Stockage stockage;
    static ArrayList<Competence> listeCompe = new ArrayList<>();
    static ArrayList<Ressource> listeRessour = new ArrayList<>();
    static ArrayList<Evaluation> listeEvaluation = new ArrayList<>();
    
    static Competence [] tabCompe = {new Competence("c1"), new Competence("c2"), new Competence("c3")};
    static Ressource [] tabRessource = {new Ressource("s1"), new Ressource("s2"), new Ressource("s3")};
    static Evaluation [] tabEval = {new Evaluation("e1")};
    
    public static void jeuxDeDonnees() {
        stockage = Stockage.getInstance();
        Competence competence = new Competence("U2.1");
        listeCompe.add(competence);
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
         
        for (Ressource ressource : tabRessource) {
            System.out.print("cas ressource :" + ressource.libelle);
            listeRessour.add(ressource);
            if(stockage.addRessources(listeRessour)){
                System.out.println(": cas valide");
            }else{
                System.out.println(": cas invalide");
                nombreErreur++;
            }
        }
         for (Ressource ressource : tabRessource) {
            System.out.print("cas ressource :" + ressource.libelle);
            listeRessour.add(ressource);
            if(stockage.addRessources(listeRessour)){
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
