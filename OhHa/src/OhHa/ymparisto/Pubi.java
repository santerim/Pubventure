package OhHa.ymparisto;

import OhHa.TiedostonLukija;
import java.util.Scanner;

public class Pubi {

    private int leveys;
    private int korkeus;
    private Pubiobjekti[][] kentta;
    private TiedostonLukija tiedostonLukija;
    private String luolanUlkonako;
    private Scanner lukija;

    public Pubi(int leveys, int korkeus) {
        this.leveys = leveys;
        this.korkeus = korkeus;
        this.kentta = new Pubiobjekti[leveys][korkeus];
        this.tiedostonLukija = new TiedostonLukija();
        this.luolanUlkonako = tiedostonLukija.lueTiedosto();
        this.lukija = new Scanner(luolanUlkonako);
    }

    public Pubiobjekti[][] luoKentta() {
        
        int rivi = 0;
        while (lukija.hasNext()) {
            String merkkirivi = lukija.next();
            //System.out.println("Skannerin sisältö: " + merkkirivi);
            for (int i = 0; i < merkkirivi.length(); i++) {
                switch (merkkirivi.charAt(i)) {
                    case 'E':
                        kentta[i][rivi] = new Pubiobjekti(".", false);
                    
                    case '-':
                        kentta[i][rivi] = new Pubiobjekti("-", true);
                        break;
                    case '.':
                        kentta[i][rivi] = new Pubiobjekti(".", false);
                        break;
                    case 'X':
                        kentta[i][rivi] = new Pubiobjekti(" ", true);
                        break;
                    case '|':
                        kentta[i][rivi] = new Pubiobjekti("|", true);
                        break;
                    case '#':
                        kentta[i][rivi] = new Pubiobjekti("#", true);
                        break;
                    case 'w':
                        kentta[i][rivi] = new Pubiobjekti("w", false);
                }
            }
            rivi++;
        }
        return kentta;
    }

    public Pubiobjekti annaObjekti(int x, int y) {
        return kentta[x][y];
    }
    
    public int getLeveys() {
        return this.leveys;
    }

    public int getKorkeus() {
        return this.korkeus;
    }
}
