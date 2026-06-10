package firma.model;
import firma.enums.RolAngajat;

public class Angajat extends Utilizator {
    private String nume;
    private RolAngajat rol;

    public Angajat(int id, String username, String parola, String nume, RolAngajat rol) {
        super(id, username, parola);
        this.nume = nume;
        this.rol = rol;
    }

    public RolAngajat getRol() { return rol; }
    public String getNume() { return nume; }
}