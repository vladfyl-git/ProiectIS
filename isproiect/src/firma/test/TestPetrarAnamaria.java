package firma.test;

import firma.enums.TipProdus;
import firma.model.ComandaCumparare;
import firma.model.Descriere;
import firma.model.Produs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPetrarAnamaria {

    private Descriere descriereTest;
    private Produs produsTest;

    @BeforeEach
    void setUp() {
        descriereTest = new Descriere("Componenta", "Test", 4);
        produsTest = new Produs(100, "ProdusTest", 100.0f, 5, TipProdus.PERIFERICE, descriereTest);
    }

    // ========================================================================
    //Testarea cos cumparaturi (ComandaCumparare)


    @Test
    void testAdaugaArticol_ScadeStocul() {
        ComandaCumparare comanda = new ComandaCumparare(1);
        int stocInitial = 5;
        comanda.adaugaArticol(produsTest);
        assertFalse(produsTest.esteInStoc(stocInitial), "Stocul ar trebui sa se reduca dupa adaugarea in cos.");
    }

    @Test
    void testAdaugaArticol_StocInsuficient() {
        ComandaCumparare comanda = new ComandaCumparare(1);
        Produs pZeroStoc = new Produs(101, "FaraStoc", 50f, 0, TipProdus.DESKTOP_PC, descriereTest);
        comanda.adaugaArticol(pZeroStoc);
        assertEquals(0, comanda.getListaArticole().size(), "Produsul nu trebuie adaugat daca stocul este 0.");
    }

    @Test
    void testCalculeazaTotal_FaraPromotie() {
        ComandaCumparare comanda = new ComandaCumparare(1);
        comanda.adaugaArticol(produsTest); // Costa 100
        assertEquals(100.0f, comanda.calculeazaTotal(), "Totalul ar trebui sa fie suma produselor simple.");
    }

    @Test
    void testAplicaPromotie_AdaugareArticolVirtual() {
        ComandaCumparare comanda = new ComandaCumparare(1);
        produsTest.setEsteLaPromotie(true);
        comanda.adaugaArticol(produsTest);

        // Verifica daca s-au adaugat DE FAPT doua articole: produsul normal si cel de promotie (-10%)
        assertEquals(2, comanda.getListaArticole().size(), "Promotia ar trebui sa adauge un articol de discount in lista.");
    }

    @Test
    void testCalculeazaTotal_CuPromotie() {
        ComandaCumparare comanda = new ComandaCumparare(1);
        produsTest.setEsteLaPromotie(true); // Reducere de 10%
        comanda.adaugaArticol(produsTest);
        // Pretul era 100. Cu promotie 10% se adauga un articol cu pret de -10. Total: 90
        assertEquals(90.0f, comanda.calculeazaTotal(), "Totalul trebuie sa scada cu 10% cand promotia e aplicata.");
    }
}