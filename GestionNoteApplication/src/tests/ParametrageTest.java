package tests;

import java.io.File;
import java.io.IOException;
import java.util.List;
import package1.Parametrage;

public class ParametrageTest {
    
    public static void main(String[] args) {
        testEstFichierCSV();
        testExtraireDonneesCSV();
    }
    
    public static void testEstFichierCSV() {
        Parametrage parametrage = new Parametrage();
        
        // Test avec un fichier CSV
        File fichierCSV;
        fichierCSV = new File("Z:\\Classeur1.csv");
        boolean resultat = parametrage.estFichierCSV(fichierCSV);
        System.out.println("Test estFichierCSV (fichier CSV) : " + resultat);
        
        // Test avec un fichier non CSV
        File fichierNonCSV = new File("exemple.txt");
        resultat = parametrage.estFichierCSV(fichierNonCSV);
        System.out.println("Test estFichierCSV (fichier non CSV) : " + resultat);
    }
    
    public static void testExtraireDonneesCSV() {
        Parametrage parametrage = new Parametrage();
        
        // Test avec un fichier CSV existant
        File fichierCSV = new File("Z:\\Classeur1.csv");
        try {
            List<String[]> donnees = parametrage.extraireDonneesCSV(fichierCSV);
            System.out.println("Test extraireDonneesCSV (fichier CSV existant) :");
            for (String[] ligne : donnees) {
                for (String colonne : ligne) {
                    System.out.print(colonne + " | ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de la lecture du fichier CSV.");
            e.printStackTrace();
        }
        
        // Test avec un fichier qui n'existe pas (attention : il doit être géré correctement dans extraireDonneesCSV)
        File fichierInexistant = new File("inexistant.csv");
        try {
            List<String[]> donnees = parametrage.extraireDonneesCSV(fichierInexistant);
            System.out.println("Test extraireDonneesCSV (fichier inexistant) :");
            for (String[] ligne : donnees) {
                for (String colonne : ligne) {
                    System.out.print(colonne + " | ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Une erreur s'est produite lors de la lecture du fichier CSV inexistant.");
            //e.printStackTrace();
        }
    }
}