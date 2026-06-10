package firma.model;
import java.util.Date;

public abstract class Comanda {
    protected int idComanda;
    protected Date dataComanda;
    protected String status;

    public Comanda(int idComanda) {
        this.idComanda = idComanda;
        this.dataComanda = new Date();
        this.status = "Inregistrata";
    }

    public void schimbaStatus(String nouStatus) {
        this.status = nouStatus;
        System.out.println("[STATUS] Comanda #" + idComanda + " a fost trecuta in starea: " + nouStatus);
    }

    public int getIdComanda() { return idComanda; }
    public String getStatus() { return status; }
    public abstract float calculeazaTotal();
}