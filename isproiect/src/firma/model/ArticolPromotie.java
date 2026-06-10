package firma.model;

public class ArticolPromotie extends Produs {
    private float reducereAplicata;

    public ArticolPromotie(String numeProdusBaza, float pretProdusBaza) {
        // Creează un articol virtual cu preț negativ (reducere de 10%)
        super(-1, "Promotie (10%): " + numeProdusBaza, -(pretProdusBaza * 0.10f), 9999, null, null);
        this.reducereAplicata = pretProdusBaza * 0.10f;
    }

    public float getReducereAplicata() {
        return reducereAplicata;
    }
}