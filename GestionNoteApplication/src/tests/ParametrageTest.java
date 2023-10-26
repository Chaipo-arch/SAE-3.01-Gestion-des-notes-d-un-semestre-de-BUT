package tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static package1.Param.extraireDonneesCSV;
import package1.Parametrage;

public class ParametrageTest {
    
    public static void main(String[] args) {
        testEstFichierCSV();
        testExtraireDonneesCSV();
    }
    
    public static void testEstFichierCSV() {
        Parametrage parametrage = new Parametrage();
        
        // Test avec un fichier CSV
        String chemin1 = "Z:\\Classeur1.csv";
        boolean resultat = parametrage.estFichierCSV(chemin1);
        System.out.println("Test estFichierCSV (fichier CSV) : " + resultat);
        
        // Test avec un fichier non CSV
        String chemin2 = "exemple.txt";
        resultat = parametrage.estFichierCSV(chemin2);
        System.out.println("Test estFichierCSV (fichier non CSV) : " + resultat);
    }
    
    public static void testExtraireDonneesCSV() {
        Parametrage parametrage = new Parametrage();

        // Test avec un fichier CSV existant
        File fichierCSV = new File("Z:\\Classeur1.csv");
        try {
            ArrayList<ArrayList<String>> donnees = parametrage.extraireDonneesCSV(fichierCSV);
            System.out.println("Test extraireDonneesCSV (fichier CSV existant) :");
            
            //affcihe le contenue de donnees
            System.out.println(donnees);
            
            //affiche le contenue de la ligne 0 colonne 2
            System.out.println(donnees.get(0).get(1));
            
            //System.out.println();
            for (ArrayList<String> ligne : donnees) {
                //System.out.println(donnees.get(0).get(1));
                for (String colonne : ligne) {
                    System.out.print(colonne + " | ");
                }
                
            }
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de la lecture du fichier CSV.");
            e.printStackTrace();
        }
 
        
        // Test avec un fichier qui n'existe pas (attention : il doit être géré correctement dans extraireDonneesCSV)
        File fichierInexistant = new File("inexistant.csv");
        try {
            ArrayList<ArrayList<String>> donnees = parametrage.extraireDonneesCSV(fichierInexistant);
            System.out.println("Test extraireDonneesCSV (fichier inexistant) :");
            
            for (ArrayList<String> ligne : donnees) {
                for (String colonne : ligne) {
                    System.out.print(colonne + " | ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de la lecture du fichier CSV inexistant.");
            // e.printStackTrace();
        }
    }
}