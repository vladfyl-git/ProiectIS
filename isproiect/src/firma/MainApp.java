package firma;

import firma.enums.RolAngajat;
import firma.enums.TipProdus;
import firma.model.*;

import java.util.ArrayList;
import java.util.List;

public class MainApp {
    private List<Produs> catalog = new ArrayList<>();
    private List<Angajat> angajati = new ArrayList<>();

    // Cerința b): Adăugare angajați (Doar Managerul)
    public void managerAdaugaAngajat(Angajat manager, Angajat angajatNou) {
        if (manager.getRol() == RolAngajat.MANAGER) {
            angajati.add(angajatNou);
            System.out.println("[MANAGER ACTION] " + angajatNou.getNume() + " a fost inrolat ca " + angajatNou.getRol());
        } else {
            System.out.println("[EROARE CONFIGURARE] Actiune permisa doar Managerului!");
        }
    }

    // Cerința b): Adăugare produse (Doar Seniorii)
    public void seniorAdaugaProdus(Angajat angajat, Produs p) {
        if (angajat.getRol() == RolAngajat.SENIOR) {
            catalog.add(p);
            System.out.println("[CATALOG UPDATE] Seniorul " + angajat.getNume() + " a adaugat: " + p.getNume());
        } else {
            System.out.println("[EROARE PERMISIUNE] Doar angajatii SENIORI au drept de scriere in catalog!");
        }
    }

    // Cerința a_promotii): Gestiune promoții (Seniorii adaugă/șterg, Juniorii doar văd)
    public void seniorSeteazaPromotie(Angajat angajat, Produs p, boolean status) {
        if (angajat.getRol() == RolAngajat.SENIOR) {
            p.setEsteLaPromotie(status);
            System.out.println("[PROMO MANAGER] Status promotie pentru " + p.getNume() + " setat la: " + status);
        } else {
            System.out.println("[EROARE PERMISIUNE] Angajatii juniori pot doar vizualiza promotiile.");
        }
    }

    // Cerința d): Vizualizare pe categorii
    public void vizualizareCatalogDupaCategorie(TipProdus categorie) {
        System.out.println("\n--- FILTRARE CATALOG: " + categorie + " ---");
        for (Produs p : catalog) {
            if (p.getCategorie() == categorie) {
                p.vizualizareDetalii();
            }
        }
    }

    public void vizualizarePromotiiActive() {
        System.out.println("\n--- CAMPANII PROMOTIONALE ACTIVE (10%) ---");
        for (Produs p : catalog) {
            if (p.isEsteLaPromotie()) {
                System.out.println("- " + p.getNume() + " | Pret Standard: " + p.getPret() + " RON");
            }
        }
    }

    // Cerința b): Schimbare status comenzi (Ambele categorii: Senior/Junior)
    public void onoreazaStatusComanda(Angajat angajat, Comanda comanda, String stare) {
        if (angajat.getRol() == RolAngajat.SENIOR || angajat.getRol() == RolAngajat.JUNIOR) {
            comanda.schimbaStatus(stare);
        } else {
            System.out.println("[EROARE PERMISIUNE] Acest tip de utilizator nu poate modifica fluxul de productie.");
        }
    }

    public static void main(String[] args) {
        MainApp app = new MainApp();

        // 1. Instanțiere Staff administrativ și tehnic
        Angajat sef = new Angajat(1, "manager_proiect", "root", "Alex Managerul", RolAngajat.MANAGER);
        Angajat senior = new Angajat(2, "ion_senior", "secv1", "Ion Seniorul", RolAngajat.SENIOR);
        Angajat junior = new Angajat(3, "dan_junior", "secv2", "Dan Juniorul", RolAngajat.JUNIOR);

        app.managerAdaugaAngajat(sef, senior);
        app.managerAdaugaAngajat(sef, junior);

        // 2. Definire și adăugare repere în Catalog
        Descriere desc1 = new Descriere("Sistem Preasamblat", "Desktop PC ideal pentru arhitectura si randari 3D complexe pe termen lung", 5);
        Produs workstation = new Produs(201, "Workstation Threadripper Pro", 12000.0f, 4, TipProdus.DESKTOP_PC, desc1);

        Descriere desc2 = new Descriere("Componenta", "Placa video de ultima generatie 16GB GDDR6X", 5);
        Produs placaVideo = new Produs(202, "NVIDIA RTX 4080 Super", 5500.0f, 8, TipProdus.DESKTOP_PC, desc2);

        app.seniorAdaugaProdus(senior, workstation);
        app.seniorAdaugaProdus(senior, placaVideo);

        // Activare campanie promoțională
        app.seniorSeteazaPromotie(senior, workstation, true);

        // Juniorul sau un vizitator anonim vizualizează catalogul
        app.vizualizareCatalogDupaCategorie(TipProdus.DESKTOP_PC);
        app.vizualizarePromotiiActive();

        // 3. Simulare Flux Client
        System.out.println("\n=== SCENARIU INTERACTIUNE CLIENT ===");
        Client client = new Client(1001, "vlad26", "parola", "Vlad Popa", "vlad@test.com", "Bucuresti", "0733444555");

        // Încercare plasare comandă fără autentificare (trebuie să eșueze conform regulii c)
        ComandaCumparare cosBlocat = new ComandaCumparare(5001);
        client.plaseazaComanda(cosBlocat);

        // Autentificare client
        if (client.login("vlad26", "parola")) {
            System.out.println("[AUTH] Bun venit, " + client.getNume() + "!");
        }

        // Creare coș și calculare cu discount automat inclus ca linie negativă în listă
        ComandaCumparare cosAprobat = new ComandaCumparare(6001);
        cosAprobat.adaugaArticol(workstation); // Cu discount
        cosAprobat.adaugaArticol(placaVideo);  // Preț întreg

        System.out.println("Total Net de Achitat calculat din cos: " + cosAprobat.calculeazaTotal() + " RON");

        Plata trx = new Plata(777, cosAprobat.calculeazaTotal(), "Ordin de Plata");
        if (trx.proceseazaPlata()) {
            client.plaseazaComanda(cosAprobat);
        }

        // Modificare stare flux producție de către Junior
        app.onoreazaStatusComanda(junior, cosAprobat, "In Asamblare / Testare");

        // 4. Depunere solicitare Service
        ComandaService tichetService = new ComandaService(9001, "0733444555", "Upgrade pasta termoconductoare si curatare praf", "20-06-2026");
        client.plaseazaComanda(tichetService);

        // Actualizare status Service de către Senior
        app.onoreazaStatusComanda(senior, tichetService, "In Lucru");
        app.onoreazaStatusComanda(senior, tichetService, "Finalizat");
    }
}