package OhHa.ymparisto;

public class Pubi {

    private int leveys;
    private int korkeus;
    private Pubiobjekti[][] kentta;

    public Pubi(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.kentta = new Pubiobjekti[leveys][korkeus];
    }

    public int getLeveys() {
        return this.leveys;
    }

    public int getKorkeus() {
        return this.korkeus;
    }
}
