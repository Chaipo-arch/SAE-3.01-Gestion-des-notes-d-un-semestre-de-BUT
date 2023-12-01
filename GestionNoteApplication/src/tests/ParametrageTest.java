/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.tests;

import GestionNoteApplication.src.main.java.package1.Competence;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.package1.Ressource;
import GestionNoteApplication.src.main.java.package1.Stockage;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import GestionNoteApplication.src.main.java.parametrage.Parametrage;
import GestionNoteApplication.src.main.java.parametrage.ParametrageNationalPrototype;
import GestionNoteApplication.src.main.java.parametrage.ParametrageRessourcePrototype;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test de la classe Parametrage avec ses classes enfantes
 * Les fichiers de tests sont dans le dossiers csv situé dans le dossier ressources
 * !!! Attention si vous effectué les tests il se peut que des 
 *     données de votre applicationsoit modifier ou supprimer !!!
 * @author enzo.cluzel
 */
public class ParametrageTest {
    
    /**
     * Tests avec des fichiers Incorrectes de national et ressource
     */
    public static void testFichierIncorrecte() {
        ArrayList<String> nomFichiersTest = new ArrayList<>();
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalCoeffIncorrecte1.csv");
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalCoeffIncorrecte2.csv");
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalRessourceIncorrecte.csv");
        nomFichiersTest.add("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalTypeRIncorrect.csv");
        for(String fichNational : nomFichiersTest) {
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
        for(String fichRessource : nomFichiersTest) {
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
    /**
     * Tests avec des fichiers correctes de national et ressource
     */
    public static void testFichierCorrecte() {
        try {
            ParametrageNationalPrototype paN= new ParametrageNationalPrototype(new File("src/GestionNoteApplication/src/ressources/csv/ParametrageNationalCorrect.csv")) ;
            paN.parse();
            ParametrageRessourcePrototype paR= new ParametrageRessourcePrototype(new File("src/GestionNoteApplication/src/ressources/csv/ParametrageRessourceCorrect.csv")) ;// TODO mettre fichier de base
            paR.parse();
            for(Competence c : Stockage.getInstance().competences ) {
                System.out.println("COMPETENCE : " + c.identifiant);
                System.out.println();
                for(Ressource r : c.ressources) {
                    System.out.println("RESSOURCE : " + r.getIdentifiant() +" " + r);
                    for(Evaluation eval : r.getEvaluation()) {
                        System.out.println(eval.getType() + ";"+eval.getDate()+";"+eval.getCoefficient());
                    }
                    System.out.println();
                }
            }
        } catch (MauvaisFormatFichierException ex) {
            System.out.println("Test incorrecte" + ex.getMessage());
        
        } catch (EvaluationException ex) {
            System.out.println("Test incorrecte" );
        
        } catch (NoteException ex) {
            System.out.println("Test incorrecte" );
        } catch (IOException ex) {
            
        }
    }
    /**
     * Main : lancer les différents tests
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) {
        testFichierIncorrecte();
        //testFichierCorrecte();
    }
    
}