package GestionNoteApplication.src.testJunit;

import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.Ressource;
import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestRessource {

    public static ArrayList<Ressource> ressourcesValide = new ArrayList<>();
    public static ArrayList<Note> listeDeMoyenne = new ArrayList<>();
    public static ArrayList<Evaluation> listeEvaluation = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        jeuxDeData();
    }

    @Test
    public void testCreationRessource() throws Exception {
        assertFalse(Ressource.isValide("", 0, "", ""));
        assertFalse(Ressource.isValide("  ", 1, "dsd", "sdsdsd"));
        assertFalse(Ressource.isValide("dsf", -1, "fsd", "sdf"));
        assertFalse(Ressource.isValide("sdqsd", 2, " ", "dqsdsqds"));
        assertFalse(Ressource.isValide("sdqsd", 2, "sdqddsq ", "      "));
        assertTrue(Ressource.isValide("sd    qsd", 2, "sdqdsqsqd ", "dqsdsqds"));
        assertTrue(Ressource.isValide("sdqsd", 100, "qsdsqdsqsdq", "dqsdsqds"));
    }

    @Test
    public void testCalculMoyenne() throws Exception {
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

    @Test
    public void testAjouterEvaluation() throws Exception {
        for (Evaluation evaluation : TestEvaluation.listeEvaluationValide) {
            assertTrue(ressourcesValide.get(0).ajouterEvaluation(evaluation));
        }

        Evaluation eval1 = new Evaluation("r1", new Note(20), "QCM", 100, "05/12/2022");
        Evaluation eval2 = new Evaluation("r1", new Note(0), "exam", 20, "10/12/2022");

        assertTrue(ressourcesValide.get(2).ajouterEvaluation(eval1));
        assertTrue(ressourcesValide.get(2).ajouterEvaluation(eval2));
    }

    @Test
    public void testSupprimerEvaluation() throws Exception {
        for (Evaluation evaluation : listeEvaluation) {
            assertTrue(ressourcesValide.get(2).supprimerEvaluation(evaluation));
            assertFalse(ressourcesValide.get(2).getEvaluation().contains(evaluation));
        }
    }

    private void jeuxDeData() throws Exception {
        TestEvaluation.batterieDeTest();

        ressourcesValide.add(new Ressource("Sae", "id", "Programmation Général", 10));
        ressourcesValide.add(new Ressource("Portefolio", "id", "Mathématique", 20));
        ressourcesValide.add(new Ressource("Ressource", "id", "intit", 10));

        listeDeMoyenne.add(new Note(12.08));
        listeDeMoyenne.add(new Note(-1.0));
        listeDeMoyenne.add(new Note(16.67));
    }
}
