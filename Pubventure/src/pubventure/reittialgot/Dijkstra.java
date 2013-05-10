
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
 */
public class Dijkstra {

    Prioriteettijono tutkitut;
    Prioriteettijono avoimet;
    EtaisyysComparator ec;
    Pubi pubi;
    Pubiobjekti lahto;
    Pubiobjekti maali;
    Pubiobjekti[][] kentta;
    Pubiobjekti[] reitti;
    int leveys;
    int korkeus;
    int kasiteltyja;
    int reitinSolmuja;

    public Dijkstra(Pubi pubi) {
        this.pubi = pubi;
        this.leveys = pubi.getLeveys();
        this.korkeus = pubi.getKorkeus();
        this.ec = new EtaisyysComparator();
        this.avoimet = new Prioriteettijono(500, ec);
        this.tutkitut = new Prioriteettijono(500, ec);
        this.kentta = pubi.getKentta();
        this.reitti = new Pubiobjekti[37];
    }

    public Pubiobjekti[] etsiReitti(Pubiobjekti lahto, Pubiobjekti maali) {
        this.avoimet.clear();
        this.tutkitut.clear();
        this.kasiteltyja = 0;
        this.reitinSolmuja = 0;
        this.reitti = new Pubiobjekti[37];
        this.lahto = lahto;
        this.maali = maali;
        nollaaViittauksetEdellisiin();

        asetaFGjaHArvot(lahto);
        avoimet.offer(lahto);

        while (!avoimet.isEmpty()) {
            Pubiobjekti nykyinen = (Pubiobjekti) avoimet.poll();
            if (nykyinen.equals(maali)) {
                return reitti = muodostaReitti(nykyinen);
            }

            kasitteleViereiset(nykyinen);
            tutkitut.offer(nykyinen);
        }
//        System.out.println("Käsiteltiin " + kasiteltyja + " solmua.");
//        System.out.println("Reittiä ei löydy.");
        return null;
    }

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

    private int laskeH(Pubiobjekti mista, Pubiobjekti mihin) {
        return Math.max(
                Math.abs(mista.getX() - mihin.getX()),
                Math.abs(mista.getY() - mihin.getY()));
    }

    private void asetaFGjaHArvot(Pubiobjekti lahto) {
//        System.out.println("Asetetaan F, G ja H -arvot");
        for (int i = 0; i < this.korkeus; i++) {
            for (int j = 0; j < this.leveys; j++) {
//                int h = laskeH(kentta[i][j], maali);
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

    private void kasitteleViereiset(Pubiobjekti minka) {
//        System.out.println("Käsitellään viereiset");
        if (minka.getX() > 0 && minka.getY() > 0) {
            Pubiobjekti kohde = kentta[minka.getY() - 1][minka.getX() - 1];
            kasitteleViereinen(minka, kohde);
        }
        if (minka.getY() > 0) {
            Pubiobjekti kohde = kentta[minka.getY() - 1][minka.getX()];
            kasitteleViereinen(minka, kohde);
        }
        if (minka.getX() < leveys - 1 && minka.getY() > 0) {
            Pubiobjekti kohde = kentta[minka.getY() - 1][minka.getX() + 1];
            kasitteleViereinen(minka, kohde);
        }
        if (minka.getX() > 0) {
            Pubiobjekti kohde = kentta[minka.getY()][minka.getX() - 1];
            kasitteleViereinen(minka, kohde);
        }
        if (minka.getX() < leveys - 1) {
            Pubiobjekti kohde = kentta[minka.getY()][minka.getX() + 1];
            kasitteleViereinen(minka, kohde);
        }
        if (minka.getX() > 0 && minka.getY() < korkeus - 1) {
            Pubiobjekti kohde = kentta[minka.getY() + 1][minka.getX() - 1];
            kasitteleViereinen(minka, kohde);
        }
        if (minka.getY() < korkeus - 1) {
            Pubiobjekti kohde = kentta[minka.getY() + 1][minka.getX()];
            kasitteleViereinen(minka, kohde);
        }
        if (minka.getX() < leveys - 1 && minka.getY() < korkeus - 1) {
            Pubiobjekti kohde = kentta[minka.getY() + 1][minka.getX() + 1];
            kasitteleViereinen(minka, kohde);
        }
    }

    private void kasitteleViereinen(Pubiobjekti minka, Pubiobjekti viereinen) {
        if (viereinen != null) {
            if (viereinen.getEste() && !viereinen.equals(maali)) {
                return;
            }
            if (viereinen.equals(maali)) {
                viereinen.setF(0);
                viereinen.setEdellinen(minka);
                avoimet.offer(viereinen);
            }

            if (!avoimet.contains(viereinen) && !tutkitut.contains(viereinen)) {
                viereinen.setEdellinen(minka);
                viereinen.setG(laskeG(viereinen));
                viereinen.setF(laskeF(viereinen));
                viereinen.setVAUlkonako(".");
                avoimet.offer(viereinen);
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

    private void nollaaViittauksetEdellisiin() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                kentta[i][j].setEdellinen(null);
            }
        }
    }
}