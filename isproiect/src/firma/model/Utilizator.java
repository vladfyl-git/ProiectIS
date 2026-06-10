package firma.model;

public abstract class Utilizator {
    protected int id;
    protected String username;
    protected String parola;
    protected boolean esteLogat; // [cite: 4]

    public Utilizator(int id, String username, String parola) {
        this.id = id;
        this.username = username;
        this.parola = parola;
        this.esteLogat = false;
    }

    public boolean login(String user, String pass) { // [cite: 7]
        if (this.username.equals(user) && this.parola.equals(pass)) {
            this.esteLogat = true;
            return true;
        }
        return false;
    }

    public void logout() {
        this.esteLogat = false;
    }

    public boolean isEsteLogat() {
        return esteLogat;
    }

    public String getUsername() {
        return username;
    }
}