/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.tests;

import noteManager.main.Evaluation;
import noteManager.main.Note;
import noteManager.main.NoteException;
import noteManager.main.EvaluationException;
import java.util.ArrayList;

/**
 *
 * @author alexandre.brouzes
 */
public class TestEvaluation {
    /**
     * fixture de test ="image fixe de test" partagée pour
     * les différents tests de la classe de évaluation 
     */
    public static ArrayList<Evaluation> listeEvaluationValide = new ArrayList<>();
    public static ArrayList<Evaluation> listeEvaluationSansNote = new ArrayList<>();
    public static ArrayList<String> listeChaineNonValide = new ArrayList<>();
    public static ArrayList<Note> listeNoteValide = new ArrayList<>();
    

    public static void batterieDeTest() throws NoteException, EvaluationException{
   
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
        
        listeChaineNonValide.add("");
        listeChaineNonValide.add(null);   
    }
    
    public static void testAjouterNote(){ 
        int nbTestNOk = 0;
        int index = 0;
        for(Evaluation evaluationTeste : listeEvaluationSansNote){
            if (!evaluationTeste.ajouterNote(listeNoteValide.get(index))){
                nbTestNOk ++;
            }   
            index ++;
        }
        for(Evaluation evaluationTeste : listeEvaluationValide){
            if (evaluationTeste.ajouterNote(listeNoteValide.get(index))){
                nbTestNOk ++;
            }    
            index ++;
        }    
        if(nbTestNOk != 0){
            System.out.println("Il y a " + nbTestNOk + "erreur(s)");
        }
    }
    
    public static void testToSring(){
        
        String affichageATester = listeEvaluationValide.get(0).toSring();
        String chaineAttendu = "Maths QCM 25/01/2023 1 0";
        String affichageATester2 = listeEvaluationValide.get(1).toSring();
        String chaineAttendu2 = "Développement Web Exam sur Machine Mi septembre 100 10,52";
        String affichageATester3 = listeEvaluationValide.get(3).toSring();
        String chaineAttendu3 = "Anglais Relevés TPs 25/12/2024 50.5 12.25";
        String affichageATester4 = listeEvaluationSansNote.get(0).toSring();
        String chaineAttendu4 = "Cryptographie QCM 25/01/2023 1 note non renseignée";
        
        if(affichageATester.compareTo(chaineAttendu) != 0){
            System.out.println("Erreur dans la première chaine comparé");
        }
        if(affichageATester2.compareTo(chaineAttendu2) != 0){
            System.out.println("Erreur dans la première chaine comparé");
        }
        if(affichageATester3.compareTo(chaineAttendu3) != 0){
            System.out.println("Erreur dans la première chaine comparé");
        }
        if(affichageATester4.compareTo(chaineAttendu4) != 0){
            System.out.println("Erreur dans la première chaine comparé");
        }
           
    }

    public static void testModifierModalite(){
        if(!listeEvaluationValide.get(0).modifierModalite("Economie",null
                ,"Exam sur machine",100,"Mi septembre")){
            
        }
        if (listeEvaluationValide.get(0).compareTo(listeEvaluationValide.get(1).getNote() != 0.0)){
            System.out.println("erreur de la classe modification");  
        }
    }  

    public static void testGetNote() {
        
    }
    
    public static void testGetCoefficient(){
        
    }
    public static void main(String[] args) throws NoteException, EvaluationException{
        batterieDeTest();
        
    }
}