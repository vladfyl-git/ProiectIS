package firma.model;

public class Descriere {
    private String tipComponenta;
    private String textPrezentare;
    private int scorCumparatori;

    public Descriere(String tipComponenta, String textPrezentare, int scorCumparatori) {
        this.tipComponenta = tipComponenta;
        this.textPrezentare = limiteazaText(textPrezentare, 100);
        setScorCumparatori(scorCumparatori);
    }

    private String limiteazaText(String text, int maxCuvinte) {
        if (text == null) return "";
        String[] cuvinte = text.split("\\s+");
        if (cuvinte.length <= maxCuvinte) return text;

        StringBuilder textLimitat = new StringBuilder();
        for (int i = 0; i < maxCuvinte; i++) {
            textLimitat.append(cuvinte[i]).append(" ");
        }
        return textLimitat.toString().trim() + "...";
    }

    public void setScorCumparatori(int scorCumparatori) {
        if (scorCumparatori >= 1 && scorCumparatori <= 5) {
            this.scorCumparatori = scorCumparatori;
        } else {
            this.scorCumparatori = 1;
        }
    }

    public String getTipComponenta() { return tipComponenta; }
    public String getTextPrezentare() { return textPrezentare; }
    public int getScorCumparatori() { return scorCumparatori; }
}