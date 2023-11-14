/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionNoteApplication.src.main.java.package1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author enzo.cluzel
 */
public class ParametrageNationalPrototype extends Parametrage {

    /**
     * TODO comment field role (attribute, association)
     */
    protected final String[] MODELE = {"BUT Informatique - Modalit� Contr�le de connaissances (programme national)", "Semestre", "Parcours", "Type �valuation;Identifiant;Libell�;Poids"};

    private static int numeroLigne;

    /**
     * TODO comment initial state
     *
     * @param chemin
     * @throws IOException
     */
    public ParametrageNationalPrototype(String chemin) throws IOException, MauvaisFormatFichierException {
        super(chemin);
    }

    @Override
    public void parse() {
        FileReader fr = null;
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
            if (!chaine[0].equals(MODELE[numeroLigne])) {
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit " );
            }
            //System.out.println(chaine.length);
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
        String saveIdentifiantC = null;
        int calculCoeff;
        while (line != null) {
            calculCoeff = 0;
            String[] chaine = line.split(";");

            if (chaine[0].equals("Comp�tence") && chaine.length == 3 && chaine[1].matches("U[1-6]\\.[1-9]")) {
                //System.out.println("passage2");
                ArrayList<Competence> comps = new ArrayList();

                Competence comp = new Competence(chaine[1],chaine[2]);
                saveIdentifiantC = chaine[1];
                comps.add(comp);
                Stockage.getInstance().addCompetences(comps);

            } else {
                System.out.println("erreur4");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit" + line);
            }
            line = newLine(br);
            if (!line.equals(MODELE[3])) {
                System.out.println("erreur3");
                throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit");
            }
            //System.out.println("passage1");
            // verification des prochaines lignes
            ArrayList<Ressource> ress = new ArrayList();
            for (line = newLine(br); line != null
                    && !line.split(";")[0].equals("Comp�tence"); line = newLine(br)) {
                chaine = line.split(";");
                if (chaine.length != 4) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: pas 4 colonne");
                }
                if (!chaine[0].matches("Ressource|Portfolio|SAE")) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + (numeroLigne+1) + " est mal écrit: " + chaine[0]);
                }
                if (!chaine[1].matches("[RPS][1-6]\\.[0-9][0-9]")) {
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne + " est mal écrit: " + chaine[1]);
                }
                if (chaine[3].matches("-([0-9]){1,}|[^0-9]")) { //TODO gerer erreur a1
                    throw new MauvaisFormatFichierException("Le fichier à la ligne " + numeroLigne + " est mal écrit: " + chaine[3]);
                }
                calculCoeff += Integer.parseInt(chaine[3]);
                ress.add(new Ressource(chaine[2], Double.parseDouble(chaine[3]), chaine[1], chaine[0]));
            }
            if (calculCoeff == 100) {
                Object compe = Stockage.getInstance().recherche(saveIdentifiantC);
                ress = Stockage.getInstance().addRessources(ress);
                if (compe instanceof Competence) {
                    for(int i=0 ; i < ress.size() ; i++){
                        ((Competence) compe).ajouterRessource(ress.get(i));
                    }
                    

                }
            } else {
                throw new MauvaisFormatFichierException("La compétence " + saveIdentifiantC + " a une somme des coefficients pas égale à 100: " + calculCoeff);
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

    
    
    public void envoyer() throws Exception {
        InetAddress serveur = InetAddress.getByName("10.2.6.20"); // Remplacez par votre adresse IP ou nom d'hôte
        Socket cli = new Socket(serveur, 9632);
        byte[] byteArray = new byte[(int) file.length()];
        
        
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(byteArray, 0, byteArray.length);
        
        OutputStream os = cli.getOutputStream();
        System.out.println("Envoi du fichier " + file);
        os.write(byteArray, 0, byteArray.length);
        os.flush();
    }
    
    public static void recevoir() throws Exception {
        ServerSocket serv = new ServerSocket(9632);
        Socket clientSocket = serv.accept();
       
        
        InputStream is = clientSocket.getInputStream();
        FileOutputStream fos = new FileOutputStream("D:/tpDevWeb/monFichier.csv");
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] byteArray = new byte[1024];
        int bytesRead;
        
        while ((bytesRead = is.read(byteArray, 0, byteArray.length)) != -1) {
            bos.write(byteArray, 0, bytesRead);
        }
        
        bos.close();
        fos.close();
        is.close();
        clientSocket.close();
        serv.close();
        // Vous pouvez maintenant utiliser clientSocket pour recevoir des données du client.
    }


    
    public static void main(String[] args) throws Exception{
        ParametrageNationalPrototype para = new ParametrageNationalPrototype("D:/tpDevWeb/Paramétrage semestre2.csv");
        
        //para.recevoir();
        para.envoyer();
    }
}
