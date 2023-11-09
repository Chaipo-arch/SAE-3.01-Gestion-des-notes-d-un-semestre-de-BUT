/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.tests;

import java.time.Clock;
import java.util.ArrayList;
import noteManager.main.Evaluation;
import noteManager.main.Note;
import noteManager.main.NoteException;
import noteManager.main.Ressource;
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
    public static ArrayList<Evaluation> listeDeToutEvaluation = new ArrayList<>();
    
    public static boolean jeuxDeData() throws Exception{
        boolean jeuValide = true;
        
            TestEvaluation.batterieDeTest();
            listeDeToutEvaluation.addAll(listeEvaluationValide);
            listeDeToutEvaluation.addAll(listeEvaluationSansNote);

            ressourcesValide.add(new Ressource("Programmation Général", 10, "id", "id"));
            ressourcesValide.add(new Ressource("Mathématique", 20, "id", "id"));
            for(int i=0 ; i < listeEvaluationValide.size() ;i++){
                ressourcesValide.get(0).ajouterEvaluation(listeEvaluationValide.get(0));
            }
            
            
//            ressourcesValide.get(0).ajouterEvaluation(listeEvaluationSansNote.get(0));
        
        System.out.println(ressourcesValide.toString());
        
       
        return jeuValide;
        
    }
    
    
    public static void testCalculMoyenne() throws NoteException{
        System.out.println("démarrage des test");
        System.out.println(ressourcesValide.get(0).calculMoyenne());
    }
    
    public static void main(String[] args) throws Exception{
       
            jeuxDeData();
            testCalculMoyenne();  
        
        
    }
}
