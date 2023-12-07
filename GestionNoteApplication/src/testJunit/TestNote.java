package GestionNoteApplication.src.testJunit;

import GestionNoteApplication.src.main.java.package1.Note;
import GestionNoteApplication.src.main.java.package1.NoteException;
import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class TestNote {

    public static ArrayList<Double> noteValide = new ArrayList<>();
    public static ArrayList<Double> noteInvalide = new ArrayList<>();
    public static ArrayList<Note> note = new ArrayList<>();

    @Before
    public void jeuxDeDonnees() throws NoteException {
        noteInvalide.add(Double.NaN);
        noteInvalide.add(-2.0);
        noteInvalide.add(20.00000000000001);
        noteValide.add(0.0);
        noteValide.add(20.0000000);
        noteValide.add(5.0);

        note.add(new Note(4));
        note.add(new Note(0));
        note.add(new Note(8));
        note.add(new Note(13));
    }

    @Test
    public void testNoteIsValide() {
        System.out.println("## TestNoteIsValide ## \n ###########################\n tests des cas Valide");
        for (Double double1 : noteValide) {
            System.out.print(double1);
            assertTrue(Note.isNote(double1));
            System.out.println(": cas valide");
        }
        for (Double double1 : noteInvalide) {
            System.out.print(double1);
            assertFalse(Note.isNote(double1));
            System.out.println(": cas invalide");
        }
    }

    @Test
    public void testNoteConstructor() {
        System.out.println("## TestNoteConstructor ## \n ###########################\n tests des cas Valide et Invalide");
        int nombreErreur = 0;
        for (Double double1 : noteValide) {
            try {
                new Note(double1);
                System.out.println(double1 + " : cas valide");
            } catch (NoteException e) {
                System.out.println(double1 + " : cas invalide");
                nombreErreur++;
            }
        }
        for (Double double1 : noteInvalide) {
            try {
                new Note(double1);
                System.out.println(double1 + " : cas invalide");
                nombreErreur++;
            } catch (NoteException e) {
                System.out.println(double1 + " : cas valide");
            }
        }
        System.out.println("le nombre d'erreur est de : " + nombreErreur);
    }

    @Test
    public void testSetNoteIsValide() throws NoteException {
        System.out.println("## TestSetNoteIsValide ## \n ###########################\n tests des cas Valide");
        int nombreErreur = 0;
        for (int i = 0; i < noteValide.size(); i++) {
            note.get(i).setNote(noteValide.get(i));
            System.out.print(note.get(i).getNote() + " : doit être modifié par  " + noteValide.get(i) + " résultat : ");
            assertEquals(noteValide.get(i), note.get(i).getNote(), 0);
            System.out.println("  cas valide");
        }
        System.out.println("le nombre d'erreur est de : " + nombreErreur);
    }
}
