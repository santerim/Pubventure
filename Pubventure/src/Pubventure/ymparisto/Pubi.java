package Pubventure.ymparisto;

import Pubventure.Sijainti;
import Pubventure.TiedostonLukija;
import Pubventure.enumit.InehmoEnum;
import Pubventure.enumit.PubiobjektiEnum;
import Pubventure.ihmiset.Asiakas;
import Pubventure.ihmiset.Inehmo;
import Pubventure.ihmiset.Portsari;
import Pubventure.ihmiset.Sankari;
import Pubventure.ihmiset.Tarjoilija;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * Luokka hallinnoi pelikenttää ja myös luo sen TiedostonLukija-luokan avulla,
 * sekä luo sille hahmot (pelaajan, asiakkaat, tarjoilijat, portsarit). Se
 * tarjoaa metodit em. toimintoja varten, sekä metodin pelikentän mittasuhteiden
 * ja mahdollisten törmäystilanteiden selvittämiseen.
 */
public class Pubi {

    private int leveys;
    private int korkeus;
    private Pubiobjekti[][] kentta;
    private TiedostonLukija tiedostonLukija;
    private String pubiMerkkijonona;
    private Scanner lukija;
    private int asiakkaita;
    private ArrayList<Inehmo> inehmot = new ArrayList<Inehmo>();
    private Random luku = new Random();
    private Inehmo sankari;

    public Pubi(int asiakkaita) {
        this.asiakkaita = asiakkaita;
        this.tiedostonLukija = new TiedostonLukija();
        this.pubiMerkkijonona = tiedostonLukija.lueTiedosto();

        etsiMittasuhteet();
        this.kentta = new Pubiobjekti[leveys][korkeus];

    }

    /**
     * Etsii kentän mittasuhteet, jotta niitä voidaan tarpeen vaatiessa käyttää
     * helposti muissa metodeissa.
     */
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

    /**
     * Tiedostonlukija-olion pub.txt-tiedostosta antama merkkijono käsitellään
     * skannerin avulla ja luodaan kutakin merkkiä vastaava pubiobjekti.
     * Näistä koostetaan kaksiulotteinen taulukko.
     *
     * @return palauttaa valmiin pubiobjektitaulukon
     */
    public Pubiobjekti[][] luoKentta() {
        this.lukija = new Scanner(pubiMerkkijonona);
        int rivi = 0;
        while (lukija.hasNext()) {
            String merkkirivi = lukija.next();
            //System.out.println("Skannerin sisältö: " + merkkirivi);
            for (int i = 0; i < merkkirivi.length(); i++) {
                switch (merkkirivi.charAt(i)) {
                    case 'Y':
                        kentta[i][rivi] = new Pubiobjekti("&nbsp;", false, PubiobjektiEnum.TALUE);
                        break;
                    case 'E':
                        kentta[i][rivi] = new Pubiobjekti("<font color='#2E8B57'>E</font>", false, PubiobjektiEnum.ULOSKAYNTI);
                        break;
                    case '-':
                        kentta[i][rivi] = new Pubiobjekti("-", true, PubiobjektiEnum.SEINA);
                        break;
                    case '.':
                        kentta[i][rivi] = new Pubiobjekti("&nbsp;", false, PubiobjektiEnum.LATTIA);
                        break;
                    case 'X':
                        kentta[i][rivi] = new Pubiobjekti(" ", true, PubiobjektiEnum.NAKYMATON);
                        break;
                    case '|':
                        kentta[i][rivi] = new Pubiobjekti("|", true, PubiobjektiEnum.SEINA);
                        break;
                    case '#':
                        kentta[i][rivi] = new Pubiobjekti("#", true, PubiobjektiEnum.POYTA);
                        break;
                    case 't':
                        kentta[i][rivi] = new Pubiobjekti("<font color='#8B4513'>¤</font>", false, PubiobjektiEnum.TUOLI);
                        break;
                    case 'w':
                        kentta[i][rivi] = new Pubiobjekti("w", true, PubiobjektiEnum.WCPYTTY);
                        break;
                    case 'p':
                        kentta[i][rivi] = new Pubiobjekti("p", true, PubiobjektiEnum.PISUAARI);
                        break;
                    case 'L':
                        kentta[i][rivi] = new Pubiobjekti("L", true, PubiobjektiEnum.LAVUAARI);
                        break;
                    case 'v':
                        kentta[i][rivi] = new Pubiobjekti(" ", false, PubiobjektiEnum.VALUE);
                        break;
                    case 'J':
                        kentta[i][rivi] = new Pubiobjekti("<font color='#87CEFA'>J</font>", true, PubiobjektiEnum.JUKEBOKSI);
                        break;
                    case 'B':
                        kentta[i][rivi] = new Pubiobjekti("B", true, PubiobjektiEnum.TISKI);
                        break;
                    case 'o':
                        kentta[i][rivi] = new Pubiobjekti("<font color='#708090'>o</font>", false, PubiobjektiEnum.OVI);
                        break;
                    case 'M':
                        kentta[i][rivi] = new Pubiobjekti("&nbsp;", false, PubiobjektiEnum.MVESSA);
                        break;
                    case 'N':
                        kentta[i][rivi] = new Pubiobjekti("&nbsp;", false, PubiobjektiEnum.NVESSA);
                        break;
                }
            }
            rivi++;
        }
        return kentta;
    }

