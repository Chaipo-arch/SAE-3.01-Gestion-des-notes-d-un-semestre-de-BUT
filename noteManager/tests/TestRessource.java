/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.tests;

import java.util.ArrayList;
import noteManager.main.java.Evaluation;
import noteManager.main.java.Note;
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
    public static ArrayList<Evaluation> listeDeToutEvaluation = new ArrayList<>();
    
    public boolean jeuxDeData() throws Exception{
        boolean jeuValide = true;
        try{
            TestEvaluation.batterieDeTest();
            listeDeToutEvaluation.addAll(listeEvaluationValide);
            listeDeToutEvaluation.addAll(listeEvaluationSansNote);

            ressourcesValide.add(new Ressource("Programmation Général", 10, "id", "id"));
            ressourcesValide.add(new Ressource("Mathématique", 20, "id", "id"));
            ressourcesValide.get(0).ajouterEvaluation(listeEvaluationSansNote.get(0));
        }catch(Exception e){
            jeuValide =  false;
        }
        
        try{
            
        }catch(Exception e){
            
        }
       
        return jeuValide;
        
    }
}
