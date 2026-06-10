package firma.model;

public class ComandaService extends Comanda {
    private String dateContact;
    private String dateProblema;
    private String datePredare;

    public ComandaService(int idComanda, String dateContact, String dateProblema, String datePredare) {
        super(idComanda);
        this.dateContact = dateContact;
        this.dateProblema = dateProblema;
        this.datePredare = datePredare;
    }

    @Override
    public float calculeazaTotal() {
        // Costul se stabilește ulterior evaluării tehnice fizice
        return 0.0f;
    }

    public String getDateContact() { return dateContact; }
    public String getDateProblema() { return dateProblema; }
    public String getDatePredare() { return datePredare; }
}