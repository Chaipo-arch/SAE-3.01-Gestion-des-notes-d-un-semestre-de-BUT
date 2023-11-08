/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;

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
public class ParametrageNationalPrototype extends Parametrage {

    /** TODO comment field role (attribute, association) */
    protected final String[] MODELE = {"BUT Informatique - Modalit� Contr�le de connaissances (programme national)","Semestre","Parcours","Type �valuation;Identifiant;Libell�;Poids"};
    
    /** TODO comment field role (attribute, association) */
    protected ArrayList<ArrayList<String>> contenueAAjouter = new ArrayList();//TODO modifier nom + enlever si isCorrect() mis dans parse()
    
    private static int numeroLigne;
    /**
     * TODO comment initial state
     * @param chemin
     * @throws IOException 
     */
    public ParametrageNationalPrototype(String chemin) throws IOException, MauvaisFormatFichierException {
        super(chemin);
    }
    @Override
    public void parse() throws IOException, MauvaisFormatFichierException {
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
        for (String line = newLine(br); numeroLigne < 3; line = newLine(br)) {
            
                String[] chaine = line.split(";");
                if(chaine[0].equals(MODELE[numeroLigne]) && (numeroLigne == 0 || chaine[1].matches("^[ABCD]$|^ $|^Tous$|^[1-6]$"))) {
                     
                } else {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne +" est mal écrit"  + line);
                }
                
            

        }
        for (String line = newLine(br); line != null; line = newLine(br)) {
            
                String[] chaine = line.split(";");
                String saveIdentifiantC ;
                if(chaine[0].equals("Comp�tence") && chaine.length == 3 && chaine[1].matches("U[1-6]\\.[1-9]") ) {
                    ArrayList<Competence> comps = new ArrayList();
                    
                    Competence comp = new Competence(chaine[1]);
                    saveIdentifiantC = chaine[1];
                    comps.add(comp);
                    Stockage.getInstance().addCompetences(comps);
                    line = br.readLine();
                    numeroLigne ++;
                } else {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne +" est mal écrit" + line);
                }
                if(!line.equals(MODELE[3])) {
                     throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne +" est mal écrit");
                }
                
                // verification des prochaines lignes
                for (line = newLine(br); line !=null 
                        && line.split(";")[0].equals("Comp�tence")
                        && chaine.length == 3; line = newLine(br), numeroLigne++) {
                    chaine = line.split(";");
                    if(!chaine[0].matches("Ressource|Portfolio|SAE")) {
                        throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne +" est mal écrit: " +chaine[0]);
                    }
                    if(!chaine[1].matches("[RPS][1-6]\\.[1-9]")) {
                         throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne +" est mal écrit: " +chaine[1]);
                    }
                    Object compe= Stockage.getInstance().recherche(saveIdentifiantC);
                     ArrayList<Ressource> ress = new ArrayList();
                     
                      
                    ress.add(new Ressource(chaine[0], chaine[1], chaine[2], chaine[3]));
                    
                        //System.out.println(ress.toString());
                    ress = Stockage.getInstance().addRessources(ress);

                    if(compe instanceof Competence) {
                       ((Competence) compe).ajouterRessources(ress);
                            //System.out.println
                    }    
                
                    
                }
                
                   
            
        }

       
        



        //renvoie true s'il y a une autre ligne à lire



    }
 /** eviter premier passage */
public static boolean flag = true;
  public String newLine(BufferedReader br) throws IOException {
    String line = null;
    do {
        line= br.readLine();
        if(flag) {
            flag = false;
        } else {
            numeroLigne++;
        }
      
    while(line.endsWith(";")) {
        line = (String) line.subSequence(0, line.length()-1);
    }
    
    } while(line.isEmpty());
    return line;
  }

    @Override
    public boolean isCorrect() {
        return false;
    }

}
