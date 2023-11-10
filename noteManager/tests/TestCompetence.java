
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noteManager.tests;

import noteManager.main.java.Note;
import noteManager.main.java.NoteException;
import java.util.ArrayList;
import noteManager.main.java.Competence;
import noteManager.main.java.Ressource;

public class TestCompetence {

    public static ArrayList<Ressource> ressourceValide = new ArrayList<>();
    public static Competence competenceValide = new Competence("Test");
    public static Competence competenceValideAvecNote = new Competence("Test2");
    

    public static void batterieDeTest() throws NoteException{
        
        Ressource ressource1 = new Ressource("Ressource1", 1.0, "ID1", "Identifiant1");
        Ressource ressource2 = new Ressource("Ressource2", 2.0, "ID2", "Identifiant2");
        Ressource ressource3 = new Ressource("Ressource3", 3.0, "ID3", "Identifiant3");
        
        competenceValide.getRessources().add(ressource1);
        competenceValide.getRessources().add(ressource2);
        
    }

    public static void testCalculMoyenneCompetence() throws NoteException{

        competenceValide.getRessources().get(0).setNote(20);
        competenceValide.getRessources().get(1).setNote(10);

        double moyenneCalculer = competenceValide.calculMoyenneCompetence();
        double numerateurMoyenne = competenceValide.ressources.get(0).getNote()
                                 * competenceValide.ressources.get(0).getCoefficient()
                                 + competenceValide.ressources.get(1).getNote()
                                 * competenceValide.ressources.get(1).getCoefficient();

        double denominateurMoyenne = competenceValide.ressources.get(0).getCoefficient() 
                           + competenceValide.ressources.get(1).getCoefficient();
                                 
        if (moyenneCalculer != numerateurMoyenne/denominateurMoyenne) {
            System.out.println("Test de calculMoyenneCompetence échoué.");
        }
    }

    public static void testCompetenceToString() {
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
    }

    public static void main(String[] args) {
        testCalculMoyenneCompetence();
        testCompetenceToString();
        testAjouterRessource();
        testSupprimerRessource();
    }
    
}
