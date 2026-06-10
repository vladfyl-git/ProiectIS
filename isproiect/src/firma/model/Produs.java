package firma.model;
import firma.enums.TipProdus;

public class Produs {
    private int id;
    private String nume;
    private float pret;
    private int stoc;
    private TipProdus categorie;
    private Descriere detalii;
    private boolean esteLaPromotie;

    public Produs(int id, String nume, float pret, int stoc, TipProdus categorie, Descriere detalii) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
        this.stoc = stoc;
        this.categorie = categorie;
        this.detalii = detalii;
        this.esteLaPromotie = false;
    }

    public void vizualizareDetalii() {
        System.out.println("Produs: " + nume + " | Pret: " + pret + " RON | Categorie: " + categorie);
        if (detalii != null) {
            System.out.println(" -> Tip: " + detalii.getTipComponenta());
            System.out.println(" -> Descriere: " + detalii.getTextPrezentare());
            System.out.println(" -> Rating: " + detalii.getScorCumparatori() + " stele");
        }
    }

    public boolean esteInStoc(int cantitate) {
        return this.stoc >= cantitate;
    }

    public void reduStoc(int cantitate) {
        this.stoc -= cantitate;
    }

    public int getId() { return id; }
    public String getNume() { return nume; }
    public float getPret() { return pret; }
    public TipProdus getCategorie() { return categorie; }
    public boolean isEsteLaPromotie() { return esteLaPromotie; }
    public void setEsteLaPromotie(boolean esteLaPromotie) { this.esteLaPromotie = esteLaPromotie; }
}