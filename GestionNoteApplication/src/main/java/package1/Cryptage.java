package GestionNoteApplication.src.main.java.package1;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Classe pour le cryptage des données. Cette classe permet de réaliser des
 * opérations de cryptage et de décryptage avec Diffie-Hellman et Vigenère. Elle
 * gère également la génération de clés de chiffrement.
 *
 * @author ahmed.bribach, Alexandres Brouzes
 */
public class Cryptage {

    public static HashMap<Integer, Character> dico = new HashMap<>();
    public static HashMap<Character, Integer> dicoReverse = new HashMap<>();
    public static int bOUa;
    public static String cle = "";
    public static int p = 509;
    public static int g;
    public static final String ENSEMBLE_CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzàéèê,;:/.ô()-'" + " ";


    /**
     * Génère un nombre premier aléatoire pour p > 10.000
     */
    public static void genereP() {
        Random random = new Random();
        int nb = random.nextInt(10001) + 10000;;
        System.out.println(nb);
        while (!estNombrePremier(nb)) {
            nb++;
        }

        System.out.println(nb + " est le nouveau nombre premier");
        p = nb;
    }


    /**
     * Vérifie si un nombre est premier.
     *
     * @param nombre Le nombre à vérifier.
     * @return true si le nombre est premier, sinon false.
     */
    public static boolean estNombrePremier(int nombre) {
        if (nombre <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(nombre); i++) {
            if (nombre % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
    * Effectue une partie du protocole de Diffie-Hellman pour générer un nombre partagé.
    *
    * @return La valeur calculée selon l'algorithme de Diffie-Hellman
    */
    public static int codeAliceEtBob() {

        // Génère un nombre aléatoire entre 0 et p - 2
        bOUa = (int) (Math.random() * p - 1);

        // Calcule g^bOUa modulo p
        int B = expModulaire(g, bOUa, p);
        return B;
    }

    /**
    * Décode le secret partagé entre Alice et Bob à l'aide du protocole de Diffie-Hellman.
    *
    * @param BouA Valeur échangée entre Alice et Bob
    * @param aOUb Valeur secrète d'Alice ou de Bob
    * @return La clé secrète partagée entre Alice et Bob
    */
    public static int decodeAliceOuBOB(int BouA, int aOUb) {
        // Calcule BouA^aOUb modulo p
        int cleSecrete = expModulaire(BouA, aOUb, p);
        return cleSecrete;
    }

    /**
    * Calcule de manière efficace l'exponentiation modulaire.
    *
    * @param base     La base de l'exponentiation
    * @param exposant L'exposant de l'exponentiation
    * @param modulo   Le modulo utilisé pour le calcul
    * @return Le résultat de l'exponentiation modulaire (base^exposant mod modulo)
    */
    public static int expModulaire(int base, int exposant, int modulo) {
        int resultat = 1;

        // Prend la base pour quel respecte les critéres du modulo
        base = base % modulo;

        while (exposant > 0) {
            // Si l'exposant est impair, multiplie le résultat par la base et prend le modulo
            if (exposant % 2 == 1) {
                resultat = (resultat * base) % modulo;
            }

            // Divise l'exposant par 2 (décalage binaire vers la droite)
            exposant = exposant >> 1;

            // Met à jour la base en élevant au carré et en prenant le modulo
            base = (base * base) % modulo;
        }

        return resultat;
    }

    /**
    * Remplit les dictionnaires 'dico' et 'dicoReverse' avec les caractères de l'ensemble spécifié.
    * 'dico' associe des entiers à des caractères, 'dicoReverse' associe des caractères à des entiers.
    */
    public static void remplissageduDico() {
        for (int i = 0; i < ENSEMBLE_CARACTERES.length(); i++) {
            char character = ENSEMBLE_CARACTERES.charAt(i);
            
            dico.put(i, character);

            dicoReverse.put(character, i);
        }

    }

    /**
    * Crée une nouvelle clé de chiffrement pour Alice ou Bob en utilisant les valeurs passées.
    * La méthode applique un algorithme de substitution à la clé existante ('cle') en utilisant 'BouA' et 'bOUa'.
    * 
    * @param BouA Valeur utilisée pour le chiffrement
    */
    public static void creationClefBobOuAlice(int BouA) {

        int nouvelIndex;
        String CleRemplacement = "";

        // Parcours de chaque caractère de la clé existante pour la substituer
        for (int i = 0; i < cle.length(); i++) {

            // Calcul du nouvel index pour chaque caractère de la clé
            nouvelIndex = (dicoReverse.get(cle.charAt(i)) + decodeAliceOuBOB(BouA, bOUa)) % ENSEMBLE_CARACTERES.length();
            
            // Ajout du caractère correspondant au nouvel index dans la nouvelle clé
            CleRemplacement += dico.get(nouvelIndex);
        }
        cle = CleRemplacement;

    }

    /**
    * Crypte un message en utilisant la clé fournie via la méthode de substitution de Vigenère.
    * 
    * @param cle             Clé utilisée pour le chiffrement
    * @param messageACrypter Message à crypter
    * @return                Message crypté
    */
    public static String cryptage(String cle, String messageACrypter) {

        String chaine = "";

        // Parcourt chaque caractère du message à crypter
        for (int i = 0; i < messageACrypter.length(); i++) {
            // Calcule le nouvel indice en utilisant la clé
            int nombre = (dicoReverse.get(messageACrypter.charAt(i)) + dicoReverse.get(cle.charAt(i % cle.length()))) % ENSEMBLE_CARACTERES.length();
            
            // Obtient le caractère correspondant au nouvel indice et l'ajoute au message crypté
            chaine += dico.get(nombre);
        }
        return chaine;
    }

    /**
    * Déchiffre un message crypté à l'aide de la clé fournie, selon la méthode de substitution de Vigenère inversée.
    * 
    * @param cle            Clé utilisée pour le déchiffrement
    * @param messageCrypter Message crypté à déchiffrer
    * @return               Message déchiffré
    */
    public static String decryptage(String cle, String messageCrypter) {
        remplissageduDico();
        String chaine = "";
        for (int i = 0; i < messageCrypter.length(); i++) {
            int nombre = (dicoReverse.get(messageCrypter.charAt(i)) - dicoReverse.get(cle.charAt(i % cle.length()))) % ENSEMBLE_CARACTERES.length();
            if (nombre < 0) {
                nombre += ENSEMBLE_CARACTERES.length();
            }

            // Obtient le caractère correspondant à l'indice et l'ajoute au message déchiffré
            chaine += dico.get(nombre);
        }
        return chaine;
    }

    /**
    * Génère la première étape de la clé pour le cryptage Diffie-Hellman avec la méthode de Vigenère.
    * 
    * @return La clé générée pour le cryptage
    */
    public static String creationCleEtape1() {
        remplissageduDico();
        genereP();
        genereG();

        String chaineCle = "";

        // Génère une chaîne de caractères aléatoire pour la clé
        for (int i = 0; i < (int) (Math.random() * 1000); i++) {
            chaineCle += dico.get((int) (Math.random() * ENSEMBLE_CARACTERES.length()));
        }
        cle = chaineCle;
        return cle;
    }

    /**
    * Teste si le nombre g est un générateur de tous les éléments dans le champ modulo p.
    * 
    * @param g Le nombre à tester pour être un générateur
    * @return Vrai s'il s'agit d'un générateur, faux sinon
    */
    public static boolean testG(int g) {
        ArrayList<Integer> G = new ArrayList<>();

        // Vérifie tous les exposants de g dans le champ modulo p
        for (int i = 0; i < p - 1; i++) {
            // Si la liste G contient déjà une valeur de g^i modulo p, alors g n'est pas un générateur
            if (G.contains(expModulaire(g, i, p))) {
                return false;
            }
            // Ajoute g^i modulo p à la liste G
            G.add(expModulaire(g, i, p));
        }
        // Si aucune valeur n'est répétée dans la liste G, g est un générateur
        return true;
    }

    /**
    * Génère un nombre g qui est un générateur de tous les éléments dans le champ modulo p.
    * 
    * @return Vrai si un nombre g a été trouvé et défini comme générateur, faux sinon
    */
    public static boolean genereG() {
        List<Integer> toutLesGPossible = new ArrayList<>();

        // Recherche des générateurs potentiels jusqu'à ce que 20 générateurs soient trouvés ou que la limite de p-1 soit atteinte
        for (int i = 2; i < p - 1 && toutLesGPossible.size() < 20; i++) {
            // Teste si i est un générateur
            if (testG(i)) {
                toutLesGPossible.add(i);
            }
        }
        System.out.println("enfin");

        // Si des générateurs ont été trouvés
        if (!toutLesGPossible.isEmpty()) {
            // Choix aléatoire d'un générateur parmi la liste des générateurs potentiels
            Random random = new Random();
            g = toutLesGPossible.get(random.nextInt(toutLesGPossible.size()));
            return true;
        }
        return false;
    }



}
