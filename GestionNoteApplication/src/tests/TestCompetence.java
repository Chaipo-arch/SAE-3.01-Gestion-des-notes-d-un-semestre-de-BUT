
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
    public static ArrayList<Competence> competenceValide = new ArrayList<>();
    
    public static ArrayList<Competence> competenceValideAvecNote = new ArrayList<>();
    

    public static void batterieDeTest() throws Exception{
        
        ressourceValide.add(new Ressource("Ressource","R2.01","R1",20));
        ressourceValide.add(new Ressource("Ressource","R2.02","R2",80));
        System.out.println(ressourceValide.toString());
        competenceValide.add(new Competence("U2.1","c1"));
        competenceValideAvecNote.add(new Competence("U2.2","c2"));
        for(int i=0 ; i< ressourceValide.size();i++){
            competenceValide.get(0).getRessources().add(ressourceValide.get(i));
        }
        
        
     
        
    }

    public static void testCalculMoyenneCompetence() throws NoteException{
        System.out.println("Test Calcul Moyenne Competence");
        
        //System.out.println(competenceValide.calculMoyenneCompetence().getNote());
        

        Note moyenneCalculer = competenceValide.get(0).calculMoyenneCompetence();
        double numerateurMoyenne = competenceValide.get(0).getRessources().get(0).getNote()
                                 * competenceValide.get(0).getRessources().get(0).getCoefficient()
                                 + competenceValide.get(0).getRessources().get(1).getNote()
                                 * competenceValide.get(0).getRessources().get(1).getCoefficient();

        double denominateurMoyenne = competenceValide.get(0).getRessources().get(0).getCoefficient() 
                           + competenceValide.get(0).getRessources().get(1).getCoefficient();
                                 
        if (moyenneCalculer.getNote() != numerateurMoyenne/denominateurMoyenne) {
            System.out.println("Test de calculMoyenneCompetence échoué.");
        } else {
            System.out.println("Test de calculMoyenneCompetence réussi");
        }
    }

    public static void testCompetenceToString() {
       System.out.println(competenceValide.get(0).toString());
    }

    public static void testAjouterRessource() throws NoteException {
        System.out.println("Test Ajouter Ressource");
        competenceValideAvecNote.get(0).ajouterRessource(new Ressource("Ressource","R2.04","R4",10.0));
        competenceValideAvecNote.get(0).ajouterRessource(new Ressource("Ressource","R2.05","R5",10.0));
        for(Ressource r: competenceValideAvecNote.get(0).getRessources()) {
           System.out.println(r.getIdentifiant());
       }
    }

    public static void testSupprimerRessource() throws NoteException {
        System.out.println("Test Supprimer Ressource");
        Competence c1 = new Competence("U2.3","c1");
        c1.ajouterRessource(new Ressource("Ressource","R2.04","R4",10.0));
        c1.supprimerRessource(c1.getRessources().get(0));
        if(c1.getRessources().size() == 0 ) {
            System.out.println("Suppression Ressource effectué");
        } else {
            System.out.println("Suppression Ressource non effectué : Test échoué");
        }
    }

    public static void main(String[] args) throws Exception{
        TestCompetence.batterieDeTest();
        TestCompetence.testCalculMoyenneCompetence();
        TestCompetence.testAjouterRessource();
        TestCompetence.testCompetenceToString();
        TestCompetence.testSupprimerRessource();
    }
    
}