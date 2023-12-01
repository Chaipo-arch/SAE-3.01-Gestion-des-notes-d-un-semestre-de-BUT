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
 *
 * @author enzo.cluzel robin.britelle
 */
public class ParametrageTest {
    
    public static void testFichierIncorrecte() {
        try {
            ParametrageNationalPrototype paN= new ParametrageNationalPrototype(new File("")) ;// TODO mettre fichier incorrecte
            paN.parse();
            
            //System.out.println(Stockage.getInstance().competences.get(0).identifiant);
            
            ParametrageRessourcePrototype paR= new ParametrageRessourcePrototype(new File("")) ;// TODO mettre fichier incorrecte
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
            
            
            
            //System.out.println(Stockage.getInstance().evaluations.toString());
        } catch (MauvaisFormatFichierException ex) {
            System.out.println("Test correcte" + ex.getMessage());
        } catch (EvaluationException ex) {
            
        } catch (NoteException ex) {
            
        } catch (IOException ex) {
            
        }
    }
    
    public static void testFichierCorrecte() {
        try {
            File existe = new File("truc.csv");
            existe.createNewFile();
            
            ParametrageNationalPrototype paN= new ParametrageNationalPrototype(new File("")) ;// TODO mettre fichier de base
            paN.parse();
            
            //System.out.println(Stockage.getInstance().competences.get(0).identifiant);
            
            ParametrageRessourcePrototype paR= new ParametrageRessourcePrototype(new File("")) ;// TODO mettre fichier de base
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
            
            
            ///System.out.println(Stockage.getInstance().evaluations.toString());
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
     * TODO comment method role
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) {
        testFichierIncorrecte();
        testFichierCorrecte();
    }
    
}