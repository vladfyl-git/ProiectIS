package firma.model;
import java.util.ArrayList;
import java.util.List;

public class Client extends Utilizator {
    private String nume;
    private String email;
    private String adresa;
    private String telefon;
    private List<Comanda> istoricComenzi;

    public Client(int id, String username, String parola, String nume, String email, String adresa, String telefon) {
        super(id, username, parola);
        this.nume = nume;
        this.email = email;
        this.adresa = adresa;
        this.telefon = telefon;
        this.istoricComenzi = new ArrayList<>();
    }

    public void plaseazaComanda(Comanda c) {
        if (!esteLogat) {
            System.out.println("[PERMISIUNE RESPINSA] Trebuie sa fiti autentificat pentru a plasa comenzi!");
            return;
        }
        istoricComenzi.add(c);
        System.out.println("[SUCCES] Cererea/Comanda cu ID " + c.getIdComanda() + " a fost plasata de " + nume);
    }

    public List<Comanda> vizualizareComenzi() { return istoricComenzi; }
    public String getNume() { return nume; }
    public String getEmail() { return email; }
}