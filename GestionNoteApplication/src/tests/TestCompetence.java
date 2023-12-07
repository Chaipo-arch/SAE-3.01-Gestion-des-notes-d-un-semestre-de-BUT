package GestionNoteApplication.src.tests;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.package1.Ressource;

import java.util.ArrayList;

public class TestCompetence {
    
    private static int n = 0;
    public static ArrayList<Ressource> ressourceValide = new ArrayList<>();
    public static ArrayList<Competence> competenceValide = new ArrayList<>();
    public static ArrayList<Competence> competenceValideAvecNote = new ArrayList<>();

    public static void batterieDeTest() throws Exception {
        ressourceValide.add(new Ressource("Ressource", "R2.01", "R1", 20));
        ressourceValide.add(new Ressource("Ressource", "R2.02", "R2", 80));

        competenceValide.add(new Competence("U2.1", "c1"));
        competenceValideAvecNote.add(new Competence("U2.2", "c2"));

        for (int i = 0; i < ressourceValide.size(); i++) {
            competenceValide.get(0).getRessources().add(ressourceValide.get(i));
        }
    }

    public static void testCalculMoyenneCompetence() throws NoteException {
        Note moyenneCalculer = competenceValide.get(0).calculMoyenneCompetence();
        double numerateurMoyenne = competenceValide.get(0).getRessources().get(0).getNote()
                * competenceValide.get(0).getRessources().get(0).getCoefficient()
                + competenceValide.get(0).getRessources().get(1).getNote()
                * competenceValide.get(0).getRessources().get(1).getCoefficient();

        double denominateurMoyenne = competenceValide.get(0).getRessources().get(0).getCoefficient()
                + competenceValide.get(0).getRessources().get(1).getCoefficient();
        System.out.println("\n=============================================="
                            +"\nTest Calcul de Moyenne"+
                            "\n==============================================");
        if (moyenneCalculer.getNote() == numerateurMoyenne / denominateurMoyenne) {
            
            n+=1;
            System.out.println("Test de calculMoyenneCompetence [ réussi ]");
            
        } else {
            System.out.println("Test de calculMoyenneCompetence échoué.");
        }
    }

    public static void testAjouterRessource() throws NoteException {
        competenceValideAvecNote.get(0).ajouterRessource(new Ressource("Ressource", "R2.04", "R4", 10.0));
        competenceValideAvecNote.get(0).ajouterRessource(new Ressource("Ressource", "R2.05", "R5", 10.0));

        if (competenceValideAvecNote.get(0).getRessources().size() == 2) {
            System.out.println("Test Ajouter Ressource [ réussi ]");
            n+=1;
        } else {
            System.out.println("Test Ajouter Ressource échoué.");
        }
    }

    public static void testSupprimerRessource() throws NoteException {
        Competence c1 = new Competence("U2.3", "c1");
        c1.ajouterRessource(new Ressource("Ressource", "R2.04", "R4", 10.0));
        c1.supprimerRessource(c1.getRessources().get(0));

        if (c1.getRessources().size() == 0) {
            System.out.println("Test Supprimer Ressource [ réussi ]");
            n+=1;
        } else {
            System.out.println("Test Supprimer Ressource échoué.");
        }
    }



    public static void testAjouterCompetenceValideAvecNote() throws NoteException {
        System.out.println("\n=============================================="
                + "\nTest d'ajout de compétence valide avec note"+
                "\n==============================================");
        Competence nouvelleCompetence = new Competence("U2.4", "c3");
        competenceValideAvecNote.add(nouvelleCompetence);

        if (competenceValideAvecNote.size() == 2) {
            System.out.println("Test Ajouter Competence Valide Avec Note [ réussi ]");
            n+=1;
        } else {
            System.out.println("Test Ajouter Competence Valide Avec Note échoué.");
        }
    }

    public static void main(String[] args) throws Exception {
        batterieDeTest();
        testCalculMoyenneCompetence();
        testAjouterRessource();
        testSupprimerRessource();

        testAjouterCompetenceValideAvecNote();
        System.out.println("\n=============================================="
                + "\nRESULTAT"+
                "\n==============================================");
        System.out.println("Total : "+n+" tests reussie sur 4");
    }
}