    /**
     * Luo pubissa olevat ihmiset. Sankari asetetaan uloskäyntiin ikäänkuin
     * vasta paikalle saapuneeksi. Tarjoilija luodaan sellaisen pubiobjektin
     * kohdalle, joka on tarjoilijoita varten tarkoitettu. Asiakkaat luodaan
     * vapaalle lattia- alueelle.
     */
    public void luoHahmot() {

        // luodaan sankari ja laitetaan hänet inehmot-listan alkuun
        Sijainti uusiSijainti = etsiPubiobjekti(PubiobjektiEnum.ULOSKAYNTI);
        this.inehmot.add(luoInehmo(InehmoEnum.SANKARI, uusiSijainti));

        //luodaan tarjoilija
        uusiSijainti = etsiPubiobjekti(PubiobjektiEnum.TALUE);
        this.inehmot.add(luoInehmo(InehmoEnum.TARJOILIJA, uusiSijainti));

        // luodaan asiakkaat niin ettei niitä ole samoilla paikoilla toistensa
        // kanssa, tai minkään esteeksi määritellyn pubiobjektin kohdalla
        int asiakkaitaJaljella = this.asiakkaita;

        while (asiakkaitaJaljella > 0) {
            uusiSijainti = arvoSijainti();

            //mikäli arvotuissa koordinaateissa ei ole estettä, tai mikäli se ei
            //ole tarjoilijoille varattua aluetta, luodaan siihen uusi asiakas
            if (!tormaako(uusiSijainti)) {
                if (!getObjekti(uusiSijainti).getTyyppi().equals(PubiobjektiEnum.TALUE)) {
                    inehmot.add(luoInehmo(InehmoEnum.ASIAKAS, uusiSijainti));
                    asiakkaitaJaljella--;
                }
            }
        }
    }

    /**
     * Etsii annetun tyyppisen pubiobjekti-luokan olion ja palauttaa sen
     * sijainnin
     *
     * @param haettuTyyppi metodian kutsutaan sillä tyypillä, mitä ollaan
     * etsimässä
     * @return palautetaan joko pubiobjekti, tai null, mikäli haluttua tyyppiä
     * ei löytynyt
     */
    public Sijainti etsiPubiobjekti(PubiobjektiEnum haettuTyyppi) {
        for (int y = 0; y < korkeus; y++) {
            for (int x = 0; x < leveys; x++) {
                if (kentta[x][y].getTyyppi() == haettuTyyppi) {
                    return new Sijainti(x, y);
                }
            }
        }
        System.out.println("Virhe: Pubiobjektia ei löytynyt.");
        return null;
    }

    /**
     * Luo hahmon peliin annettujen parametrien mukaan
     *
     * @param tyyppi InehmoEnum-luokan enum
     * @param sijainti
     * @return
     */
    public Inehmo luoInehmo(InehmoEnum tyyppi, Sijainti sijainti) {
        switch (tyyppi) {
            case SANKARI:
                return new Sankari(sijainti, "@", InehmoEnum.SANKARI, true);
            case ASIAKAS:
                return new Asiakas(sijainti, "a", InehmoEnum.ASIAKAS, true);
            case PORTSARI:
                return new Portsari(sijainti, "P", InehmoEnum.PORTSARI, false);
            case TARJOILIJA:
                return new Tarjoilija(sijainti, "t", InehmoEnum.TARJOILIJA, true);
        }
        return null;
    }

    /**
     * Tutkitaan onko annetuissa koordinaateissa este
     *
     * @param x on leveyssuuntainen koordinaatti
     * @param y on korkeussuuntainen koordinaatti
     * @return palauttaa true, mikäli parametrien mukaisessa paikassa on este,
     * muuten false
     */
    public boolean tormaako(Sijainti sijainti) {
        if (getObjekti(sijainti).getEste()) {
            return true;
        }
        for (Inehmo inehmo : inehmot) {
            if (inehmo.getSijainti().getX() == sijainti.getX() 
                    && inehmo.getSijainti().getY() == sijainti.getY()) {
                return true;
            }

        }
        return false;
    }

    /**
     * Toimii luokan satunnaislukugeneraattorina
     *
     * @param maksimi on maksimiarvo, jonka satunnaisluku voi saada
     * @return palauttaa arvotun kokonaisluvun
     */
    public int arvoLuku(int maksimi) {
        return luku.nextInt(maksimi);
    }

    public Sijainti arvoSijainti() {
        int x = arvoLuku(leveys - 1);
        int y = arvoLuku(korkeus - 1);
        return new Sijainti(x, y);
    }
    
    //kaksi alempaa metodia poistumassa
    public int arvoX() {
        return luku.nextInt(this.leveys);
    }

    public int arvoY() {
        return luku.nextInt(this.korkeus);
    }

    public Pubiobjekti getObjekti(Sijainti sijainti) {
        return kentta[sijainti.getX()][sijainti.getY()];
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
