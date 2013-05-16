
package pubventure.reittialgot;

import pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 * 
 * Tämä luokka toimii Javan valmiin PriorityQueue:n korvaajana
 */
public class Prioriteettijono {

    /**
     * Comparator-luokka jolla tutkitaan solmujen sijoitusta jonossa
     */
    private EtaisyysComparator ec;
    
    /**
     * Pubiobjekteista koostuva taulukko, jonka nollas on A*- ja Dijkstra-
     * toteutusten otollisin solmu
     */
    private Pubiobjekti[] solmut;
    
    /**
     * Taulukon alkoiden määrä. Käytetään määrittämään paljonko siinä todella
     * on alkioita, ettei yritetä poimia solmua tyhjältä paikalta.
     */
    private int koko;
    
    /**
     * Jonon pituus
     */
    private int pituus;

    public Prioriteettijono(int pituus, EtaisyysComparator ec) {
        this.ec = ec;
        this.solmut = new Pubiobjekti[pituus];
        this.pituus = pituus;
    }

    /**
     * Kuten PriorityQueue-luokassa.
     * 
     * @return palauttaa joko jonon ensimmäisen alkion, tai null-arvon jos
     * edellinen ei onnistu
     */
    public Pubiobjekti poll() {
        if (solmut[0] != null) {
            Pubiobjekti palautettava = solmut[0];
            solmut[0] = null;
            jarjesta();
            this.koko--;
            return palautettava;
        } else {
            return null;
        }

    }

    /**
     * Kuten PriorityQueue-luokassa.
     * @param solmu on jonoon sijoitettava solmu
     */
    public void offer(Pubiobjekti solmu) {
        solmut[this.koko] = solmu;
        this.koko++;
        jarjesta();
    }

    /**
     * Järjestää solmut suuruusjärjestykseen käyttäen hyväksi EtaisyysComparator-
     * luokkaa. Pienimmän f-arvon saanut tulee ensimmäiseksi, eli sellainen
     * solmu, jonka laskettu etäisyys sekä lähtöön että maaliin on pienin.
     */
    private void jarjesta() {
        int a = 0;
        int b = 1;
        while (true) {
            if (solmut[b] == null || (solmut[a] == null && solmut[b] == null)) {
                break;
            }
            if (solmut[a] == null) {
                siirraAlkiot();
            }
            if (ec.compare(solmut[a], solmut[b]) == 1) {
                Pubiobjekti c = solmut[a];
                solmut[a] = solmut[b];
                solmut[b] = c;
            }
            a++;
            b++;
        }
    }
    
    /**
     * Jarjesta-metodin apumetodi.
     */
    private void siirraAlkiot() {
        int i = 1;
        while (solmut[i] != null) {
            solmut[i - 1] = solmut[i];
            i++;
        }
    }

    /**
     * Nollaa taulukon tiedettyjen alkoiden lukumäärän, sekä luo solmut-
     * muuttujalle uuden taulukko-objektin.
     */
    public void clear() {
        this.koko = 0;
        solmut = new Pubiobjekti[pituus];
    }

    public boolean isEmpty() {
        return this.koko == 0;
    }

    /**
     * Metodi kertoo josko solmut-taulukossa on jokin tietty Pubiobjekti.
     * @param po on haettava Pubiobjekti
     * @return palauttaa true, mikäli haettu objekti löytyi
     */
    public boolean contains(Pubiobjekti po) {
        if (this.koko > 0) {
            for (int i = 0; i < koko; i++) {
                if (solmut[i] != null) {
                    if (solmut[i].equals(po)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
