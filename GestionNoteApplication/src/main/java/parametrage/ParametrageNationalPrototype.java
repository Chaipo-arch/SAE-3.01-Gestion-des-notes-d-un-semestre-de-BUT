/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.parametrage;


import GestionNoteApplication.src.main.java.package1.MauvaisFormatFichierException;

import GestionNoteApplication.src.main.java.package1.Stockage;
import GestionNoteApplication.src.main.java.package1.Competence;
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

/**
 *
 * @author enzo.cluzel
 */
public class ParametrageNationalPrototype extends Parametrage {

    /**
     * TODO comment field role (attribute, association)
     */
    protected final String[] MODELE = {"BUT Informatique - Modalite Controle de connaissances (programme national)", "Semestre", "Parcours", "Type evaluation;Identifiant;Libelle;Poids"};

    private static int numeroLigne;

    /**
     * TODO comment initial state
     *
     * @param chemin
     * @throws IOException
     */
    public ParametrageNationalPrototype(File chemin) throws IOException, MauvaisFormatFichierException, EvaluationException {
        super(chemin);
        flag = true;
    }

    @Override
    public void parse() throws IOException, MauvaisFormatFichierException, NoteException {
        FileReader fr = null;
        // prerequis
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // bufferedReader plus pratique
        BufferedReader br = new BufferedReader(fr);
        // lecture ligne par ligne
        numeroLigne = 0;
        String line;
        for (line = newLine(br); numeroLigne < 3; line = newLine(br)) {
            String[] chaine = line.split(";");
            if (!chaine[0].equals(MODELE[numeroLigne])) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal ecrit " );
            }
            //System.out.println(chaine.length);
            if(chaine.length != 2 && numeroLigne == 1) {
               
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal ecrit: pas 2 colonnes" );
                
            } else if(numeroLigne == 1 &&!chaine[1].matches("^[1-6]$")) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal ecrit: la deuxieme colonne n'a pas de chiffre entre 1 et 6");
            }
            if (numeroLigne == 2 && chaine.length != 2) {
                
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal ecrit: pas 2 colonnes");
            } else if( numeroLigne == 2 && !chaine[1].matches("^[ABCD]$|^ $|^Tous$")) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) +  " est mal ecrit: la deuxieme colonne n'a pas ABCD, rien ou Tous");
            }

        }
        String saveIdentifiantC = null;
        int calculCoeff;
        while (line != null) {
            calculCoeff = 0;
            String[] chaine = line.split(";");

            if (chaine[0].equals("Competence") && chaine.length == 3 && chaine[1].matches("U[1-6]\\.[1-9]")) {
                //System.out.println("passage2");
                ArrayList<Competence> comps = new ArrayList();

                Competence comp = new Competence(chaine[1],chaine[2]);
                saveIdentifiantC = chaine[1];
                comps.add(comp);
                Stockage.getInstance().addCompetences(comps);
            } else {
                System.out.println("erreur4");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal ecrit" + line);
            }
            line = newLine(br);
            if (!line.equals(MODELE[3])) {
                System.out.println("erreur3");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal ecrit");
            }
            //System.out.println("passage1");
            // verification des prochaines lignes
            ArrayList<Ressource> ress = new ArrayList();
            for (line = newLine(br); line != null
                    && !line.split(";")[0].equals("Competence"); line = newLine(br)) {
                chaine = line.split(";");
                if (chaine.length != 4) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal ecrit: pas 4 colonne");
                }
                if (!chaine[0].matches("Ressource|Portfolio|SAE")) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal ecrit: " + chaine[0]);
                }
                if (!chaine[1].matches("[RPS][1-6]\\.[0-9][0-9]")) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne + " est mal ecrit: " + chaine[1]);
                }
                if (chaine[3].matches("-([0-9]){1,}|[^0-9]")) { //TODO gerer erreur a1
                    throw new MauvaisFormatFichierException("Le fichier à la ligne  " + numeroLigne + " est mal ecrit: " + chaine[3]);
                }
                calculCoeff += Integer.parseInt(chaine[3]);
                ress.add(new Ressource(chaine[0], chaine[1], chaine[2], Double.parseDouble(chaine[3])));
            }
            if (calculCoeff == 100) {
               
                ArrayList<Object> compe = Stockage.getInstance().recherche(saveIdentifiantC);
                
                ress = Stockage.getInstance().addRessources(ress,(Competence)compe.get(0));
                for(Object o : compe) {
                    if (o instanceof Competence) {
                       for(Ressource r : ress) {
                           ((Competence) o).ajouterRessource(r);
                       }
                   }
                }
            } else {
                throw new MauvaisFormatFichierException("La competence " + saveIdentifiantC + " a une somme des coefficients pas egale à 100: " + calculCoeff);
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
    
    /**
     * 
     */
     public static void createCsv() {
        File file = new File("NationalExporte.csv");
        String csv = "NationalExporte.csv";
        try {
            file.createNewFile();
        } catch (IOException ex) {
            
        }
        //ACBufferedWriter ecritureLigne = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\test.csv"), "UTF-8")
        try (BufferedWriter ecritureLigne = new BufferedWriter(new FileWriter(csv))){
            ecritureLigne.write("BUT Informatique - Modalite Controle de connaissances (programme national)\n");
            ecritureLigne.write("Semestre;2\nParcours;Tous\n");
            for(Competence c : Stockage.getInstance().competences) {
                ecritureLigne.write("Competence;"+ c.identifiant+";"+c.libelle+ "\n");
                ecritureLigne.write("Type evaluation;Identifiant;Libelle;Poids\n");
                for(Ressource r: c.getRessources()) {
                    ecritureLigne.write(r.getType() +";"+ r.getIdentifiant()+";"+r.getIdentifiant()+ ";" +(int) r.getCoefficient()+"\n");
                }
            }
        } catch (IOException ex) {
            
        }
    }

}
