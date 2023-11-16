/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.parametrage;

import GestionNoteApplication.src.main.java.modele.MauvaisFormatFichierException;
import GestionNoteApplication.src.main.java.modele.Stockage;
import GestionNoteApplication.src.main.java.package1.Evaluation;
import GestionNoteApplication.src.main.java.package1.EvaluationException;
import GestionNoteApplication.src.main.java.package1.Ressource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    protected final String[] MODELE = {"BUT Informatique - Modalit� Contr�le de connaissances ressources semestre", "Semestre", "Parcours", "Type �valuation;Date;Poids"};

    private static int numeroLigne;

    /**
     * TODO comment initial state
     *
     * @param chemin
     * @throws IOException
     */
    public ParametrageRessourcePrototype(File chemin) throws IOException, MauvaisFormatFichierException {
        super(chemin);
    }

    @Override
    public void parse()  {
        FileReader fr = null;
        //System.out.println("ok");
        // prerequis
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try{
            
        
        // bufferedReader plus pratique
        BufferedReader br = new BufferedReader(fr);
        // lecture ligne par ligne
        numeroLigne = 0;
        String line;
        for (line = newLine(br); numeroLigne < 3; line = newLine(br)) {
            String[] chaine = line.split(";");
            if (numeroLigne == 0) {
                chaine[0] = chaine[0].substring(0, chaine[0].length() - 2);
            }
           if (!chaine[0].equals(MODELE[numeroLigne])) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit " );
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
        String saveIdentifiantR = null;
        int calculCoeff;
        while (line != null) {
            calculCoeff = 0;
            String[] chaine = line.split(";");

            if ((chaine[0].equals("Comp�tence") || chaine[0].equals("Ressource")) && chaine.length == 3 && chaine[1].matches("[RPS][1-6]\\.[0-9][0-9]")) {
                saveIdentifiantR = chaine[1];
            } else {
                System.out.println("erreur4");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne + " est mal écrit" + line);
            }
            line = newLine(br);
            if (!line.equals(MODELE[3])) {
                System.out.println("erreur3");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne + " est mal écrit");
            }
            //System.out.println("passage1");
            // verification des prochaines lignes
            ArrayList<Evaluation> evals = new ArrayList();
            for (line = newLine(br); line != null
                    && !line.split(";")[0].equals("Comp�tence")
                    && !line.split(";")[0].equals("Ressource"); line = newLine(br)) {
                chaine = line.split(";");
                if (chaine.length != 3) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne + " est mal écrit: pas 3 colonne");
                }
                if (chaine[2].matches("-([0-9]){1,}|[(^0-9)]")) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne + " est mal écrit: " + chaine[2]);
                }
                
                calculCoeff += Integer.parseInt(chaine[2]);
                evals.add(new Evaluation(chaine[0], Double.parseDouble(chaine[2]), chaine[1]));
            }
            //System.out.println(calculCoeff);
            if (calculCoeff == 100) {
                Object ressource = Stockage.getInstance().recherche(saveIdentifiantR);
                Stockage.getInstance().addEvaluations(evals);
                if (ressource instanceof Ressource) {
                    for(int i=0 ; i < evals.size() ; i++){
                        ((Ressource) ressource).ajouterEvaluation(evals.get(i));

                    }

                }
            } else {
                throw new MauvaisFormatFichierException("La compétence " + saveIdentifiantR + " a une somme des coefficients pas égale à 100: " + calculCoeff);
            }

        }
        br.close();
        fr.close();
        }catch(Exception e){
            
        }
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
    public String newLine(BufferedReader br) throws IOException {
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

    @Override
    public boolean isCorrect() {
        return false;
    }

}
