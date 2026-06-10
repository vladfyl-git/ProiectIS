package firma.test;

import firma.model.Descriere;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSlavuVlad {

    // Testarea clasa Descriere (testarea limitelor)

    @Test
    void testSetScorCumparatori_ValoareValida() {
        Descriere d = new Descriere("Tip", "Prezentare", 3);
        assertEquals(3, d.getScorCumparatori(), "Scorul ar trebui sa fie 3.");
    }

    @Test
    void testSetScorCumparatori_SubLimitaInferioara() {
        Descriere d = new Descriere("Tip", "Prezentare", -2); // Testare la limita
        assertEquals(1, d.getScorCumparatori(), "Scorul invalid sub 1 ar trebui sa devina 1 implicit.");
    }

    @Test
    void testSetScorCumparatori_PesteLimitaSuperioara() {
        Descriere d = new Descriere("Tip", "Prezentare", 7);
        assertEquals(1, d.getScorCumparatori(), "Scorul invalid peste 5 ar trebui sa devina 1 implicit.");
    }

    @Test
    void testLimitaTextPrezentare_Sub100Cuvinte() {
        Descriere d = new Descriere("Tip", "Un text scurt.", 5);
        assertEquals("Un text scurt.", d.getTextPrezentare(), "Textul scurt nu trebuie modificat.");
    }

    @Test
    void testLimitaTextPrezentare_Peste100Cuvinte() {
        StringBuilder textLung = new StringBuilder();
        for (int i = 0; i < 110; i++) textLung.append("cuvant ");

        Descriere d = new Descriere("Tip", textLung.toString(), 5);
        assertTrue(d.getTextPrezentare().endsWith("..."), "Textul care depaseste 100 de cuvinte trebuie sa se incheie cu '...'");
    }
}