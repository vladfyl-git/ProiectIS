package firma.model;
import java.util.Date;

public class Plata {
    private int id;
    private float suma;
    private String metoda;
    private String status;
    private Date dataPlatii;

    public Plata(int id, float suma, String metoda) {
        this.id = id;
        this.suma = suma;
        this.metoda = metoda;
        this.status = "In asteptare";
    }

    public boolean proceseazaPlata() {
        this.status = "Aprobata";
        this.dataPlatii = new Date();
        System.out.println("[SISTEM PLATI] Suma de " + suma + " RON decontata prin " + metoda);
        return true;
    }
}