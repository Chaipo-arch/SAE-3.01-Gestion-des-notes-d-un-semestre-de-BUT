/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package package1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import package1.Parametrage;

/**
 *
 * @author robin.britelle
 */
public class ParametrageNational extends Parametrage{
    public static void main(String[] args) {
        // Définition du modèle
        String[] modele = {
            "BUT Informatique - Modalité Contrôle de connaissances ressources semestre 2;;",		
            "Semestre;2;",
            "Parcours;Tous;",
            ";;",
            "Ressource;R2.01;Développement orienté objets;",
            "Type évaluation;Date;Poids;",
            "QCM;Mi septembre;15;",
            "Ecrit;15 octobre;40;"
        };

        // Fichier CSV à vérifier
        String fichierCSV = "Z:\\Paramétrage ressources semestre2.csv";

        // Vérification de la cohérence
        try {
            File fichier = new File("Z:\\Paramétrage ressources semestre2.csv");
            Parametrage parametrage = new Parametrage();

            ArrayList<ArrayList<String>> donnees = parametrage.extraireDonneesCSV(fichier);
            System.out.println("Test extraireDonneesCSV (fichier CSV existant) :");
            
            //affcihe le contenue de donnees
            System.out.println(donnees);
            
            //affiche le contenue de la ligne 0 colonne 2
            //System.out.println(donnees.get(0).get(1));
            
            //affcihe la ligne 1
            //System.out.println(donnees.get(0));
            
            
            
            
            
            
            
            
            /*String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                System.out.println(modele[lineNumber - 1]);
                if (lineNumber <= modele.length) {
                    if (line.equals(modele[lineNumber - 1])) {
                        System.out.println("La ligne " + lineNumber + " du fichier CSV est conforme au modèle.");
                    } else {
                        System.out.println("La ligne " + lineNumber + " du fichier CSV ne correspond pas au modèle.");
                    }
                } else {
                    System.out.println("Le fichier CSV contient plus de lignes que le modèle.");
                }

                lineNumber++;
            }

            if (lineNumber <= modele.length) {
                System.out.println("Le fichier CSV contient moins de lignes que le modèle.");
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
