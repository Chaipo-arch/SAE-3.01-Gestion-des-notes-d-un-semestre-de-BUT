/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.tests;

import java.util.ArrayList;
import noteManager.main.java.Evaluation;
import noteManager.main.java.Note;
import noteManager.main.java.NoteException;
import noteManager.main.java.Ressource;
import static noteManager.tests.TestEvaluation.listeChaineNonValide;
import static noteManager.tests.TestEvaluation.listeEvaluationSansNote;
import static noteManager.tests.TestEvaluation.listeEvaluationValide;
import static noteManager.tests.TestEvaluation.listeNoteValide;

/**
 *
 * @author ahmed.bribach
 */
public class TestRessource {
    
    public static ArrayList<Ressource> ressourcesValide= new ArrayList<>() ;
    public static ArrayList<Note> listeDeMoyenne = new ArrayList<>();
    public static ArrayList<Evaluation> listeEvaluation = new ArrayList<>();
    public static void jeuxDeData() throws Exception{
        boolean jeuValide = true;
        
            TestEvaluation.batterieDeTest();
            ressourcesValide.add(new Ressource("Programmation Général", 10, "id", "id"));
            ressourcesValide.add(new Ressource("Mathématique", 20, "id", "id"));
            ressourcesValide.add(new Ressource("intit", 10, "id", "id"));
           
            listeDeMoyenne.add(new Note(12.08));
            listeDeMoyenne.add(new Note(-1.0));
            listeDeMoyenne.add(new Note(16.67));
            
        // ressourcesValide.get(0).ajouterEvaluation(listeEvaluationSansNote.get(0));
        
        
    }
    public static void testCreationRessource() throws Exception{
        int nbErreur = 0;
        if(Ressource.isValide("", 0, "", "")){
            nbErreur++;
        }
        if(Ressource.isValide("  ", 1, "dsd", "sdsdsd")){
            nbErreur++;
        }
        if(Ressource.isValide("dsf", -1, "fsd", "sdf")){
            nbErreur++;
        }if(Ressource.isValide("sdqsd", 2, " ", "dqsdsqds")){
            nbErreur++;
        }if(Ressource.isValide("sdqsd", 2, "sdqddsq ", "      ")){
            nbErreur++;
        }
        if(!Ressource.isValide("sd    qsd", 2, "sdqdsqsqd ", "dqsdsqds")){
            nbErreur++;
        }
        if(!Ressource.isValide("sdqsd", 100, "qsdsqdsqsdq", "dqsdsqds")){
            nbErreur++;
        }
        
        if(nbErreur==0){
            System.out.println("TEST : testCreationRessource VALIDE");
        }else{
           System.out.println("nombre d'erreur : " + nbErreur); 
        }
        
        
            
            
            
    }
    
    public static void testCalculMoyenne() throws Exception{
       int nbErreur = 0;
        for(int i=0;i<ressourcesValide.size();i++){
            if(!ressourcesValide.get(i).calculMoyenne().equals(listeDeMoyenne.get(i))){
                nbErreur++;
                System.out.print("Moyenne invalide pour la ressource" + ressourcesValide.get(i).toString());
            }
        }
        if(nbErreur ==0){
            System.out.println("TEST : testCalculMoyenne VALIDE");
        }else{
            System.out.println("TEST : testCalculMoyenne INVALIDE nombre erreur : " + nbErreur);
        }
    }
    /**
     * 
     * @throws Exception 
     */
    public static void testAjouterEvaluation() throws Exception{
        int nbErreur =0;
        for(int i=0 ; i < listeEvaluationValide.size() ;i++){
            if(!ressourcesValide.get(0).ajouterEvaluation(listeEvaluationValide.get(i))){
                nbErreur++;
                System.out.println("echec de l'insertion de : " + listeEvaluationValide.get(i));
            }
        }
        listeEvaluation.add(new Evaluation("id", new Note(20), "qcm", 100.0, "05/12/2022"));
        listeEvaluation.add(new Evaluation("id", new Note(0), "qcm", 20.0, "05/12/2022"));
        if(!ressourcesValide.get(2).ajouterEvaluation(listeEvaluation.get(0))){
            nbErreur++;
        }
        if(!ressourcesValide.get(2).ajouterEvaluation(listeEvaluation.get(1))){
            nbErreur++;
        }
        
        if(nbErreur ==0){
            System.out.println("TEST : testAjouterEvaluation VALIDE");
        }
        
    }
    /**
     * 
     * @throws Exception 
     */
    public static void testSupprimerEvaluation() throws Exception{
        int nbErreur = 0;
        for(int i=0 ; i < listeEvaluation.size() ; i++){
            if(!ressourcesValide.get(2).supprimerEvaluation(listeEvaluation.get(i)) && ressourcesValide.get(2).getEvaluation().contains(listeEvaluation.get(i)) ){
                nbErreur++;
            }
        }
        if(nbErreur == 0){
            System.out.println("TEST : testSupprimerEvaluation VALIDE");
        }else{
            System.out.println("nombre d'erreur :" + nbErreur);
        }
        
    }
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception{
        testCreationRessource();
        jeuxDeData();
        testAjouterEvaluation();
        testCalculMoyenne();  
        testSupprimerEvaluation();
    }
}
