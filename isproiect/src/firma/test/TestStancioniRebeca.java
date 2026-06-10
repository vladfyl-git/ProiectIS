package firma.test;

import firma.MainApp;
import firma.enums.RolAngajat;
import firma.enums.TipProdus;
import firma.model.Angajat;
import firma.model.Descriere;
import firma.model.Produs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestStancioniRebeca {

    private MainApp sistemTest;
    private Angajat managerTest;
    private Angajat seniorTest;
    private Angajat juniorTest;
    private Produs produsTest;

    @BeforeEach
    void setUp() {
        sistemTest = new MainApp();
        managerTest = new Angajat(2, "man1", "pass", "Manager", RolAngajat.MANAGER);
        seniorTest = new Angajat(3, "sen1", "pass", "Senior", RolAngajat.SENIOR);
        juniorTest = new Angajat(4, "jun1", "pass", "Junior", RolAngajat.JUNIOR);

        Descriere descriereTest = new Descriere("Componenta", "Test", 4);
        produsTest = new Produs(100, "ProdusTest", 100.0f, 5, TipProdus.PERIFERICE, descriereTest);
    }

    @Test
    void testManagerAdaugaAngajat_PermisiuneAcordata() {
        Angajat angajatNou = new Angajat(5, "nou", "123", "Nou", RolAngajat.JUNIOR);
        assertDoesNotThrow(() -> sistemTest.managerAdaugaAngajat(managerTest, angajatNou), "Managerul trebuie sa poata adauga angajati fara erori.");
    }

    @Test
    void testJuniorAdaugaAngajat_PermisiuneRespinsa() {
        Angajat angajatNou = new Angajat(5, "nou", "123", "Nou", RolAngajat.JUNIOR);
        sistemTest.managerAdaugaAngajat(juniorTest, angajatNou);
        assertNotNull(juniorTest, "Juniorul nu are voie sa adauge angajati; sistemul ar trebui sa respinga logica intern.");
    }

    @Test
    void testSeniorAdaugaProdus_PermisiuneAcordata() {
        assertDoesNotThrow(() -> sistemTest.seniorAdaugaProdus(seniorTest, produsTest), "Seniorul are dreptul sa adauge produse in catalog.");
    }

    @Test
    void testSeniorActiveazaPromotie_PermisiuneAcordata() {
        sistemTest.seniorSeteazaPromotie(seniorTest, produsTest, true);
        assertTrue(produsTest.isEsteLaPromotie(), "Seniorul trebuie sa poata seta promotia la TRUE.");
    }

    @Test
    void testJuniorActiveazaPromotie_PermisiuneRespinsa() {
        sistemTest.seniorSeteazaPromotie(juniorTest, produsTest, true);
        assertFalse(produsTest.isEsteLaPromotie(), "Juniorul nu trebuie sa poata schimba starea de promotie a produsului.");
    }
}