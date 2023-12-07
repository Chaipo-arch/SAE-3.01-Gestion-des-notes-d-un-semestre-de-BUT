package GestionNoteApplication.src.testJunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.package1.Ressource;
import GestionNoteApplication.src.main.java.package1.Stockage;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;

public class ParametrageTest {

    @Test
    public void testFichierIncorrecte() throws EvaluationException {
        ArrayList<String> nomFichiersTest = new ArrayList<>();
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalCoeffIncorrecte1.csv");
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalCoeffIncorrecte2.csv");
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalRessourceIncorrecte.csv");
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalTypeRIncorrect.csv");

        for (String fichNational : nomFichiersTest) {
            try {
                ParametrageNationalPrototype paN= new ParametrageNationalPrototype(new File(fichNational)) ;// TODO mettre fichier incorrecte
                paN.parse();
                System.out.println("Test Incorrecte " + fichNational);
            } catch (MauvaisFormatFichierException ex) {
                System.out.println("Test correcte : " + ex.getMessage());
            } catch (NoteException ex) {

            } catch (IOException ex) {

            }
        }

        nomFichiersTest = new ArrayList<>();
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageRessourceNomRIncorrect.csv");

        for (String fichRessource : nomFichiersTest) {
            try {
                ParametrageRessourcePrototype paR= new ParametrageRessourcePrototype(new File(fichRessource)) ;// TODO mettre fichier incorrecte
                paR.parse();
                System.out.println("Test Incorrecte" + fichRessource);
            } catch (MauvaisFormatFichierException ex) {
                System.out.println("Test correcte" + ex.getMessage());
            } catch (EvaluationException ex) {

            } catch (NoteException ex) {

            } catch (IOException ex) {

            }
        }
    }

    @Test
    public void testFichierCorrecte() {
        try {
            ParametrageNationalPrototype paN = new ParametrageNationalPrototype(
                    new File("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalCorrect.csv"));
            paN.parse();
            ParametrageRessourcePrototype paR = new ParametrageRessourcePrototype(
                    new File("src/GestionNoteApplication/src/ressources/csv/ParametrageRessourceCorrect.csv"));
            paR.parse();

            for (Competence c : Stockage.getInstance().competences) {
                System.out.println("COMPETENCE : " + c.identifiant);
                System.out.println();
                for (Ressource r : c.ressources) {
                    System.out.println("RESSOURCE : " + r.getIdentifiant() + " " + r);
                    for (Evaluation eval : r.getEvaluation()) {
                        System.out.println(eval.getType() + ";" + eval.getDate() + ";" + eval.getCoefficient());
                    }
                    System.out.println();
                }
            }
        } catch (MauvaisFormatFichierException ex) {
            fail("Le test avec le fichier correct a levé une exception incorrecte: " + ex.getMessage());
        } catch (EvaluationException | NoteException | IOException ex) {
            fail("Le test avec le fichier correct a levé une exception incorrecte.");
        }
    }
}
