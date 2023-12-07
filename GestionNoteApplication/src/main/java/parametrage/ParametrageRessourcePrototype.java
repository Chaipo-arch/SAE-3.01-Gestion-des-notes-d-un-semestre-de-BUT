/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.parametrage;

//import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;
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
    protected final String[] MODELE = {"BUT Informatique - Modalite Controle de connaissances ressources semestre " + Stockage.getInstance().getSemestre(), "Semestre", "Parcours", "Type evaluation;Date;Poids"};

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

    @Override
    public void parse() throws IOException, MauvaisFormatFichierException, EvaluationException, NoteException {
        MODELE[0] = "BUT Informatique - Modalite Controle de connaissances ressources semestre " + Stockage.getInstance().getSemestre();
        if (Stockage.getInstance().getSemestre() ==0) {
            throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: un fichier national n'a pas était importé", "Erreur Importation" );
        }
        FileReader fr = null;
        //System.out.println("ok");
        // prerequis
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // bufferedReader plus pratique
         br = new BufferedReader(fr);
        // lecture ligne par ligne
        numeroLigne = 0;
        String line;
        for (line = newLine(); numeroLigne < 3; line = newLine()) {
            if (line == null) {
                //NotificationController
                throw new MauvaisFormatFichierException("Fichier vide" , "Erreur Importation" );
            }
            String[] chaine = line.split(";");
           if (!chaine[0].matches(MODELE[numeroLigne])) {
               System.out.println(chaine[0]);
               System.out.println("ok");
               
               System.out.println(numeroLigne);
               System.out.println(MODELE[numeroLigne]);
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit " + line + numeroLigne , "Erreur Importation" );
                
            }
            
             if(chaine.length != 2 && numeroLigne == 1 ) {
               
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: pas 2 colonnes" , "Erreur Importation" );
                
            } else if(numeroLigne == 1 &&!chaine[1].matches("^[1-6]$") || numeroLigne == 1 && Stockage.getInstance().getSemestre() != Integer.parseInt(""+chaine[1].charAt(0))) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: Le semestre ne correspond pas au semestre importé dans le fichier national", "Erreur Importation" );
            }
            if (numeroLigne == 2 && chaine.length != 2) {
                
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: pas 2 colonnes", "Erreur Importation" );
            } else if( numeroLigne == 2 && !chaine[1].matches("^[ABCD]$|^ $|^Tous$") && chaine[1].equals(Stockage.getInstance().getParcour())) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) +  " est mal écrit: Le parcours ne correspond pas au semestre importé dans le fichier national", "Erreur Importation" );
            }

        }
        String saveIdentifiantR = null;
        int calculCoeff;
        while (line != null) {
            calculCoeff = 0;
            String[] chaine = line.split(";");

            if ((chaine[0].matches("Competence") || chaine[0].matches("Ressource")) && chaine.length == 3 && chaine[1].matches("[RPS][1-6]\\.[0-9][0-9]") && Stockage.getInstance().getSemestre() == Integer.parseInt("" +chaine[1].charAt(1))) {
                saveIdentifiantR = chaine[1];
            } else {
                System.out.println("erreur4");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit" + line, "Erreur Importation" );
            }
            line = newLine();
            if (!line.equals(MODELE[3])) {
                System.out.println("erreur3");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit", "Erreur Importation" );
            }
            //System.out.println("passage1");
            // verification des prochaines lignes
            ArrayList<Evaluation> evals = new ArrayList();
            for (line = newLine(); line != null
                    && !line.split(";")[0].equals("Competence")
                    && !line.split(";")[0].equals("Ressource"); line = newLine()) {
                chaine = line.split(";");
                if (chaine.length != 3) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: pas 3 colonne", "Erreur Importation" );
                }
                if (!chaine[2].matches("^-?\\d*\\.?\\d+$")) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: " + chaine[2], "Erreur Importation" );
                }
                
                calculCoeff += Double.parseDouble(chaine[2]);
                evals.add(new Evaluation(chaine[0], chaine[1], Double.parseDouble(chaine[2])));
            }
            //System.out.println(calculCoeff);
            if (calculCoeff == 100) {
                ArrayList<Object> ressource = Stockage.getInstance().recherche(saveIdentifiantR);
                if(ressource.size() == 0) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: La ressource avec comme identifiant " + saveIdentifiantR + " n'existe pas", "Erreur Importation" );
                }
                Stockage.getInstance().addEvaluations(evals);
                
                 for(Object o : ressource) {
                    if (o instanceof Ressource) {
                    for(Evaluation e: evals) {
                        ((Ressource) o).ajouterEvaluation(e);
                    }
                    

                }
                }
            } else {
                throw new MauvaisFormatFichierException("La compétence " + saveIdentifiantR + " a une somme des coefficients pas égale à 100: " + calculCoeff, "Erreur Importation" );
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
            file.createNewFile();
        } catch (IOException ex) {
           
        }
        try (BufferedWriter ecritureLigne = new BufferedWriter(new FileWriter(csv))){
            ecritureLigne.write("BUT Informatique - Modalite Controle de connaissances ressources semestre\n");
            ecritureLigne.write("Semestre;"+ Stockage.getInstance().getSemestre() +"\nParcours;"+ Stockage.getInstance().getParcour() +"\n");
            for(Ressource r: Stockage.getInstance().ressources) {
                if (!idDejaApparue.contains(r.getIdentifiant())){
                    idDejaApparue.add(r.getIdentifiant());
                    if(r.getEvaluation().size() != 0) {
                        ecritureLigne.write("Ressource;"+ r.getIdentifiant()+";"+r.getIdentifiant()+ "\n");
                        ecritureLigne.write("Type evaluation;Date;Poids\n");


                    }
                    for(Evaluation e: r.getEvaluation()) {
                        ecritureLigne.write(e.getType() +";"+ e.getDate()+";"+ e.getCoefficient()+ "\n");  
                    }
                }
            }
        } catch (IOException ex) { 
        }
    }
}