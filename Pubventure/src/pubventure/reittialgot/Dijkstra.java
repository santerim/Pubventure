
package pubventure.reittialgot;

import pubventure.ymparisto.Pubi;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Dijkstra-algoritmin toteutus. Tämä on toteutettu muokkaamalla A*-
 * algoritmia siten, että sen etäisyysarvio käsiteltävästä solmusta maaliin
 * on nolla.
 * 
 * Tämän luokan kommentointi on vielä kesken, mutta metodit ovat valtaosin
 * identtiset jo kommentoidun Astar-luokan kanssa.
 */
public class Dijkstra {

    /**
     * Tutkitut solmut.
     */
    private Minimikeko tutkitut;
    /**
     * Tutkittavat solmut.
     */
    private Minimikeko avoimet;
    /**
     * Logiikka-luokalta saadaan viite Pubi-luokkaan, jota tarvitaan sen
     * tarjoamiin metodeihin kiinnipääsemiseksi
     */
    private Pubi pubi;
    /**
     * Etsittäessä reittiä meillä on jokin mistä etsintää lähdetään
     * suorittamaan. Tässä tapauksessa se on jokin Pubiobjekti-luokan
     * olio
     */
    private Pubiobjekti lahto;
    /**
     * Etsittävä reitti päättyy maaliin
     */
    private Pubiobjekti maali;
    /**
     * Pubi-luokka tarjoaa metodin, jolla saadaan pelikenttä kaksiulotteiseen
     * taulukkoon. Tämän avulla voidaan käydä kenttää läpi tarpeen vaatiessa
     * käyttämällä sisäkkäisiä for-silmukoita.
     */
    private Pubiobjekti[][] kentta;
    /**
     * Kun reitti lähtöpisteestä kohteeseen on löydetty, se otetaan talteen
     * Pubiobjekteista koostuvaan taulukkoon.
     */
    private Pubiobjekti[] reitti;
    /**
     * Pelikentän leveys
     */
    private int leveys;
    /**
     * Pelikentän korkeus
     */
    private int korkeus;
    /**
     * Debuggaus-apumuuttuja, jolla lasketaan käsiteltyjä solmuja
     * (pubiobjekteja).
     */
    private int kasiteltyja;
    /**
     * Debuggaus-apumuuttuja, jolla pidetään kirjaa siitä, montako solmua
     * (pubiobjektia) löydetyssä reitissä on.
     */
    private int reitinSolmuja;
    
