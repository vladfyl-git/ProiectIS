package firma.test;

import firma.model.Client;
import firma.model.ComandaCumparare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestOniceanuDalia {

    private Client clientTest;

    @BeforeEach
    void setUp() {
        clientTest = new Client(1, "user1", "pass1", "Ion", "ion@test.com", "Adresa 1", "0700");
    }

    //Testarea autentificare si client

    @Test
    void testLogin_DateCorecte() {
        boolean rezultat = clientTest.login("user1", "pass1");
        assertTrue(rezultat, "Login-ul ar trebui sa reuseasca pentru credentiale corecte.");
        assertTrue(clientTest.isEsteLogat(), "Starea clientului trebuie sa devina logat.");
    }

    @Test
    void testLogin_DateIncorecte() {
        boolean rezultat = clientTest.login("user1", "parolaGresita");
        assertFalse(rezultat, "Login-ul ar trebui sa esueze pentru parola gresita.");
        assertFalse(clientTest.isEsteLogat(), "Starea clientului trebuie sa ramana nelogat.");
    }

    @Test
    void testLogout_ReseteazaStarea() {
        clientTest.login("user1", "pass1");
        clientTest.logout();
        assertFalse(clientTest.isEsteLogat(), "Dupa logout, starea trebuie sa fie falsa.");
    }

    @Test
    void testPlaseazaComanda_ClientNelogat() {
        ComandaCumparare c = new ComandaCumparare(1);
        clientTest.plaseazaComanda(c);
        assertEquals(0, clientTest.vizualizareComenzi().size(), "O comanda nu poate fi plasata daca clientul nu e logat.");
    }

    @Test
    void testPlaseazaComanda_ClientLogat() {
        clientTest.login("user1", "pass1");
        ComandaCumparare c = new ComandaCumparare(1);
        clientTest.plaseazaComanda(c);
        assertEquals(1, clientTest.vizualizareComenzi().size(), "Comanda ar trebui sa fie inregistrata in istoric.");
    }
}