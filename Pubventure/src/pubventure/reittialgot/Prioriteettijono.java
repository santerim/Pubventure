package pubventure.reittialgot;

import Pubventure.ymparisto.Pubiobjekti;

/**
 *
 * @author Santeri
 */
public class Prioriteettijono {

    private EtaisyysComparator ec;
    private Pubiobjekti[] solmut;
    private int koko;
    private int pituus;

    public Prioriteettijono(int pituus, EtaisyysComparator ec) {
        this.ec = ec;
        this.solmut = new Pubiobjekti[pituus];
        this.pituus = pituus;
    }

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

    public void offer(Pubiobjekti solmu) {
        solmut[this.koko] = solmu;
        this.koko++;
        jarjesta();
    }

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
    
    private void siirraAlkiot() {
        int i = 1;
        while (solmut[i] != null) {
            solmut[i - 1] = solmut[i];
            i++;
        }
    }

    public void clear() {
        this.koko = 0;
        solmut = new Pubiobjekti[pituus];
    }

    public boolean isEmpty() {
        return this.koko == 0;
    }

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
