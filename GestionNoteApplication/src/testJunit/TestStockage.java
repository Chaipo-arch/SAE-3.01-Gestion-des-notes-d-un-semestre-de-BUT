package GestionNoteApplication.src.testJunit;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.package1.Ressource;
import GestionNoteApplication.src.main.java.package1.Stockage;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class TestStockage {

    private Stockage stockage;
    private ArrayList<Competence> listeCompe;
    private ArrayList<Ressource> listeRessour;
    private ArrayList<Evaluation> listeEvaluation;

    private Competence[] tabCompe;
    private Ressource[] tabRessource;
    private Evaluation[] tabEval;

    @Before
    public void setUp() throws NoteException, EvaluationException {
        stockage = Stockage.getInstance();
        listeCompe = new ArrayList<>();
        listeRessour = new ArrayList<>();
        listeEvaluation = new ArrayList<>();
        tabCompe = new Competence[2];
        tabCompe[0] = new Competence("U2.1", "c1");
        tabCompe[1] = new Competence("U2.2", "c2");
        tabRessource = new Ressource[2];
        tabRessource[0] = new Ressource("Sae", "id", "Programmation Général", 10);
        tabRessource[1] = new Ressource("Portefolio", "id", "Mathématique", 20);
        tabEval = new Evaluation[1];
        tabEval[0] = new Evaluation("QCM", "25/01/2023", 1);
        Competence competence = new Competence("U2.1", "c3");
        listeCompe.add(competence);
    }

    @Test
    public void testAdd() {
        ////System.out.println("## TestStockageAdd ## \n ###########################\n ");
        for (Competence competence : tabCompe) {
            listeCompe.add(competence);
            ////System.out.print("cas competence :" + competence.libelle);
            assertTrue(stockage.addCompetences(listeCompe));
        }

        for (Competence competence : tabCompe) {
            listeCompe.add(competence);
            ////System.out.print("cas competence :" + competence.libelle);
            assertFalse(stockage.addCompetences(listeCompe));
        }

        listeCompe.get(0).getRessources().clear();
        stockage.competences.get(0).getRessources().clear();
        stockage.ressources.clear();
        listeRessour.clear();
        for (Ressource ressource : tabRessource) {
            ////System.out.print("cas ressource :" + ressource.getLibelle());
            listeRessour.add(ressource);
            stockage.competences.get(0).ajouterRessource(ressource);
            stockage.addRessources(listeRessour, listeCompe.get(0));
            assertTrue(stockage.ressources.containsAll(listeRessour));
        }
        listeCompe.get(0).getRessources().clear();
        stockage.ressources.clear();
        stockage.competences.get(0).getRessources().clear();
        for (Ressource ressource : tabRessource) {
            listeRessour.add(ressource);
            stockage.competences.get(0).ajouterRessource(ressource);
            stockage.addRessources(listeRessour, listeCompe.get(0));
            assertTrue(stockage.ressources.containsAll(listeRessour));
        }
    }

    @Test
    public void testSuppression() {
        ////System.out.println("## TestStockageSuppression ## \n ###########################\n ");

        assertNotNull(stockage.recherche("U2.1"));
        stockage.supprimerDonnees();
         assertTrue(stockage.recherche("U2.1").size() == 0);
    }

    @Test
    public void testRecherche() {
        ////System.out.println("## TestStockageRecherche ## \n ###########################\n ");

        assertNotNull(stockage.recherche("U2.1"));
        assertTrue(stockage.recherche("U2.").size() == 0);
    }
}