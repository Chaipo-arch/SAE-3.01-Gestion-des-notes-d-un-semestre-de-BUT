package GestionNoteApplication.src.testJunit;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.package1.Ressource;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestCompetence {

    private static ArrayList<Ressource> ressourceValide;
    private static ArrayList<Competence> competenceValide;
    private static ArrayList<Competence> competenceValideAvecNote;

    @Before
    public void setUp() throws Exception {
        ressourceValide = new ArrayList<>();
        competenceValide = new ArrayList<>();
        competenceValideAvecNote = new ArrayList<>();
        
        ressourceValide.add(new Ressource("Ressource","R2.01","R1",20));
        ressourceValide.add(new Ressource("Ressource","R2.02","R2",80));

        competenceValide.add(new Competence("U2.1","c1"));
        competenceValideAvecNote.add(new Competence("U2.2","c2"));

        for(int i = 0; i < ressourceValide.size(); i++) {
            competenceValide.get(0).getRessources().add(ressourceValide.get(i));
        }
    }

    @Test
    public void testCalculMoyenneCompetence() throws NoteException {
        double expectedMoyenne = (ressourceValide.get(0).getNote() * ressourceValide.get(0).getCoefficient()
                                + ressourceValide.get(1).getNote() * ressourceValide.get(1).getCoefficient())
                                / (ressourceValide.get(0).getCoefficient() + ressourceValide.get(1).getCoefficient());

        Note moyenneCalculer = competenceValide.get(0).calculMoyenneCompetence();
        assertEquals("La moyenne de la compétence n'est pas correcte.", expectedMoyenne, moyenneCalculer.getNote(), 0.001);
    }

    @Test
    public void testAjouterRessource() throws NoteException {
        Ressource nouvelleRessource = new Ressource("Ressource","R2.04","R4",10.0);
        competenceValideAvecNote.get(0).ajouterRessource(nouvelleRessource);

        assertTrue("La ressource ajoutée n'est pas présente dans la liste des ressources de la compétence.",
                   competenceValideAvecNote.get(0).getRessources().contains(nouvelleRessource));
    }

    @Test
    public void testSupprimerRessource() throws NoteException {
        Competence c1 = new Competence("U2.3","c1");
        Ressource ressourceASupprimer = new Ressource("Ressource","R2.04","R4",10.0);
        c1.ajouterRessource(ressourceASupprimer);
        c1.supprimerRessource(ressourceASupprimer);

        assertEquals("La suppression de la ressource n'a pas été effectuée correctement.",
                     0, c1.getRessources().size());
    }
}
