/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author robin.britelle
 */


public class Parametrage {
    
    

    public boolean estFichierCSV(File fichier) {
        if (fichier.isFile() && fichier.getName().toLowerCase().endsWith(".csv")) {
            return true;
        }
        return false;
    }

    public List<String[]> extraireDonneesCSV(File fichier) throws IOException {
        List<String[]> donnees = new ArrayList<>();
        try (Scanner scanner = new Scanner(fichier)) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] colonnes = ligne.split(",");
                donnees.add(colonnes);
            }
        }
        return donnees;
    }
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Entrez le chemin du fichier : ");
        String cheminFichier = scanner.nextLine();
        
        File fichier = new File(cheminFichier);
        Parametrage parametrage = new Parametrage();
        
        if (parametrage.estFichierCSV(fichier)) {
            System.out.println("Le fichier est de type CSV.");
            
            try {
                List<String[]> donneesCSV = parametrage.extraireDonneesCSV(fichier);

                System.out.println("Données extraites du fichier CSV :");
                for (String[] ligne : donneesCSV) {
                    for (String colonne : ligne) {
                        System.out.print(colonne + " | ");
                    }
                    System.out.println(); // Saut de ligne entre les lignes.
                }
            } catch (IOException e) {
                System.err.println("Une erreur s'est produite lors de la lecture du fichier.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Le fichier n'est pas de type CSV.");
        }
        
        scanner.close();
    }
}
