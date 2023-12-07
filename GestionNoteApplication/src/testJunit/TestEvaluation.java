package GestionNoteApplication.src.testJunit;

import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.NoteException;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestEvaluation {
    
    public static ArrayList<Evaluation> listeEvaluationValide = new ArrayList<>();
    public static ArrayList<String> listeChaineNonValide = new ArrayList<>();
    public static ArrayList<Note> listeNoteValide = new ArrayList<>();
    public static ArrayList<Double> listeCoefficientValide = new ArrayList<>();
    public static ArrayList<Double> listeCoefficientNonValide = new ArrayList<>();
    
    @Before
    public void setUp() throws NoteException, EvaluationException {
        batterieDeTest();
    }
    public static void batterieDeTest() throws NoteException, EvaluationException{
        
        // Initialisation de listes de notes valides et de coefficients valides
        listeNoteValide.add(new Note(0.5));
        listeNoteValide.add(new Note(10.524));
        listeNoteValide.add(new Note(20));
        listeNoteValide.add(new Note(12.256));

        listeCoefficientValide.add(1.0);
        listeCoefficientValide.add(100.0);
        listeCoefficientValide.add(20.0);
        listeCoefficientValide.add(50.5);

        // Initialisation de listes de coefficients non valides
        
        listeCoefficientNonValide.add(-1.0);
        listeCoefficientNonValide.add(-9456.4898);
        listeCoefficientNonValide.add(100.0001);

        // Initialisation de listes d'évaluations valides avec des notes, des coefficients et des modalités
        listeEvaluationValide.add(new Evaluation("r1",listeNoteValide.get(0)
                ,"QCM",listeCoefficientValide.get(0),"25/01/2023"));
        listeEvaluationValide.add(new Evaluation("r1",listeNoteValide.get(1)
                ,"Exam sur machine",listeCoefficientValide.get(1),"Mi septembre"));
        listeEvaluationValide.add(new Evaluation("r1",listeNoteValide.get(2)
                ,"Ecrit",listeCoefficientValide.get(2),""));
        listeEvaluationValide.add(new Evaluation("r1",listeNoteValide.get(3)
                ,"Relevés TPs",listeCoefficientValide.get(3),"25/12/2024"));

        // Initialisation de listes de chaînes non valides
        listeChaineNonValide.add("");
        listeChaineNonValide.add(null);
    }
    @Test
    public void testAjouterNote() {
        System.out.println("TEST AjouterNote");
        int nbTestNOk = 0;
        int index = 0;

        System.out.println("Test avec evaluation valide");
        for (Evaluation evaluationTeste : listeEvaluationValide) {
            if (evaluationTeste.ajouterNote(listeNoteValide.get(index))) {
                System.out.println(listeNoteValide.get(index) + " non valide pour " + evaluationTeste.getType());
                nbTestNOk++;
            }
            index++;
        }
        assertEquals(0, nbTestNOk);
    }
    @Test
    public void testModifierModalite() {
        assertTrue(listeEvaluationValide.get(0).modifierModalite("r2", listeNoteValide.get(1), "Exam sur machine", 10, "Mi septembre"));
        
    }

    @Test
    public void testGetNote() {
        for (int i = 0; i < listeEvaluationValide.size(); i++) {
            assertEquals(listeNoteValide.get(i).getNote(), listeEvaluationValide.get(i).getNote(), 0.001);
        }
    }

    @Test
    public void testGetCoefficient() {
        for (int i = 0; i < listeEvaluationValide.size(); i++) {
            assertEquals(listeCoefficientValide.get(i), listeEvaluationValide.get(i).getCoefficient(), 0.001);
        }
    }

    @Test
    public void testIsCoefficient() {
        for (double coefficientATester : listeCoefficientValide) {
            assertTrue(Evaluation.isCoefficient(coefficientATester));
        }
        for (double coefficientNonValideATester : listeCoefficientNonValide) {
            assertFalse(Evaluation.isCoefficient(coefficientNonValideATester));
        }
    }
}