    /**
     * nämä kaksi muuttujaa pitävät sisällään x- ja y-koordinaatit, joita
     * tarvitaan tutkittaessa pubiobjektin naapurit
     * @see #kasitteleViereiset(pubventure.ymparisto.Pubiobjekti)  
     */
    private int[] vierusYt = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] vierusXt = {-1, 0, 1, -1, 1, -1, 0, 1};

    public Dijkstra(Pubi pubi) {
        this.pubi = pubi;
        this.leveys = pubi.getLeveys();
        this.korkeus = pubi.getKorkeus();
        this.avoimet = new Minimikeko(500);
        this.tutkitut = new Minimikeko(500);
        this.kentta = pubi.getKentta();
        this.reitti = new Pubiobjekti[37];
    }

    /**
     * Metodi etsii reitin annettujen pisteiden välillä.
     * @param lahto on lähtöpiste
     * @param maali on haettavan reitin määränpää
     * @return palauttaa valmiin reitin, tai null mikäli reittiä ei voi
     * muodostaa
     */
    public Pubiobjekti[] etsiReitti(Pubiobjekti lahto, Pubiobjekti maali) {
        reitinhaunAlkutoimet(lahto, maali);

        while (avoimet.getSolmujenLKM() > 0) {
            Pubiobjekti nykyinen = (Pubiobjekti) avoimet.annaJaPoistaPienin();
            nykyinen.setAvoimissa(false);
            if (nykyinen.equals(maali)) {
                return reitti = muodostaReitti(nykyinen);
            }

            kasitteleViereiset(nykyinen);
            tutkitut.lisaaKekoon(nykyinen);
            nykyinen.setTutkituissa(true);
        }
        return null;
    }
    
    /**
     * Metodi suorittaa reitinhaun alkutoimet puhdistamalla keot ja asettamalla
     * muuttujat lähtöarvoihinsa.
     * @param lahto on reitinhaun lähtösolmu
     * @param maali on reitinhaun maalisolmu
     */
    private void reitinhaunAlkutoimet(Pubiobjekti lahto, Pubiobjekti maali) {
        this.avoimet.nollaa();
        this.tutkitut.nollaa();
        this.kasiteltyja = 0;
        this.reitinSolmuja = 0;
        this.reitti = new Pubiobjekti[50];
        this.lahto = lahto;
        this.maali = maali;
        nollaaViittauksetEdellisiin();
        nollaaKuulumisetKekoihin();

        asetaFGjaHArvot(lahto);
        avoimet.lisaaKekoon(lahto);
        lahto.setAvoimissa(true);
    }

    /**
     * Mikäli etsiReitti-metodi löysi maalin, tämä metodi koostaa reitin ja
     * muuttaa sitä vastaavien Pubiobjektien ulkonäön punaisiksi tähdiksi.
     * 
     * @param mista on reitin päätepiste (maali, tai maalin viereinen
     * Pubiobjekti, mikäli pelin hahmot eivät voi liikkua sen päälle)
     * 
     * @return palauttaa reitin Pubiobjekteista koostuvana taulukkona
     */
    private Pubiobjekti[] muodostaReitti(Pubiobjekti mista) {
        Pubiobjekti nykyinen = mista;
        int i = 0;
        while (nykyinen.getEdellinen() != null) {
            reitti[i] = nykyinen;
            this.reitinSolmuja++;
            if (i != 0) {
                reitti[i].setVAUlkonako("<font color='#FF0000'>*</font>");
            }
            nykyinen = nykyinen.getEdellinen();
            i++;
        }
        return reitti;
    }

    private int laskeF(Pubiobjekti minka) {
        return minka.getG() + minka.getH();
    }

    private int laskeG(Pubiobjekti mista) {
        Pubiobjekti kasiteltava = mista;
        int matka = 0;
        while (kasiteltava.getEdellinen() != null) {
            matka += kasiteltava.getHidastearvo();
            kasiteltava = kasiteltava.getEdellinen();
        }
        return matka;
    }

    private void asetaFGjaHArvot(Pubiobjekti lahto) {
        for (int i = 0; i < this.korkeus; i++) {
            for (int j = 0; j < this.leveys; j++) {
                if (kentta[i][j].equals(lahto)) {
                    kentta[i][j].setG(0);
                    kentta[i][j].setH(0);
                    kentta[i][j].setF(0);
                } else {
                    kentta[i][j].setG(1000);
                    kentta[i][j].setH(0);
                    kentta[i][j].setF(1000);
                }
            }
        }
    }

    /**
     * Metodi käsittelee tutkittavan solmun naapurit kasitteleViereinen-
     * apumetodia hyväksikäyttäen.
     * @param minka on tutkittava solmu
     */
    private void kasitteleViereiset(Pubiobjekti minka) {
//        System.out.println("Käsitellään viereiset");
        
        // otetaan koodin selkeyden vuoksi pubiobjektin x- ja y-koordinaatit
        // talteen
        int x = minka.getX();
        int y = minka.getY();
        
        // mikäli kyse ei ole pelialueen laidalla olevasta objektista, käydään
        // sen naapurit läpi. Laitaobjektit eivät joka tapauksessa kuulu
        // varsinaiseen pelialueeseen, joten tästä ei aiheudu mitään haittaa.
        if (x > 0 && x < leveys && y > 0 && y < korkeus) {
            for (int i = 0; i < vierusYt.length; i++) {
                Pubiobjekti kohde = kentta[y + vierusYt[i]]
                                          [x + vierusXt[i]];
                kasitteleViereinen(minka, kohde);
            }
        }
    }

    /**
     * Käsittelee annetun solmun viereisen solmun
     * @param minka on em. annettu solmu
     * @param viereinen on se viereinen
     */
    private void kasitteleViereinen(Pubiobjekti minka, Pubiobjekti viereinen) {
        if (viereinen != null) {
            if (viereinen.getEste() && !viereinen.equals(maali)) {
                return;
            }
            if (viereinen.equals(maali)) {
                vieruskasittely_1_Maali(minka, viereinen);
            }
            if (!viereinen.getAvoimissa() && !viereinen.getTutkituissa()) {
                vieruskasittely_2_Uusi(minka, viereinen);
            } else {
                vieruskasittely_3_Muu(minka, viereinen);
            }
        }
    }
    
    /**
     * kasitteleViereinen-metodi kutsuu tätä, mikäli käsiteltävä solmu on
     * maalisolmu
     * 
     * @param minka on solmu jonka vierussolmua käsitellään
     * @param viereinen on käsiteltävä vierussolmu
     * 
     * @see #kasitteleViereinen(pubventure.ymparisto.Pubiobjekti, pubventure.ymparisto.Pubiobjekti) 
     */
    private void vieruskasittely_1_Maali(Pubiobjekti minka, Pubiobjekti viereinen) {
        viereinen.setF(0);
        viereinen.setEdellinen(minka);
        avoimet.lisaaKekoon(viereinen);
        viereinen.setAvoimissa(true);
    }
    
    /**
     * kasitteleViereinen-metodi kutsuu tätä, mikäli käsiteltävä solmu ei ole
     * avoimissa tai tutkituissa
     * 
     * @param minka on solmu jonka vierussolmua käsitellään
     * @param viereinen on käsiteltävä vierussolmu
     * 
     * @see #kasitteleViereinen(pubventure.ymparisto.Pubiobjekti, pubventure.ymparisto.Pubiobjekti) 
     */
    private void vieruskasittely_2_Uusi(Pubiobjekti minka, Pubiobjekti viereinen) {
        viereinen.setEdellinen(minka);
        viereinen.setG(laskeG(viereinen));
        viereinen.setF(laskeF(viereinen));
        viereinen.setVAUlkonako(".");
        avoimet.lisaaKekoon(viereinen);
        viereinen.setAvoimissa(true);
    }
    
    /**
     * kasitteleViereinen-metodi kutsuu tätä, mikäli käsiteltävä solmu on joko
     * avoimissa tai tutkituissa
     * 
     * @param minka on solmu jonka vierussolmua käsitellään
     * @param viereinen on käsiteltävä vierussolmu
     * 
     * @see #kasitteleViereinen(pubventure.ymparisto.Pubiobjekti, pubventure.ymparisto.Pubiobjekti) 
     */
    private void vieruskasittely_3_Muu(Pubiobjekti minka, Pubiobjekti viereinen) {
        if (minka.getG() + viereinen.getHidastearvo() < viereinen.getG()) {
            viereinen.setEdellinen(minka);
            viereinen.setG(laskeG(viereinen));
            viereinen.setF(laskeF(viereinen));
        }
    }

    private void nollaaViittauksetEdellisiin() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                kentta[i][j].setEdellinen(null);
            }
        }
    }
    
    private void nollaaKuulumisetKekoihin() {
        for (int i = 0; i < pubi.getKorkeus(); i++) {
            for (int j = 0; j < pubi.getLeveys(); j++) {
                kentta[i][j].setAvoimissa(false);
                kentta[i][j].setTutkituissa(false);
            }
        }
    }
}