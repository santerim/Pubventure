package pubventure.ymparisto;

import java.util.Scanner;
import pubventure.Sijainti;
import pubventure.enumit.PubiobjektiEnum;

/**
 *
 * @author Santeri
 */
public class Kentanluoja {

    private Scanner lukija;
    private Pubiobjekti[][] kentta;

    public Kentanluoja(String pubiMerkkijonona, Pubiobjekti[][] kentta) {
        this.lukija = new Scanner(pubiMerkkijonona);
        this.kentta = kentta;
        luoKentta();
    }

    /**
     * Pubi-luokan antama merkkijono luetaan, ja sen pohjalta luodaan
     * pubiobjektit kaksiulotteiseen taulukkoon, joka tulee sisältämään
     * kaikki pelialueen ei-ihmisobjektit.
     *
     * Pubiobjektien saamat parametrit järjestyksessä: 1. Ulkonäkö, 2. onko
     * este, 3. Enum-tyyppi, 4. kuvaus, 5. sijainti, 6. hidastearvo
     *
     * @see Pubiobjekti
     * @see Pubi
     *
     * @return palauttaa valmiin pubiobjektitaulukon
     */
    private void luoKentta() {
        int rivi = 0;
        while (lukija.hasNext()) {
            String merkkirivi = lukija.next();
            //System.out.println("Skannerin sisältö: " + merkkirivi);
            for (int i = 0; i < merkkirivi.length(); i++) {
                Sijainti uusiSijainti = new Sijainti(i, rivi);
                switch (merkkirivi.charAt(i)) {
                    case 'Y':
                        kentta[rivi][i] = new Pubiobjekti("&nbsp;", false, PubiobjektiEnum.TALUE, "tarjoilijan alue", uusiSijainti, 1);
                        break;
                    case 'E':
                        kentta[rivi][i] = new Pubiobjekti("<font color='#2E8B57'>E</font>", false, PubiobjektiEnum.ULOSKAYNTI, "uloskäynti", uusiSijainti, 1);
                        break;
                    case '-':
                        kentta[rivi][i] = new Pubiobjekti("-", true, PubiobjektiEnum.SEINA, "seinä", uusiSijainti, 1000);
                        break;
                    case '.':
                        kentta[rivi][i] = new Pubiobjekti("&nbsp;", false, PubiobjektiEnum.LATTIA, "lattia", uusiSijainti, 1);
                        break;
                    case 'X':
                        kentta[rivi][i] = new Pubiobjekti("&nbsp;", true, PubiobjektiEnum.NAKYMATON, "TätäEiPitäisiNäkyä", uusiSijainti, 1000);
                        break;
                    case '|':
                        kentta[rivi][i] = new Pubiobjekti("|", true, PubiobjektiEnum.SEINA, "seinä", uusiSijainti, 1000);
                        break;
                    case '#':
                        kentta[rivi][i] = new Pubiobjekti("#", true, PubiobjektiEnum.POYTA, "pöytä", uusiSijainti, 1000);
                        break;
                    case 't':
                        kentta[rivi][i] = new Pubiobjekti("<font color='#8B4513'>¤</font>", false, PubiobjektiEnum.TUOLI, "tuoli", uusiSijainti, 2);
                        break;
                    case 'w':
                        kentta[rivi][i] = new Pubiobjekti("w", true, PubiobjektiEnum.WCPYTTY, "wc-pytty", uusiSijainti, 1000);
                        break;
                    case 'p':
                        kentta[rivi][i] = new Pubiobjekti("p", true, PubiobjektiEnum.PISUAARI, "pisuaari", uusiSijainti, 1000);
                        break;
                    case 'L':
                        kentta[rivi][i] = new Pubiobjekti("L", true, PubiobjektiEnum.LAVUAARI, "lavuaari", uusiSijainti, 1000);
                        break;
                    case 'v':
                        kentta[rivi][i] = new Pubiobjekti(" ", false, PubiobjektiEnum.VALUE, "lattia", uusiSijainti, 1);
                        break;
                    case 'J':
                        kentta[rivi][i] = new Pubiobjekti("<font color='#FFD700'>J</font>", true, PubiobjektiEnum.JUKEBOKSI, "jukeboksi", uusiSijainti, 1000);
                        break;
                    case 'B':
                        kentta[rivi][i] = new Pubiobjekti("B", true, PubiobjektiEnum.TISKI, "baaritiski", uusiSijainti, 1000);
                        break;
                    case 'o':
                        kentta[rivi][i] = new Pubiobjekti("<font color='#708090'>o</font>", false, PubiobjektiEnum.OVI, "ovi", uusiSijainti, 1);
                        break;
                    case 'M':
                        kentta[rivi][i] = new Pubiobjekti("&nbsp;", false, PubiobjektiEnum.MVESSA, "lattia", uusiSijainti, 1);
                        break;
                    case 'N':
                        kentta[rivi][i] = new Pubiobjekti("&nbsp;", false, PubiobjektiEnum.NVESSA, "lattia", uusiSijainti, 1);
                        break;
                }
            }
            rivi++;
        }
    }
}
