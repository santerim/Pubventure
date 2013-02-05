package OhHa.ymparisto;

import OhHa.Sijainti;
import OhHa.TiedostonLukija;
import OhHa.ihmiset.Asiakas;
import OhHa.ihmiset.Inehmo;
import OhHa.ihmiset.Sankari;
import OhHa.ihmiset.Tarjoilija;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * Luokka hallinnoi pelikenttää ja myös luo sen TiedostonLukija-luokan avulla,
 * sekä luo sille hahmot (pelaajan, asiakkaat, tarjoilijat, portsarit).
 * Se tarjoaa metodit em. toimintoja varten, sekä metodin pelikentän mittasuhteiden
 * selvittämiseen ja mahdollisten törmäystilanteiden selvittämiseen.
 */
public class Pubi {

    private int leveys;
    private int korkeus;
    private Pubiobjekti[][] kentta;
    private TiedostonLukija tiedostonLukija;
    private String pubiMerkkijonona;
    private Scanner lukija;
    private int asiakkaita;
    private ArrayList<Inehmo> inehmot = new ArrayList<>();
    private Random luku = new Random();
    private Inehmo sankari;

    public Pubi(int asiakkaita) {
        this.asiakkaita = asiakkaita;
        this.tiedostonLukija = new TiedostonLukija();
        this.pubiMerkkijonona = tiedostonLukija.lueTiedosto();
        
        etsiMittasuhteet();
        this.kentta = new Pubiobjekti[leveys][korkeus];

    }

    public final void etsiMittasuhteet() {
        this.lukija = new Scanner(pubiMerkkijonona);
        int riveja = 0;
        int riviKayty = 0;

        while (lukija.hasNext()) {
            String nykRivi = lukija.next();
            if (riviKayty == 0) {
                this.leveys = nykRivi.length();
                riviKayty = 1;
            }
            riveja++;
        }
        this.korkeus = riveja;
        this.lukija.close();
    }

    public Pubiobjekti[][] luoKentta() {
        this.lukija = new Scanner(pubiMerkkijonona);
        int rivi = 0;
        while (lukija.hasNext()) {
            String merkkirivi = lukija.next();
            //System.out.println("Skannerin sisältö: " + merkkirivi);
            for (int i = 0; i < merkkirivi.length(); i++) {
                switch (merkkirivi.charAt(i)) {
                    case 'Y':
                        kentta[i][rivi] = new Pubiobjekti(".", true);
                        break;
                    case 'E':
                        kentta[i][rivi] = new Pubiobjekti(".", false);
                        break;
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
                        kentta[i][rivi] = new Pubiobjekti("w", true);
                        break;
                }
            }
            rivi++;
        }
        return kentta;
    }

    public void luoOlennot() {
        // luodaan sankari ja laitetaan hänet inehmot-listan alkuun
        int itsetunto = luku.nextInt(100);
        this.sankari = new Sankari(new Sijainti(13, 2), "@", "sankari", true, itsetunto);
        this.inehmot.add(sankari);

        int asennePelaajaan = luku.nextInt(100);
        //luodaan tarjoilija
        this.inehmot.add(new Tarjoilija(new Sijainti(5, 2), "t", "tarjoilija", false, asennePelaajaan));

        // luodaan asiakkaat niin ettei niitä ole samoilla paikoilla
        // tai seinissä yms
        int asiakkaitaJaljella = this.asiakkaita;

        while (asiakkaitaJaljella > 0) {
            int uusiX = this.arvoX();
            int uusiY = this.arvoY();
            asennePelaajaan = luku.nextInt(100);

            if (!tormaako(uusiX, uusiY)) {
                inehmot.add(new Asiakas(new Sijainti(uusiX, uusiY), "a", "asiakas", true, asennePelaajaan));
                asiakkaitaJaljella--;
            }
        }
    }

    
    //jos annetuissa koordinaateissa on este tai jos niissä on joku hahmo, palauta true
    public boolean tormaako(int x, int y) {
        if (getObjekti(x, y).getEste()) {
            return true;
        }
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getSijainti().getX() == x && inehmo.getSijainti().getY() == y) {
                return true;
            }

        }
        return false;
    }
    
    public int arvoX() {
        return luku.nextInt(this.leveys);
    }

    public int arvoY() {
        return luku.nextInt(this.korkeus);
    }

    public Pubiobjekti getObjekti(int x, int y) {
        return kentta[x][y];
    }

    public int getLeveys() {
        return this.leveys;
    }

    public int getKorkeus() {
        return this.korkeus;
    }

    public ArrayList<Inehmo> getInehmot() {
        return this.inehmot;
    }

    public int getAsiakkaita() {
        return this.asiakkaita;
    }
}
