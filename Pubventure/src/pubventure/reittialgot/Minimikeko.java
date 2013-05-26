
package pubventure.reittialgot;

import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Minimikeko-toteutus Astar- ja Dijkstra-luokkien käyttöön
 * @see Astar
 * @see Dijkstra
 */
public class Minimikeko {

    private Pubiobjekti[] keko;
    private int pituus;
    private int solmuja;
    
    public Minimikeko(int koko) {
        this.keko = new Pubiobjekti[koko];
    }
    
    /**
     * heap-insert -toteutus
     * @param uusi on kekoon lisättävä Pubiobjekti
     */
    public void lisaaKekoon(Pubiobjekti uusi) {
        solmuja++;
        int i = solmuja;
        while (i > 1 && keko[vanhempi(i)].getF() > uusi.getF()) {
            keko[i] = keko[vanhempi(i)];
            i = vanhempi(i);
        }
        keko[i] = uusi;
    }
    
    /**
     * heap-min -toteutus
     * @return palauttaa pienimmän f-arvon omaavan Pubiobjektin
     */
    public Pubiobjekti annaPienin() {
        return keko[0];
    }
    
    /**
     * heap-del-min -toteutus
     * @return palauttaa pienimmän f-arvon omaavan Pubiobjektin, poistaa sen
     * keosta, ja järjestää keon uudelleen
     */
    public Pubiobjekti annaJaPoistaPienin() {
        Pubiobjekti palautettava = keko[0];
        keko[0] = keko[solmuja - 1];
        solmuja--;
        jarjesta(0);
        return palautettava;
    }
    
    /**
     * heapify-toteutus
     * @param mista on indeksi mistä alkaen kekoa aletaan järjestää
     */
    private void jarjesta(int mista) {
        
    }
    
    /**
     * metodi palauttaa solmun vanhemman indeksinumeron keossa
     * @param sija on indeksinumero solmulle, jonka vanhempi halutaan saada
     * selville
     */
    private int vanhempi(int sija) {
        return ((sija + 1) / 2) - 1;
    }
    
    /**
     * metodi palauttaa solmun vasemman lapsen
     * @param sija on lastaan kaipaavan solmun indeksinumero keossa
     * @return palauttaa vasemman lapsen indeksinumeron
     */
    private int vasenLapsi(int sija) {
        return ((sija + 1) * 2) - 1;
    }
    
    /**
     * metodi palauttaa solmun oikean lapsen
     * @param sija on lastaan kaipaavan solmun indeksinumero keossa
     * @return palauttaa oikean lapsen indeksinumeron
     */
    private int oikeaLapsi(int sija) {
        return ((sija + 1) * 2);
    }
}
