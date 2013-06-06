
package pubventure.reittialgot;

import pubventure.ymparisto.Pubi;
import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * A*-algoritmin toteutus
 */
public class Astar {

    /**
     * Tutkitut solmut.
     */
    Minimikeko tutkitut;
    
    /**
     * Tutkittavat solmut.
     */
    Minimikeko avoimet;
    
    /**
     * Logiikka-luokalta saadaan viite Pubi-luokkaan, jota tarvitaan sen
     * tarjoamiin metodeihin kiinnipääsemiseksi
     */
    Pubi pubi;
    
    /**
     * Etsittäessä reittiä meillä on jokin mistä etsintää lähdetään
     * suorittamaan. Tässä tapauksessa se on jokin Pubiobjekti-luokan
     * olio
     */
    Pubiobjekti lahto;
    
    /**
     * Etsittävä reitti päättyy maaliin
     */
    Pubiobjekti maali;
    
    /**
     * Pubi-luokka tarjoaa metodin, jolla saadaan pelikenttä kaksiulotteiseen
     * taulukkoon. Tämän avulla voidaan käydä kenttää läpi tarpeen vaatiessa
     * käyttämällä sisäkkäisiä for-silmukoita.
     */
    Pubiobjekti[][] kentta;
    
    /**
     * Kun reitti lähtöpisteestä kohteeseen on löydetty, se otetaan talteen
     * Pubiobjekteista koostuvaan taulukkoon.
     */
    Pubiobjekti[] reitti;
    
    /**
     * Pelikentän leveys
     */
    int leveys;
    
    /**
     * Pelikentän korkeus
     */
    int korkeus;
    
    /**
     * Debuggaus-apumuuttuja, jolla lasketaan käsiteltyjä solmuja
     * (pubiobjekteja).
     */
    int kasiteltyja;
    
    /**
     * Debuggaus-apumuuttuja, jolla pidetään kirjaa siitä, montako solmua
     * (pubiobjektia) löydetyssä reitissä on.
     */
    int reitinSolmuja;
    
    /**
     * nämä kaksi muuttujaa pitävät sisällään x- ja y-koordinaatit, joita
     * tarvitaan tutkittaessa pubiobjektin naapurit
     * @see #kasitteleViereiset(pubventure.ymparisto.Pubiobjekti)  
     */
    private int[] vierusYt = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] vierusXt = {-1, 0, 1, -1, 1, -1, 0, 1};

    public Astar(Pubi pubi) {
        this.pubi = pubi;
        this.leveys = pubi.getLeveys();
        this.korkeus = pubi.getKorkeus();
        this.avoimet = new Minimikeko(500);
        this.tutkitut = new Minimikeko(500);
        this.kentta = pubi.getKentta();
    }

    /**
     * Metodi etsii reitin annettujen pisteiden välillä.
     * @param lahto on lähtöpiste
     * @param maali on haettavan reitin määränpää
     * @return palauttaa valmiin reitin, tai null mikäli reittiä ei voi
     * muodostaa
     */
    public Pubiobjekti[] etsiReitti(Pubiobjekti lahto, Pubiobjekti maali) {
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

        while (avoimet.getSolmujenLKM() > 0) {
            Pubiobjekti nykyinen = (Pubiobjekti) avoimet.annaJaPoistaPienin();
            nykyinen.setAvoimissa(false);
            kasiteltyja++;
//            System.out.println(kasiteltyja);
            if (nykyinen.equals(maali)) {
                return reitti = muodostaReitti(nykyinen);
            }

            kasitteleViereiset(nykyinen);
            tutkitut.lisaaKekoon(nykyinen);
            nykyinen.setTutkituissa(true);
        }
//        System.out.println("Käsiteltiin " + kasiteltyja + " solmua.");
//        System.out.println("Reittiä ei löydy.");
        return null;
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
//        System.out.println("Muodostetaan reitti");
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
//        System.out.println("Käsiteltiin " + kasiteltyja + " solmua.\nReitin pituus " + this.reitinSolmuja + " solmua.");
        return reitti;
    }

    /**
     * Laskee solmun f-arvon, eli summan etäisyydestä lähtöön ja maaliin
     * @param minka
     * @return palauttaa lasketun arvon kokonaislukuna
     */
    private int laskeF(Pubiobjekti minka) {
        return minka.getG() + minka.getH();
    }

    /**
     * Laskee solmun g-arvon, eli etäisyyden lähtöön.
     * @param mista on g-arvoa kaipaava solmu
     * @return palauttaa g-arvon kokonaislukuna
     */
    private int laskeG(Pubiobjekti mista) {
        Pubiobjekti kasiteltava = mista;
        int matka = 0;
        while (kasiteltava.getEdellinen() != null) {
            matka += kasiteltava.getHidastearvo();
            kasiteltava = kasiteltava.getEdellinen();
        }
        return matka;
    }

    /**
     * Laskee solmun h-arvon, eli arvioidun etäisyyden maaliin.
     * @param mista on solmu jonka h-arvoa kaivataan
     * @param mihin on maali
     * @return palauttaa h-arvon kokonaislukuna
     */
    private int laskeH(Pubiobjekti mista, Pubiobjekti mihin) {
        return Math.max(
                Math.abs(mista.getX() - mihin.getX()),
                Math.abs(mista.getY() - mihin.getY()));
    }

    /**
     * Asettaa solmuille f-, g- ja h-arvot
     * @param lahto on etsittävän reitin lähtöpiste, jolle asetetaan erikseen
     * g-arvoksi 0.
     */
    private void asetaFGjaHArvot(Pubiobjekti lahto) {
//        System.out.println("Asetetaan F, G ja H -arvot");
        for (int i = 0; i < this.korkeus; i++) {
            for (int j = 0; j < this.leveys; j++) {
                int h = laskeH(kentta[i][j], maali);
                if (kentta[i][j].equals(lahto)) {
                    kentta[i][j].setG(0);
                    kentta[i][j].setH(h);
                    kentta[i][j].setF(h);
                } else {
                    kentta[i][j].setG(1000);
                    kentta[i][j].setH(h);
                    kentta[i][j].setF(h + 1000);
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
                viereinen.setF(0);
                viereinen.setEdellinen(minka);
                avoimet.lisaaKekoon(viereinen);
                viereinen.setAvoimissa(true);
            }
            
            if (!viereinen.getAvoimissa() && !viereinen.getTutkituissa()) {
                viereinen.setEdellinen(minka);
                viereinen.setG(laskeG(viereinen));
                viereinen.setF(laskeF(viereinen));
                viereinen.setVAUlkonako(".");
                avoimet.lisaaKekoon(viereinen);
                viereinen.setAvoimissa(true);
            } else {
                if (minka.getG() + viereinen.getHidastearvo() < viereinen.getG()) {
                    viereinen.setEdellinen(minka);
                    viereinen.setG(laskeG(viereinen));
                    viereinen.setF(laskeF(viereinen));
                }
            }
//            kasiteltyja++;
//            kl.piirraAlue();
        }
    }

    /**
     * Metodi nollaa solmujen (Pubiobjektien) muuttujat, jotka viittaavat
     * reitin etsinnässä siihen solmuun, mistä kyseiseen tultiin.
     */
    private void nollaaViittauksetEdellisiin() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                kentta[i][j].setEdellinen(null);
            }
        }
    }
    
    public Pubiobjekti[] getReitti() {
        return this.reitti;
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