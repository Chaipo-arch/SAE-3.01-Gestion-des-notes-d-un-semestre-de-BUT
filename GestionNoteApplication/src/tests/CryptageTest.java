package GestionNoteApplication.src.tests;

import GestionNoteApplication.src.main.java.package1.Cryptage;

public class CryptageTest {
    
    private static int n = 0;

    public void setUp() {
        Cryptage.remplissageduDico();
        Cryptage.creationCleEtape1();
    }

    public void testExpModulaire() {
        System.out.println("\n=============================================="
                + "\ntestExpModulaire"+
                "\n==============================================");
        if (Cryptage.expModulaire(2, 3, 10) == 8) {
            System.out.println("Test ExpModulaire réussi : expModulaire(2, 3, 10) == 8");
            n+=1;
        } else {
            System.out.println("Le test ExpModulaire a échoué");
        }

        if (Cryptage.expModulaire(5, 0, 10) == 1) {
            System.out.println("Test ExpModulaire réussi :  expModulaire(5, 0, 10) == 1");
            n+=1;
        } else {
            System.out.println("Le test ExpModulaire a échoué");
        }
    }

    public void testCodeAlice() {
        int g = 3; // Valeur arbitraire pour g
        Cryptage.g = g; // Définition de la valeur arbitraire de g dans la classe Cryptage
        String codeAlice = Cryptage.codeAlice();
        
        System.out.println("\n=============================================="
                + "\ntestCode Alice et Bob"+
                "\n==============================================");
        

        if (codeAlice != null && codeAlice.matches("\\d+")) {
            int expectedA = Cryptage.expModulaire(g, Cryptage.a, Cryptage.p);
            if (codeAlice.equals(String.valueOf(expectedA))) {
                System.out.println("Test CodeAlice réussi : la valeur du code est egal a la valeur attendue");
                n+=1;
            } else {
                System.out.println("Le test CodeAlice a échoué");
            }
        } else {
            System.out.println("Le test CodeAlice a échoué");
        }
    }

    public void testCodeBob() {
        int g = 5; // Valeur arbitraire pour g
        Cryptage.g = g; // Définition de la valeur arbitraire de g dans la classe Cryptage
        String codeBob = Cryptage.codeBob();
        
        
        if (codeBob != null && codeBob.matches("\\d+")) {
            int expectedB = Cryptage.expModulaire(g, Cryptage.b, Cryptage.p);
            if (codeBob.equals(String.valueOf(expectedB))) {
                System.out.println("Test CodeBob réussi : la valeur du code est egal a la valeur attendue");
                n+=1;
            } else {
                System.out.println("Le test CodeBob a échoué");
            }
        } else {
            System.out.println("Le test CodeBob a échoué");
        }
    }

    public void testDecodeAlice() {
        int B = 5; // Valeur de test
        int a = 3; // Valeur de test
        
        System.out.println("\n=============================================="
                + "\ntestDecode Alice et Bob"+
                "\n==============================================");
        
        int cleSecrete = Cryptage.decodeAlice(B, a);
        int expectedCleSecrete = Cryptage.expModulaire(B, a, Cryptage.p);

        if (cleSecrete == expectedCleSecrete) {
            System.out.println("Test DecodeAlice réussi");
            n+=1;
        } else {
            System.out.println("Le test DecodeAlice a échoué");
        }
    }

    public void testDecodeBob() {
        int A = 5; // Valeur arbitraire pour A
        int b = 3; // Valeur arbitraire pour b
        int cleSecrete = Cryptage.decodeBob(A, b);
        int expectedCleSecrete = Cryptage.expModulaire(A, b, Cryptage.p);

        if (cleSecrete == expectedCleSecrete) {
            System.out.println("Test DecodeBob réussi");
            n+=1;
        } else {
            System.out.println("Le test DecodeBob a échoué");
        }
    }

    public void testCryptage() {
        String cle = "cle"; // Clé utilisée pour le cryptage
        String messageOriginal = "message"; // Message original à crypter
        
        System.out.println("\n=============================================="
                + "\nCryptage"+
                "\n==============================================");

        String messageCrypte = Cryptage.cryptage(cle, messageOriginal);

        if (messageCrypte != null && !messageCrypte.equals(messageOriginal)) {
            System.out.println("Test Cryptage réussi");
            n+=1;
        } else {
            System.out.println("Le test Cryptage a échoué");
        }
    }

    public void testDecryptage() {
        String cle = "cle"; // Clé utilisée pour le cryptage
        String messageOriginal = "message"; // Message original à crypter

        String messageCrypte = Cryptage.cryptage(cle, messageOriginal);
        String messageDecrypte = Cryptage.decryptage(cle, messageCrypte);

        if (messageDecrypte != null && messageOriginal.equals(messageDecrypte)) {
            System.out.println("Test Decryptage réussi");
            n+=1;
        } else {
            System.out.println("Le test Decryptage a échoué");
        }
    }

    public static void main(String[] args) {
        CryptageTest tester = new CryptageTest();
        tester.setUp();

        tester.testExpModulaire();
        tester.testCodeAlice();
        tester.testCodeBob();
        tester.testDecodeAlice();
        tester.testDecodeBob();
        tester.testCryptage();
        tester.testDecryptage();
        
        System.out.println("\n=============================================="
                + "\nRESULTAT"+
                "\n==============================================");
        System.out.println("Total : " + n + " tests réussie sur 8 Tests réaliser");
    }
}
