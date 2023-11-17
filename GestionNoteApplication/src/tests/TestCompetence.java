
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.tests;

import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.NoteException;
import java.util.ArrayList;
import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Ressource;

public class TestCompetence {

    public static ArrayList<Ressource> ressourceValide = new ArrayList<>();
    public static Competence competenceValide ;
    public static Competence competenceValideAvecNote ;
    

    public static void batterieDeTest() throws Exception{
        
        
        System.out.println(ressourceValide.toString());
        
        for(int i=0 ; i< ressourceValide.size();i++){
            competenceValide.getRessources().add(ressourceValide.get(i));
        }
        
        
     
        
    }

    public static void testCalculMoyenneCompetence() throws NoteException{
        
        
        System.out.println(competenceValide.calculMoyenneCompetence().getNote());
        

        /*double moyenneCalculer = competenceValide.calculMoyenneCompetence();
        double numerateurMoyenne = competenceValide.ressources.get(0).getNote()
                                 * competenceValide.ressources.get(0).getCoefficient()
                                 + competenceValide.ressources.get(1).getNote()
                                 * competenceValide.ressources.get(1).getCoefficient();

        double denominateurMoyenne = competenceValide.ressources.get(0).getCoefficient() 
                           + competenceValide.ressources.get(1).getCoefficient();
                                 
        if (moyenneCalculer != numerateurMoyenne/denominateurMoyenne) {
            System.out.println("Test de calculMoyenneCompetence échoué.");
        }*/
    }

   /* public static void testCompetenceToString() {
        competenceValideAvecNote.note.setNote(10.0);
        String chaineAttendu = "Test note non renseignee ..."; //TODO FINIR LE toString de ressource et remplacer les ...
        String chaineAttendu2 = "Test2 10,0 ...";//TODO FINIR LE toString de ressource et remplacer les ...
        String chaineTrouvee = competenceValide.competenceToString();
        String chaineTrouvee2 = competenceValideAvecNote.competenceToString();
        if (chaineAttendu.equals(chaineTrouvee)
            && chaineAttendu2.equals(chaineTrouvee2)) {
            System.out.println("Test de competenceToString échoué.");
        } 
    }

    public static void testAjouterRessource() {

        if (!competence.ajouterRessource(ressource1) 
            || competence.ajouterRessource(ressource3)) {
            System.out.println("Test d'ajouterRessource échoué.");
        }     
    }

    public static void testSupprimerRessource() {
        
        if (!competence.supprimerRessource(ressource2)
            ||competence.supprimerRessource(ressource3)) {
            System.out.println("Test de supprimerRessource échoué.");
        }
    }*/

    public static void main(String[] args) throws Exception{
        TestEvaluation.batterieDeTest();
        TestEvaluation.testAjouterNote();
        TestRessource.testCreationRessource();    
        TestRessource.jeuxDeData();
        batterieDeTest();
        testCalculMoyenneCompetence();
       // testCompetenceToString();
        //testAjouterRessource();
        //testSupprimerRessource();
    }
    
}
