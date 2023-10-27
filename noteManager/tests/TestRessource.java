/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.tests;

import java.util.ArrayList;
import noteManager.main.Evaluation;
import noteManager.main.Note;
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
    
    public void jeuxDeData() throws Exception{
        listeNoteValide.add(new Note(0.5));
        listeNoteValide.add(new Note(10.524));
        listeNoteValide.add(new Note(20));
        listeNoteValide.add(new Note(12.256));
        
        listeEvaluationValide.add(new Evaluation("Maths",listeNoteValide.get(0)
                ,"QCM",1,"25/01/2023"));
        listeEvaluationValide.add(new Evaluation("Développement Web",listeNoteValide.get(1)
                ,"Exam sur machine",100,"Mi septembre"));
        listeEvaluationValide.add(new Evaluation("Sql",listeNoteValide.get(2)
                ,"Ecrit",20,""));
        listeEvaluationValide.add(new Evaluation("Anglais",listeNoteValide.get(3)
                ,"Relevés TPs",50.5,"25/12/2024"));
        
        
        listeEvaluationSansNote.add(new Evaluation("Cryptographie",null
                ,"QCM",1,"25/01/2023"));
        listeEvaluationSansNote.add(new Evaluation("Economie",null
                ,"Exam sur machine",100,"Mi septembre"));
        listeEvaluationSansNote.add(new Evaluation("Réseaux",null
                ,"Ecrit",20,""));
        listeEvaluationSansNote.add(new Evaluation("Programmation",null
                ,"Relevés TPs",50.5,"25/12/2024"));
        
        
        ressourcesValide.add(new Ressource("Programmation Général", 10, "id", "id"));
        ressourcesValide.add(new Ressource("Mathématique", 20, "id", "id"));
        ressourcesValide.get(0).ajouterEvaluation(listeEvaluationSansNote.get(0));
        
        
       
        
    }
}
