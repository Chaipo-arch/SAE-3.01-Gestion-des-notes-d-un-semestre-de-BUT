/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parametrage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author enzo.cluzel robin.britelle
 */
public class ParametrageNational extends Parametrage {

    /** TODO comment field role (attribute, association) */
    protected final String[] MODELE = {"BUT Informatique - Modalit� Contr�le de connaissances (programme national)","Semestre","Parcours","Type �valuation;Identifiant;Libell�;Poids"};
    
    /** TODO comment field role (attribute, association) */
    protected ArrayList<ArrayList<String>> contenueAAjouter = new ArrayList();//TODO modifier nom + enlever si isCorrect() mis dans parse()
    
    /**
     * TODO comment initial state
     * @param chemin
     * @throws IOException 
     */
    public ParametrageNational(String chemin) throws IOException {
        super(chemin);

        ArrayList<String> result = new ArrayList<String>();

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
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            result.add(line);

        }

        br.close();
        fr.close();
        for(String ligne: result) {

            // enlever tout ; a la fin
            while(ligne.endsWith(";")) {

                ligne = (String) ligne.subSequence(0, ligne.length()-1);
            }
           

            // si ligne vide ne pas ajouter
            if (!ligne.isEmpty()) {
                contenue.add(ligne);
            }
        }
        if (isCorrect()) {
            parse();
        }
        



        //renvoie true s'il y a une autre ligne à lire



    }

    @Override
    public void parse() {
        for (ArrayList<String> aAjouter : contenueAAjouter) {
            String[] chaine = aAjouter.get(0).split(";");
            ArrayList<Competence> comps = new ArrayList();
            //System.out.println(chaine[1]);
            Competence comp = new Competence(chaine[1]);
            comps.add(comp);
            Stockage.getInstance().addCompetences(comps);
            Object compe= Stockage.getInstance().recherche(chaine[1]);
            ArrayList<Ressource> ress = new ArrayList();
            for(int ligne = 2; ligne< aAjouter.size(); ligne++) {
                chaine = aAjouter.get(ligne).split(";");
                ress.add(new Ressource(chaine[0], chaine[1], chaine[2], chaine[3]));
            }
            //System.out.println(ress.toString());
             ress = Stockage.getInstance().addRessources(ress);
            
            if(compe instanceof Competence) {
                ((Competence) compe).ajouterRessources(ress);
                //System.out.println
            }
            
            
        }
    }

    @Override
    public boolean isCorrect() {
        boolean resultat = true;
        // verification des 3 premieres lignes
        for (int l=0 ; l< 3; l++) {
            String[] chaine = contenue.get(0).split(";");
            //System.out.println(chaine[0]);
            
         // si égal au modéle alors bon
            if(chaine[0].equals(MODELE[l]) && (l == 0 || chaine[1].matches("^[ABCD]$|^ $|^Tous$|^[1-6]$"))) { // TODO ajouter condition
                contenue.remove(0); // stocké ou pas ce qui est enlever ?
            } else {
                //System.out.println("?");
                resultat = false;
            }
        }
        // on crée une liste par élément à ajouter dans le parse pour verifie
        /* exemple
             Compétence U2.1    Réaliser un développement d’application 
Type évaluation Identifiant     Libellé Poids
Ressource       R2.01   Développement orienté objets    21
Ressource       R2.02   Développement d'applications avec IHM   21
Ressource       R2.03   Qualité de développement        12
Ressource       R2.13   Communication technique 6
Portfolio       P2.01   Portfolio       2
SAE     S2.01   Développement d'une application 38
         */
        ArrayList<ArrayList<String>> aVerifier = new ArrayList();//TODO modifier nom

        String[] chaine = contenue.get(0).split(";"); //TODO modifier nom
        //System.out.println(contenue.get(0));
        //System.out.println(contenue.size());
        while(contenue.size() != 0 && resultat != false) {
           //System.out.println(chaine.length);
            // verification des 2 premiere lignes
            if(chaine[0].equals("Comp�tence") && chaine.length == 3 ) {
                //System.out.println(contenue.get(0));
                ArrayList<String> cat = new ArrayList(); // TODO modifier nom
                for(int l=0;l< 2;l++) {
                    //System.out.println(contenue.get(0));
                    cat.add(contenue.get(0));
                    contenue.remove(0);
                }
                // verification des prochaines lignes
                while (contenue.size() != 0 && !contenue.get(0).split(";")[0].equals("Comp�tence") && chaine.length == 3) {
                    //System.out.println(contenue.get(0));
                    cat.add(contenue.get(0));
                    contenue.remove(0);
                    
                }
                aVerifier.add(cat);

                // verification 
                for (ArrayList<String> aAjouter : aVerifier) { // TODO ajouter condition
                    if(aAjouter.get(1).equals(MODELE[3])) {
                        
                    } else {
                        //System.out.println("?");
                        resultat = false;
                    }
                }
            } else {
                contenue.remove(0); // on enleve jusqu'a qu'on trouve quelque chose de correcte ou que ce ce soit vide
            }
           
            
        }
            //System.out.println(aVerifier.toString());

            contenueAAjouter = aVerifier;
            return resultat;
    }

}

