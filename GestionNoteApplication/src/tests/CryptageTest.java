import GestionNoteApplication.src.main.java.package1.Cryptage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class CryptageTest {

    private Cryptage cryptage;

    @Before
    public void setUp() {
        cryptage = new Cryptage();
        Cryptage.remplissageduDico();
        Cryptage.creationCleEtape1();
    }

    @Test
    public void testExpModulaire() {
        assertEquals(8, cryptage.expModulaire(2, 3, 10));
        assertEquals(1, cryptage.expModulaire(5, 0, 10));
    }

    @Test
    public void testCodeAlice() {
        int g = 3;
        Cryptage.g = g;

        int codeAlice = cryptage.codeAliceEtBob();

        assertNotNull(codeAlice);
        int expectedA = cryptage.expModulaire(g, Cryptage.bOUa, Cryptage.p);
        assertEquals(expectedA, codeAlice);
    }

    @Test
    public void testCodeBob() {
        int g = 5;
        Cryptage.g = g;

        int codeBob = cryptage.codeAliceEtBob();

        assertNotNull(codeBob);
        int expectedB = cryptage.expModulaire(g, Cryptage.bOUa, Cryptage.p);
        assertEquals(expectedB, codeBob);
    }

    @Test
    public void testDecodeAlice() {
        int B = 5;
        int a = 3;

        int cleSecrete = cryptage.decodeAliceOuBOB(B, a);
        int expectedCleSecrete = cryptage.expModulaire(B, a, Cryptage.p);

        assertEquals(expectedCleSecrete, cleSecrete);
    }

    @Test
    public void testDecodeBob() {
        int A = 5;
        int b = 3;

        int cleSecrete = cryptage.decodeAliceOuBOB(A, b);
        int expectedCleSecrete = cryptage.expModulaire(A, b, Cryptage.p);

        assertEquals(expectedCleSecrete, cleSecrete);
    }

    @Test
    public void testCryptage() {
        String cle = "cle";
        String messageOriginal = "message";

        String messageCrypte = cryptage.cryptage(cle, messageOriginal);

        assertNotNull(messageCrypte);
        assertEquals("Message crypté devrait être différent du message original", messageOriginal.equals(messageCrypte),
                false);
    }

    @Test
    public void testDecryptage() {
        String cle = "cle";
        String messageOriginal = "message";

        String messageCrypte = cryptage.cryptage(cle, messageOriginal);
        String messageDecrypte = cryptage.decryptage(cle, messageCrypte);

        assertNotNull(messageDecrypte);
        assertEquals("Message décodé devrait être égal au message original", messageOriginal, messageDecrypte);
    }
}