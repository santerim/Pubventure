
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

    Minimikeko tutkitut;
    Minimikeko avoimet;
    Pubi pubi;
    Pubiobjekti lahto;
    Pubiobjekti maali;
    Pubiobjekti[][] kentta;
    Pubiobjekti[] reitti;
    int leveys;
    int korkeus;
    int kasiteltyja;
    int reitinSolmuja;
    
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

    public Pubiobjekti[] etsiReitti(Pubiobjekti lahto, Pubiobjekti maali) {
        this.avoimet.nollaa();
        this.tutkitut.nollaa();
        this.kasiteltyja = 0;
        this.reitinSolmuja = 0;
        this.reitti = new Pubiobjekti[37];
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