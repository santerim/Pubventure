package pubventure.reittialgot;

import pubventure.ymparisto.Pubiobjekti;

/**
 * @author Santeri
 *
 * Minimikeko-toteutus Astar- ja Dijkstra-luokkien käyttöön, toimien näiden
 * prioriteettijonona.
 * @see Astar
 * @see Dijkstra
 */
public class Minimikeko {

    /**
     * Keko on käytännössä pubiobjekteista koostuva taulukko
     */
    private Pubiobjekti[] keko;
    
    /**
     * Keon maksimikoko.
     */
    private int koko;
    
    /**
     * Tämä pitää kirjaa siitä, montako pubiobjektia kekoon varsinaisesti
     * kuuluu. Keon sisällään pitävässä taulukossa voi hyvin olla kekoon
     * kuulumattomia objekteja.
     */
    private int solmuja;

    /**
     * Konstruktori, joka luo kekotaulukon annetun parametrin laajuiseksi.
     */
    public Minimikeko(int koko) {
        this.koko = koko;
        this.keko = new Pubiobjekti[koko];
    }

    /**
     * heap-insert -toteutus
     *
     * @param uusi on kekoon lisättävä Pubiobjekti
     */
    public void lisaaKekoon(Pubiobjekti uusi) {
        solmuja++;
        int i = solmuja - 1;
        while (i > 0 && keko[vanhempi(i)].getF() > uusi.getF()) {
            keko[i] = keko[vanhempi(i)];
            i = vanhempi(i);
        }
        keko[i] = uusi;
    }

    /**
     * heap-min -toteutus
     *
     * @return palauttaa pienimmän f-arvon omaavan Pubiobjektin
     */
    public Pubiobjekti annaPienin() {
        if (solmuja > 0) {
            return keko[0];
        } else {
            return null;
        }

    }

    /**
     * heap-del-min -toteutus
     *
     * @return palauttaa pienimmän f-arvon omaavan Pubiobjektin, poistaa sen
     * keosta, ja järjestää keon uudelleen
     */
    public Pubiobjekti annaJaPoistaPienin() {
        if (solmuja > 0) {
            Pubiobjekti palautettava = keko[0];
            keko[0] = keko[solmuja - 1];
            solmuja--;
            jarjesta(0);
            return palautettava;
        } else {
            return null;
        }

    }

    /**
     * heapify-toteutus
     * Järjestää keon rekursiivisesti halutusta solmusta alkaen.
     *
     * @param mista on indeksi mistä alkaen kekoa aletaan järjestää
     */
    private void jarjesta(int mista) {
        int vasen = vasenLapsi(mista);
        int oikea = oikeaLapsi(mista);
        int pienempi;
        
        if (oikea < solmuja) {
            if (keko[vasen].getF() > keko[oikea].getF()) {
                pienempi = oikea;
            } else {
                pienempi = vasen;
            }
            if (keko[mista].getF() > keko[pienempi].getF()) {
                vaihdaPaikseen(mista, pienempi);
                jarjesta(pienempi);
            }
        } else if (vasen == solmuja && keko[mista].getF() > keko[vasen].getF()) {
            vaihdaPaikseen(mista, vasen);
        }
    }
    
    /**
     * Vaihtaa keon kahden pubiobjektin paikkaa keskenään
     */
    public void vaihdaPaikseen(int mika, int mihin) {
        Pubiobjekti valiaikainen = keko[mika];
        keko[mika] = keko[mihin];
        keko[mihin] = valiaikainen;
    }
    
    /**
     * metodi palauttaa solmun vanhemman indeksinumeron keossa
     *
     * @param sija on indeksinumero solmulle, jonka vanhempi halutaan saada
     * selville
     */
    public int vanhempi(int sija) {
        return ((sija + 1) / 2) - 1;
    }

    /**
     * metodi palauttaa solmun vasemman lapsen
     *
     * @param sija on lastaan kaipaavan solmun indeksinumero keossa
     * @return palauttaa vasemman lapsen indeksinumeron
     */
    public int vasenLapsi(int sija) {
        return ((sija + 1) * 2) - 1;
    }

    /**
     * metodi palauttaa solmun oikean lapsen
     *
     * @param sija on lastaan kaipaavan solmun indeksinumero keossa
     * @return palauttaa oikean lapsen indeksinumeron
     */
    public int oikeaLapsi(int sija) {
        return ((sija + 1) * 2);
    }
    
    /**
     * Palauttaa keon. Tätä käytetään nykyisellään vain keon toimivuuden
     * testaamisessa
     */
    public Pubiobjekti[] getKeko() {
        return this.keko;
    }
    
    /**
     * Niinikään keon testaamisessa käytettävä metodi. Antaa keon solmujen
     * lukumäärän.
     */
    public int getSolmujenLKM() {
        return this.solmuja;
    }
    
    /**
     * Aloittaa keon luomisen alusta. Tarpeellinen haettaessa reittiä
     * toistuvasti, jotta vanhat haut eivät johda virheellisiin tuloksiin.
     */
    public void nollaa() {
        this.keko = new Pubiobjekti[koko];
        this.solmuja = 0;
    }
}
