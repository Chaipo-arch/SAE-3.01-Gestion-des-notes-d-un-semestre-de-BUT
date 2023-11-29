/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.parametrage;

import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.package1.Stockage;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.NoteException;
import GestionNoteApplication.src.main.java.package1.Ressource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author enzo.cluzel
 */
public class ParametrageRessourcePrototype extends Parametrage {

    /**
     * TODO comment field role (attribute, association)
     */
    protected final String[] MODELE = {"BUT Informatique - Modalite Controle de connaissances ressources semestre", "Semestre", "Parcours", "Type evaluation;Date;Poids"};

    private static BufferedReader br;
    private static int numeroLigne;

    /**
     * TODO comment initial state
     *
     * @param chemin
     * @throws IOException
     */
    public ParametrageRessourcePrototype(File chemin) throws IOException, MauvaisFormatFichierException {
        super(chemin);
        flag = true;
    }

    /**
     * Lis un fichier ligne par ligne et créer des instances de Evaluation et les ajoutent dans une ressource
     * @throws MauvaisFormatFichierException Si les données du fichier sont mauvaises 
     *         exemple : fichier vide, taille de la colonne pas suffisantes, identifiant mal ecrit
     * 
     *         Voir dans le dossier csv situé dans ressources pour un exemple de fichier csv correct
     */
    @Override
    public void parse() throws IOException, MauvaisFormatFichierException, EvaluationException, NoteException {
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            
        }
         br = new BufferedReader(fr);
        numeroLigne = 0;
        String line;
        // Lecture des 3 premières lignes du fichier
        for (line = newLine(); numeroLigne < 3; line = newLine()) {
            // Verif fichier non vide
            if(line == null) {
                throw new MauvaisFormatFichierException("Fichier vide");
            }
                
            String[] chaine = line.split(";");
            if (numeroLigne == 0) {
                chaine[0] = chaine[0].substring(0, chaine[0].length() - 2);
            }
           if (!chaine[0].matches(MODELE[numeroLigne])) {
               System.out.println(chaine[0]);
               System.out.println("ok");
               
               System.out.println(numeroLigne);
               System.out.println(MODELE[numeroLigne]);
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit " + line + numeroLigne );
                
            }
            
             if(chaine.length != 2 && numeroLigne == 1) {
               
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: pas 2 colonnes" );
                
            } else if(numeroLigne == 1 &&!chaine[1].matches("^[1-6]$")) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: la deuxieme colonne n'a pas de chiffre entre 1 et 6");
            }
            if (numeroLigne == 2 && chaine.length != 2) {
                
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: pas 2 colonnes");
            } else if( numeroLigne == 2 && !chaine[1].matches("^[ABCD]$|^ $|^Tous$")) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) +  " est mal écrit: la deuxieme colonne n'a pas ABCD, rien ou Tous");
            }

        }
        // Sauvegarde l'identifiant de la Ressource dans lequel des evaluations sont attribuées
        String saveIdentifiantR = null;
        int calculCoeff;
        // lecture des lignes suivantes arret lors de la fin du fichier
        while (line != null) {
            calculCoeff = 0;
            String[] chaine = line.split(";");

            if ((chaine[0].matches("Competence") || chaine[0].matches("Ressource")) && chaine.length == 3 && chaine[1].matches("[RPS][1-6]\\.[0-9][0-9]")) {
                saveIdentifiantR = chaine[1];
            } else {
                System.out.println("erreur4");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit" + line);
            }
            line = newLine();
            if (!line.equals(MODELE[3])) {
                System.out.println("erreur3");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit");
            }
            // verification des prochaines lignes
            ArrayList<Evaluation> evals = new ArrayList();
            for (line = newLine(); line != null
                    && !line.split(";")[0].equals("Competence")
                    && !line.split(";")[0].equals("Ressource"); line = newLine()) {
                chaine = line.split(";");
                if (chaine.length != 3) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: pas 3 colonne");
                }
                if (!chaine[2].matches("\\d*")) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: " + chaine[2]);
                }
                
                calculCoeff += Integer.parseInt(chaine[2]);
                evals.add(new Evaluation(chaine[0], chaine[1], Double.parseDouble(chaine[2])));
            }
            if (calculCoeff == 100) {
                // Recherche de la ressource ou des ressources dans stockage avec le même identifiant
                ArrayList<Object> ressource = Stockage.getInstance().recherche(saveIdentifiantR);
                // Verfication que la ressource existe
                if(ressource.size() == 0) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: La ressource avec comme identifiant " + saveIdentifiantR + " n'existe pas");
                }
                Stockage.getInstance().addEvaluations(evals);
                // Pour chaque ressource recherché, ajouté les evaluations 
                for(Object o : ressource) {
                    if (o instanceof Ressource) {
                        for(Evaluation e: evals) {
                            ((Ressource) o).ajouterEvaluation(e);
                        }
                    }
                }
            } else {
                throw new MauvaisFormatFichierException("La compétence " + saveIdentifiantR + " a une somme des coefficients pas égale à 100: " + calculCoeff);
            }

        }
        br.close();
        fr.close();
    }
    /**
     * eviter premier passage
     */
    public static boolean flag = true;

    /**
     *
     * @param br
     * @return
     * @throws IOException
     */
    public String newLine() throws IOException {
        String line = null;
        do {
            line = br.readLine();
            if (line == null) {
                return line;
            }
            if (flag) {
                flag = false;
            } else {
                numeroLigne++;
            }
            System.out.println(line);
            while (line.endsWith(";")) {
                line = (String) line.subSequence(0, line.length() - 1);
            }
            //System.out.println(line + numeroLigne);
        } while (line.isEmpty());
        return line;
    }

      /**
     * 
     */
     public static void createCsv() {
        File file = new File("RessourceExporte.csv");
        String csv = "RessourceExporte.csv";
        ArrayList<String> idDejaApparue = new ArrayList();
        try {
            if(file.exists()) {
                file.delete();
                file.createNewFile();
            } else {
                file.createNewFile();
            }
            
        } catch (IOException ex) {
            
        }
        try (BufferedWriter ecritureLigne = new BufferedWriter(new FileWriter(csv))){
            ecritureLigne.write("BUT Informatique - Modalite Controle de connaissances ressources semestre 2\n");
            System.out.println("BUT Informatique - Modalite Controle de connaissances ressources semestre 2\n");
            ecritureLigne.write("Semestre;2\nParcours;Tous\n");
            for(Ressource r: Stockage.getInstance().ressources) {
                if (!idDejaApparue.contains(r.getIdentifiant())){
                    idDejaApparue.add(r.getIdentifiant());
                    if(r.getEvaluation().size() != 0) {
                        ecritureLigne.write("Ressource;"+ r.getIdentifiant()+";"+r.getLibelle()+ "\n");
                        ecritureLigne.write("Type evaluation;Date;Poids\n");


                    }
                    for(Evaluation e: r.getEvaluation()) {
                        ecritureLigne.write(e.getType() +";"+ e.getDate()+";"+ ((int)e.getCoefficient())+ "\n");  
                    }
                }
            }
           
        } catch (IOException ex) {
           
        }
    }

}