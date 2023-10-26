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
    
    

    public boolean estFichierCSV(String chemin) {
        File cheminFichier;
        cheminFichier = new File(chemin);
        if (cheminFichier.isFile() && cheminFichier.getName().toLowerCase().endsWith(".csv")) {
            return true;
        }
        return false;
    }
    
    
    public ArrayList<ArrayList<String>> extraireDonneesCSV(File fichier) throws IOException {
        ArrayList<ArrayList<String>> donnees = new ArrayList<>();
        try (Scanner scanner = new Scanner(fichier)) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] colonnes = ligne.split(";");
                ArrayList<String> colonnesList = new ArrayList<>();
                for (String colonne : colonnes) {
                    colonnesList.add(colonne);
                }
                donnees.add(colonnesList);
            }
        }
        return donnees;
    }

    /*public List<String[]> extraireDonneesCSV(File fichier) throws IOException {
        List<String[]> donnees = new ArrayList<>();
        try (Scanner scanner = new Scanner(fichier)) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] colonnes = ligne.split(",");
                donnees.add(colonnes);
            }
        }
        return donnees;
    }*/
    
    //TODO a voir si on rajoute le ISCORRECT pour l'abstract Class
    
    // TODO revoir la methodes d'extraction des donnees pour un acces facile a chacune des cases du csv a partir d'un index de ligne
    
    //Rajout d'une methodes permettant de verifier la variable "Chemin" pour verifier la saisie Utilisateur et message d'erreur adapter
    
    
}