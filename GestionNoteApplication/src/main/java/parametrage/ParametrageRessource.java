package GestionNoteApplication.src.main.java.parametrage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author enzo.cluzel
 */
public class ParametrageRessource extends Parametrage {

   /** TODO comment field role (attribute, association) */
   protected final String[] MODELE = {"BUT Informatique - Modalit� Contr�le de connaissances ressources semestre","Semestre","Parcours","Type �valuation;Date;Poids"};
   
   /** TODO comment field role (attribute, association) */
   protected ArrayList<ArrayList<String>> contenueAAjouter = new ArrayList();//TODO modifier nom + enlever si isCorrect() mis dans parse()
   
   /**
    * TODO comment initial state
    * @param chemin
    * @throws IOException 
    */
   public ParametrageRessource(String chemin) throws IOException {
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
           
           Object aTrouver = Stockage.getInstance().recherche(chaine[1]);
           ArrayList<Evaluation> evals = new ArrayList();
           for(int ligne = 2; ligne< aAjouter.size(); ligne++) {
               chaine = aAjouter.get(ligne).split(";");
               evals.add(new Evaluation(chaine[0], chaine[1], chaine[2]));
           }
           Stockage.getInstance().addEvaluations(evals);
           if(aTrouver instanceof Ressource) {
               ((Ressource) aTrouver).ajouterEvaluations(evals);
           }
       }
   }

   @Override
   public boolean isCorrect() {
       boolean resultat = true;
       // verification des 3 premieres lignes
       for (int l=0 ; l< 3; l++) {
           String[] chaine = contenue.get(0).split(";");
           
           if(l == 0) {
               chaine[0] = chaine[0].substring(0, chaine[0].length()-2);
           }
        // si égal au modéle alors bon 
           if(chaine[0].equals(MODELE[l])&& (l == 0 || chaine[1].matches("^[ABCD]$|^ $|^Tous$|^[1-6]$"))) { // TODO ajouter condition
               contenue.remove(0); // stocké ou pas ce qui est enlever ?
           } else {
              System.out.println(MODELE[l]);
              System.out.println(chaine[0]);
               resultat = false;
           }
       }
       // on crée une liste par élément à ajouter dans le parse pour verifie
       /* exemple
            Ressource;R2.01;D�veloppement orient� objets
           Type �valuation;Date;Poids
           QCM;Mi septembre;15
           Ecrit;15 octobre;40
           Relev�s TPs;;5
           Ecrit;15/12/2023;40
        */
       ArrayList<ArrayList<String>> aVerifier = new ArrayList();//TODO modifier nom

       String[] chaine = contenue.get(0).split(";"); //TODO modifier nom
       while(contenue.size() != 0 && resultat != false) {
           // verification des 2 premiere lignes
           if((chaine[0].equals("Ressource") ||chaine[0].equals("Comp�tence"))  && chaine.length == 3) {
               ArrayList<String> cat = new ArrayList(); // TODO modifier nom
               for(int l=0;l< 2;l++) {
                   cat.add(contenue.get(0));
                   contenue.remove(0);
               }
               // verification des prochaines lignes
               while (contenue.size() != 0 && chaine.length == 3 && !contenue.get(0).split(";")[0].equals("Ressource") && !contenue.get(0).split(";")[0].equals("Comp�tence")) {
                   cat.add(contenue.get(0));
                   contenue.remove(0);
                   
               }
               aVerifier.add(cat);

               // verification 
               for (ArrayList<String> aAjouter : aVerifier) { // TODO ajouter condition
                   if(aAjouter.get(1).equals(MODELE[3])) {
                       
                   } else {
                       resultat = false;
                   }
               }
           } else {
               contenue.remove(0); // on enleve jusqu'a qu'on trouve quelque chose de correcte ou que ce ce soit vide
           }
          
           
       }
           System.out.println(aVerifier.toString());

           contenueAAjouter = aVerifier;
           return resultat;
   }

}

