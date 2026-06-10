package firma.model;
import java.util.ArrayList;
import java.util.List;

public class ComandaCumparare extends Comanda {
    private List<Produs> listaArticole;

    public ComandaCumparare(int idComanda) {
        super(idComanda);
        this.listaArticole = new ArrayList<>();
    }

    public void adaugaArticol(Produs p) {
        if (p.esteInStoc(1)) {
            listaArticole.add(p);
            p.reduStoc(1);
            System.out.println(" -> Adaugat in cos: " + p.getNume() + " (" + p.getPret() + " RON)");

            if (p.isEsteLaPromotie()) {
                aplicaPromotie(p);
            }
        } else {
            System.out.println("[STOC INSUFICIENT] Produsul " + p.getNume() + " nu este disponibil.");
        }
    }

    private void aplicaPromotie(Produs p) {
        ArticolPromotie discount = new ArticolPromotie(p.getNume(), p.getPret());
        listaArticole.add(discount);
        System.out.println("    [PROMO] S-a atasat automat discountul: " + discount.getPret() + " RON");
    }

    @Override
    public float calculeazaTotal() {
        float total = 0;
        for (Produs p : listaArticole) {
            total += p.getPret();
        }
        return total;
    }

    public List<Produs> getListaArticole() { return listaArticole; }
}