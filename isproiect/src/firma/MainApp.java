package firma;

import firma.enums.RolAngajat;
import firma.enums.TipProdus;
import firma.model.*;

import java.util.ArrayList;
import java.util.List;

public class MainApp {
    private List<Produs> catalog = new ArrayList<>();
    private List<Angajat> angajati = new ArrayList<>();

    //Adaugare angajați
    public void managerAdaugaAngajat(Angajat manager, Angajat angajatNou) {
        if (manager.getRol() == RolAngajat.MANAGER) {
            angajati.add(angajatNou);
            System.out.println("[MANAGER ACTION] " + angajatNou.getNume() + " a fost inrolat ca " + angajatNou.getRol());
        } else {
            System.out.println("[EROARE CONFIGURARE] Actiune permisa doar Managerului!");
        }
    }

    //Adaugare produse
    public void seniorAdaugaProdus(Angajat angajat, Produs p) {
        if (angajat.getRol() == RolAngajat.SENIOR) {
            catalog.add(p);
            System.out.println("[CATALOG UPDATE] Seniorul " + angajat.getNume() + " a adaugat: " + p.getNume());
        } else {
            System.out.println("[EROARE PERMISIUNE] Doar angajatii SENIORI au drept de scriere in catalog!");
        }
    }

    // Promotii
    public void seniorSeteazaPromotie(Angajat angajat, Produs p, boolean status) {
        if (angajat.getRol() == RolAngajat.SENIOR) {
            p.setEsteLaPromotie(status);
            System.out.println("[PROMO MANAGER] Status promotie pentru " + p.getNume() + " setat la: " + status);
        } else {
            System.out.println("[EROARE PERMISIUNE] Angajatii juniori pot doar vizualiza promotiile.");
        }
    }

    //Vizualizare categorii
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

    // Status comanda
    public void onoreazaStatusComanda(Angajat angajat, Comanda comanda, String stare) {
        if (angajat.getRol() == RolAngajat.SENIOR || angajat.getRol() == RolAngajat.JUNIOR) {
            comanda.schimbaStatus(stare);
        } else {
            System.out.println("[EROARE PERMISIUNE] Acest tip de utilizator nu poate modifica fluxul de productie.");
        }
    }

    public static void main(String[] args) {
        MainApp app = new MainApp();

        // staff
        Angajat sef = new Angajat(1, "manager_proiect", "root", "Alex Managerul", RolAngajat.MANAGER);
        Angajat senior = new Angajat(2, "ion_senior", "secv1", "Ion Seniorul", RolAngajat.SENIOR);
        Angajat junior = new Angajat(3, "dan_junior", "secv2", "Dan Juniorul", RolAngajat.JUNIOR);

        app.managerAdaugaAngajat(sef, senior);
        app.managerAdaugaAngajat(sef, junior);

        // adaugare produse
        Descriere desc1 = new Descriere("Sistem Preasamblat", "Desktop PC ideal pentru arhitectura si randari 3D complexe pe termen lung", 5);
        Produs workstation = new Produs(201, "Workstation Threadripper Pro", 12000.0f, 4, TipProdus.DESKTOP_PC, desc1);

        Descriere desc2 = new Descriere("Componenta", "Placa video de ultima generatie 16GB GDDR6X", 5);
        Produs placaVideo = new Produs(202, "NVIDIA RTX 4080 Super", 5500.0f, 8, TipProdus.DESKTOP_PC, desc2);

        app.seniorAdaugaProdus(senior, workstation);
        app.seniorAdaugaProdus(senior, placaVideo);

        // promotie
        app.seniorSeteazaPromotie(senior, workstation, true);

        // vizualizare
        app.vizualizareCatalogDupaCategorie(TipProdus.DESKTOP_PC);
        app.vizualizarePromotiiActive();

        // simulare client
        System.out.println("\n=== SCENARIU INTERACTIUNE CLIENT ===");
        Client client = new Client(1001, "vlad26", "parola", "Vlad Popa", "vlad@test.com", "Bucuresti", "0733444555");

        // plasere comanda fara cont
        ComandaCumparare cosBlocat = new ComandaCumparare(5001);
        client.plaseazaComanda(cosBlocat);

        // autentificare
        if (client.login("vlad26", "parola")) {
            System.out.println("[AUTH] Bun venit, " + client.getNume() + "!");
        }

        // cos si discount
        ComandaCumparare cosAprobat = new ComandaCumparare(6001);
        cosAprobat.adaugaArticol(workstation);
        cosAprobat.adaugaArticol(placaVideo);

        System.out.println("Total Net de Achitat calculat din cos: " + cosAprobat.calculeazaTotal() + " RON");

        Plata trx = new Plata(777, cosAprobat.calculeazaTotal(), "Ordin de Plata");
        if (trx.proceseazaPlata()) {
            client.plaseazaComanda(cosAprobat);
        }

        // modificare stare
        app.onoreazaStatusComanda(junior, cosAprobat, "In Asamblare / Testare");

        // solicitare service
        ComandaService tichetService = new ComandaService(9001, "0733444555", "Upgrade pasta termoconductoare si curatare praf", "20-06-2026");
        client.plaseazaComanda(tichetService);

        // stare service
        app.onoreazaStatusComanda(senior, tichetService, "In Lucru");
        app.onoreazaStatusComanda(senior, tichetService, "Finalizat");
    }
}